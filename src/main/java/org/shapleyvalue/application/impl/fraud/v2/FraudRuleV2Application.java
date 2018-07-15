package org.shapleyvalue.application.impl.fraud.v2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.shapleyvalue.application.facade.CoalitionStrategy;
import org.shapleyvalue.application.facade.ShapleyApplication;
import org.shapleyvalue.application.facade.ShapleyApplicationException;
import org.shapleyvalue.application.impl.fraud.v2.CharacteristicFunctionV2.CharacteristicFunctionBuilderV2;

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
	
	private CharacteristicFunctionV2 cfunction;
	private ShapleyValueV2 shapleyValue;

	
	public FraudRuleV2Application(FraudRuleV2ApplicationBuilder builder) {

		CharacteristicFunctionBuilderV2 cfunctionBuilder = 
				new CharacteristicFunctionV2.CharacteristicFunctionBuilderV2(builder.getNbPlayers());
		cfunction = cfunctionBuilder.build();
		cfunction.setRuledTransactions(builder.getRuledTransactions());
		shapleyValue = new ShapleyValueV2(cfunction);
	}

	@Override
	public Map<String, Double> calculate() {
		shapleyValue.calculate(0, false);
		List<Double> tempRes = shapleyValue.getResult(1);
		Map<String, Double> res = new HashMap<>();
		for(int i=1; i<=shapleyValue.getSize(); i++) {
			res.put(""+i, tempRes.get(i));
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
	public static class FraudRuleV2ApplicationBuilder {
		
		private int nbPlayers;
		private Map<Integer, String> range;
		private List<RuledTransaction> ruledTransactions;
		
		public FraudRuleV2ApplicationBuilder() {
			nbPlayers = 0;		
			ruledTransactions = new ArrayList<>();
			range = new HashMap<>();
		}
		
		public FraudRuleV2ApplicationBuilder addRule(RuledTransaction ruledTransaction) {
			nbPlayers = ruledTransaction.getNbRules();
			ruledTransactions.add(ruledTransaction);

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
		
		public  List<RuledTransaction> getRuledTransactions() {
			return ruledTransactions;
		}
		
	}

	@Override
	public Map<String, Double> calculate(long nbCoalitions) throws ShapleyApplicationException {
		shapleyValue.calculate(nbCoalitions, false);
		List<Double> tempRes = shapleyValue.getResult(1);
		Map<String, Double> res = new HashMap<>();
		for(int i=1; i<=shapleyValue.getSize(); i++) {
			res.put(""+i, tempRes.get(i));
		}
		return res;
	}

	@Override
	public Map<String, Double> calculate(long nbCoalitions, CoalitionStrategy strategy)
			throws ShapleyApplicationException {
		if(strategy.isSequential())
			shapleyValue.calculate(nbCoalitions, false);
		else 
			shapleyValue.randomCalculateWithThread(nbCoalitions);
		
		List<Double> tempRes = shapleyValue.getResult(1);
		Map<String, Double> res = new HashMap<>();
		for(int i=1; i<=shapleyValue.getSize(); i++) {
			res.put(""+i, tempRes.get(i));
		}
		return res;
	}

	@Override
	public boolean isLastCoalitionReached() throws ShapleyApplicationException {

		return shapleyValue.isLastReached();
	}

}
