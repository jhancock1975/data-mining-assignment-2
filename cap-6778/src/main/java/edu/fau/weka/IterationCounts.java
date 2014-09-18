package edu.fau.weka;

public enum IterationCounts {
	ten(10), twentyFive(25);
	private int numIterations;
	IterationCounts(int count){
		this.numIterations = count;
	}
	public int intVal(){
		return this.numIterations;
	}
}
