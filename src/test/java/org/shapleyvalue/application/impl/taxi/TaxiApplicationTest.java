package org.shapleyvalue.application.impl.taxi;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.shapleyvalue.application.facade.ShapleyApplicationException;

public class TaxiApplicationTest {

	@Test
	public void testCalculateOneParticipant() {
		
		TaxiApplication taxiCalculation = 
				new TaxiApplication.TaxiApplicationBuilder()
				.addUser(1.0, "A").build();
	
		
		Map<String,Double> output = taxiCalculation.calculate();
		double phiA = output.get("A");
		
		assertEquals(phiA, 1.0, 0.01);
	}
	

	@Test
	public void testCalculateTwoParticipants() {
		
		TaxiApplication taxiCalculation = 
				new TaxiApplication.TaxiApplicationBuilder()
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
		TaxiApplication taxiCalculation = 
				new TaxiApplication.TaxiApplicationBuilder()
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
	
	@Test
	public void testCalculateThreeParticipantsPerStep() throws ShapleyApplicationException {		
		TaxiApplication taxiCalculation = 
				new TaxiApplication.TaxiApplicationBuilder()
				.addUser(6.0, "A")
				.addUser(12.0, "B")
				.addUser(42.0, "C")
				.build();

		Map<String,Double> output = null;
		while(! taxiCalculation.isLastCoalitionReached()) {
			output = taxiCalculation.calculate(1);
		}
		double phiA = output.get("A");
		double phiB = output.get("B");
		double phiC = output.get("C");
				
		assertEquals(phiA, 2.0, 0.01);
		assertEquals(phiB, 5.0, 0.01);
		assertEquals(phiC, 35.0, 0.01);	
	}

}
