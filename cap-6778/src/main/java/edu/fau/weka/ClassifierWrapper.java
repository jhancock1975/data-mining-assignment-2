package edu.fau.weka;

import org.springframework.stereotype.Component;

import weka.classifiers.Classifier;

/**
 * encapsulates a classifier and 
 * status message to alert caller
 * if anything went wrong when
 * classifier was created
 * @author john
 *
 */
@Component
public class ClassifierWrapper {
	private Classifier classifier;
	private RuntimeException exception;
	public ClassifierWrapper(Classifier classifier) {
		super();
		this.classifier = classifier;
	}
	public ClassifierWrapper() {
	}
	public Classifier getClassifier() {
		return classifier;
	}
	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}
	public RuntimeException getException() {
		return exception;
	}
	public void setException(RuntimeException exception) {
		this.exception = exception;
	}
	
}
