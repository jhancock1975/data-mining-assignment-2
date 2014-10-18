package edu.fau.weka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
