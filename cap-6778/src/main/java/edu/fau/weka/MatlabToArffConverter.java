package edu.fau.weka;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		Set<Double> classValues = new HashSet<Double>();
		for (int i = 0; i < cells.getSize(); i++){
			classValues.add(cells.get(i));
		}
		StringBuilder sb = new StringBuilder();
		for (Double d: classValues){
			sb.append(d+",");
		}
		if (sb.length() > 0){
			sb.setLength(sb.length() -1);
		}
		outFile.println(sb.toString() + "}");
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
}
