package edu.fau.weka.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.fau.weka.AssignUtil;
import edu.fau.weka.Assignment2;
import edu.fau.weka.model.PointsWithTitle;
@Service
public class ReportServiceImpl implements ReportService {
	
	static final Logger LOG = LoggerFactory.getLogger(Assignment2.class);
	
	@Autowired
	@Qualifier("assign4OutputDir")
	String outputDir;
	
	private final static  String sep = System.getProperty("file.separator");
	
	public void generateGnuPlot(String outputSubDirName, List<PointsWithTitle> pointsToPlot, 
			String xLabel, String yLabel) {
		File outputSubdir = new File(outputDir + sep + outputSubDirName.replaceAll(" ", "-")); 
		if ( ! outputSubdir.exists()){
			outputSubdir.mkdir();
		}
		generateDatFiles(outputSubdir, pointsToPlot);
		generateGnuPlotFile(outputSubDirName, outputSubdir, pointsToPlot, xLabel, yLabel);
	}
	private String getOutputFileName(File datFileDir, String datFileName){
		return datFileDir.getAbsolutePath() + sep + datFileName + ".dat";
	}
	private void generateDatFiles(File outputSubdir,
			List<PointsWithTitle> pointsToPlot) {
		for (PointsWithTitle points: pointsToPlot){
			try {
				PrintWriter pw = new PrintWriter(getOutputFileName(outputSubdir, points.getTitle()));
				for (List<Double> xy : points.getPointsList()){
					pw.println(String.format("%.5f", xy.get(0)) + " " + String.format("%.5f", xy.get(1)));
				}
				pw.close();
			} catch (FileNotFoundException e) {
				LOG.error("unable to open PrintWriter " + e.getMessage());
			}
		}
	}
	private void generateGnuPlotFile(String title, File outputSubdir,
			List<PointsWithTitle> pointsToPlot, String xLabel, String yLabel) {
		StringBuilder gnuPlotStr = new StringBuilder(
				AssignUtil.aucGnuPlotFileHeader(xLabel, yLabel));
		gnuPlotStr.append(outputSubdir.getAbsolutePath() + sep +  "gnuplot.gplt" + ".ps'\n");
		
		int gnuplotSymbolNum = 5; 
		
		gnuPlotStr.append(" plot ");
		
		for (PointsWithTitle points: pointsToPlot){
			
			gnuPlotStr.append(" '").append(getOutputFileName(outputSubdir, points.getTitle())).append("' ");
			
			gnuPlotStr.append("title '").append(title).append(" ");
			
			gnuPlotStr.append(points.getTitle()).append("' ");
			
			gnuPlotStr.append(" with linespoints lw 3 ");
			
			gnuPlotStr.append(" pt ").append(gnuplotSymbolNum++).append(", ");
			
		}
		
		gnuPlotStr.setLength(gnuPlotStr.length() -2);
		
		gnuPlotStr.append("\n");
		String gnuPlotFileName = outputSubdir.getAbsolutePath() + sep +  "gnuplot.gplt"; 
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
