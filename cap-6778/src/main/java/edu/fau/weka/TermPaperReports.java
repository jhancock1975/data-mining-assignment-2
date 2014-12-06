package edu.fau.weka;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.collect.Lists;

import edu.fau.weka.model.FeatureLists;
import edu.fau.weka.service.FeatureListService;
import edu.fau.weka.service.PairwiseSimResultsService;
import edu.fau.weka.service.RawFeatureListService;

public class TermPaperReports {
	static final Logger LOG = LoggerFactory.getLogger(TermPaperReports.class);
	@Autowired
	private PairwiseSimResultsService pairwiseSimSvc;
	@Autowired
	private RawFeatureListService rawFeatureSvc;
	@Autowired
	private FeatureListService featureSvc;
	List<String> datasets = Lists.newArrayList("CLL-SUB-111", "GLA-BRA-180", "GLI-85", "TOX-171");
	List<Double> peturbLevels = Lists.newArrayList(0.0, 0.1, 0.2, 0.3, 0.4, 0.9);
	List<String> evaluators = Lists.newArrayList("InfoGainAttributeEval", "ChiSquaredAttributeEval", 
			"ReliefFAttributeEval", "ReliefFWeightedWrapper", "PairwiseSim");
	Map<String, Integer> featureSetSizes;{
		featureSetSizes = new HashMap<String, Integer>();
		featureSetSizes.put("CLL-SUB-111", 11340);
		featureSetSizes.put("GLA-BRA-180", 49151);
		featureSetSizes.put("TOX-171", 5748);
		featureSetSizes.put("GLI-85", 22283);
	}
	public static final int NUM_FOLDS = 10;
	public static void main(String args[]) throws FileNotFoundException{
		ApplicationContext context =   new ClassPathXmlApplicationContext(AssignUtil.SPRING_CONTEXT_FILE_NAME);
		TermPaperReports reporter = (TermPaperReports) context.getBean("termPaperReports");
		reporter.generateReports();
		((ClassPathXmlApplicationContext) context).close();
	}
	private void generateReports() throws FileNotFoundException {
		printAvgJaccardSim();
	}
	
	String outputDir="/home/john/Documents/school/fall-2014/data-mining/assignments/term-paper/gnuplot";
	class PlotParams{
		private String fileName;
		private String dataSet;
		private String evaluator;
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getDataSet() {
			return dataSet;
		}
		public void setDataSet(String dataSet) {
			this.dataSet = dataSet;
		}
		public String getEvaluator() {
			return evaluator;
		}
		public void setEvaluator(String evaluator) {
			this.evaluator = evaluator;
		}
		
	}
	private void printAvgJaccardSim() throws FileNotFoundException {
		for (String dataSetName: datasets){
			List<PlotParams> jaccardFileNameEtc = new ArrayList<PlotParams>();
			List<PlotParams> kunchevaFileNameEtc = new ArrayList<PlotParams>();
			for (String selectorName: evaluators){
				PlotParams jaccardFileNameStuff = new PlotParams();
				PlotParams kunchevaFileNameStuff = new PlotParams();

				jaccardFileNameStuff.setFileName(outputDir+"/"+ dataSetName+"-"+selectorName+"-Jaccard.dat");
				jaccardFileNameStuff.setEvaluator(selectorName);
				jaccardFileNameStuff.setDataSet(dataSetName);
				jaccardFileNameEtc.add(jaccardFileNameStuff);

				kunchevaFileNameStuff.setFileName(outputDir+"/"+ dataSetName+"-"+selectorName+"-Kuncheva.dat");
				kunchevaFileNameStuff.setEvaluator(selectorName);
				kunchevaFileNameStuff.setDataSet(dataSetName);
				kunchevaFileNameEtc.add(kunchevaFileNameStuff);

				PrintWriter jaccardDatFilePw = new PrintWriter(new File(jaccardFileNameStuff.getFileName()));
				PrintWriter kunchevaDatFilePw = new PrintWriter(new File (kunchevaFileNameStuff.getFileName()));
				
				for (Double noiseLevel: peturbLevels){
					
					List<Set<FeatureLists>> featureSetList = new ArrayList<Set<FeatureLists>>();
					for (int fold = 0; fold < NUM_FOLDS; fold++){
						List<FeatureLists> featureLists= featureSvc.getFeatureSetForDataSetNoiseLevelSelectorFold(
								dataSetName, noiseLevel, selectorName, fold);
						Set<FeatureLists> featureSet = new HashSet<FeatureLists>();
						featureSet.addAll(featureLists);
						featureSetList.add(featureSet);
					}
					System.out.println("avg Jaccard similarity for " + dataSetName +" noise level "+
							noiseLevel + " attribute selection technique " + selectorName + " "+
							avgJaccard(featureSetList));
					System.out.println("avg Kuncheva similarity for " + dataSetName +" noise level "+
							noiseLevel + " attribute selection technique " + selectorName + " "+
							avgKuncheva(featureSetList, dataSetName));
					System.out.println("pairwise similarity for dataset " + dataSetName + " noise level "
							+ noiseLevel + " " + 
							pairwiseSimSvc.getPairwiseSim(dataSetName, noiseLevel)
							.get(0).getAvgPairwiseSimilarity());
					if (selectorName.equals("PairwiseSim")){
						jaccardDatFilePw.println(noiseLevel +" " + pairwiseSimSvc.getPairwiseSim(dataSetName, noiseLevel)
								.get(0).getAvgPairwiseSimilarity());
					} else {
						jaccardDatFilePw.println(noiseLevel +" " + avgJaccard(featureSetList));
						kunchevaDatFilePw.println(noiseLevel +" " + avgKuncheva(featureSetList, dataSetName));
					}
				}
				jaccardDatFilePw.close();
				kunchevaDatFilePw.close();
			}
			gnerateGnuPlot(outputDir+"/gnuplot-"+dataSetName+"Jaccard.gplt", jaccardFileNameEtc);
			gnerateGnuPlot(outputDir+"/gnuplot-"+dataSetName+"Kuncheva.gplt", kunchevaFileNameEtc);
		}
	}

