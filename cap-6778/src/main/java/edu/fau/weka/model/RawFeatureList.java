package edu.fau.weka.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RawFeatureList{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer rawFeatureListId;
	private String rawFeatureList;
	private int pairwiseResultId;
	private double peturbLevel;
	private String dataSetName;
	private String evaluatorName;
	private int numFeatures;
	private String experimentUuid;
	private Timestamp experimentStartTime;
	
	public Integer getRawFeatureListId() {
		return rawFeatureListId;
	}
	public void setRawFeatureListId(Integer rawFeatureListId) {
		this.rawFeatureListId = rawFeatureListId;
	}
	public double getPeturbLevel() {
		return peturbLevel;
	}
	public void setPeturbLevel(double peturbLevel) {
		this.peturbLevel = peturbLevel;
	}
	public String getDataSetName() {
		return dataSetName;
	}
	public void setDataSetName(String dataSetName) {
		this.dataSetName = dataSetName;
	}
	public String getEvaluatorName() {
		return evaluatorName;
	}
	public void setEvaluatorName(String evaluatorName) {
		this.evaluatorName = evaluatorName;
	}
	
	public String getRawFeatureList() {
		return rawFeatureList;
	}
	public void setRawFeatureList(String rawFeatureList) {
		this.rawFeatureList = rawFeatureList;
	}
	public int getPairwiseResultId() {
		return pairwiseResultId;
	}
	public void setPairwiseResultId(int pairwiseResultId) {
		this.pairwiseResultId = pairwiseResultId;
	}
	public int getNumFeatures() {
		return numFeatures;
	}
	public void setNumFeatures(int numFeatures) {
		this.numFeatures = numFeatures;
	}
	public String getExperimentUuid() {
		return experimentUuid;
	}
	public void setExperimentUuid(String experimentUuid) {
		this.experimentUuid = experimentUuid;
	}
	public Timestamp getExperimentStartTime() {
		return experimentStartTime;
	}
	public void setExperimentStartTime(Timestamp experimentStartTime) {
		this.experimentStartTime = experimentStartTime;
	}	
}