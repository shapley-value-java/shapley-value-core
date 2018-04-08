package org.shapleyvalue.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;


public class PermutationsTest {



	@Test
	public void testGetAllPermutation1() {
		int permutationSize =1;
		List<List<Integer>> permutations =Permutations.getAllPermutation(permutationSize);
		
		assertEquals(permutations.toString(),permutations.size(),FactorialUtil.factorial(permutationSize));
		assertTrue(permutations.toString(),permutations.contains(Arrays.asList(1)));
	}
	
	@Test
	public void testGetAllPermutation2() {
		int permutationSize =2;
		List<List<Integer>> permutations =Permutations.getAllPermutation(permutationSize);
		
		assertEquals(permutations.toString(),permutations.size(),FactorialUtil.factorial(permutationSize));
		assertTrue(permutations.toString(),permutations.contains(Arrays.asList(1,2)));
	}
	
	@Test
	public void testGetAllPermutation3() {
		int permutationSize =3;
		List<List<Integer>> permutations =Permutations.getAllPermutation(permutationSize);
		
		assertEquals(permutations.toString(),permutations.size(),FactorialUtil.factorial(permutationSize));
		assertTrue(permutations.toString(),permutations.contains(Arrays.asList(1,3,2)));
	}

}
