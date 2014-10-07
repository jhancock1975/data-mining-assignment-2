package edu.fau.weka;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.ChiSquaredAttributeEval;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

@Component
public class Assignment4Impl implements Assignment4 {
		@Autowired
		ClassifierResults results;
	public void runClassifier(String dataSourceName) throws Exception {
		DataSource source;
		
			source = new DataSource(dataSourceName);
			Instances data = source.getDataSet();
			data.setClassIndex(data.numAttributes() - 1);

			ASEvaluation chiSqEval =  new ChiSquaredAttributeEval();
			
			results.setFilterClassName(chiSqEval.getClass().getName());

			Ranker search = new Ranker();
			search.setNumToSelect(20);
			results.setFeatureSetSize(20);

			NaiveBayes base = new NaiveBayes();
			results.setClassifierClassName(base.getClass().getName());

			AttributeSelection filter = new AttributeSelection();
			filter.setSearch(search);
			filter.setInputFormat(data);			
			filter.setEvaluator(chiSqEval);

			Evaluation evaluation= new Evaluation(data);

			evaluation.crossValidateModel(base, Filter.useFilter(data, filter), 10, new Random(1));
			results.setEvaluation(evaluation);
	}

	 public static void main(String[] args){
		ApplicationContext context =   new ClassPathXmlApplicationContext(AssignUtil.SPRING_CONTEXT_FILE_NAME);
		Assignment4 assign4 = (Assignment4 ) context.getBean("assign4");
		try {
			assign4.runClassifier(args[0]);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			((ClassPathXmlApplicationContext ) context).close();
		}
	}
}
