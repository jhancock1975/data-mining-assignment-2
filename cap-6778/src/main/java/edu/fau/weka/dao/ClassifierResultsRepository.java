package edu.fau.weka.dao;

import java.util.List;

import edu.fau.weka.model.ClassifierResults;


public interface ClassifierResultsRepository {
	public void save(ClassifierResults classifierResults);

	public List<Double> getLastFprResultsFor(String classifierName,
			String attribEvalName);

	public List<Double> getLastFnrResultsFor(String classifierName,
			String attribEvalName);

	public List<Double> getLastPAucResultsFor(String classifierName,
			String attribEvalName);

	public List<Double> getLastNAucResultsFor(String classifierName,
			String attribEvalName);

	public List<Double> getLastWAucResultsFor(String classifierName,
			String attribEvalName);
}
