package edu.fau.weka.service;

import java.util.List;

import edu.fau.weka.model.ClassifierResults;

public interface ClassifierResultsService {
	public void saveResults(ClassifierResults results);
	public List<Double> getLastFprResultsFor(String classifierName, String attribEvalName);
	public List<Double> getLastFnrResultsFor(String classifierName, String attribEvalName);
	public List<Double> getLastPAucResultsFor(String classifierName, String attribEvalName);
	public List<Double> getLastNAucResultsFor(String classifierName, String attribEvalName);
	public List<Double> getLastWAucResultsFor(String classifierName, String attribEvalName);
}
