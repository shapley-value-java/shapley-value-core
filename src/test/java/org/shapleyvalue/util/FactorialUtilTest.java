package org.shapleyvalue.util;

import static org.junit.Assert.*;

import org.junit.Test;



public class FactorialUtilTest {



	@Test
	public void testFactoriel() {
		
		assertEquals(FactorialUtil.factorial(0),1);
		assertEquals(FactorialUtil.factorial(1),1);
		assertEquals(FactorialUtil.factorial(3),6);
		assertEquals(FactorialUtil.factorial(4),24);
		assertEquals(FactorialUtil.factorial(400),Long.MAX_VALUE);
	}

}
