package edu.fau.weka.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.fau.weka.model.PairwiseSimResults;
@Repository
@Transactional
public class PairwiseSimResultsRepositoryImpl extends RepositoryBase implements PairwiseSimResultsRepository{

	public void saveResults(PairwiseSimResults results) {
		currentSession().save(results);
	}

}
