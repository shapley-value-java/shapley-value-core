package org.shapleyvalue.application.impl.fraud.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.shapleyvalue.application.facade.CoalitionStrategy;
import org.shapleyvalue.application.facade.ShapleyApplicationException;
import org.shapleyvalue.application.impl.fraud.v2.FraudRuleV2Application.FraudRuleV2ApplicationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

public class FraudRuleV2ApplicationTest {
	
	private final Logger logger = LoggerFactory.getLogger(FraudRuleV2ApplicationTest.class);

	@Test
	public void testEvaluationOneRule() {
		
		FraudRuleV2Application evaluation = 
				new FraudRuleV2Application.FraudRuleV2ApplicationBuilder()
				.addRule(new RuledTransaction("1,1"))
				.build();
	
		
		Map<String,Double> output = evaluation.calculate();
		double phiRule1 = output.get("1");
		
		assertEquals(phiRule1, 1.0, 0.01);
	}
	

	@Test
	public void testEvaluationTwoRules() {
		
		FraudRuleV2Application evaluation = 
				new FraudRuleV2Application.FraudRuleV2ApplicationBuilder()
				.addRule(new RuledTransaction("1,1,0"))
				.addRule(new RuledTransaction("1,0,1"))
				.build();
	
		
		Map<String,Double> output = evaluation.calculate();
		double phiRule1 = output.get("1");
		double phiRule2 = output.get("2");
		
		assertEquals(phiRule1, 0.5, 0.01);
		assertEquals(phiRule2, 0.5, 0.01);
	}
	
	@Test
	public void testEvaluationFourRules() {
		
		FraudRuleV2Application evaluation = 
				new FraudRuleV2Application.FraudRuleV2ApplicationBuilder()
				.addRule(new RuledTransaction("1,1,1,1,0"))
				.addRule(new RuledTransaction("1,1,1,1,0"))
				.addRule(new RuledTransaction("1,1,1,1,0"))
				.addRule(new RuledTransaction("1,0,0,0,1"))
				.build();
		
		Map<String,Double> output = evaluation.calculate();
		double phiRule1 = output.get("1");
		double phiRule2 = output.get("2");
		double phiRule3 = output.get("3");
		//double phiRule4 = output.get("4");
		
		assertEquals(phiRule1, phiRule2, 0.01);
		assertEquals(phiRule2, phiRule3, 0.01);

	}
	
	@Test
	public void testEvaluationFourRulesPerStep() throws ShapleyApplicationException {
		
		FraudRuleV2Application evaluation = 
				new FraudRuleV2Application.FraudRuleV2ApplicationBuilder()
				.addRule(new RuledTransaction("1,1,1,1,0"))
				.addRule(new RuledTransaction("1,1,1,1,0"))
				.addRule(new RuledTransaction("1,1,1,1,0"))
				.addRule(new RuledTransaction("1,0,0,0,1"))
				.build();
		
		Map<String,Double> output =null;
		while(!evaluation.isLastCoalitionReached()) {
			output = evaluation.calculate(1);
		}		
		logger.debug("output {}", output);
		double phiRule1 = output.get("1");
		double phiRule2 = output.get("2");
		double phiRule3 = output.get("3");
		//double phiRule4 = output.get(4);
		
		assertEquals(phiRule1, phiRule2, 0.01);
		assertEquals(phiRule2, phiRule3, 0.01);
	}
	
	
	@Test
	public void testEvaluationXXXRules() throws ShapleyApplicationException, FileNotFoundException, IOException {
		
		
		FraudRuleV2Application evaluation = 
				new FraudRuleV2Application.FraudRuleV2ApplicationBuilder()
				.addRule(new RuledTransaction("1,1,1,1,1,1,0,1,0,1,0,1,0,1,0"))
				.addRule(new RuledTransaction("1,1,0,1,0,0,1,0,1,0,0,1,0,0,0"))
				.addRule(new RuledTransaction("1,1,0,1,0,0,1,0,1,0,0,1,0,0,0"))
				.addRule(new RuledTransaction("1,1,0,1,0,0,1,0,1,0,1,0,0,1,0"))
				.addRule(new RuledTransaction("1,1,0,1,0,0,1,0,1,0,0,1,1,1,0"))
				.addRule(new RuledTransaction("1,1,0,1,0,0,1,0,1,0,0,0,0,0,0"))
				.addRule(new RuledTransaction("1,1,0,1,0,0,1,0,1,0,0,0,0,1,0"))
				.addRule(new RuledTransaction("0,1,0,1,0,0,1,0,1,0,0,0,0,0,0"))
				.addRule(new RuledTransaction("1,1,0,1,0,0,1,0,1,0,0,0,0,0,0"))
				.addRule(new RuledTransaction("1,1,0,1,0,0,1,0,1,0,0,1,1,1,0"))
				.addRule(new RuledTransaction("1,1,0,1,0,0,1,0,1,0,1,0,0,0,0"))
				.addRule(new RuledTransaction("1,1,0,1,0,0,1,0,1,0,1,0,1,0,0"))
				.addRule(new RuledTransaction("1,1,0,1,0,0,1,0,1,0,1,0,0,0,0"))
				.addRule(new RuledTransaction("0,1,0,1,0,0,1,0,1,0,1,0,0,0,1"))
				.addRule(new RuledTransaction("0,1,0,1,0,0,1,0,1,0,1,0,0,0,1"))
				.build();
	
		
		for(int i=1; i<10;i++) {
		Map<String,Double> output = evaluation.calculate(10_000,CoalitionStrategy.RANDOM);
			double phiRule1 = output.get("1");
			double phiRule2 = output.get("2");
			logger.info("loop {}",i);
			logger.info("phiRule1={}",String.format("%.3f", phiRule1));
			logger.info("phiRule2={}",String.format("%.3f", phiRule2));
		}

	}
	
