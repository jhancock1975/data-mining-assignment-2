/**
 * @author john
 * this class holds a set of points
 * to be plotted with gnuplot
 * and a title for the points
 */
package edu.fau.weka.model;

import java.util.List;

public class PointsWithTitle {
	private List<List<Double>> pointsList;
	private String title;
	public PointsWithTitle(List<List<Double>> pointsList, String title) {
		super();
		this.pointsList = pointsList;
		this.title = title;
	}
	public List<List<Double>> getPointsList() {
		return pointsList;
	}
	public void setPointsList(List<List<Double>> pointsList) {
		this.pointsList = pointsList;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
