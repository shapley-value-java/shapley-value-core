package org.shapleyvalue.application.fraud;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FraudRuleEvaluationTest {
	
	private final Logger logger = LoggerFactory.getLogger(FraudRuleEvaluationTest.class);

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
	
	
	@Test
	public void testEvaluationXXXRules() {
		
		FraudRuleEvaluation evaluation = 
				new FraudRuleEvaluation.FraudRuleEvaluationBuilder()
				.addRule("Rule1", 1,2,3,4,5,7,9)
				.addRule("Rule2", 1,3,6,8)
				//.addRule("Rule3", 1,2,7)
				//.addRule("Rule4", 1,9)
				//.addRule("Rule5", 2)
				//.addRule("Rule6", 1,2)
				//.addRule("Rule7", 3)
				//.addRule("Rule8", 9)
				//.addRule("Rule9", 1,8)
				.build();
	
		
		Map<String,Double> output = evaluation.calculate();
		double phiRule1 = output.get("Rule1");
		double phiRule2 = output.get("Rule2");
		//double phiRule3 = output.get("Rule3");
		//double phiRule4 = output.get("Rule4");
		//double phiRule5 = output.get("Rule5");
		//double phiRule6 = output.get("Rule6");
		//double phiRule7 = output.get("Rule7");
		//double phiRule8 = output.get("Rule8");
		//double phiRule9 = output.get("Rule9");

		
		logger.info("phiRule1={}",phiRule1);
		logger.info("phiRule2={}",phiRule2);
		//System.out.println(phiRule3);
		//System.out.println(phiRule4);
		//System.out.println(phiRule5);
		//System.out.println(phiRule6);
		//System.out.println(phiRule7);
		//System.out.println(phiRule8);
		//System.out.println(phiRule9);
	}


}
