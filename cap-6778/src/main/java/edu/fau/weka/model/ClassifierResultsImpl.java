package edu.fau.weka.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;

import weka.classifiers.Evaluation;
@Component
@Entity(name="ClassifierResults")
public class ClassifierResultsImpl implements ClassifierResults{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer classifierResultId;
	@Transient
	private Evaluation evaluation;
	
	private String classifierClassName;
	
	private String filterClassName;
	
	private Integer featureSetSize;
	
	private Double fpr;
	 
	private Double fnr;
	
	private Double pAuc;
	
	private Double nAuc;
	
	private String featureSet;
	@Column(name="experimentStartTime")
	private Date startTime;
	
	public String getFeatureSet() {
		return featureSet;
	}
	public void setFeatureSet(String featureSet) {
		this.featureSet = featureSet;
	}
	public Double getpAuc() {
		return pAuc;
	}
	public void setpAuc(Double pAuc) {
		this.pAuc = pAuc;
	}
	public Double getnAuc() {
		return nAuc;
	}
	public void setnAuc(Double nAuc) {
		this.nAuc = nAuc;
	}
	public Double getwAuc() {
		return wAuc;
	}
	public void setwAuc(Double wAuc) {
		this.wAuc = wAuc;
	}
	private Double wAuc;
	
	private String instances;
	
	public Double getFpr() {
		return fpr;
	}
	public void setFpr(Double fpr) {
		this.fpr = fpr;
	}
	public Double getFnr() {
		return fnr;
	}
	public void setFnr(Double fnr) {
		this.fnr = fnr;
	}
	public String getInstances() {
		return instances;
	}
	public void setInstances(String instances) {
		this.instances = instances;
	}
	public void setFeatureSetSize(Integer featureSetSize) {
		this.featureSetSize = featureSetSize;
	}
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
	public void setExperimentStartTime(Date startTime) {
		this.startTime = startTime;
		
	}
	public Date getExperimentStartTime() {
		return startTime;
	}
	public Integer getClassifierResultId() {
		return this.classifierResultId;
	}
	
}
