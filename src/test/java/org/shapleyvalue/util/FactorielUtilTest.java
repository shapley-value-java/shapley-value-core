package org.shapleyvalue.util;

import static org.junit.Assert.*;

import org.junit.Test;



public class FactorielUtilTest {



	@Test
	public void testFactoriel() {
		
		assertEquals(FactorielUtil.factoriel(0),1);
		assertEquals(FactorielUtil.factoriel(3),6);
		assertEquals(FactorielUtil.factoriel(4),24);
	}

}
