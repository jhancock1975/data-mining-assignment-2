package edu.fau.weka;


public interface OptionsStringService {
	public String getOptionsString( String metaClass, IterationCounts iterations,
			double type2ErrorCost, String baseClassifier);
}
