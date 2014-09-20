package edu.fau.weka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.fau.weka.types.ClassifierTypes;

public class Assign2Util {
	public static Map<ClassifierTypes, List<ClassifierWrapper>> initClassifierTypeWrapperListMap(){
		Map<ClassifierTypes, List<ClassifierWrapper>> result = 
				new HashMap<ClassifierTypes, List<ClassifierWrapper>>();
		for (ClassifierTypes classifierType : ClassifierTypes.values()){
			result.put(classifierType, new ArrayList<ClassifierWrapper>());
		}
		return result;
	}
}
