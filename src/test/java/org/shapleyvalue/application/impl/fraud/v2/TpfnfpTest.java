package org.shapleyvalue.application.impl.fraud.v2;

import static org.junit.Assert.*;

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


}
