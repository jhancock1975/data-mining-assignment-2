package edu.fau.weka.service;

import java.util.List;

import edu.fau.weka.model.ClassifierResults;

public interface ClassifierResultsService {
	public void saveResults(ClassifierResults results);
	public List<List<Double>> getLastFprResultsFor(String classifierName, String attribEvalName);
	public List<List<Double>> getLastFnrResultsFor(String classifierName, String attribEvalName);
	public List<List<Double>> getLastPAucResultsFor(String classifierName, String attribEvalName);
	public List<List<Double>> getLastNAucResultsFor(String classifierName, String attribEvalName);
	public List<List<Double>> getLastWAucResultsFor(String classifierName, String attribEvalName);
	public List<String> getClassifierNames();
	public List<String> getAttribEvalNames();
}
