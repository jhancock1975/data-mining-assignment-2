package edu.fau.weka.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

@Component
@Entity(name="PairwiseSimResults")
public class PairwiseSimResults {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pairwiseSimResultId;
	private String dataSetName;
	private Double peturbLevel;
	private Integer sampleSize;
	private Integer numFolds;
	private Double avgPairwiseSimilarity;
	private String experimentUuid;
	private Timestamp experimentStartTime;
	
	public Integer getPairwiseSimResultId() {
		return pairwiseSimResultId;
	}
	public void setPairwiseSimResultId(Integer pairwiseSimResultId) {
		this.pairwiseSimResultId = pairwiseSimResultId;
	}
	public String getDataSetName() {
		return dataSetName;
	}
	public void setDataSetName(String dataSetName) {
		this.dataSetName = dataSetName;
	}
	public Double getPeturbLevel() {
		return peturbLevel;
	}
	public void setPeturbLevel(Double peturbLevel) {
		this.peturbLevel = peturbLevel;
	}
	public Integer getSampleSize() {
		return sampleSize;
	}
	public void setSampleSize(Integer sampleSize) {
		this.sampleSize = sampleSize;
	}
	public Integer getNumFolds() {
		return numFolds;
	}
	public void setNumFolds(Integer numFolds) {
		this.numFolds = numFolds;
	}
	public Double getAvgPairwiseSimilarity() {
		return avgPairwiseSimilarity;
	}
	public void setAvgPairwiseSimilarity(Double avgPairwiseSimilarity) {
		this.avgPairwiseSimilarity = avgPairwiseSimilarity;
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
