package edu.fau.weka.model;

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
	Integer pairwiseSimResultId;
	String dataSetName;
	Double peturbLevel;
	Double sampleSize;
	Integer numFolds;
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
	public Double getSampleSize() {
		return sampleSize;
	}
	public void setSampleSize(Double sampleSize) {
		this.sampleSize = sampleSize;
	}
	public Integer getNumFolds() {
		return numFolds;
	}
	public void setNumFolds(Integer numFolds) {
		this.numFolds = numFolds;
	}
	
}
