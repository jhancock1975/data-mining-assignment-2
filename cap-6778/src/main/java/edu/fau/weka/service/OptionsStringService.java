package edu.fau.weka.service;

import edu.fau.weka.types.IterationCounts;


public interface OptionsStringService {
	public String getOptionsString( String metaClass, IterationCounts iterations,
			double type2ErrorCost, String baseClassifier);
}