	@Test
	public void testIsLastCoalitionReachedFalse() throws ShapleyApplicationException {
		
		FraudRuleV2Application evaluation = 
				new FraudRuleV2Application.FraudRuleV2ApplicationBuilder()
				.addRule(new RuledTransaction("1,1,1,1"))
				.addRule(new RuledTransaction("1,1,1,1"))
				.build();
	
		
		assertFalse(evaluation.isLastCoalitionReached());

	}
	
	@Test
	public void testIsLastCoalitionReachedTrue() throws ShapleyApplicationException {
		
		FraudRuleV2Application evaluation = 
				new FraudRuleV2Application.FraudRuleV2ApplicationBuilder()
				.addRule(new RuledTransaction("1,1,1"))
				.addRule(new RuledTransaction("1,1,1"))
				.addRule(new RuledTransaction("1,1,1"))
				.build();
	
		
		assertFalse(evaluation.isLastCoalitionReached());
		evaluation.calculate(1);
		assertFalse(evaluation.isLastCoalitionReached());
		evaluation.calculate(1);
		assertTrue(evaluation.isLastCoalitionReached());
		

	}
	

	@Test
	public void testEvaluationFromFileRules() throws ShapleyApplicationException, FileNotFoundException, IOException {
		
		FraudRuleV2Application evaluation = null; 
		FraudRuleV2ApplicationBuilder builder=
				new FraudRuleV2Application.FraudRuleV2ApplicationBuilder();
				
		try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\AdminFBE\\workspace\\shapley-value-java\\shapley-value-core\\src\\test\\resources\\shapley_data_small.csv"))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       builder.addRule(new RuledTransaction(line));
		    }
		}
		logger.info("file read ok");
		evaluation = builder.build();

		for(int i=1; i<=10;i++) {
			Stopwatch stopwatch = Stopwatch.createStarted();
			
			Map<String,Double> output = evaluation.calculate(10,CoalitionStrategy.RANDOM);
			double phiRule1 = output.get("1");
			double phiRule2 = output.get("2");
			double phiRule3 = output.get("3");
			double phiRule4 = output.get("4");
			logger.info("loop {}",i);
			logger.info("phiRule1={}",String.format("%.5f", phiRule1));
			logger.info("phiRule2={}",String.format("%.5f", phiRule2));
			logger.info("phiRule3={}",String.format("%.5f", phiRule3));
			logger.info("phiRule4={}",String.format("%.5f", phiRule4));
			long duration = stopwatch.elapsed(TimeUnit.SECONDS);
			logger.info(" duration  {}",duration);
		}
	}

}
