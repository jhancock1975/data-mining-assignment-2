package edu.fau.weka.service;

import java.util.List;

import edu.fau.weka.model.PointsWithTitle;

public interface ReportService {

	void generateGnuPlot(String outputSubDirName, List<PointsWithTitle> pointsList, String xLabel, String yLabel);

}
