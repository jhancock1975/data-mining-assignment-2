package edu.fau.weka;

import weka.classifiers.meta.AdaBoostM1;

public enum MetaClassifierTypes {
	Boosting(AdaBoostM1.class, " -P 100 -S 1 -I "), 
	Bagging(weka.classifiers.meta.Bagging.class, " -P 100 -S 1 -I ");
	private String optionStr;
	private Class classType;
	private MetaClassifierTypes(Class classType, String optionStr){
		this.optionStr = optionStr;
		this.classType = classType;
	}
	public String toString(){
		return optionStr;
	}
	public Class getClassType(){
		return this.classType;
	}
}
