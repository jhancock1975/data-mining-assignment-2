package edu.fau.weka;

import weka.classifiers.Evaluation;

public interface ClassifierResults {
	public String getClassifierClassName();
	public void setClassifierClassName(String className);
	public String getFilterClassName();
	public void setFilterClassName(String className);
	public int getFeatureSetSize();
	public void setFeatureSetSize(int size);
	public Evaluation getEvaluation();
	public Evaluation setEvaluation(Evaluation evaluation);
}
