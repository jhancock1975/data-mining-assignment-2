package edu.fau.weka.service;
/**
 * we introduce this exception
 * class because Weka classifier constructor
 * throws exception
 * 
 * @author john
 *
 */
public class ClassifierServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private Exception e;
	public ClassifierServiceException(String msg, Exception e){
		super(msg);
		this.e = e;
	}
	public Exception getNestedException(){
		return e;
	}
}
