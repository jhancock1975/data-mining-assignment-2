package edu.fau.weka.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.fau.weka.model.ClassifierResults;

@Repository
@Transactional
public class ClassifierResultsRepositoryImpl  extends RepositoryBase implements ClassifierResultsRepository{
	
	public void save(ClassifierResults classifierResults) {
		currentSession().save(classifierResults);
	}
	
	
}
