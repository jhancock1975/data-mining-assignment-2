package edu.fau.weka.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weka.classifiers.Classifier;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.Bagging;

@Service
public class MetaClassServiceImpl implements MetaClassService{
	@Autowired
	private AdaBoostM1 booster;
	@Autowired
	private Bagging bagger;
	
	
	public List<Classifier> getClassifiers() {
		return Arrays.asList(new Classifier[] {booster, bagger});
	}
}
