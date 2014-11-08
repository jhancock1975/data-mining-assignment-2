package edu.fau.weka.dao;

import edu.fau.weka.model.PairwiseSimResults;

public interface PairwiseSimResultsRepository {
	public void saveResults(PairwiseSimResults results);
}
