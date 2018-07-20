package org.shapleyvalue.application.impl.fraud.v2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

public class TpfnfpTest {

	@Test
	public void testRecall() {
		Tpfnfp v = new Tpfnfp(1,1,1);
	
		assertEquals(v.recall(), 0.5, 0.01);
	}
	
	@Test
	public void testPrecision() {
		Tpfnfp v = new Tpfnfp(1,1,1);
	
		assertEquals(v.precision(), 0.5, 0.01);
	}
	
	@Test
	public void testScore() {
		Tpfnfp v = new Tpfnfp(1,1,1);
	
		assertEquals(v.score(), 0.5, 0.01);
	}
	
	@Test
	public void testScoreIs1() {
		Tpfnfp v = new Tpfnfp(1,0,0);
	
		assertEquals(v.score(), 1.0, 0.01);
	}
	
	
	@Test
	public void testScoreIs0() {
		Tpfnfp v = new Tpfnfp(0,1,1);
	
		assertEquals(v.score(), 0.0, 0.01);
	}
	
	@Test
	public void testScoreDummyUser() {
		Tpfnfp v = new Tpfnfp(0,0,0);
	
		assertEquals(v.score(), 0.0, 0.01);
	}


	@Test
	public void testScoreWithRules() {
		RuledTransaction t1 = new RuledTransaction("1,1,0");
		RuledTransaction t2 = new RuledTransaction("1,0,1");
		
		List<RuledTransaction> transactions = new ArrayList<>();
		transactions.add(t1);
		transactions.add(t2);
		
		Tpfnfp v1 = new Tpfnfp(transactions,  new HashSet<>(Arrays.asList(1)));
		assertEquals(v1.score(), 0.66, 0.01);
		
		Tpfnfp v2 = new Tpfnfp(transactions,  new HashSet<>(Arrays.asList(1)));
		assertEquals(v2.score(), 0.66, 0.01);
		
	}
}
