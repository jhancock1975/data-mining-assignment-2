package edu.fau.weka.model;

import java.util.Date;

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
	public void setFeatureSet(String featureSet);
	public String getFeatureSet();
	public Double getpAuc();
	public void setpAuc(Double pAuc);
	public Double getnAuc();
	public void setnAuc(Double nAuc);
	public Double getwAuc();
	public void setwAuc(Double wAuc);
	public Double getFpr();
	public void setFpr(Double fpr);
	public Double getFnr();
	public void setFnr(Double fnr);
	public void setExperimentStartTime(Date startTime);
	public Date getExperimentStartTime();
	public Integer getClassifierResultId();
	
}
