package edu.fau.weka.service;

import java.util.List;

import weka.attributeSelection.ASEvaluation;

public interface AttributeSelectionEvaluationService {
	public List<ASEvaluation> getAttributeSelectionEvaluators();
}
