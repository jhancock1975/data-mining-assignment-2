package edu.fau.weka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.fau.weka.types.ClassifierTypes;

public class AssignUtil {
	public static final String SPRING_CONTEXT_FILE_NAME="appContext.xml";
	
	public static Map<ClassifierTypes, List<ClassifierWrapper>> initClassifierTypeWrapperListMap(){
		Map<ClassifierTypes, List<ClassifierWrapper>> result = 
				new HashMap<ClassifierTypes, List<ClassifierWrapper>>();
		for (ClassifierTypes classifierType : ClassifierTypes.values()){
			result.put(classifierType, new ArrayList<ClassifierWrapper>());
		}
		return result;
	}
	
	public static String aucGnuPlotFileHeader(String xLabel, String yLabel){ 
	StringBuilder sb = new StringBuilder().append("set term postscript eps 22\n")
		.append("set key outside\n")
		.append("set key center top\n")
		.append("set xlabel '").append(xLabel).append("'\n")
		.append("set ylabel '").append(yLabel).append("'\n")
		.append("set linestyle 1 lt 2 lw 3\n")
		.append("set key box linestyle 1 \n")
		.append("set output '");
		return sb.toString();
	}
	
	public static String aucGnuPlotFileHeaderLogXScale(String xLabel, String yLabel){ 
		StringBuilder sb = new StringBuilder().append("set term postscript eps 22\n")
			.append("set key outside\n")
			.append("set key center top\n")
			.append("set xlabel '").append(xLabel).append("'\n")
			.append("set ylabel '").append(yLabel).append("'\n")
			.append("set linestyle 1 lt 2 lw 3\n")
			.append("set logscale x\n")
			.append("set key box linestyle 1 \n")
			.append("set output '");
			return sb.toString();
		}
	
	public static String getStringAfterLastDot(String str){
		if (str == null){
			return str;
		} else if (str.trim().length() == 0) {
			return str;
		} else if ( ! str.contains(".")){
			return str;
		} else {
			return str.substring(str.lastIndexOf('.')+1, str.length());
		}
	}
	public static String abbreviateAttributeEvalName(String attributeEvalName){
		if (attributeEvalName != null){
			if (attributeEvalName.contains("ChiSquaredAttributeEval")){
				return("CS");
			}
			else if (attributeEvalName.contains("GainRatioAttributeEval")){
				return("GR");
			}
			else if (attributeEvalName.contains("InfoGainAttributeEval")){
				return("IG");
			}
			else if (attributeEvalName.contains("ReliefFAttributeEval")){
				return("RF");
			}
			else if (attributeEvalName.contains("ReliefFWeightedWrapper")){
				return("RFW");
			}
			else  if (attributeEvalName.contains("SymmetricalUncertAttributeEval")){
				return("SU");
			} else {
				return attributeEvalName;
			}
			
		} else {
			return attributeEvalName;
		}
	}
	public static String abbreviateClassifierName(String classifierName){
		if (classifierName != null){
			if (classifierName.contains("IBk")){
				return("5NN");
			}
			else if (classifierName.contains("NaiveBayes")){
				return("NB");
			} else {
				return classifierName;
			}
			
		} else {
			return classifierName;
		}
	}
	public static Random rand = new Random();
}
