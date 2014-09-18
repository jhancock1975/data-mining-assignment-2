/*
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/*
 *    WekaDemo.java
 *    Copyright (C) 2009 University of Waikato, Hamilton, New Zealand
 *
 */

package edu.fau.weka;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import weka.classifiers.Evaluation;
import weka.classifiers.meta.AdaBoostM1;
import weka.core.Instances;

public class Assignment2 {
	static final Logger LOG = LoggerFactory.getLogger(Assignment2.class);
	@Autowired
	static ClassifierService classSvc;
	public static void main(String[] args) throws Exception{
		ApplicationContext context =   new ClassPathXmlApplicationContext("appContext.xml");
				
		BufferedReader reader = new BufferedReader(
				new FileReader("/home/john/Documents/school/fall-2014/data-mining/assignments/assignment-1/Lymphoma96x4026.arff"));
		Instances data = new Instances(reader);
		reader.close();
		   data.setClassIndex(data.numAttributes() - 1); 
		
		AdaBoostM1 adaClasser = new AdaBoostM1();
		weka.classifiers.functions.SMO scheme = new weka.classifiers.functions.SMO();
		String[] options = weka.core.Utils.splitOptions("-P 100 -S 1 -I 10 -W weka.classifiers.meta.CostSensitiveClassifier -- -cost-matrix \"[0.0 2.0; 1.0 0.0]\" -S 1 -W weka.classifiers.trees.DecisionStump");
		adaClasser.setOptions(options);
		
		Evaluation eval = new Evaluation(data);
		
		eval.crossValidateModel(classSvc.getNextClassifier().getClassifier(), data, 10, new Random(1));
		
		
		LOG.debug("false positive rate " + eval.falsePositiveRate(0));
		LOG.debug("false negative rate " + eval.falsePositiveRate(1));
		((ClassPathXmlApplicationContext ) context).close();
	}
}
