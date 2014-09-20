package edu.fau.weka.types;

public enum ClassifierTypes {
	BOOSTING_J48_10("Boosting J48 10"),
	BOOSTING_J48_25("Boosting J48 25"),
	BAGGING_J48_10("Bagging J48 10"),
	BAGGING_J48_25("Bagging J48 25"),
	BOOSTING_DECISION_STUMP_10("Boosting Decision Stump 10"),
	BOOSTING_DECISION_STUMP_25("Boosting Decision Stump 25"),
	BAGGING_DECISION_STUMP_10("Bagging Decision Stump 10"),
	BAGGING_DECISION_STUMP_25("Bagging Decision Stump 25");
	private String description;
	ClassifierTypes(String description){
		this.description = description;
	}
	public String toString(){
		return description;
	}
}
