package org.shapleyvalue.application.impl.fraud.v2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.shapleyvalue.application.facade.CoalitionStrategy;
import org.shapleyvalue.application.facade.ShapleyApplication;
import org.shapleyvalue.application.facade.ShapleyApplicationException;
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
 * @since 0.0.2
 *
 */
public class FraudRuleV2Application implements ShapleyApplication {
	
	private CharacteristicFunction cfunction;
	private ShapleyValue shapleyValue;
	private Map<Integer, String> range;
	
	public FraudRuleV2Application(FraudRuleApplicationBuilder builder) {
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
		shapleyValue = new ShapleyValue(cfunction);
	}

	@Override
	public Map<String, Double> calculate() {
		shapleyValue.calculate(0, false);
		List<Double> tempRes = shapleyValue.getResult(1);
		Map<String, Double> res = new HashMap<>();
		for(int i=1; i<=shapleyValue.getSize(); i++) {
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
	public static class FraudRuleApplicationBuilder {
		
		private int nbPlayers;
		private Map<Integer, String> range;
		private Map<Integer, List<Integer>> v;
		private List<RuledTransaction> ruledTransactions;
		
		public FraudRuleApplicationBuilder() {
			nbPlayers = 0;		
			ruledTransactions = new ArrayList<>();
			range = new HashMap<>();
			v = new HashMap<>();
		}
		
		public FraudRuleApplicationBuilder addRule(RuledTransaction ruledTransaction) {
			nbPlayers++;
			

			return this;
		}
		
		public Map<Integer, String> getRange() {
			return range;
		}

		public int getNbPlayers() {
			return nbPlayers;
		}

		public FraudRuleV2Application build() {

			return new FraudRuleV2Application(this);
		}
		
		public  Map<Integer, List<Integer>> getV() {
			return v;
		}
		
	}

	@Override
	public Map<String, Double> calculate(long nbCoalitions) throws ShapleyApplicationException {
		shapleyValue.calculate(nbCoalitions, false);
		List<Double> tempRes = shapleyValue.getResult(1);
		Map<String, Double> res = new HashMap<>();
		for(int i=1; i<=shapleyValue.getSize(); i++) {
			res.put(range.get(i), tempRes.get(i));
		}
		return res;
	}

	@Override
	public Map<String, Double> calculate(long nbCoalitions, CoalitionStrategy strategy)
			throws ShapleyApplicationException {
		if(strategy.isSequential())
			shapleyValue.calculate(nbCoalitions, false);
		else 
			shapleyValue.calculate(nbCoalitions, true);
		
		List<Double> tempRes = shapleyValue.getResult(1);
		Map<String, Double> res = new HashMap<>();
		for(int i=1; i<=shapleyValue.getSize(); i++) {
			res.put(range.get(i), tempRes.get(i));
		}
		return res;
	}

	@Override
	public boolean isLastCoalitionReached() throws ShapleyApplicationException {

		return shapleyValue.isLastReached();
	}

}
