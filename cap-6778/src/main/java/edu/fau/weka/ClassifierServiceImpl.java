package edu.fau.weka;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import weka.classifiers.Classifier;

@Service
public class ClassifierServiceImpl implements ClassifierService{
	@Autowired 
	private OptionsStringService optionsService;
	public List<ClassifierWrapper> getClassifiers() throws ClassifierServiceException {
		List<ClassifierWrapper> result = new ArrayList<ClassifierWrapper>();
		
		try {
			for(MetaClassifierTypes meta: MetaClassifierTypes.values()){
				for (IterationCounts count: IterationCounts.values()){
					for (String costMatrix: CostMatrices.values){
						for (BaseClassifierTypes base: BaseClassifierTypes.values()){
							ClassifierWrapper wrapper = new ClassifierWrapper();
							String[] options  = weka.core.Utils.splitOptions(
									optionsService.getOptionsString(meta, count, costMatrix, base));
							Classifier adaClasser =  Classifier.forName(meta.getClassType().getName(), options);
							wrapper.setClassifier(adaClasser);
							result.add(wrapper);
						}
					}
				}
			}
			//String[] options = weka.core.Utils.splitOptions("-P 100 -S 1 -I 10 -W weka.classifiers.meta.CostSensitiveClassifier -- -cost-matrix \"[0.0 2.0; 1.0 0.0]\" -S 1 -W weka.classifiers.trees.DecisionStump");
			//String[] options = weka.core.Utils.splitOptions("-P 100 -S 1 -I 10 -W weka.classifiers.meta.CostSensitiveClassifier -- -cost-matrix \"[0.0 2.0; 1.0 0.0]\" -S 1 -W weka.classifiers.trees.DecisionStump");
			//adaClasser.setOptions(options);
			//wrapper.setClassifier(adaClasser);
			//result.add(wrapper);
			
		} catch(Exception e){
			ClassifierWrapper wrapper = new ClassifierWrapper();
			ClassifierServiceException ex = new ClassifierServiceException("classifier creation exception ", e);
			wrapper.setException(ex);
		}
		return result;
	}

}
