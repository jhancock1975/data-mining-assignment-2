package edu.fau.weka.dao;

import java.util.List;

import edu.fau.weka.model.PairwiseSimResults;

public interface PairwiseSimResultsRepository {
	public void saveResults(PairwiseSimResults results);
	public List<PairwiseSimResults> getPairwiseSim(String dataSetName, double noiseLevel);
}
