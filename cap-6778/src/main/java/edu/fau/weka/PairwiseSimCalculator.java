package edu.fau.weka;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.Ranker;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import edu.fau.weka.dao.PairwiseSimResultsRepository;
import edu.fau.weka.model.FeatureLists;
import edu.fau.weka.model.PairwiseSimCalcParams;
import edu.fau.weka.model.PairwiseSimResults;
import edu.fau.weka.model.RawFeatureList;
import edu.fau.weka.service.ArffFileService;
import edu.fau.weka.service.AttributeSelectionEvaluationService;
import edu.fau.weka.service.FeatureListService;
import edu.fau.weka.service.PairwiseSimResultsService;
import edu.fau.weka.service.PairwiseSimResultsServiceImpl;
import edu.fau.weka.service.RawFeatureListService;
@Component
public class PairwiseSimCalculator {
	static final Logger LOG = LoggerFactory.getLogger(MatlabToArffConverter.class);
	@Autowired
	private PairwiseSimCalcParams params;
	@Autowired
	private ArffFileService arffSvc;
	@Autowired
	private AttributeSelectionEvaluationService attributeSelEvalSvc;
	@Autowired
	private PairwiseSimResultsService pairwiseSimSvc;
	@Autowired
	private RawFeatureListService rawFeatureSvc;
	public void calculatePairwiseSim(){
		for (String dirName: params.getOutputDirNames()){
			arffSvc.setInputDirectory(dirName);
			List<Map<Object, List<List<Double>>>> partitionedInstances = new ArrayList<Map<Object, List<List<Double>>>>();
			String uuid = UUID.randomUUID().toString();
			double peturbLevel=0;
			//making the assumption that all files returned from arffSvc
			//have the same noise level
			if (arffSvc.hasMoreFiles()){
				while(arffSvc.hasMoreFiles()){
					try {
						Instances data = arffSvc.getNextData();
						peturbLevel = parsePeturbLevel(data.relationName());
						saveFeatureSelection(data, peturbLevel, uuid);
						partitionedInstances.add(partitonData(data));
						LOG.debug(data.relationName());
						
					} catch (IOException e) {
						LOG.debug("i/o exception when reading arff file");
					}
				}
				
				double avgSim = PairwiseSim.avgPairwiseSim(partitionedInstances);
				PairwiseSimResults results = new PairwiseSimResults();
				results.setDataSetName(dirName);
				results.setSampleSize(partitionedInstances.get(0).values().size());
				results.setNumFolds(partitionedInstances.size());
				results.setAvgPairwiseSimilarity(avgSim);
				results.setPeturbLevel(peturbLevel);
				results.setExperimentUuid(uuid);
				pairwiseSimSvc.saveResults(results);
				
				LOG.debug("dataset dir: " + dirName + " avgSim: " + avgSim);
			} else{
				LOG.debug("no arff files for directory " + dirName);
			}
		}
	}
	//peturb level should be saved in relation name
	//otherwise this will not work
	//examples of relation names
	//CLL-SUB-111-0.0-0.arff
	//GLI-85-0.0-0.arff
	//peturb level is second number
	private double parsePeturbLevel(String relationName) {
		Pattern p = Pattern.compile("\\d+\\-(\\d+\\.\\d+)\\-");
		Matcher m = p.matcher(relationName);
		if (m.find()){
			try {
				return Double.parseDouble(m.group(1));
			} catch(NumberFormatException e){
				LOG.debug("unable to get noise level from relation name" + relationName);
			}
		}
		return 0;
	}
	private void saveFeatureSelection( Instances data, double peturbLevel, String uuid) {
		
		int numFeatures = (int) (data.numAttributes() * 0.01);
		if (numFeatures < 1){
			numFeatures = 1;
		}
		
		List<ASEvaluation> asEvalList = attributeSelEvalSvc.getAttributeSelectionEvaluators();
		
		for (ASEvaluation asEval: asEvalList){

			Ranker search = new Ranker();
			search.setNumToSelect(numFeatures);

			AttributeSelection filter = new AttributeSelection();
			filter.setSearch(search);
			try {
				filter.setInputFormat(data);
			} catch (Exception e) {
				LOG.debug("unable to set input format"+e.getMessage());
			}

			Instances filteredData;

			filter.setEvaluator(asEval);
			try {
				RawFeatureList result = new RawFeatureList();
				filteredData  = Filter.useFilter(data, filter);
				result.setRawFeatureList(filteredData.toSummaryString());
				result.setPeturbLevel(peturbLevel);
				result.setDataSetName(filteredData.relationName());
				result.setEvaluatorName(getTopLevel(asEval.getClass().toString()));
				result.setNumFeatures(numFeatures);
				result.setExperimentUuid(uuid);
				rawFeatureSvc.saveRawFeatureList(result);
			} catch (Exception e) {
				LOG.debug("unable to apply filter " + e.getMessage());
			}	
		}
	}
	private String getTopLevel(String string) {
		return string.substring(string.lastIndexOf('.')+1, string.length());
	}
	private Map<Object, List<List<Double>>> partitonData(Instances data) {
		Map<Object, List<List<Double>>> result = new HashMap<Object, List<List<Double>>>();
		Enumeration<Instance> dataEnum = (Enumeration<Instance>) data.enumerateInstances();
		while (dataEnum.hasMoreElements()){
			Instance curInst = (Instance) dataEnum.nextElement();
			//class value is the index of the
			//class attribute in the list of
			//class attributes in the arff file
			Double classVal = curInst.classValue();
			List<List<Double>> curAttrListList;
			curAttrListList  = result.get(classVal);
			if (curAttrListList == null){
				curAttrListList = new ArrayList<List<Double>>();
			}
			List<Double> nextAttrs = instanceToDoubleList(curInst);
			curAttrListList.add(nextAttrs);
			result.put(classVal, curAttrListList);
		}
		return result;
	}
	private List<Double> instanceToDoubleList(Instance curInst) {
		List<Double> result = new ArrayList<Double>();
		int numValues = curInst.numValues();
		for (int i=0; i< numValues; i++){
			result.add(curInst.value(i));
		}
		return result;
	}
	public static void main(String[] args){
		ApplicationContext context =   new ClassPathXmlApplicationContext(AssignUtil.SPRING_CONTEXT_FILE_NAME);
		PairwiseSimCalculator calc = (PairwiseSimCalculator) context.getBean("pairwiseSimCalc");
		calc.calculatePairwiseSim();
		((ClassPathXmlApplicationContext) context).close();
	}
}
