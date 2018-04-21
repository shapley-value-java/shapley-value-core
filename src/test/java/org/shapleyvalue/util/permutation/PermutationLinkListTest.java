package org.shapleyvalue.util.permutation;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.shapleyvalue.util.FactorialUtil;

public class PermutationLinkListTest {

	@Test
	public void test2() {
		int n =2;
		PermutationLinkList permutations = new PermutationLinkList(n);
		
		
		List<Integer> permutation1 = permutations.getNextPermutation();
		assertEquals(permutation1.get(0).intValue(),1);
		
		List<Integer> permutation2 = permutations.getNextPermutation();
		assertEquals(permutation2.get(0).intValue(),2);
		
		List<Integer> permutation3 = permutations.getNextPermutation();
		assertTrue(permutation3.isEmpty());
		
		assertEquals(permutations.getPermutationRange(), FactorialUtil.factorial(n));
		
	}
	
	@Test
	public void test3() {
		int n = 3;
		PermutationLinkList permutations = new PermutationLinkList(n);		
		List<Integer> permutation1 = permutations.getNextPermutation();
	
		while(!permutation1.isEmpty()) {
			permutation1 = permutations.getNextPermutation();
		}
		
		assertEquals(permutations.getPermutationRange(), FactorialUtil.factorial(n));
		

	}
	
	
	@Test
	public void test8() {
		int n = 8;
		PermutationLinkList permutations = new PermutationLinkList(n);		
		List<Integer> permutation1 = permutations.getNextPermutation();
	
		while(!permutation1.isEmpty()) {
			permutation1 = permutations.getNextPermutation();
		}
		
		assertEquals(permutations.getPermutationRange(), FactorialUtil.factorial(n));
		

	}

}
