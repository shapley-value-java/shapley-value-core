package org.shapleyvalue.application.impl.fraud.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
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
		
		TreeMap<String, Double> prev_sorted_map = new TreeMap<String, Double>();

		for(int i=1; i<=10;i++) {
			Stopwatch stopwatch = Stopwatch.createStarted();
			
			Map<String,Double> output = evaluation.calculate(300,CoalitionStrategy.RANDOM);
			long duration = stopwatch.elapsed(TimeUnit.SECONDS);


	        ValueComparator bvc = new ValueComparator(output);
	        TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);
			sorted_map.putAll(output);
			System.out.println("loop "+i);
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
				System.out.println("compare "+count);		
			}
			
			prev_sorted_map = new TreeMap<String, Double>(bvc);
			prev_sorted_map.putAll(output);
			
			
			


			logger.info(" duration  {}",duration);
		}
	}
	

	
}

class ValueComparator implements Comparator<String> {
    Map<String, Double> base;

    public ValueComparator(Map<String, Double> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with
    // equals.
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
