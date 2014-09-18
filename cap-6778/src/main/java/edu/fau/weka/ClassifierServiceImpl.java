package edu.fau.weka;

import org.springframework.stereotype.Service;


import weka.classifiers.meta.AdaBoostM1;

@Service
public class ClassifierServiceImpl implements ClassifierService{

	public ClassifierWrapper getNextClassifier() throws ClassifierServiceException {
		ClassifierWrapper result = new ClassifierWrapper();
		AdaBoostM1 adaClasser = new AdaBoostM1();
		try {
			String[] options = weka.core.Utils.splitOptions("-P 100 -S 1 -I 10 -W weka.classifiers.meta.CostSensitiveClassifier -- -cost-matrix \"[0.0 2.0; 1.0 0.0]\" -S 1 -W weka.classifiers.trees.DecisionStump");
			adaClasser.setOptions(options);
			result.setClassifier(adaClasser);
		} catch(Exception e){
			ClassifierServiceException ex = new ClassifierServiceException("classifier creation exception ", e);
			result.setException(ex);
		}
		return result;
	}

}
