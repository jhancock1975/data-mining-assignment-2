package edu.fau.weka.service;

import java.util.List;
import java.util.Map;

import edu.fau.weka.ClassifierWrapper;
import edu.fau.weka.types.ClassifierTypes;

public interface ResultsService {

	public void generateGnuplot(Map<ClassifierTypes, List<ClassifierWrapper>> resultsObj);
	public void generateGnuplot(ClassifierTypes classifierType, 
			 List<ClassifierWrapper> wrapperList);

}
