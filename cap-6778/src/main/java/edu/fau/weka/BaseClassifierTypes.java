package edu.fau.weka;

public enum BaseClassifierTypes {
	//we let the compiler check the class names
	//this way, at least if the library changes and
	//class names change in the library, the compiler
	//will tell us before runtime
	J48(weka.classifiers.trees.J48.class.getName()  + " -- -C 0.25 -M 2"), DecisionStump(weka.classifiers.trees.DecisionStump.class.getName());
	private String classifierStr;
	BaseClassifierTypes(String clasifierStr){
		this.classifierStr = clasifierStr;
	}
	public String toString(){
		return this.classifierStr;
	}
}
