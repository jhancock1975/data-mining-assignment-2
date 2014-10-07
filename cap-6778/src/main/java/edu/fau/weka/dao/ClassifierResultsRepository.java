package edu.fau.weka.dao;

import edu.fau.weka.model.ClassifierResults;


public interface ClassifierResultsRepository {
	public void save(ClassifierResults classifierResults);
}
