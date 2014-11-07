package edu.fau.weka;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLCell;
import com.jmatio.types.MLChar;
import com.jmatio.types.MLDouble;



public class MatlabToArffConverter {

	static final Logger LOG = LoggerFactory.getLogger(MatlabToArffConverter.class);

	public static final String ATTRIBUTE_LIST_KEY = "geneID";
	public static final String CLASS_ATTRIBUTE_LIST_KEY = "Y";
	public static final String DATA_KEY = "X";

	public static final String inputDir= "/home/john/Documents/school/fall-2014/data-mining/assignments/term-paper/";
	static String[] inputFiles = new String[]{
			inputDir + "CLL-SUB-111.mat",
			inputDir + "GLA-BRA-180.mat",
			inputDir + "GLI-85.mat",
			inputDir + "TOX-171.mat"
	};
	
	List<Double> peturbLevels = Lists.newArrayList(0.0, 0.1, 0.2, 0.3, 0.4, 0.9);
	
	public static void main(String[] args){
		MatlabToArffConverter converter =  new MatlabToArffConverter();
		for (String inFileName: inputFiles){
			try {
				converter.convert(inFileName);
			} catch (FileNotFoundException e) {
				LOG.debug("could not open file " + e.getMessage());
			} catch (IOException e) {
				LOG.debug("io exception " + e.getMessage());
			}
		}
	}
	
	public void convert(String inFileName) throws FileNotFoundException, IOException{
		String outFileName = generateArffFileName(inFileName);
		MatFileReader reader = new MatFileReader(inFileName);
		PrintWriter outFile = new PrintWriter(new File(outFileName));
		Map<String, MLArray> map  = reader.getContent();
		printAttributes(map.get(ATTRIBUTE_LIST_KEY), outFile);
		printClassAttributes(map.get(CLASS_ATTRIBUTE_LIST_KEY), outFile);
		printData(((MLDouble) reader.getMLArray(DATA_KEY)).getArray(), 
				(MLDouble) map.get(CLASS_ATTRIBUTE_LIST_KEY), outFile);
		outFile.close();
	}

	private String generateArffFileName(String matFileName){
		return matFileName.substring(0, matFileName.lastIndexOf('.')) + ".arff";
	}
	
	private String generatePeturbedFileName(String matFileName, double peturbLevel){
		return matFileName.substring(0, matFileName.lastIndexOf('.')) + "-"+peturbLevel+".arff";
	}
	
	public void convertSamplePeturb(String inFileName, double peturbation, int sampleSize)throws FileNotFoundException, IOException{
		String outFileName = generatePeturbedFileName(inFileName, peturbation);
		MatFileReader reader = new MatFileReader(inFileName);
		PrintWriter outFile = new PrintWriter(new File(outFileName));
		Map<String, MLArray> map  = reader.getContent();
		printAttributes(map.get(ATTRIBUTE_LIST_KEY), outFile);
		printClassAttributes(map.get(CLASS_ATTRIBUTE_LIST_KEY), outFile);
		samplePeturbPrintData(((MLDouble) reader.getMLArray(DATA_KEY)).getArray(), 
				(MLDouble) map.get(CLASS_ATTRIBUTE_LIST_KEY), outFile,  peturbation,  sampleSize);
		outFile.close();
	}
	
	public static final String ATTRIBUTE_MARKER = "@ATTRIBUTE";
	public static final String REAL_ATTRIBUTE_TYPE_NAME = "REAL";
	public static final String ARFF_FILE_HEADER="@RELATION figure1";
	/**
	 * prints attribute names to arff file
	 * such as, "@ATTRIBUTE GENE1835X REAL" 
	 * @param mlArray
	 * @param outFile
	 */
	private void printAttributes(MLArray mlArray, PrintWriter outFile) {
		MLCell cells = (MLCell) mlArray;
		outFile.println(ARFF_FILE_HEADER);
		for (int i = 0; i < cells.getSize(); i++){
			outFile.print(ATTRIBUTE_MARKER + " ");
			MLChar mlChar = ((MLChar) cells.get(i));
			for (int m = 0; m < mlChar.getM(); m++){
				for (int n = 0; n < mlChar.getN(); n++){
					outFile.print(mlChar.getChar(m, n));
				}
			}
			//TODO support multiple types
			outFile.println(" " + REAL_ATTRIBUTE_TYPE_NAME);
		}

	}

	public static final String CLASS_ATTRIBUTE_MARKER = "@ATTRIBUTE CLASS";
	private void printClassAttributes(MLArray mlArray, PrintWriter outFile) {
		outFile.print(CLASS_ATTRIBUTE_MARKER);
		outFile.print(" {");
		MLDouble cells = (MLDouble) mlArray;
		Set<Double> classValues = mLDoubleListToSet(cells);
		StringBuilder sb = new StringBuilder();
		for (Double d: classValues){
			sb.append(d+",");
		}
		if (sb.length() > 0){
			sb.setLength(sb.length() -1);
		}
		outFile.println(sb.toString() + "}");
	}
	
	public Set<Double> mLDoubleListToSet(MLDouble cells){
		Set<Double> classValues = new HashSet<Double>();
		for (int i = 0; i < cells.getSize(); i++){
			classValues.add(cells.get(i));
		}
		return classValues;
	}
	public static final String DATA_MARKER = "@DATA";
	private void printData(double[][] data, MLDouble attributeList,
			PrintWriter outFile) {
		outFile.println(DATA_MARKER);
		for (int i=0; i < data.length; i++){
			for (int j=0; j < data[i].length; j++){
				outFile.print(data[i][j]+",");
			}
			outFile.println(attributeList.get(i));
		}	
	}
	
	private void samplePeturbPrintData(double[][] data, MLDouble attributeList,
			PrintWriter outFile, double peturbation, int sampleSize) {
		if (sampleSize > data.length){
			throw new RuntimeException("sample size is larger than size of data, cannot sample without replacement");
		}
		
		Set<Double> classValues = mLDoubleListToSet(attributeList);
		Double[] classValuesArr = (Double[]) classValues.toArray();
		
		List<double[]> dataList = Arrays.asList(data);
		for (int i=0; i < sampleSize; i++){
			
			//figure out which samples we can still choose from
			List<Integer> availableSamples = new ArrayList<Integer>();
			for (int j = 0; j < dataList.size(); j++){
				if (dataList.get(j) != null){
					availableSamples.add(j);
				}
			}

			//randomly choose an unused sample
			int curListIndex = availableSamples.get(AssignUtil.rand.nextInt( availableSamples.size()));
			
			double[] curList = dataList.get(curListIndex);
			for (int j=0; j < curList.length; j++){
				outFile.print(data[i][j]+",");
			}
			//sample without replacement
			dataList.set(curListIndex, null); 
			
			//randomly inject noise with probability peturbation
			if (AssignUtil.rand.nextDouble() < peturbation){
				outFile.println(injectNoise(attributeList.get(curListIndex), classValuesArr));
			} else {
				outFile.println(attributeList.get(curListIndex));
			}
		}
	}

	
	private double injectNoise(Double curClass, Double[] classValuesArr) {
			double newValue = classValuesArr[AssignUtil.rand.nextInt(classValuesArr.length)];
			while (newValue == curClass){
				 newValue = classValuesArr[AssignUtil.rand.nextInt(classValuesArr.length)];
			}
		return newValue;
	}

}
