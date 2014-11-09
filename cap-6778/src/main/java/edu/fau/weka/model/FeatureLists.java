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
	public FeatureLists(Integer experimentResultId, String attribute) {
		this.experimentResultId = experimentResultId;
		this.attribute = attribute;
	}
	public FeatureLists(){}
	public Integer getExperimentResultId() {
		return experimentResultId;
	}
	public void setExperimentResultId(Integer experimentResultId) {
		this.experimentResultId = experimentResultId;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attribute == null) ? 0 : attribute.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FeatureLists other = (FeatureLists) obj;
		if (attribute == null) {
			if (other.attribute != null)
				return false;
		} else if (!attribute.equals(other.attribute))
			return false;
		return true;
	}
	

}
