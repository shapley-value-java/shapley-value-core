package org.shapleyvalue.application.fraud;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.shapleyvalue.application.CoalitionStrategy;
import org.shapleyvalue.application.ShapleyApplication;
import org.shapleyvalue.application.ShapleyApplicationException;
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
 * @version	0.0.2
 * @since 0.0.1
 *
 */
public class FraudRuleApplication implements ShapleyApplication {
	
	private CharacteristicFunction cfunction;
	private ShapleyValue shapleyValue;
	private Map<Integer, String> range;
	
	public FraudRuleApplication(FraudRuleEvaluationBuilder builder) {
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

	@Override
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
			return range;
		}

		public int getNbPlayers() {
			return nbPlayers;
		}

		public FraudRuleApplication build() {

			return new FraudRuleApplication(this);
		}
		
		public  Map<Integer, List<Integer>> getV() {
			return v;
		}
		
	}

	@Override
	public Map<String, Double> calculate(long nbCoalitions) throws ShapleyApplicationException {
		shapleyValue = new ShapleyValue(cfunction);
		shapleyValue.calculate(nbCoalitions, false);
		Map<Integer, Double> tempRes = shapleyValue.getResult(1);
		Map<String, Double> res = new HashMap<>();
		for(Integer i : tempRes.keySet()) {
			res.put(range.get(i), tempRes.get(i));
		}
		return res;
	}

	@Override
	public Map<String, Double> calculate(long nbCoalitions, CoalitionStrategy strategy)
			throws ShapleyApplicationException {
		shapleyValue = new ShapleyValue(cfunction);
		if(strategy.equals(CoalitionStrategy.SEQUENTIAL))
			shapleyValue.calculate(nbCoalitions, false);
		else 
			shapleyValue.calculate(nbCoalitions, true);
		
		Map<Integer, Double> tempRes = shapleyValue.getResult(1);
		Map<String, Double> res = new HashMap<>();
		for(Integer i : tempRes.keySet()) {
			res.put(range.get(i), tempRes.get(i));
		}
		return res;
	}

	@Override
	public boolean isLastCoalitionReached() throws ShapleyApplicationException {

		return shapleyValue.isLastReached();
	}

}
