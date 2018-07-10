package org.shapleyvalue.application.impl.fraud.v2;

import static org.junit.Assert.*;

import org.junit.Test;

public class RuledTransactionTest {

	@Test
	public void ruleTransaction1Test() {
		RuledTransaction t = new RuledTransaction("0,0");
		
		assertNotNull(t);
		assertFalse(t.isFraud());
		assertNotNull(t.getRules());
		assertEquals(t.getRules().size(),0);
		
	}
	
	@Test
	public void ruleTransaction2Test() {
		RuledTransaction t = new RuledTransaction("1,0,0");
		
		assertNotNull(t);
		assertTrue(t.isFraud());
		assertNotNull(t.getRules());
		assertEquals(t.getRules().size(),0);
		assertEquals(t.getNbRules(),2);
	}
	
	@Test
	public void ruleTransaction3Test() {
		RuledTransaction t = new RuledTransaction("0,0,1");
		
		assertNotNull(t);
		assertFalse(t.isFraud());
		assertNotNull(t.getRules());
		assertEquals(t.getRules().size(),1);
		assertTrue(t.getRules().contains(2));
		assertEquals(t.getNbRules(),2);
	}
	
	
	@Test
	public void ruleTransaction4Test() {
		RuledTransaction t = new RuledTransaction("1,1,0,0,1");
		
		assertNotNull(t);
		assertTrue(t.isFraud());
		assertNotNull(t.getRules());
		assertEquals(t.getRules().size(),2);
		assertTrue(t.getRules().contains(1));
		assertTrue(t.getRules().contains(4));
		assertEquals(t.getNbRules(),4);
	}

}
