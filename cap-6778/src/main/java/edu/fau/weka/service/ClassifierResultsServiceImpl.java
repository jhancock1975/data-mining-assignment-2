package edu.fau.weka.service;

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
		String[] split = rawFeatureList.split("\n");
		Pattern pattern = Pattern.compile("\\d+\\s+([a-zA-Z0-9]+)");
		for (String line: split){
			Matcher m = pattern.matcher(line);
			if (m.find()){
				featureSvc.saveFeature(new FeatureLists(results.getClassifierResultId(), m.group(1)));
			}
		}
	}
	public List<Double> getLastFprResultsFor(String classifierName,
			String attribEvalName) {
		return classifierRepo.getLastFprResultsFor(classifierName, attribEvalName);
	}
	public List<Double> getLastFnrResultsFor(String classifierName,
			String attribEvalName) {
		return classifierRepo.getLastFnrResultsFor(classifierName, attribEvalName);
	}
	public List<Double> getLastPAucResultsFor(String classifierName,
			String attribEvalName) {
		return classifierRepo.getLastPAucResultsFor(classifierName, attribEvalName);
	}
	public List<Double> getLastNAucResultsFor(String classifierName,
			String attribEvalName) {
		return classifierRepo.getLastNAucResultsFor(classifierName, attribEvalName);
	}
	public List<Double> getLastWAucResultsFor(String classifierName,
			String attribEvalName) {
		return classifierRepo.getLastWAucResultsFor(classifierName, attribEvalName);
	}
}
