package edu.fau.weka.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weka.classifiers.Classifier;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
@Service
public class BaseClassServiceImpl  implements BaseClassService {
	@Autowired
	private J48 j48;
	@Autowired
	private DecisionStump stump;
	
	
	public List<Classifier> getClassifiers() {
		return Arrays.asList(new Classifier[] {j48, stump});
	}
}
