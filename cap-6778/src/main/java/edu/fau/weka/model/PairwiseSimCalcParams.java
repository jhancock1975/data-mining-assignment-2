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
	
	
	public void setOutputDirNames(List<String> outputDirNames){
		this.outputDirNames = outputDirNames;
	}
	public List<String> getOutputDirNames(){
		return this.outputDirNames;
	}
	
	public String getInputDirName() {
		return inputDirName;
	}
		
	public void setInputDirName(String inputDirName) {
		this.inputDirName = inputDirName;
	}
}
