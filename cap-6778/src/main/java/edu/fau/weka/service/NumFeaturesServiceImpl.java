package edu.fau.weka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;

public class NumFeaturesServiceImpl implements NumFeaturesService {
	private List<Integer> numFeaturesList;
	@Required 
	public void setNumFeaturesList(List<Integer> numFeaturesList){
		this.numFeaturesList = numFeaturesList;
	}
	public List<Integer> getNumFeaturesList() {
		return numFeaturesList;
	}

}
