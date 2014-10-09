package edu.fau.weka.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.lazy.IBk;
@Service
public class Assign4ClassifierSerivceImpl implements ClassifierService {
	@Autowired
	private NaiveBayes naiveBayes;
	@Autowired
	private IBk ibk; // 5 nearest neighbors
	public List<Classifier> getClassifiers() {
		return Arrays.asList(new Classifier[] {naiveBayes, ibk});
	}

}
