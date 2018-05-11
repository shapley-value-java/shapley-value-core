package org.shapleyvalue.application.impl.glovegame;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GloveGameApplicationTest {
	
	
	private final Logger logger = LoggerFactory.getLogger(GloveGameApplicationTest.class);

	@Test
	public void testEvaluationOnePlayer() {
		
		GloveGameApplication evaluation = 
				new GloveGameApplication.GloveGameApplicationBuilder()
				.addPlayer("Adam", "left")
				.build();
	
		
		Map<String,Double> output = evaluation.calculate();
		double phiAdam= output.get("Adam");
		
		assertEquals(phiAdam, 0.0, 0.01);
	}
	
	
	@Test
	public void testEvaluationTwoPlayersNull() {
		
		GloveGameApplication evaluation = 
				new GloveGameApplication.GloveGameApplicationBuilder()
				.addPlayer("Adam1", "left")
				.addPlayer("Adam2", "left")
				.build();
	
		
		Map<String,Double> output = evaluation.calculate();
		double phiAdam1= output.get("Adam1");
		double phiAdam2= output.get("Adam2");
		
		assertEquals(phiAdam1, 0.0, 0.01);
		assertEquals(phiAdam2, 0.0, 0.01);
	}
	
	
	@Test
	public void testEvaluationTwoPlayersOne() {
		
		GloveGameApplication evaluation = 
				new GloveGameApplication.GloveGameApplicationBuilder()
				.addPlayer("Adam1", "left")
				.addPlayer("Lea1", "right")
				.build();
	
		
		Map<String,Double> output = evaluation.calculate();
		double phiAdam1= output.get("Adam1");
		double phiLea1 = output.get("Lea1");
		
		assertEquals(phiAdam1, 0.5, 0.01);
		assertEquals(phiLea1, 0.5, 0.01);
	}

}
