package edu.fau.weka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import weka.attributeSelection.ASEvaluation;

public class AttributeSelectionEvaluationServiceImpl implements
		AttributeSelectionEvaluationService {
	
	private List<ASEvaluation> aSEvaluationList;
	@Required
	public void setASEvaluationList (List<ASEvaluation> list){
		this.aSEvaluationList = list;
	}
	public List<ASEvaluation> getAttributeSelectionEvaluators() {
		return this.aSEvaluationList;
	}

}
