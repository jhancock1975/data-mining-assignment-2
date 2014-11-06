package edu.fau.weka;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class PairwiseSimTest {
	static final Logger LOG = LoggerFactory.getLogger(PairwiseSimTest.class); 
	public static List<List<Double>> l1 = Arrays.asList( 
			Arrays.asList(new Double[]{1.0,2.0,3.0}),
			Arrays.asList(new Double[]{3.0,4.0,5.0})
			);
	public static List<List<Double>> l2 = Arrays.asList( 
			Arrays.asList(new Double[]{1.0,2.0,3.0}),
			Arrays.asList(new Double[]{3.1,4.1,5.1})
			);

	@Test
	public void testSizeOfIntersection() {
		assertTrue(PairwiseSim.sizeOfIntersection(l1, l2) == 1);
	}
	
	@Test
	public void testPairwiseSim(){
		Map<Object, List<List<Double>>> fold1 = new HashMap<Object, List<List<Double>>>();
		fold1.put("class1", l1);
		Map<Object, List<List<Double>>> fold2 = new HashMap<Object, List<List<Double>>>();
		fold2.put("class1", l1);
		List<Map<Object, List<List<Double>>>> folds = Lists.newArrayList(fold1, fold2);
		double sim = PairwiseSim.avgPairwiseSim(folds);
		LOG.debug("pairwise sim = " + sim);
		assertTrue(sim != 0);
	}

}
