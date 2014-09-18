package edu.fau.weka;

import weka.classifiers.Classifier;
/**
 * 
 * @author john
 *
 */
public interface ClassifierService {
	ClassifierWrapper getNextClassifier();
}
