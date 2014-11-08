package edu.fau.weka.service;

import java.util.List;

import edu.fau.weka.model.FeatureLists;

public interface FeatureListService {
	public void saveFeature(FeatureLists feature);

	public void saveFeatures(Integer classifierResultId, String rawFeatureList);
	
	public List<FeatureLists> getFeatureSetForDataSetNoiseLevelSelectorFold(String dataSetName, 
			double noiseLevel, String selectorName, int fold ) ;
}
