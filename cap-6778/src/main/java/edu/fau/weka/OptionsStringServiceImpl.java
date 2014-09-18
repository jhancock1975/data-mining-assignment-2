package edu.fau.weka;

import org.springframework.stereotype.Service;

@Service
public class OptionsStringServiceImpl implements OptionsStringService {

	public String getOptionsString(MetaClassifierTypes meta,
			IterationCounts iterations, String costMatrix,
			BaseClassifierTypes baseClassifier) {
		return meta.toString() + iterations.intVal() 
				+ " -W weka.classifiers.meta.CostSensitiveClassifier -- -cost-matrix \"" 
				+ costMatrix
				+ "\" -S 1 -W " 
				+ baseClassifier.toString();
	}

}
