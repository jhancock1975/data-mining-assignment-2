package edu.fau.weka.service;

import edu.fau.weka.model.FeatureLists;

public interface FeatureListService {
	public void saveFeature(FeatureLists feature);

	public void saveFeatures(Integer classifierResultId, String rawFeatureList);
}
