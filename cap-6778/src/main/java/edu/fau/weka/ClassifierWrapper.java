package edu.fau.weka;

import org.springframework.stereotype.Component;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;

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
	private ClassifierStatus status;
	private ClassifierTypes classifierType;
	private Double errorCost;
	private Evaluation eval;
	
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
	public void setStatus(ClassifierStatus constructError) {
		this.status = constructError;
	}
	public ClassifierStatus getStatus() {
		return status;
	}
	public ClassifierTypes getClassifierType() {
		return classifierType;
	}
	public void setClassifierType(ClassifierTypes classifierType) {
		this.classifierType = classifierType;
	}
	public Double getErrorCost() {
		return errorCost;
	}
	public void setErrorCost(Double errorRate) {
		this.errorCost = errorRate;
	}
	public Evaluation getEval() {
		return eval;
	}
	public void setEval(Evaluation eval) {
		this.eval = eval;
	}	
}
