package edu.fau.weka;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * this class has methods for clacluating 
 * similarity between two sets as defined in 
 * 
 * S. Alelyani et al., “A Dilemma in Assessing Stability of Feature Selection 
 * Algorithms,” in IEEE International Conf. High Performance Computing 
 * and Communications, 2011, pp. 701-707.
 * 
 * @author john
 *
 */
public class PairwiseSim {

	static final Logger LOG = LoggerFactory.getLogger(PairwiseSim.class);
	
	public static double avgDist(List<List<Double>> fold1, List<List<Double>> fold2){
		double sum = 0;
		for (List<Double> l1: fold1){
			for (List<Double> l2: fold2){
				sum = sum + length(normalize(vectorDiff(l1, l2)));
			}
		}
		return sum/(fold1.size() * fold2.size());
	}

	public static List<Double> normalize(List<Double> l1){
		List<Double> result = new ArrayList<Double>();
		double length = length(l1);
		if (length == 0){
			result = l1;
		} else {
			for (double d: l1){
				result.add(d/length);
			}
		}
		return result;
	}
	public static List<Double> vectorDiff(List<Double> l1, List<Double> l2){
		if (l1.size() != l2.size()){
			throw new RuntimeException("trying to subtract two vectors not the same length.");
		}
		List<Double> result = new ArrayList<Double>(l1.size());
		for (int i = 0; i < l1.size(); i++){
			result.add(l1.get(i) - l2.get(i));
		}
		return result;
	}

	public static Double length(List<Double> l){
		double sum = 0;
		for (double d: l){
			sum = sum + d*d;
		}
		return Math.sqrt(sum);
	}
	public static double  avgPairwiseSim(List<Map<Object, List<List<Double>>>> partitionedInstances){
		if (partitionedInstances.size() < 2){
			throw new RuntimeException("cannot compute pairwise similarity with less than 2 folds");
		}
		Object [] featureSetArr =  partitionedInstances.toArray();
		int numFolds = featureSetArr.length;
		double sum = 0;
		for (int i=0; i < numFolds - 1; i++){
			for (int j = i+1; j < numFolds; j++){
				Map<Object, List<List<Double>>> fold1Map = (Map<Object, List<List<Double>>> ) featureSetArr[i];
				Map<Object, List<List<Double>>> fold2Map = (Map<Object, List<List<Double>>> ) featureSetArr[j];
				Set<Object> fold1Classes = fold1Map.keySet();
				Set<Object> fold2Classes = fold2Map.keySet();
				Set<Object> intersect = intersection(fold1Classes, fold2Classes);
				double innerSum = 0;
				for (Object o: intersect){
					List<List<Double>> fold1 = fold1Map.get(o);
					List<List<Double>> fold2 = fold2Map.get(o);
					double avgDist = avgDist(fold1, fold2);
					LOG.debug("avgDist  = " + avgDist);
					innerSum += avgDist(fold1, fold2)/intersect.size();
				}
				sum += innerSum;
			}
			return (2*sum)/(numFolds * (numFolds -1));
		}
		return 0;
	}
	public static double sizeOfIntersection(List<List<Double>> fold1, List<List<Double>> fold2){
		double sum = 0;
		for (List<Double> l1: fold1){
			if (fold2.contains(l1)){
				sum++;
			}
		}
		return sum;
	}

	public static <T> Set<T> intersection(Set<T> s1, Set<T> s2){
		//doing intersect and union this way
		//so we leave s1, s2 unchanged
		Set<T> result  = new HashSet<T>();
		for (T t: s1){
			if (s2.contains(t)){
				result.add(t);
			}
		}
		return result;
	}
	public static <T> Set<T> union(Set<T> s1, Set<T> s2){
		 Set<T> result = new HashSet<T>();
		 for (T t: s1){
			 result.add(t);
		 }
		 for (T t: s2){
			 result.add(t);
		 }
		 return result;
	}
	public static double jaccard(Set<String> s1, Set<String> s2){
		//doing intersect and union this way
		//so we leave s1, s2 unchanged
		Set<String> intersection = intersection(s1, s2);
		Set<String> union = new HashSet<String>();
		for (String s: s1){
			union.add(s);	
		}
		for (String s: s2){
			union.add(s);
		}
		return intersection.size()/union.size();
	}
	public static void main(String[] args){
		
		
	}
}
