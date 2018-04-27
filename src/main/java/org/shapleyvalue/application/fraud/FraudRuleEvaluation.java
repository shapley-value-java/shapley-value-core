package org.shapleyvalue.application.fraud;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.shapleyvalue.core.CharacteristicFunction;
import org.shapleyvalue.core.CharacteristicFunction.CharacteristicFunctionBuilder;
import org.shapleyvalue.core.ShapleyValue;
import org.shapleyvalue.util.Powerset;

/**
 * Application of the Shapley value for fraud rules evaluation
 * Each fraud rule may detect a set of fraud events
 * 
 * Example:
 * The rules "rule1" "rule2" "rule3" detect the event 1 2 and 3
 * The rule "rule4" detects the event 4
 * 
 * Result:
 * The Shapley value (normalized to 1) for each rule is 0.25  
 * The rule "rule4" (with only one detection) has 
 * the same value than "rule1" (with 3 detections) because it detects a event 
 * which is not detected by the other rules.
 * 
 * @author Franck Benault
 *
 */
public class FraudRuleEvaluation {
	
	private CharacteristicFunction cfunction;
	private ShapleyValue shapleyValue;
	private Map<Integer, String> range;
	
	public FraudRuleEvaluation(FraudRuleEvaluationBuilder builder) {
		Set<Set<Integer>> sets = Powerset.calculate(builder.getNbPlayers());

		CharacteristicFunctionBuilder cfunctionBuilder = 
				new CharacteristicFunction.CharacteristicFunctionBuilder(builder.getNbPlayers());
		
		for(Set<Integer> set : sets) {
			Set<Integer> rulesFound = new HashSet<>();
			for(Integer i : set) {	
				rulesFound.addAll(builder.getV().get(i));			
			}
			cfunctionBuilder.addCoalition(rulesFound.size(), set.toArray(new Integer[set.size()]));
		}
		range = builder.getRange();
		cfunction = cfunctionBuilder.build();
	}

	public Map<String, Double> calculate() {
		shapleyValue = new ShapleyValue(cfunction);
		shapleyValue.calculate(0, false);
		Map<Integer, Double> tempRes = shapleyValue.getResult(1);
		Map<String, Double> res = new HashMap<>();
		for(Integer i : tempRes.keySet()) {
			res.put(range.get(i), tempRes.get(i));
		}
		return res;
		
	}
	
	/**
	 * 
	 * Builder for FraudRuleEvaluation class
	 * 
	 * @author Franck Benault
	 *
	 */
	public static class FraudRuleEvaluationBuilder {
		
		private int nbPlayers;
		private Map<Integer, String> range;
		private Map<Integer, List<Integer>> v;
		
		public FraudRuleEvaluationBuilder() {
			nbPlayers = 0;		
			range = new HashMap<>();
			v = new HashMap<>();
		}
		
		public FraudRuleEvaluationBuilder addRule(String ruleName, Integer... eventIds) {
			nbPlayers++;
			range.put(nbPlayers, ruleName);
			v.put(nbPlayers, Arrays.asList(eventIds));
			return this;
		}
		
		public Map<Integer, String> getRange() {
			// TODO Auto-generated method stub
			return range;
		}

		public int getNbPlayers() {
			return nbPlayers;
		}

		public FraudRuleEvaluation build() {

			return new FraudRuleEvaluation(this);
		}
		
		public  Map<Integer, List<Integer>> getV() {
			return v;
		}
		
	}

}
