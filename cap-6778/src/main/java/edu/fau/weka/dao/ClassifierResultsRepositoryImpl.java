package edu.fau.weka.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.fau.weka.model.ClassifierResults;

@Repository
@Transactional
public class ClassifierResultsRepositoryImpl  extends RepositoryBase implements ClassifierResultsRepository{
	
	public void save(ClassifierResults classifierResults) {
		currentSession().save(classifierResults);
	}

	public List<Double> getLastFprResultsFor(String classifierName,
			String attribEvalName) {
		currentSession().
	}

	public List<Double> getLastFnrResultsFor(String classifierName,
			String attribEvalName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Double> getLastPAucResultsFor(String classifierName,
			String attribEvalName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Double> getLastNAucResultsFor(String classifierName,
			String attribEvalName) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Double> getLastWAucResultsFor(String classifierName,
			String attribEvalName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
