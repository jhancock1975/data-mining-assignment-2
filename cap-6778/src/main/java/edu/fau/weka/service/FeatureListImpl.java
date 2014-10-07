package edu.fau.weka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fau.weka.dao.FeatureListRepository;
import edu.fau.weka.model.FeatureLists;
@Service
public class FeatureListImpl implements FeatureListService {
	@Autowired
	FeatureListRepository featureRepo;
	public void saveFeature(FeatureLists feature) {
		featureRepo.save(feature);
	}

}
