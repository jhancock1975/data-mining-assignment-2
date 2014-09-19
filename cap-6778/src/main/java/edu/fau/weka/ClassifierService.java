package edu.fau.weka;

import java.util.List;

import weka.classifiers.Classifier;

public interface ClassifierService {
	List<Classifier> getClassifiers();
}
