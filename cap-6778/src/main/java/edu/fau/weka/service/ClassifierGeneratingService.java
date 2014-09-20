package edu.fau.weka.service;

import java.util.List;
import java.util.Map;

import edu.fau.weka.ClassifierWrapper;
import edu.fau.weka.types.ClassifierTypes;

public interface ClassifierGeneratingService {
	Map<ClassifierTypes, List<ClassifierWrapper>> getClassifiers();
}
