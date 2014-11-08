package edu.fau.weka.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.fau.weka.model.ClassifierResults;
import edu.fau.weka.model.FeatureLists;

@Repository
@Transactional
public class FeatureListRepositoryImpl extends RepositoryBase implements FeatureListRepository{

	public void save(FeatureLists feature) {
		currentSession().save(feature);
	}
	public List<FeatureLists> getFeatureSetForDataSetNoiseLevelSelectorFold(String dataSetName, 
			double noiseLevel, String selectorName, int fold ) {
		SQLQuery query = currentSession()
				.createSQLQuery("call getFeatureSetForDataSetNoiseLevelSelectorFold('"+dataSetName+"'," +
						noiseLevel+","+
						"'"+selectorName+"',"+
						fold+
						")").addEntity(FeatureLists.class);
		List<FeatureLists> result = query.list();
		return result;
	}

}
