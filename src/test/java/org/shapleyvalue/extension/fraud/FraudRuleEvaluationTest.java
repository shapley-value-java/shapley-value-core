package org.shapleyvalue.extension.fraud;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

public class FraudRuleEvaluationTest {

	@Test
	public void testEvaluationOneRule() {
		
		FraudRuleEvaluation evaluation = 
				new FraudRuleEvaluation.FraudRuleEvaluationBuilder()
				.addRule("Rule1", 1)
				.build();
	
		
		Map<String,Double> output = evaluation.calculate();
		double phiRule1 = output.get("Rule1");
		
		assertEquals(phiRule1, 1.0, 0.01);
	}
	

	@Test
	public void testEvaluationTwoRules() {
		
		FraudRuleEvaluation evaluation = 
				new FraudRuleEvaluation.FraudRuleEvaluationBuilder()
				.addRule("Rule1", 1)
				.addRule("Rule2", 1)
				.build();
	
		
		Map<String,Double> output = evaluation.calculate();
		double phiRule1 = output.get("Rule1");
		double phiRule2 = output.get("Rule2");
		
		assertEquals(phiRule1, 0.5, 0.01);
		assertEquals(phiRule2, 0.5, 0.01);
	}
	
	@Test
	public void testEvaluationFourRules() {
		
		FraudRuleEvaluation evaluation = 
				new FraudRuleEvaluation.FraudRuleEvaluationBuilder()
				.addRule("Rule1", 1,2,3)
				.addRule("Rule2", 1,2,3)
				.addRule("Rule3", 1,2,3)
				.addRule("Rule4", 4)
				.build();
	
		
		Map<String,Double> output = evaluation.calculate();
		double phiRule1 = output.get("Rule1");
		double phiRule2 = output.get("Rule2");
		double phiRule3 = output.get("Rule3");
		double phiRule4 = output.get("Rule4");
		
		assertEquals(phiRule1, 0.25, 0.01);
		assertEquals(phiRule2, 0.25, 0.01);
		assertEquals(phiRule3, 0.25, 0.01);
		assertEquals(phiRule4, 0.25, 0.01);
	}

}
