package edu.fau.weka;

import java.util.List;
import java.util.Map;

public interface ClassifierGeneratingService {
	Map<ClassifierTypes, List<ClassifierWrapper>> getClassifiers();
}
