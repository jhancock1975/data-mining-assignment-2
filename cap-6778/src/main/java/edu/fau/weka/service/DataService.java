package edu.fau.weka.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import weka.core.Instances;

public interface DataService {
	public Instances getData() throws IOException, FileNotFoundException;
}
