package edu.fau.weka;

public interface OptionsStringService {
	public String getOptionsString(MetaClassifierTypes meta, IterationCounts iterations,
			String costMatrix, BaseClassifierTypes baseClassifier);
}
