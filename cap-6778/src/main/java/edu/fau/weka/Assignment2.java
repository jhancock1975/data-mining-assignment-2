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
 *    Assignment2.java
 *    Copyright (C) 2014 John Hancock, Florida Atlantic University
 *
 */

package edu.fau.weka;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import weka.classifiers.Evaluation;
import weka.core.Instances;
@Component
public class Assignment2 {

	@Autowired
	private ClassifierGeneratingService classSvc;
	@Autowired
	private DataService dataService;
	@Autowired
	private ResultsService resultsService;
	
	static final Logger LOG = LoggerFactory.getLogger(Assignment2.class);

	private Map<ClassifierTypes, List<ClassifierWrapper>> initResultsObj(){
		Map<ClassifierTypes, List<ClassifierWrapper>> result = 
				new HashMap<ClassifierTypes, List<ClassifierWrapper>>();
		for (ClassifierTypes classifierType : ClassifierTypes.values()){
			result.put(classifierType, new ArrayList<ClassifierWrapper>());
		}
		return result;
	}
	
	public void runClassifications(){
		try {
			Instances data = dataService.getData();
			
			Evaluation eval = new Evaluation(data);
			
			Map<ClassifierTypes, List<ClassifierWrapper>> classifierMap = classSvc.getClassifiers(); 
			
			
			for (ClassifierTypes classifierType: ClassifierTypes.values()){

				List<ClassifierWrapper> wrapperList = classifierMap.get(classifierType);
				
				for (ClassifierWrapper wrapper: wrapperList){
					
					if (wrapper.getStatus() == ClassifierStatus.CONSTRUCT_SUCCESS){

						eval.crossValidateModel(wrapper.getClassifier(), data, 10, new Random(1));

						wrapper.setEval(eval);

						LOG.debug("classifier " + wrapper.getClassifierType().toString());
						LOG.debug("Type II error cost " + wrapper.getErrorCost());
						LOG.debug("false positive rate " + eval.falsePositiveRate(0));
						LOG.debug("false negative rate " + eval.falsePositiveRate(1));
						LOG.debug("negative class auc " + eval.areaUnderROC(0));
						LOG.debug("positive class auc " + eval.areaUnderROC(1));

					} else if (wrapper.getStatus() == ClassifierStatus.CONSTRUCT_ERROR){
						LOG.error("Error constructing classifier" + wrapper.toString());
					} else {
						LOG.error("Unknown status after attempt to construct classifier");
					}
				}
				//@Todo
				//enforce lists having all
				//the same type of classifier
				//@Todo
				//use AOP -the results service should react to a list
				//in the map having all of its evaluations set
				resultsService.generateGnuplot(classifierType, wrapperList);
				LOG.debug("Generated plot for classifier type " + classifierType.toString());
			}

		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage());
		} catch (IOException e) {
			LOG.error(e.getMessage());
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}
	public static void main(String[] args) throws Exception{
		ApplicationContext context =   new ClassPathXmlApplicationContext("appContext.xml");
		Assignment2 assign2 = (Assignment2) context.getBean("assign2");
		assign2.runClassifications();
		((ClassPathXmlApplicationContext ) context).close();
	}
}
