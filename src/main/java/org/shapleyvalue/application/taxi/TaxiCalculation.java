package org.shapleyvalue.application.taxi;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.shapleyvalue.core.CharacteristicFunction;
import org.shapleyvalue.core.ShapleyValue;
import org.shapleyvalue.core.CharacteristicFunction.CharacteristicFunctionBuilder;
import org.shapleyvalue.util.Powerset;

/**
 * Application of the Shapley value for sharing a taxi
 * How to divide "fairly" the cost when we share a taxi
 * 
 * Example: 
 * A, B and C are sharing a taxi (they are going to the same direction...)
 * For A alone it costs 6.0
 * For B alone it costs 12.0
 * For C alone it costs 42.0
 * 
 * Result:
 * Shapley value for A is 2.0
 * Shapley value for B is 5.0
 * Shapley value for C is 35.0
 * the sum is 42.0 (the highest cost alone)
 * 
 * @author Franck Benault
 *
 */
public class TaxiCalculation {
	
	private CharacteristicFunction cfunction;
	private ShapleyValue shapleyValue;
	private Map<Integer, String> range;
	
	private TaxiCalculation(TaxiCalculationBuilder builder) {
		Set<Set<Integer>> sets = Powerset.calculate(builder.getNbPlayers());

		CharacteristicFunctionBuilder cfunctionBuilder = 
				new CharacteristicFunction.CharacteristicFunctionBuilder(builder.getNbPlayers());
		
		for(Set<Integer> set : sets) {
			double max =0;
			for(Integer i : set) {
				double val = builder.getV().get(i);
				if(val>max) max=val;
			}
			cfunctionBuilder.addCoalition(max, set.toArray(new Integer[set.size()]));
		}
		range = builder.getRange();
		cfunction = cfunctionBuilder.build();
	}
	
	/**
	 * Builder for TaxiCalculation class
	 * 
	 * @author Franck Benault
	 *
	 */
	public static class TaxiCalculationBuilder {
		private int nbPlayers;
		private Map<Integer, Double> v;
		private Map<Integer, String> range;

		public TaxiCalculationBuilder() {
			nbPlayers = 0;
			v = new HashMap<>();
			range = new HashMap<>();
		}

		public TaxiCalculationBuilder addUser(double value, String userName) {
			nbPlayers ++;
			v.put(nbPlayers, value);
			range.put(nbPlayers, userName);
			return this;
		}

		public TaxiCalculation build() {
			return new TaxiCalculation(this);
		}

		public int getNbPlayers() {
			return nbPlayers;
		}
		
		public  Map<Integer, Double> getV() {
			return v;
		}
		
		public  Map<Integer, String> getRange() {
			return range;
		}

	}


	public Map<String, Double> calculate() {
		shapleyValue = new ShapleyValue(cfunction);
		shapleyValue.calculate(); 
		Map<Integer, Double> tempRes = shapleyValue.getResult(); 
		Map<String, Double> res = new HashMap<>();
		for(Integer i : tempRes.keySet()) {
			res.put(range.get(i), tempRes.get(i));
		}
		return res;
		
	}

}
