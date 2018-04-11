package org.shapleyvalue.extension.fraud;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.shapleyvalue.core.CharacteristicFunction;
import org.shapleyvalue.core.ShapleyValue;
import org.shapleyvalue.core.CharacteristicFunction.CharacteristicFunctionBuilder;
import org.shapleyvalue.util.SubSets;

public class FraudRuleEvaluation {
	
	private CharacteristicFunction cfunction;
	private ShapleyValue shapleyValue;
	private Map<Integer, String> range;
	
	
	public FraudRuleEvaluation(FraudRuleEvaluationBuilder builder) {
		Set<Set<Integer>> sets = SubSets.getAllSubSetsNonEmpty(builder.getNbPlayers());

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
		Map<Integer, Double> tempRes = shapleyValue.calculate(true);
		Map<String, Double> res = new HashMap<>();
		for(Integer i : tempRes.keySet()) {
			res.put(range.get(i), tempRes.get(i));
		}
		return res;
		
	}
	
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
