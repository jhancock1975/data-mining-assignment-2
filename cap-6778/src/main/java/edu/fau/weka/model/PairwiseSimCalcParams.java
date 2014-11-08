package edu.fau.weka.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PairwiseSimCalcParams {
	@Value("#{'${pairwiseSimParams.inputDir}'}")
	private String inputDirName;
	@Value("#{'${pairwiseSimParams.outputDirNames}'.split(',')}") 
	private List<String> outputDirNames;
	
	public List<String> getOutputDirNames(){
		return this.outputDirNames;
	}
	
	public String getInputDirName() {
		return inputDirName;
	}
}
