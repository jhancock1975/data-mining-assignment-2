package edu.fau.weka;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ResultsObj implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Map<ClassifierTypes, List<ClassifierWrapper>> results;
	public ResultsObj(){
		results = new HashMap<ClassifierTypes, List<ClassifierWrapper>>();
		for (ClassifierTypes classifierType: ClassifierTypes.values()){
			results.put(classifierType, new ArrayList<ClassifierWrapper>());
		}
	}
	public void addEval(ClassifierTypes classifierType, ClassifierWrapper wrapper){
		List<ClassifierWrapper> wrapperList = results.get(classifierType);
		wrapperList.add(wrapper);
	}
	public List<ClassifierWrapper> getWrapperList(ClassifierTypes classifierType){
		return results.get(classifierType);
	}
}
