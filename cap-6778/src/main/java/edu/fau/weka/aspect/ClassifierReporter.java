package edu.fau.weka.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.fau.weka.model.ClassifierResults;
import edu.fau.weka.service.ClassifierResultsService;
/**
 * this is here as an example of how to use AOP
 * we have not thought of a good use-case for it
 * for this project yet
 * @author john
 *
 */
@Aspect
public class ClassifierReporter {
	Logger LOG;
	@Autowired
	ClassifierResultsService classifierSvc;
	/*@AfterReturning(pointcut="execution(* edu.fau.weka.Assignment4.runClassifiers(..))",
			returning="result")
	public void storeResults(JoinPoint joinPoint, Object result) {
		ClassifierResults classifierResults = (ClassifierResults) result;
		classifierSvc.saveResults(classifierResults);
		LOG = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
		LOG.debug("Intecepted class " + joinPoint.getTarget().getClass().getName());
		LOG.debug("filter class name " + classifierResults.getFilterClassName());
		LOG.debug(classifierResults.getEvaluation().toSummaryString());
		LOG.debug("false positive rate " + classifierResults.getEvaluation().falsePositiveRate(0));
		LOG.debug("false negative rate " + classifierResults.getEvaluation().falsePositiveRate(1));
		LOG.debug("negative class auc " + classifierResults.getEvaluation().areaUnderROC(0));
		LOG.debug("positive class auc " + classifierResults.getEvaluation().areaUnderROC(1));
		LOG.debug("weighted average auc " + classifierResults.getEvaluation().weightedAreaUnderROC());
	}*/
}