	private void gnerateGnuPlot(String gnuPlotFileName, List<PlotParams> fileNamePlotStuff) throws FileNotFoundException {
		PrintWriter gnuPlotPw = new PrintWriter ( new File (gnuPlotFileName));
		String yAxisTitle = fileNamePlotStuff.iterator().next().getDataSet();
		StringBuilder gnuPlotFileContents= new StringBuilder("set term postscript eps 22\n"+
				"set key outside\n"+
				"set key center top\n"+
				"set xlabel 'Noise Level'\n"+
				"set ylabel '" + yAxisTitle + " Stability'\n"+
				"set linestyle 1 lt 2 lw 3\n"+
				"set key box linestyle 1\n"+
				"set output '" + gnuPlotFileName + ".ps'\n");
		gnuPlotFileContents.append("plot ");
		int ptCount = 4;
		for (PlotParams curStuff: fileNamePlotStuff){
			gnuPlotFileContents.append("'" + curStuff.getFileName())
			.append("' title '" +curStuff.getEvaluator()+ "' with linespoints lw 3 pt " + ptCount++)
			.append(", ");
		}
		gnuPlotFileContents.setLength(gnuPlotFileContents.length() - 1);
		gnuPlotPw.println(gnuPlotFileContents);
		gnuPlotPw.close();
	}
	
	private double avgJaccard(List<Set<FeatureLists>> featureSetList){
		double sum = 0;
		for (int i = 0 ; i < featureSetList.size()-1; i++){
			for (int j = i+1; j < featureSetList.size(); j++){
				Set<FeatureLists> s1 = featureSetList.get(i);
				Set<FeatureLists> s2 = featureSetList.get(j);
				Set<FeatureLists> union = PairwiseSim.union(s1, s2);
				Set<FeatureLists> intersect= PairwiseSim.intersection(s1, s2);
				double unionSize = union.size();
				double intersectSize = intersect.size();
				sum += intersectSize/unionSize;
			}
		}
		return 2*sum/(featureSetList.size()*(featureSetList.size()-1));
	}
	
	private double avgKuncheva(List<Set<FeatureLists>> featureSetList,  String dataSetName){
		double sum = 0;
		for (int i = 0; i < featureSetList.size()-1; i++){
			for (int j=i+1; j < featureSetList.size(); j++ ){
				double k = featureSetList.get(j).size();
				double n = featureSetSizes.get(dataSetName);
				double r = PairwiseSim.intersection(featureSetList.get(i), featureSetList.get(j)).size();
				sum = sum + (r*n-k*k)/(k*(n-k));
			}
		}
		return 2*sum/(featureSetList.size()*(featureSetList.size()-1));
	}
}
