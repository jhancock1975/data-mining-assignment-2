package edu.fau.weka;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import weka.core.Instances;
import edu.fau.weka.model.PairwiseSimCalcParams;
import edu.fau.weka.service.ArffFileService;
@Component
public class PairwiseSymCalculator {
	static final Logger LOG = LoggerFactory.getLogger(MatlabToArffConverter.class);
	@Autowired
	PairwiseSimCalcParams params;
	@Autowired
	ArffFileService arffSvc;
	public void calculatePairwiseSim(){
		for (String dirName: params.getOutputDirNames()){
			arffSvc.setInputDirectory(dirName);
			while(arffSvc.hasMoreFiles()){
				try {
					Instances data = arffSvc.getNextData();
					LOG.debug(data.relationName());
				} catch (IOException e) {
					LOG.debug("i/o exception when reading arff file");
				}
			}
		}
	}
	public static void main(String[] args){
		ApplicationContext context =   new ClassPathXmlApplicationContext(AssignUtil.SPRING_CONTEXT_FILE_NAME);
		PairwiseSymCalculator calc = (PairwiseSymCalculator) context.getBean("pairwiseSimCalc");
		calc.calculatePairwiseSim();
		((ClassPathXmlApplicationContext) context).close();
	}
}
