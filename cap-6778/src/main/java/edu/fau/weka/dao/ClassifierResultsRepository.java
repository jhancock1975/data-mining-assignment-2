package edu.fau.weka.dao;

import java.util.List;

import edu.fau.weka.model.ClassifierResults;


public interface ClassifierResultsRepository {
	public void save(ClassifierResults classifierResults);

	public List<ClassifierResults> getLastResultsFor(String classifierName,
			String attribEvalName);

	public List<String> getClassifierNames();

	public List<String> getAttribEvalNames();
}
