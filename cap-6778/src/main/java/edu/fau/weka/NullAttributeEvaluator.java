package edu.fau.weka;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.AttributeEvaluator;
import weka.core.Instances;

public class NullAttributeEvaluator extends ASEvaluation implements  AttributeEvaluator{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void buildEvaluator(Instances data) throws Exception {
		// TODO Auto-generated method stub

	}

	public double evaluateAttribute(int attribute) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
