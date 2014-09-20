package edu.fau.weka;

import org.springframework.stereotype.Service;

@Service
public class OptionsStringServiceImpl implements OptionsStringService {
	private String getCostMatrix(double type2ErrorCost){
		return "\"[0.0 " + type2ErrorCost + "; 1.0 0.0]\"";
	}
	public String getOptionsString(String metaClass, IterationCounts iterations, double type2ErrorCost,
			String baseClassifier) {
		if (baseClassifier != null && baseClassifier.contains("J48")){
			//@TODO
			//this seems hackish there should
			//be a better way to set options
			baseClassifier = baseClassifier + " -- -C 0.25 -M 2";
		}
		return  "-cost-matrix "
				+ getCostMatrix(type2ErrorCost)
				+" -S 1 "
				+" -W "
				+ metaClass
				+ " -- -P 100 -S 1 -I " + iterations.intVal() 
				+ " -W " 
				+ baseClassifier;
	}
}
