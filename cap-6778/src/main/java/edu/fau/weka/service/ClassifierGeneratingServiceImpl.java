package edu.fau.weka.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.fau.weka.Assign2Util;
import edu.fau.weka.Assignment2;
import edu.fau.weka.ClassifierWrapper;
import edu.fau.weka.types.ClassifierTypes;
import edu.fau.weka.types.IterationCounts;

import weka.classifiers.Classifier;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.Bagging;
import weka.classifiers.meta.CostSensitiveClassifier;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;

@Service
public class ClassifierGeneratingServiceImpl implements ClassifierGeneratingService{
	static final Logger LOG = LoggerFactory.getLogger(Assignment2.class);
	@Autowired 
	private OptionsStringService optionsService;

	@Autowired
	private MetaClassService metas;

	@Autowired
	private BaseClassService bases;

	@Autowired
	private Type2ErrorRatesService type2ErrorCostSvc;

	@Autowired
	private CostSensitiveClassifier cheapo;

	public Map<ClassifierTypes, List<ClassifierWrapper>> getClassifiers() throws ClassifierServiceException {
		
		Map<ClassifierTypes, List<ClassifierWrapper>> result
			= Assign2Util.initClassifierTypeWrapperListMap();

		for (IterationCounts count: IterationCounts.values()){

			for(Classifier  meta:  metas.getClassifiers()){

				for (Classifier base: bases.getClassifiers()){

					for (Double type2Cost: type2ErrorCostSvc.getType2ErrorRates()){

						ClassifierWrapper wrapper = new ClassifierWrapper();

						try {
							String optionsStr = optionsService.getOptionsString(meta.getClass().getName(), count, type2Cost, base.getClass().getName());
							String[] options  = weka.core.Utils.splitOptions(optionsStr);
							
							Classifier classer =  Classifier.forName(cheapo.getClass().getName(), options);							
							wrapper.setClassifier(classer);
							wrapper.setClassifierType(getClassifierType(meta.getClass(), base.getClass(), count.intVal()));
							wrapper.setErrorCost(type2Cost);
							wrapper.setStatus(ClassifierStatus.CONSTRUCT_SUCCESS);
							List<ClassifierWrapper> wrapperList = result.get(wrapper.getClassifierType());
							wrapperList.add(wrapper);

						} catch(Exception e){
							LOG.error("exception constructing classifier");
							LOG.error("exception message: " + e.getMessage());
							wrapper.setStatus(ClassifierStatus.CONSTRUCT_ERROR);
						}

					}
					
				}
			}
		}

		return result;
	}

		
	private ClassifierTypes getClassifierType(Class metaClass, Class baseClass, Integer iterations) {
		if (metaClass.equals(AdaBoostM1.class) && baseClass.equals(J48.class) 
				&& iterations == IterationCounts.ten.intVal()){
			return ClassifierTypes.BOOSTING_J48_10;
		} else if (metaClass.equals(AdaBoostM1.class) && baseClass.equals(J48.class) 
				&& iterations == IterationCounts.twentyFive.intVal()){
			return ClassifierTypes.BOOSTING_J48_25;
		}
		else if (metaClass.equals(Bagging.class) && baseClass.equals(J48.class)
				&& iterations == IterationCounts.ten.intVal()){
			return ClassifierTypes.BAGGING_J48_10;
		}
		else if (metaClass.equals(Bagging.class) && baseClass.equals(J48.class)
				&& iterations == IterationCounts.twentyFive.intVal()){
			return ClassifierTypes.BAGGING_J48_25;
		}
		else if (metaClass.equals(AdaBoostM1.class) && baseClass.equals(DecisionStump.class)
				&& iterations == IterationCounts.ten.intVal()){
			return ClassifierTypes.BOOSTING_DECISION_STUMP_10;
		}
		else if (metaClass.equals(AdaBoostM1.class) && baseClass.equals(DecisionStump.class)
				&& iterations == IterationCounts.twentyFive.intVal()){
			return ClassifierTypes.BOOSTING_DECISION_STUMP_25;
		}
		else if (metaClass.equals(Bagging.class) && baseClass.equals(DecisionStump.class)
				&& iterations == IterationCounts.ten.intVal()){
			return ClassifierTypes.BAGGING_DECISION_STUMP_10;
		}
		else if (metaClass.equals(Bagging.class) && baseClass.equals(DecisionStump.class)
				&& iterations == IterationCounts.twentyFive.intVal()){
			return ClassifierTypes.BAGGING_DECISION_STUMP_25;
		} else {
			throw new RuntimeException("classifier created that is not required for assignment");
		}
	}

}
