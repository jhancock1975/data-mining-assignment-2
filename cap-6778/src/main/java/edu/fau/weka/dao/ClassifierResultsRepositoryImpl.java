package edu.fau.weka.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.fau.weka.model.ClassifierResults;

@Repository
@Transactional
public class ClassifierResultsRepositoryImpl  extends RepositoryBase implements ClassifierResultsRepository{
	
	public void save(ClassifierResults classifierResults) {
		currentSession().save(classifierResults);
	}

	public List<ClassifierResults> getLastResultsFor(String classifierName,
			String attribEvalName) {
		SQLQuery query = currentSession()
				.createSQLQuery("call getLastResults('"+classifierName+"','"+attribEvalName+"')").addEntity(ClassifierResults.class);
		List<ClassifierResults> result = query.list();
		return result;
	}

	public List<String> getClassifierNames() {
		SQLQuery query = currentSession()
				.createSQLQuery("call getClassifierNames()");
		List<String> result = query.list();
		return result;
	}

	public List<String> getAttribEvalNames() {
		SQLQuery query = currentSession()
				.createSQLQuery("call getAttribEvalNames()");
		List<String> result = query.list();
		return result;
	}

}
