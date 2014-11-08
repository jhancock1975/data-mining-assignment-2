package edu.fau.weka.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.fau.weka.model.RawFeatureList;
@Repository
@Transactional
public class RawFeatureListRepositoryImpl extends RepositoryBase implements RawFeatureListRepository {

	public void saveRawFeatureList(RawFeatureList rawList) {
		currentSession().save(rawList);

	}

}
