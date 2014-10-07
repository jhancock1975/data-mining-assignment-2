package edu.fau.weka;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weka.attributeSelection.ChiSquaredAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

public class Assignment4 {
	static final Logger LOG = LoggerFactory.getLogger(Assignment4.class);
	public static void main(String args[]){
		DataSource source;
		try {
			source = new DataSource(args[0]);
			Instances data = source.getDataSet();
			data.setClassIndex(data.numAttributes() - 1);
			
			ChiSquaredAttributeEval chiSqEval =  new ChiSquaredAttributeEval();
						
			Ranker search = new Ranker();
			search.setNumToSelect(20);
			
			NaiveBayes base = new NaiveBayes();
			
			AttributeSelection filter = new AttributeSelection();
			filter.setSearch(search);
			filter.setInputFormat(data);			
			filter.setEvaluator(chiSqEval);

			Evaluation evaluation = new Evaluation(data);
			
			evaluation.crossValidateModel(base, Filter.useFilter(data, filter), 10, new Random(1));
			LOG.debug(evaluation.toSummaryString());
			LOG.debug("false positive rate " + evaluation.falsePositiveRate(0));
			LOG.debug("false negative rate " + evaluation.falsePositiveRate(1));
			LOG.debug("negative class auc " + evaluation.areaUnderROC(0));
			LOG.debug("positive class auc " + evaluation.areaUnderROC(1));
			LOG.debug("weighted average auc " + evaluation.weightedAreaUnderROC());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
