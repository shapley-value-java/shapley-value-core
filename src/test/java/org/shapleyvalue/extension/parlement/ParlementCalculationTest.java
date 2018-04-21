package org.shapleyvalue.extension.parlement;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParlementCalculationTest {
	
	private final Logger logger = LoggerFactory.getLogger(ParlementCalculationTest.class);

	@Test
	public void testExampleMicronesia() {
		
		ParlementCalculation parlementCalculation = 
				new ParlementCalculation.ParlementCalculationBuilder()
				.addParty("A", 45)
				.addParty("B", 25)
				.addParty("C", 15)
				.addParty("D", 15)
				.build();
	
		
		Map<String,Double> output = parlementCalculation.calculate();
		double phiA = output.get("A");
		double phiB = output.get("B");
		double phiC = output.get("C");
		double phiD = output.get("D");
		
		assertEquals(phiA, 0.5,   0.001);
		assertEquals(phiB, 0.167, 0.001);
		assertEquals(phiC, 0.167, 0.001);
		assertEquals(phiD, 0.167, 0.001);
	}
	
	
	@Test
	@Ignore
	public void testExampleBelgium() {
		
		ParlementCalculation parlementCalculation = 
				new ParlementCalculation.ParlementCalculationBuilder()
				.addParty("NVA", 31)
				.addParty("PS", 23)
				.addParty("MR", 20)
				.addParty("CD&V", 18)
				.addParty("openVLD", 14)
				.addParty("EcoloGroen", 12)
				.addParty("CDH", 9)
				.addParty("VB", 3)
				/////.addParty("others", 7)
				//.addParty("Defi", 2)
				//.addParty("PTB", 2)
				//.addParty("VW", 2)
				.addParty("PP", 1)
				.build();
	
		
		Map<String,Double> output = parlementCalculation.calculate();
		double phiNVA = output.get("NVA");
		logger.info("phiNVA= {}",phiNVA);
		double phiPS = output.get("PS");
		logger.info("phiPS= {}",phiPS);
		
		double phiCDH = output.get("CDH");
		logger.info("phiCDH= {}",phiCDH);
		
		double phiVB = output.get("VB");
		logger.info("phiVB= {}",phiVB);
		
		double phiPP = output.get("PP");
		logger.info("phiPP= {}",phiPP);
	}
	




}
