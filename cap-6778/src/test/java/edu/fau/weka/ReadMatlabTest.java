package edu.fau.weka;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.jmatio.io.MatFileReader;
import com.jmatio.types.MLArray;
import com.jmatio.types.MLCell;
import com.jmatio.types.MLChar;
import com.jmatio.types.MLDouble;

public class ReadMatlabTest {
	String charArrToStr(char[] charArr){
		if (charArr != null && charArr.length > 0){
			StringBuilder res = new StringBuilder();
			for (char c: charArr){
				res.append(c);
			}
			return res.toString();
		} else {
			return null;
		}
	}
	@Test
	public void readMatlabTest() throws FileNotFoundException, IOException{

		MatFileReader reader = new MatFileReader("/home/john/Documents/school/fall-2014/data-mining/assignments/term-paper/CLL-SUB-111.mat");
		Map<String, MLArray> map  = reader.getContent();
		// the value of hash map with key "X" is the actual instance values for CLL-SUB dataset

		for (String s: map.keySet()){
			MLArray arr = map.get(s);
			System.out.println("key is " + s);
			if ( reader.getMLArray(s) instanceof MLDouble){
				
					double[][] data = ((MLDouble) reader.getMLArray(s)).getArray();
					System.out.println(data[0][0]);
					System.out.println(data[0][data[0].length -1]);
				
			} else if (reader.getMLArray(s) instanceof MLCell){
				MLCell cells = (MLCell) reader.getMLArray(s);
				for (int i = 0; i < cells.getSize(); i++){
					MLChar mlChar = ((MLChar) cells.get(i));
					for (int m = 0; m < mlChar.getM(); m++){
						for (int n = 0; n < mlChar.getN(); n++){
							System.out.print(mlChar.getChar(m, n));
						}
					}
					System.out.println();
				}
			}
			System.out.println(arr.getClass().getCanonicalName());
		}
	}
}
