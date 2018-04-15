package org.shapleyvalue.util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PowersetTest {
	
	private final Logger logger = LoggerFactory.getLogger(PowersetTest.class);
	
	@Test
	public void test0() {
		int nbElements = 0;
		Set<Set<Integer>> sets = Powerset.calculate(nbElements);
		//set = {}
		logger.info("sets= {}",sets.toArray());
		assertEquals(sets.size(),Math.pow(2, nbElements), 0.01);
		assertTrue(sets.contains(Powerset.nullSet));
		
	}

	@Test
	public void test1() {
		int nbElements = 1;
		Set<Set<Integer>> sets = Powerset.calculate(nbElements);
		//set = {} {1}
		logger.info("sets= {}",sets.toArray());
		assertEquals(sets.size(),Math.pow(2, nbElements), 0.01);
		assertTrue(sets.contains(Powerset.nullSet));
		assertTrue(sets.contains(new HashSet<>(Arrays.asList(1))));
		
	}
	
	@Test
	public void test2() {
		int nbElements = 2;
		Set<Set<Integer>> sets = Powerset.calculate(nbElements);
		//set = {} {1} {2} {1,2}
		logger.info("sets= {}",sets.toArray());
		assertEquals(sets.size(),Math.pow(2, nbElements), 0.01);
		assertTrue(sets.contains(Powerset.nullSet));
		assertTrue(sets.contains(new HashSet<>(Arrays.asList(1,2))));
		
	}
	
	@Test
	public void test3() {
		int nbElements = 3;
		Set<Set<Integer>> sets = Powerset.calculate(nbElements);
		//set = {} {1} {2} {3} {1,2} {1,3} {2,3} {1,2,3}
		logger.info("sets= {}",sets.toArray());
		assertEquals(sets.size(),Math.pow(2, nbElements), 0.01);
		assertTrue(sets.contains(Powerset.nullSet));
		assertTrue(sets.contains(new HashSet<>(Arrays.asList(1,2,3))));
		
	}

}
