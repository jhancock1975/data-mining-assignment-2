package edu.fau.weka.dao;

import java.util.List;

import edu.fau.weka.model.FeatureLists;

public interface FeatureListRepository {
	public void save(FeatureLists feature);
	public List<FeatureLists> getFeatureSetForDataSetNoiseLevelSelectorFold(String dataSetName, 
			double noiseLevel, String selectorName, int fold ) ;
}
