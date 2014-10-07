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
	private Integer classifierResultId;
	private String attribute;
	public FeatureLists(Integer classifierResultId, String attribute) {
		this.classifierResultId = classifierResultId;
		this.attribute = attribute;
	}
	public Integer getClassifierResultId() {
		return classifierResultId;
	}
	public void setClassifierResultId(Integer classifierResultId) {
		this.classifierResultId = classifierResultId;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
}
