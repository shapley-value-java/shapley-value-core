package org.shapleyvalue.application.taxi;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class TaxiCalculationTest {

	@Test
	public void testCalculateOneParticipant() {
		
		TaxiCalculation taxiCalculation = 
				new TaxiCalculation.TaxiCalculationBuilder()
				.addUser(1.0, "A").build();
	
		
		Map<String,Double> output = taxiCalculation.calculate();
		double phiA = output.get("A");
		
		assertEquals(phiA, 1.0, 0.01);
	}
	

	@Test
	public void testCalculateTwoParticipants() {
		
		TaxiCalculation taxiCalculation = 
				new TaxiCalculation.TaxiCalculationBuilder()
				.addUser(1.0, "A")
				.addUser(1.0, "B")
				.build();

		
		Map<String,Double> output = taxiCalculation.calculate();
		double phiA = output.get("A");
		double phiB = output.get("B");
		
		
		assertEquals(phiA, 0.5, 0.01);
		assertEquals(phiA, phiB, 0.01);
		
	}
	
	@Test
	public void testCalculateThreeParticipants() {		
		TaxiCalculation taxiCalculation = 
				new TaxiCalculation.TaxiCalculationBuilder()
				.addUser(6.0, "A")
				.addUser(12.0, "B")
				.addUser(42.0, "C")
				.build();

		Map<String,Double> output = taxiCalculation.calculate();
		double phiA = output.get("A");
		double phiB = output.get("B");
		double phiC = output.get("C");
				
		assertEquals(phiA, 2.0, 0.01);
		assertEquals(phiB, 5.0, 0.01);
		assertEquals(phiC, 35.0, 0.01);	
	}

}
