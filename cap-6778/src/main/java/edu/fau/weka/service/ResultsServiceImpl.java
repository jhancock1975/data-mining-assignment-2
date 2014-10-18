package edu.fau.weka.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.fau.weka.AssignUtil;
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
	
	private static final String NAUC_FILE_NAME = "nauc.dat";
	private static final String PAUC_FILE_NAME = "pauc.dat";
	private static final String WAUC_FILE_NAME = "wauc.dat";
	
	
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
		String fullPath = outputSubdir.getAbsolutePath() + sep;
		
		String fprFileName = fullPath + FPR_FILE_NAME;
		writeErrorRateFile(fprFileName, classifierType, ErrorTypes.Type1, wrapperList);
		
		String fnrFileName =  fullPath + FNR_FILE_NAME;
		writeErrorRateFile(fnrFileName, classifierType, ErrorTypes.Type2, wrapperList);
		
		String gnuPlotFileName = fullPath+ "" + outputSubdir.getName() + ".gplt";
		writeGnuPlotFile(fullPath, gnuPlotFileName, classifierType.toString());
		
		String pAucFileName = fullPath + PAUC_FILE_NAME;
		writeAucFile(pAucFileName, classifierType, ErrorTypes.Type1, wrapperList);
		
		String nAucFileName = fullPath +  NAUC_FILE_NAME;
		writeAucFile(nAucFileName, classifierType, ErrorTypes.Type2, wrapperList);
		
		String wAucFileName = fullPath + WAUC_FILE_NAME;
		writeWAucFile( wAucFileName, classifierType,  wrapperList);
		
		String aucGnuPlotFileName =  fullPath +  outputSubdir.getName() + "-AUC.gplt";
		writeAucGnuPlot(fullPath, aucGnuPlotFileName, classifierType.toString() );
		
		
	}
	
	

	private void writeAucGnuPlot(String fullPath, String aucGnuPlotFileName, String title) {
		
		StringBuilder gnuPlotStr = new StringBuilder(AssignUtil.aucGnuPlotFileHeader("Type II Error Cost", "Area Under ROC")); 
		
		gnuPlotStr.append(title.replaceAll(" ", "-")).append("-AUC.ps'\n");
		
		gnuPlotStr.append("plot '").append(fullPath + PAUC_FILE_NAME).append("' ");
		
		gnuPlotStr.append("title '").append(title).append(" PAUC' ");
		
		gnuPlotStr.append(" with linespoints lw 3 pt 4, ");
		
		gnuPlotStr.append("'").append(fullPath + NAUC_FILE_NAME).append("' ");
		
		gnuPlotStr.append("title '").append(title).append(" NAUC' ");
		
		gnuPlotStr.append("with linespoints lw 3 pt 8, ");
		
		gnuPlotStr.append("'").append(fullPath + WAUC_FILE_NAME).append("' ");
		
		gnuPlotStr.append("title '").append(title).append(" WAUC' ");
		
		gnuPlotStr.append("with linespoints lw 3 pt 6, ");
		
		try {
			PrintWriter pw = new PrintWriter(new File(aucGnuPlotFileName));
			
			pw.print(gnuPlotStr.toString());
			
			pw.close();
			
		} catch(FileNotFoundException e){
			LOG.error("could not open file " + aucGnuPlotFileName);
		}
		 try {
			Runtime.getRuntime().exec("gnuplot < " + aucGnuPlotFileName);
		} catch (IOException e) {
			LOG.error("Error generating graph with gnuplot.  Is gnuplot installed on this system?");
		}
	}
		
	

	private void writeWAucFile(String fileName,
			ClassifierTypes classifierType, List<ClassifierWrapper> wrapperList) {
		try {
			PrintWriter pw = new PrintWriter ( new File(fileName));
			for (ClassifierWrapper wrapper: wrapperList){
				pw.println(wrapper.getErrorCost()+ " " 
						+ String.format("%.5f", wrapper.getEval().weightedAreaUnderROC()));
			}
			pw.close();
		} catch (FileNotFoundException e) {
			LOG.error("could not open file " + fileName);
		}	
		
	}

	private void writeAucFile(String fileName,
			ClassifierTypes classifierType, ErrorTypes errorType,
			List<ClassifierWrapper> wrapperList) {
		
		try {
			PrintWriter pw = new PrintWriter ( new File(fileName));
			for (ClassifierWrapper wrapper: wrapperList){
				pw.println(wrapper.getErrorCost()+ " " 
						+ String.format("%.5f", wrapper.getEval().areaUnderROC(errorType.intVal())));
			}
			pw.close();
		} catch (FileNotFoundException e) {
			LOG.error("could not open file " + fileName);
		}	
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
			.append("set key outside\n")
			.append("set key center top\n")
			.append("set xlabel 'Type II Error Cost'\n")
			.append("set ylabel 'Missclassifications'\n")
			.append("set linestyle 1 lt 2 lw 3\n")
			.append("set logscale x\n")
			.append("set key box linestyle 1 \n")
			.append("set output '");
	
	private void writeGnuPlotFile(String fullPath, String gnuPlotFileName, String title) {
		
		StringBuilder gnuPlotStr = new StringBuilder(gnuPlotFileHeader); 
		
		gnuPlotStr.append(title.replaceAll(" ", "-")).append(".ps'\n");
		
		gnuPlotStr.append("plot '").append(fullPath + FPR_FILE_NAME).append("' ");
		
		gnuPlotStr.append("title '").append(title).append(" FPR' ");
		
		gnuPlotStr.append(" with linespoints lw 3 pt 4, ");
		
		gnuPlotStr.append("'").append(fullPath +  FNR_FILE_NAME).append("' ");
		
		gnuPlotStr.append("title '").append(title).append(" FNR' ");
		
		gnuPlotStr.append("with linespoints lw 3 pt 8 \n");
		
		try {
			PrintWriter pw = new PrintWriter(new File(gnuPlotFileName));
			
			pw.print(gnuPlotStr.toString());
			
			pw.close();
			
		} catch(FileNotFoundException e){
			LOG.error("could not open file " + gnuPlotFileName);
		}
		 try {
			Runtime.getRuntime().exec("gnuplot < " + gnuPlotFileName);
		} catch (IOException e) {
			LOG.error("Error generating graph with gnuplot.  Is gnuplot installed on this system?");
		}
	}
}
