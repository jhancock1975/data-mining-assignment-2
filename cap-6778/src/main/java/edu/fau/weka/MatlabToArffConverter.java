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

	static String outputDir = inputDir + "output/";
	static List<Double> peturbLevels = Lists.newArrayList(0.0, 0.1, 0.2, 0.3, 0.4, 0.9);
	public static final int numFolds = 10;

	public static void main(String[] args){
		
		MatlabToArffConverter converter =  new MatlabToArffConverter();
		for (String inFileName: inputFiles){
			File inFile = new File(inFileName);
			try {
				converter.convert(inFileName);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*for (Double peturbLevel: peturbLevels){
				String peturbLevelDirName = outputDir + "/" +inFile.getName() + "/" + peturbLevel + "/";
				
				for (int i = 0; i< numFolds; i++){
					try {
						converter.convertSamplePeturb(inFileName, peturbLevelDirName, peturbLevel, i, 0.25);
					} catch (FileNotFoundException e) {
						LOG.debug("could not open file " + e.getMessage());
					} catch (IOException e) {
						LOG.debug("io exception " + e.getMessage());
					}
				}
			}*/
		}
	}

	public void convert(String inFileName) throws FileNotFoundException, IOException{
		String outFileName = generateArffFileName(inFileName);
		MatFileReader reader = new MatFileReader(inFileName);
		File outFile = new File(outFileName);
		PrintWriter outFilePw = new PrintWriter(outFile);
		Map<String, MLArray> map  = reader.getContent();
		printAttributes(map.get(ATTRIBUTE_LIST_KEY), outFilePw, outFile.getName());
		printClassAttributes(map.get(CLASS_ATTRIBUTE_LIST_KEY), outFilePw);
		printData(((MLDouble) reader.getMLArray(DATA_KEY)).getArray(), 
				(MLDouble) map.get(CLASS_ATTRIBUTE_LIST_KEY), outFilePw);
		outFilePw.close();
	}

	private String generateArffFileName(String matFileName){
		return matFileName.substring(0, matFileName.lastIndexOf('.')) + ".arff";
	}

	private String generatePeturbedFileName(File matFile, double peturbLevel, int foldNumber){
		return matFile.getName().substring(0, matFile.getName().lastIndexOf('.')) + "-"+peturbLevel+"-"+foldNumber+".arff";
	}

	public void convertSamplePeturb(String inFileName, String  peturbLevelDirName, double peturbation, 
			int foldNumber, double sampleSize)throws FileNotFoundException, IOException{
		
		String outFileName = generatePeturbedFileName(new File(inFileName), peturbation, foldNumber);
		
		File outDir = new File(peturbLevelDirName);
		if (! outDir.exists()){
			outDir.mkdirs();
		}
		File outFile = new File(peturbLevelDirName+"/"+outFileName);
		
		MatFileReader reader = new MatFileReader(inFileName);
		
		PrintWriter outFilePw = new PrintWriter(outFile);
		
		Map<String, MLArray> map  = reader.getContent();
		
		printAttributes(map.get(ATTRIBUTE_LIST_KEY), outFilePw, outFile.getName());
		
		printClassAttributes(map.get(CLASS_ATTRIBUTE_LIST_KEY), outFilePw);
		
		samplePeturbPrintData(((MLDouble) reader.getMLArray(DATA_KEY)).getArray(), 
				((MLDouble) map.get(CLASS_ATTRIBUTE_LIST_KEY)).getArray(), outFilePw,  peturbation,  sampleSize);
		
		outFilePw.close();
	}

	public static final String ATTRIBUTE_MARKER = "@ATTRIBUTE";
	public static final String REAL_ATTRIBUTE_TYPE_NAME = "REAL";
	public static final String ARFF_FILE_HEADER="@RELATION";
	/**
	 * prints attribute names to arff file
	 * such as, "@ATTRIBUTE GENE1835X REAL" 
	 * @param mlArray
	 * @param outFile
	 */
	private void printAttributes(MLArray mlArray, PrintWriter outFile, String relationName) {
		MLCell cells = (MLCell) mlArray;
		outFile.println(ARFF_FILE_HEADER + " " + relationName);
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
	public Set<Double> doubleArrToSet(double[] doubles){
		Set<Double> classValues = new HashSet<Double>();
		for (double d: doubles){
			classValues.add(d);
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

	private void samplePeturbPrintData(double[][] data, double[][] attributeList,
			PrintWriter outFile, double peturbation, double sampleSize) {
		if (sampleSize > data.length){
			throw new RuntimeException("sample size is larger than size of data, cannot sample without replacement");
		}

		outFile.println(DATA_MARKER);
		double[] attributes = new double[ attributeList.length];
		for (int i = 0; i < attributeList.length; i++){
			attributes[i] = attributeList[i][0];
		}
		
		Set<Double> classValues = doubleArrToSet(attributes);
		Double[] classValuesArr = classValues.toArray(new Double[classValues.size()]);
		
		List<double[]> dataList = Arrays.asList(data);
		int numIterations = (int) (sampleSize * dataList.size());
		
		if (sampleSize <= 0){
			throw new RuntimeException("sample size zero");
		}
		
		for (int i=0; i < numIterations; i++){

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
				outFile.print(data[curListIndex][j]+",");
			}
			//sample without replacement
			dataList.set(curListIndex, null); 

			//randomly inject noise with probability perturbation
			if (AssignUtil.rand.nextDouble() < peturbation){
				outFile.println(injectNoise(attributes[curListIndex], classValuesArr));
			} else {
				outFile.println(attributes[curListIndex]);
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
