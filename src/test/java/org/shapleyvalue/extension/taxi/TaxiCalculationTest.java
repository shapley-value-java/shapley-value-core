package org.shapleyvalue.extension.taxi;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.shapleyvalue.CharacteristicFunction;
import org.shapleyvalue.ShapleyValue;

public class TaxiCalculationTest {

	@Test
	public void testCalculateOneParticipant() {
		
		TaxiCalculation taxiCalculation = 
				new TaxiCalculation.TaxiCalculationBuilder(1)
				.addUser(1.0, "A").build();
	
		//ShapleyValue s = new ShapleyValue(cfunction);
		
		Map<String,Double> output = taxiCalculation.calculate();
		double phiA = output.get("A");
		
		assertEquals(phiA, 1.0, 0.01);
	}
	

	@Test
	public void testCalculateTwoParticipants() {
		
		TaxiCalculation taxiCalculation = 
				new TaxiCalculation.TaxiCalculationBuilder(2)
				.addUser(1.0, "A")
				.addUser(1.0, "B")
				.build();
	
		//ShapleyValue s = new ShapleyValue(cfunction);
		
		Map<String,Double> output = taxiCalculation.calculate();
		double phiA = output.get("A");
		double phiB = output.get("B");
		
		
		assertEquals(phiA, 0.5, 0.01);
		assertEquals(phiA, phiB, 0.01);
		
	}

}
