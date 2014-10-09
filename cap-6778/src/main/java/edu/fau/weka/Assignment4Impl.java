package edu.fau.weka;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.Ranker;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import edu.fau.weka.model.ClassifierResults;
import edu.fau.weka.service.AttributeSelectionEvaluationService;
import edu.fau.weka.service.ClassifierResultsService;
import edu.fau.weka.service.ClassifierService;
import edu.fau.weka.service.DataService;
import edu.fau.weka.service.NumFeaturesService;

public class Assignment4Impl implements Assignment4 {

	@Autowired
	private DataService dataSvc;
	@Autowired
	private AttributeSelectionEvaluationService attributeSelEvalSvc;
	@Autowired
	private ClassifierResultsService classifierResultsSvc;
	@Autowired
	private NumFeaturesService numFeaturesService;
	
	private ClassifierService classifierService;
	@Required
	public void setClassifierService(ClassifierService classifierService){
		this.classifierService = classifierService;
	}
	static final Date experimentStartTime = new Date(System.currentTimeMillis()); 

	private ClassifierResults saveResults(ClassifierResults results, Instances filteredInstances, 
			Evaluation evaluation, Classifier base, ASEvaluation asEval, Ranker search){
		results.setFeatureSet(asEval == null ? null:filteredInstances.toSummaryString());
		results.setFpr(evaluation.falsePositiveRate(0));
		results.setFnr(evaluation.falsePositiveRate(1));
		results.setnAuc(evaluation.areaUnderROC(0));
		results.setpAuc(evaluation.areaUnderROC(1));
		results.setClassifierClassName(base.getClass().getName());
		results.setFilterClassName(asEval == null ? "No feature selection" : asEval.getClass().getName());
		results.setEvaluation(evaluation);
		results.setExperimentStartTime(experimentStartTime);
		results.setFeatureSetSize(search.getNumToSelect());
		return results;
	}

	public void  runClassifiers(String dataSourceName) throws Exception {

		Instances data = dataSvc.getData();

		List<ASEvaluation> asEvalList = attributeSelEvalSvc.getAttributeSelectionEvaluators();
		List<Integer> numFeaturesList = numFeaturesService.getNumFeaturesList();
		List<Classifier> classifiers = classifierService.getClassifiers();
		
		for (Classifier classifier: classifiers){
			for (ASEvaluation asEval: asEvalList){
				for (Integer numFeatures: numFeaturesList){
					
					ClassifierResults results = new ClassifierResults();
					
					Ranker search = new Ranker();
					search.setNumToSelect(numFeatures);
					
					AttributeSelection filter = new AttributeSelection();
					filter.setSearch(search);
					filter.setInputFormat(data);
					
					Instances filteredData;
					
					if (asEval == null){
						filteredData = data;
					} else {
						results.setFilterClassName(asEval.getClass().getName());
						filter.setEvaluator(asEval);
						filteredData  = Filter.useFilter(data, filter);
					}
					
					Evaluation evaluation= new Evaluation(data);
					evaluation.crossValidateModel(classifier, filteredData, 10, new Random(1));
					
					results = saveResults(results, filteredData==data ? null:filteredData, 
							evaluation, classifier, asEval, search);
					classifierResultsSvc.saveResults(results);
				}
			}
		}
	}

	public static void main(String[] args){
		ApplicationContext context =   new ClassPathXmlApplicationContext(AssignUtil.SPRING_CONTEXT_FILE_NAME);
		Assignment4Impl assign4 = (Assignment4Impl ) context.getBean("assign4");
		try {
			assign4.runClassifiers(args[0]);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			((ClassPathXmlApplicationContext ) context).close();
		}
	}
}
