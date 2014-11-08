package edu.fau.weka.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fau.weka.dao.ClassifierResultsRepository;
import edu.fau.weka.model.ClassifierResults;
import edu.fau.weka.model.FeatureLists;
@Service
public class ClassifierResultsServiceImpl implements ClassifierResultsService {
	@Autowired
	private ClassifierResultsRepository classifierRepo;
	@Autowired
	private FeatureListService featureSvc;
	public void saveResults(ClassifierResults results) {
		classifierRepo.save(results);
		if (results.getFeatureSet() != null){
			parseAndSaveFeatures(results);
		} 
	}
	private void parseAndSaveFeatures(ClassifierResults results) {
		String rawFeatureList = results.getFeatureSet();
		int beginFeatures = rawFeatureList.indexOf("Num Attributes");
		rawFeatureList = rawFeatureList.substring(beginFeatures, rawFeatureList.length());
		featureSvc.saveFeatures(results.getClassifierResultId(), rawFeatureList);
		
	}
	/**
	 * without functional abstraction this is the best we can do
	 * it would be better if we could pass the get*() method as a
	 * parameter. alternative is to use reflection but that breaks
	 * compile time check of method names.
	 * @param xVal
	 * @param yVal
	 * @param resultList
	 * @return
	 */
	private List<List<Double>> buildListHelper(Double xVal, Double yVal, List<List<Double>> resultList){
		List<Double> nextDataPoint = new ArrayList<Double>();
		nextDataPoint.addAll(Arrays.asList(new Double[]{xVal,yVal}));
		resultList.add(nextDataPoint);
		return resultList;
	}
		
	public List<List<Double>> getLastFprResultsFor(String classifierName,
			String attribEvalName) {
		List<ClassifierResults> results = classifierRepo.getLastResultsFor(classifierName, attribEvalName);
		List<List<Double>> fprList = new ArrayList<List<Double>>();
		for (ClassifierResults result: results){
			fprList = buildListHelper( new Double(result.getFeatureSetSize()), result.getFpr(), fprList);
		}
		return fprList;
	}
	
	public List<List<Double>> getLastFnrResultsFor(String classifierName,
			String attribEvalName) {
		List<ClassifierResults> results = classifierRepo.getLastResultsFor(classifierName, attribEvalName);
		List<List<Double>> fnrList = new ArrayList<List<Double>>();
		for (ClassifierResults result: results){
			fnrList = buildListHelper(new Double(result.getFeatureSetSize()), result.getFnr(), fnrList);
		}
		return fnrList;
	}
	public List<List<Double>> getLastPAucResultsFor(String classifierName,
			String attribEvalName) {
		List<ClassifierResults> results = classifierRepo.getLastResultsFor(classifierName, attribEvalName);
		List<List<Double>> pAucList = new ArrayList<List<Double>>();
		for (ClassifierResults result: results){
			pAucList = buildListHelper(new Double(result.getFeatureSetSize()), result.getpAuc(), pAucList);
		}
		return pAucList;
	}
	public List<List<Double>> getLastNAucResultsFor(String classifierName,
			String attribEvalName) {
		List<ClassifierResults> results = classifierRepo.getLastResultsFor(classifierName, attribEvalName);
		List<List<Double>> nAucList = new ArrayList<List<Double>>();
		for (ClassifierResults result: results){
			nAucList = buildListHelper(new Double(result.getFeatureSetSize()), result.getnAuc(), nAucList);
		}
		return nAucList ;
	}
	public List<List<Double>> getLastWAucResultsFor(String classifierName,
			String attribEvalName) {
		List<ClassifierResults> results = classifierRepo.getLastResultsFor(classifierName, attribEvalName);
		List<List<Double>> wAucList = new ArrayList<List<Double>>();
		for (ClassifierResults result: results){
			wAucList = buildListHelper(new Double(result.getFeatureSetSize()), result.getwAuc(), wAucList);
		}
		return wAucList ;
	}
	public List<String> getClassifierNames() {
		return classifierRepo.getClassifierNames();
	}
	public List<String> getAttribEvalNames() {
		return classifierRepo.getAttribEvalNames();
	}
}
