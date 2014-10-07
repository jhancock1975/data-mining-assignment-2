package edu.fau.weka;

import org.springframework.stereotype.Component;

import weka.classifiers.Evaluation;
@Component
public class ClassifierResultsImpl implements ClassifierResults{
	Evaluation evaluation;
	String classifierClassName;
	String filterClassName;
	int featureSetSize;
	
	public Evaluation getEvaluation() {
		return evaluation;
	}
	public Evaluation setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
		return evaluation;
	}
	public String getClassifierClassName() {
		return classifierClassName;
	}
	public void setClassifierClassName(String classifierClassName) {
		this.classifierClassName = classifierClassName;
	}
	public String getFilterClassName() {
		return filterClassName;
	}
	public void setFilterClassName(String filterClassName) {
		this.filterClassName = filterClassName;
	}
	public int getFeatureSetSize() {
		return featureSetSize;
	}
	public void setFeatureSetSize(int featureSetSize) {
		this.featureSetSize = featureSetSize;
	}
	
}
