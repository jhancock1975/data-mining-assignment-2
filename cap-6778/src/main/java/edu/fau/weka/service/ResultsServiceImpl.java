package edu.fau.weka.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.fau.weka.Assignment2;
import edu.fau.weka.ClassifierWrapper;
import edu.fau.weka.types.ClassifierTypes;
import edu.fau.weka.types.ErrorTypes;
@Service
public class ResultsServiceImpl implements ResultsService{
	
	static final Logger LOG = LoggerFactory.getLogger(Assignment2.class);
	
	
	
	@Autowired
	@Qualifier("outputDir")
	String outputDir;
	
	private static final String FPR_FILE_NAME = "fpr.dat";
	private static final String FNR_FILE_NAME = "fnr.dat";
	
	@Override
	public void generateGnuplot(
			Map<ClassifierTypes, List<ClassifierWrapper>> resultsObj) {
		for (ClassifierTypes classifierType: ClassifierTypes.values()){
			generateGnuplot(classifierType, resultsObj.get(classifierType));
		}
	}
	
	private final static  String sep = System.getProperty("file.separator");
	
	public void generateGnuplot(ClassifierTypes classifierType, 
			List<ClassifierWrapper> wrapperList){
		
		File outputSubdir = new File(outputDir + sep + classifierType.toString().replaceAll(" ", "-")); 
		if ( ! outputSubdir.exists()){
			outputSubdir.mkdir();
		}
		String fprFileName = outputSubdir.getAbsolutePath() + sep + FPR_FILE_NAME;
		writeErrorRateFile(fprFileName, classifierType, ErrorTypes.Type1, wrapperList);
		
		String fnrFileName =  outputSubdir.getAbsolutePath() + sep + FNR_FILE_NAME;
		writeErrorRateFile(fnrFileName, classifierType, ErrorTypes.Type2, wrapperList);
		
		String gnuPlotFileName = outputSubdir.getAbsolutePath() + sep + "" + outputSubdir.getName() + ".gplt";
		writeGnuPlotFile(gnuPlotFileName, classifierType.toString());
	}

	private void writeErrorRateFile(String fileName, ClassifierTypes classifierType, ErrorTypes errorType,
			 List<ClassifierWrapper> wrapperList) {
		try {
			PrintWriter pw = new PrintWriter ( new File(fileName));
			for (ClassifierWrapper wrapper: wrapperList){
				pw.println(wrapper.getErrorCost()+ " " 
						+ wrapper.getEval().falsePositiveRate(errorType.intVal()));
			}
			pw.close();
		} catch (FileNotFoundException e) {
			LOG.error("could not open file " + fileName);
		}	
	}
	private static final StringBuilder gnuPlotFileHeader 
		= new StringBuilder().append("set term postscript eps 22\n")
			.append("set key center top\n")
			.append("set xlabel 'Type II Error Cost'\n")
			.append("set ylabel 'Missclassifications'\n")
			.append("set linestyle 1 lt 2 lw 3\n")
			.append("set logscale x\n")
			.append("set key box linestyle 1 \n")
			.append("set output '");
	
	private void writeGnuPlotFile(String gnuPlotFileName, String title) {
		
		StringBuilder gnuPlotStr = new StringBuilder(gnuPlotFileHeader); 
		
		gnuPlotStr.append(title.replaceAll(" ", "-")).append(".ps'\n");
		
		gnuPlotStr.append("plot '").append(FPR_FILE_NAME).append("' ");
		
		gnuPlotStr.append("title '").append(title).append(" FPR' ");
		
		gnuPlotStr.append(" with linespoints lw 3 pt 4, ");
		
		gnuPlotStr.append("'").append(FNR_FILE_NAME).append("' ");
		
		gnuPlotStr.append("title '").append(title).append(" FNR' ");
		
		gnuPlotStr.append("with linespoints lw 3 pt 8 \n");
		
		try {
			PrintWriter pw = new PrintWriter(new File(gnuPlotFileName));
			
			pw.print(gnuPlotStr.toString());
			
			pw.close();
			
		} catch(FileNotFoundException e){
			LOG.error("could not open file " + gnuPlotFileName);
		}
	}
}
