package edu.fau.weka;

import java.util.List;
import java.util.Map;

public interface ResultsService {

	public void generateGnuplot(Map<ClassifierTypes, List<ClassifierWrapper>> resultsObj);
	public void generateGnuplot(ClassifierTypes classifierType, 
			 List<ClassifierWrapper> wrapperList);

}
