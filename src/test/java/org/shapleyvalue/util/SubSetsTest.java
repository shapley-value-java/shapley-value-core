package org.shapleyvalue.util;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class SubSetsTest {

	@Test
	public void test() {
		Set<Set<Integer>> sets = SubSets.getAllSubSetsNonEmpty(1);
		//set = {1}
		System.out.println(sets);
		assertEquals(sets.size(),1);
		assertTrue(sets.contains(new HashSet<>(Arrays.asList(1))));
		
	}
	
	@Test
	public void test2() {
		Set<Set<Integer>> sets = SubSets.getAllSubSetsNonEmpty(2);
		//set = {1} {2} {1,2}
		System.out.println(sets);
		assertEquals(sets.size(),3);
		assertTrue(sets.contains(new HashSet<>(Arrays.asList(1,2))));
		
	}
	
	@Test
	public void test3() {
		Set<Set<Integer>> sets = SubSets.getAllSubSetsNonEmpty(3);
		//set = {1} {2} {3} {1,2} {1,3} {2,3} {1,2,3}
		System.out.println(sets);
		assertEquals(sets.size(),7);
		assertTrue(sets.contains(new HashSet<>(Arrays.asList(1,2,3))));
		
	}

}
