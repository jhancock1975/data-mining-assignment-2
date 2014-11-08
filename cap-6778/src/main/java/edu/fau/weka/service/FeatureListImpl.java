package edu.fau.weka.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public void saveFeatures(Integer classifierResultId, String rawFeatureList) {
		String[] split = rawFeatureList.split("\n");
		Pattern pattern = Pattern.compile("\\d+\\s+([a-zA-Z0-9]+)");
		for (String line: split){
			Matcher m = pattern.matcher(line);
			if (m.find()){
				this.saveFeature(new FeatureLists(classifierResultId, m.group(1)));
			}
		}
	}

}
