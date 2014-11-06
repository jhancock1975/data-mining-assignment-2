package edu.fau.weka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import edu.fau.weka.model.PointsWithTitle;
import edu.fau.weka.service.AttributeSelectionEvaluationService;
import edu.fau.weka.service.ClassifierResultsService;
import edu.fau.weka.service.ReportService;
@Component
@Qualifier("assign4Reports")
public class Assign4Reports {
	private static Logger LOG = LoggerFactory.getLogger(Assign4Reports.class);
	
	@Autowired
	private ClassifierResultsService classifierResultsSvc;
	@Autowired
	private ReportService reportSvc;
	
	private void generateFprFnrPlots(){
		for (String classifierName: classifierResultsSvc.getClassifierNames()){
			for (String attribSelEvalName: classifierResultsSvc.getAttribEvalNames()){
				List<List<Double>> fprList = 
						classifierResultsSvc.getLastFprResultsFor(classifierName, attribSelEvalName);
				List<List<Double>> fnrList = 
						classifierResultsSvc.getLastFnrResultsFor(classifierName, attribSelEvalName);
				String title = AssignUtil.abbreviateClassifierName(
						AssignUtil.getStringAfterLastDot(classifierName)) +
						" with " +
						AssignUtil.abbreviateAttributeEvalName(
								AssignUtil.getStringAfterLastDot(attribSelEvalName));
				PointsWithTitle fprPoints = new PointsWithTitle(fprList, "FPR");
				PointsWithTitle fnrPoints = new PointsWithTitle(fnrList, "FNR");
				List<PointsWithTitle> points = new ArrayList<PointsWithTitle>();
				points.add(fprPoints);
				points.add(fnrPoints);
				reportSvc.generateGnuPlot(title, points,"Number of Features",  "Type I/II Error Rate");
			}
		}
	}
	private void generateAucPlots(){
		for (String classifierName: classifierResultsSvc.getClassifierNames()){
			for (String attribSelEvalName: classifierResultsSvc.getAttribEvalNames()){
				List<List<Double>> pAucList = 
						classifierResultsSvc.getLastPAucResultsFor(classifierName, attribSelEvalName);
				List<List<Double>> nAucList = 
						classifierResultsSvc.getLastNAucResultsFor(classifierName, attribSelEvalName);
				List<List<Double>> wAucList = 
						classifierResultsSvc.getLastWAucResultsFor(classifierName, attribSelEvalName);
				String title = "AUC " + 
						AssignUtil.abbreviateClassifierName(
							AssignUtil.getStringAfterLastDot(classifierName)) +
							" with " +
						AssignUtil.abbreviateAttributeEvalName(
								AssignUtil.getStringAfterLastDot(attribSelEvalName));
				PointsWithTitle pAucPoints = new PointsWithTitle(pAucList, "pAUC");
				PointsWithTitle nAucPoints  = new PointsWithTitle(nAucList, "nAUC");
				PointsWithTitle wAucPoints = new PointsWithTitle(wAucList, "wAUC");
				List<PointsWithTitle> points = new ArrayList<PointsWithTitle>();
				points.add(pAucPoints );
				points.add(nAucPoints );
				points.add(wAucPoints );
				reportSvc.generateGnuPlot(title, points, "Number of Features", "AUC");
			}
		}
		
	}
	private void generatePlots(){
		generateFprFnrPlots();
		generateAucPlots();
	}
	private void generateJ48Overlap(){
		
	}
	public void doReports(){
		generatePlots();
		generateJ48Overlap();
	}
	public static void main(String[] args) {
		ApplicationContext context =   new ClassPathXmlApplicationContext(AssignUtil.SPRING_CONTEXT_FILE_NAME);
		Assign4Reports reporter = (Assign4Reports) context.getBean("assign4Reports");
		reporter.doReports();
		((ClassPathXmlApplicationContext ) context).close();
	}
}
