package edu.fau.weka;

import weka.classifiers.Evaluation;

public interface ResultsService {

	void saveResults(ClassifierWrapper classifier, Evaluation eval);

}
