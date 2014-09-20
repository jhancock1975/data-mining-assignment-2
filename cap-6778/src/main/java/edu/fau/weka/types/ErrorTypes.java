package edu.fau.weka.types;

public enum ErrorTypes {
	Type1(0), Type2(1);
	private int type;
	ErrorTypes(int type){
		this.type = type;
	}
	public int intVal(){
		return type;
	}
}
