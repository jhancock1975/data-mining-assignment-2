package edu.fau.weka.service;

import java.util.List;

import weka.classifiers.Classifier;

public interface MetaClassService extends ClassifierService{

	List<Classifier> getClassifiers();

}
