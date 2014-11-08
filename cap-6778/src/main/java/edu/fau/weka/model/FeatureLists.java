package edu.fau.weka.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FeatureLists {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer featureListId;
	private Integer experimentResultId;
	private String attribute;
	public FeatureLists(Integer classifierResultId, String attribute) {
		this.experimentResultId = classifierResultId;
		this.attribute = attribute;
	}
	public Integer getExperimentResultId() {
		return experimentResultId;
	}
	public void setExperimentResultId(Integer classifierResultId) {
		this.experimentResultId = classifierResultId;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
}
