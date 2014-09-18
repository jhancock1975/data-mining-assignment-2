package edu.fau.weka;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import weka.core.Instances;


public class DataServiceImpl implements DataService{
	private String fileName;
	

	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public Instances getData() throws IOException, FileNotFoundException{
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		Instances data = new Instances(reader);
		reader.close();
		data.setClassIndex(data.numAttributes() - 1);
		return data;
	} 
}
