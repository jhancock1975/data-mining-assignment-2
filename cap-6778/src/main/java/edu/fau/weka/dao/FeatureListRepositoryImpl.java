package edu.fau.weka.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.fau.weka.model.FeatureLists;

@Repository
@Transactional
public class FeatureListRepositoryImpl extends RepositoryBase implements FeatureListRepository{

	public void save(FeatureLists feature) {
		currentSession().save(feature);
	}

}
