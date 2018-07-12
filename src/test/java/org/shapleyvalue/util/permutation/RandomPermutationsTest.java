package org.shapleyvalue.util.permutation;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomPermutationsTest {
	
	private final Logger logger = LoggerFactory.getLogger(RandomPermutationsTest.class);

	
	@Test
	public void testGetRandom2() {
		int size = 2;
		List<Integer> res =RandomPermutations.getRandom(size);
		logger.debug("res = {}",res);
		
		assertEquals(res.size(),size);
		
		for(int i=1 ; i<=size; i++) {
			assertTrue(res.contains(i));
		}
	}

	@Test
	public void testGetRandom15() {
		int size = 15;
		List<Integer> res =RandomPermutations.getRandom(size);
		logger.debug("res = {}",res);
		
		assertEquals(res.size(),size);
		assertTrue(res.contains(1));
		for(int i=1 ; i<=size; i++) {
			assertTrue(res.contains(i));
		}
	}
	
	@Test
	public void testGetRandom30() {
		int size = 30;
		List<Integer> res =RandomPermutations.getRandom(size);
		logger.debug("res = {}",res);
		
		assertEquals(res.size(),size);
		assertTrue(res.contains(1));
		for(int i=1 ; i<=size; i++) {
			assertTrue(res.contains(i));
		}
	}

}
