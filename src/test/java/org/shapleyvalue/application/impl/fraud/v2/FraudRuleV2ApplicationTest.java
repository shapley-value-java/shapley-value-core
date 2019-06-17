package org.shapleyvalue.application.impl.fraud.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeMap;
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
		
		double precision1 = evaluation.getPrecision("1");
		double recall1 = evaluation.getRecall("1");
		double fscore1 = evaluation.getFscore("1");
		
		assertEquals(precision1, 1.0, 0.01);
		assertEquals(recall1, 0.5, 0.01);
		assertEquals(fscore1, 0.67, 0.01);
		
		double precision2 = evaluation.getPrecision("2");
		double recall2 = evaluation.getRecall("2");
		double fscore2 = evaluation.getFscore("2");
		
		assertEquals(precision2, 1.0, 0.01);
		assertEquals(recall2, 0.5, 0.01);
		assertEquals(fscore2, 0.67, 0.01);

	}
	
	@Test
	public void testEvaluationThreeRules() {
		
		FraudRuleV2Application evaluation = 
				new FraudRuleV2Application.FraudRuleV2ApplicationBuilder()
				.addRule(new RuledTransaction("1,1,0,1"))
				.addRule(new RuledTransaction("1,1,1,0"))
				.addRule(new RuledTransaction("0,0,1,1"))
				.build();
	
		
		Map<String,Double> output = evaluation.calculate();
		double phiRule1 = output.get("1");
		double phiRule2 = output.get("2");
		double phiRule3 = output.get("2");
		

		assertEquals(phiRule1, 0.433, 0.001);
		assertEquals(phiRule2, 0.183, 0.001);
		assertEquals(phiRule3, 0.183, 0.001);
		
		//symmetry
		assertEquals(phiRule2, phiRule3, 0.00001);
		
	}
	
	@Test
	public void testEvaluationFourRules() {
		
		FraudRuleV2Application evaluation = 
				new FraudRuleV2Application.FraudRuleV2ApplicationBuilder()
				.addRule(new RuledTransaction("1,1,1,1,0"))
				.addRule(new RuledTransaction("1,1,1,1,0"))
				.addRule(new RuledTransaction("1,1,1,1,0"))
				.addRule(new RuledTransaction("1,0,0,0,1"))
				.addRule(new RuledTransaction("0,1,1,1,0"))
				.build();
		
		Map<String,Double> output = evaluation.calculate();
		double phiRule1 = output.get("1");
		double phiRule2 = output.get("2");
		double phiRule3 = output.get("3");
		double phiRule4 = output.get("4");
		
		
		double precision1 = evaluation.getPrecision("1");
		double recall1 = evaluation.getRecall("1");
		double fscore1 = evaluation.getFscore("1");
		
		System.out.println(precision1);
		System.out.println(recall1);
		System.out.println(fscore1);
		
		double precision4 = evaluation.getPrecision("4");
		double recall4 = evaluation.getRecall("4");
		double fscore4 = evaluation.getFscore("4");
		
		System.out.println(precision4);
		System.out.println(recall4);
		System.out.println(fscore4);

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
	
	
	@Ignore
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
	

	@Ignore
	@Test
	public void testEvaluationFromFileRules() throws ShapleyApplicationException, FileNotFoundException, IOException {
		
		FraudRuleV2Application evaluation = null; 
		FraudRuleV2ApplicationBuilder builder=
				new FraudRuleV2Application.FraudRuleV2ApplicationBuilder();
		
		
		ClassLoader classLoader = getClass().getClassLoader();
		FileReader file = new FileReader(classLoader.getResource("shapley_data_small.csv").getFile());
		//FileReader file = new FileReader(classLoader.getResource("foo3.csv").getFile());

		
		try (BufferedReader br = new BufferedReader(file)) { 
		    String line;
		    while ((line = br.readLine()) != null) {
		       builder.addRule(new RuledTransaction(line));
		    }
		}
		logger.info("file read ok");
		evaluation = builder.build();
		
		TreeMap<String, Double> prev_sorted_map = new TreeMap<String, Double>();

		for(int i=1; i<=10;i++) {
			Stopwatch stopwatch = Stopwatch.createStarted();
			
	        TreeMap<String, Double> sorted_map = evaluation.randomCalculateWithThreads(30,4);
			long duration = stopwatch.elapsed(TimeUnit.SECONDS);


			logger.info("loop {}",i);
			for(Map.Entry<String,Double> entry : sorted_map.entrySet()) {
				  String key = entry.getKey();
				  Double value = entry.getValue();

				  System.out.print(key + " => " + String.format("%.5f", value)+"; ");
			}
			System.out.println();
			//compare sorted map
			if(!prev_sorted_map.isEmpty()) {
				NavigableSet<String> prevkeys = prev_sorted_map.navigableKeySet();
				Iterator<String> prevIterator = prevkeys.iterator();				
				NavigableSet<String> keys = sorted_map.navigableKeySet();
				Iterator<String> iterator = keys.iterator();
				
				int count = 0;
				while(iterator.hasNext()) {
					String key = iterator.next();
					String prevKey = prevIterator.next();
					if(key.equals(prevKey)) {
						count++;
					} else {
						break;
					}
				}
				logger.info("compare {}",count);		
			}
			
			prev_sorted_map = sorted_map;
	
			/*
			System.out.println(evaluation.getCfunction().getValue(new HashSet<>(Arrays.asList(1))));		
			System.out.println(evaluation.getCfunction().getValue(new HashSet<>(Arrays.asList(2))));
			System.out.println(evaluation.getCfunction().getValue(new HashSet<>(Arrays.asList(3))));
			System.out.println();
			*/
			/*
			System.out.println(evaluation.getCfunction().getValue(new HashSet<>(Arrays.asList(288))));		
			System.out.println(evaluation.getCfunction().getValue(new HashSet<>(Arrays.asList(288, 188))));
			System.out.println(evaluation.getCfunction().getValue(new HashSet<>(Arrays.asList(288, 188, 18))));
			System.out.println(evaluation.getCfunction().getValue(new HashSet<>(Arrays.asList(288, 188, 18, 295))));
			System.out.println(evaluation.getCfunction().getValue(new HashSet<>(Arrays.asList(288, 188, 18, 295, 45))));
			System.out.println(evaluation.getCfunction().getValue(new HashSet<>(Arrays.asList(288, 188, 18, 295, 45, 365))));
			System.out.println(evaluation.getCfunction().getValue(new HashSet<>(Arrays.asList(288, 188, 18, 295, 45, 365, 343))));
			System.out.println(evaluation.getCfunction().getValue(new HashSet<>(Arrays.asList(288, 188, 18, 295, 45, 365, 343, 91))));
			System.out.println(evaluation.getCfunction().getValue(new HashSet<>(Arrays.asList(288, 188, 18, 295, 45, 365, 343, 91, 293))));
			*/
			
			/*System.out.println(""+308);
			System.out.println(evaluation.getCfunction().getValue(new HashSet<>(Arrays.asList(308))));
			System.out.println(""+1);
			System.out.println(evaluation.getCfunction().getValue(new HashSet<>(Arrays.asList(1))));*/
			
			logger.info(" duration  {}",duration);
		}
	}
	

	
}


