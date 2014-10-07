package edu.fau.weka.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.fau.weka.ClassifierResultsImpl;

@Aspect
public class ClassifierReporter {
	Logger LOG;
	@AfterReturning(pointcut = "execution(* edu.fau.weka.ClassifierResults.setEvaluation(..))", 
			returning ="result")
	public void logBefore(JoinPoint joinPoint, Object result) {
		//ClassifierResultsImpl classifierResults = (ClassifierResultsImpl) result;
		ClassifierResultsImpl classifierResults = (ClassifierResultsImpl) joinPoint.getTarget();
		LOG = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
		LOG.debug(classifierResults.getEvaluation().toSummaryString());
		LOG.debug("false positive rate " + classifierResults.getEvaluation().falsePositiveRate(0));
		LOG.debug("false negative rate " + classifierResults.getEvaluation().falsePositiveRate(1));
		LOG.debug("negative class auc " + classifierResults.getEvaluation().areaUnderROC(0));
		LOG.debug("positive class auc " + classifierResults.getEvaluation().areaUnderROC(1));
		LOG.debug("weighted average auc " + classifierResults.getEvaluation().weightedAreaUnderROC());
		LOG.debug("class name" + classifierResults.getFilterClassName());
		
	}
}
