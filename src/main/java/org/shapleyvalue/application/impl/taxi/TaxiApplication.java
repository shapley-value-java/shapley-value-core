package org.shapleyvalue.application.impl.taxi;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.shapleyvalue.application.facade.CoalitionStrategy;
import org.shapleyvalue.application.facade.ShapleyApplication;
import org.shapleyvalue.application.facade.ShapleyApplicationException;
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
 * @version	0.0.2
 * @since 0.0.1
 * 
 */
public class TaxiApplication implements ShapleyApplication {
	
	private CharacteristicFunction cfunction;
	private ShapleyValue shapleyValue;
	private Map<Integer, String> range;
	
	private TaxiApplication(TaxiApplicationBuilder builder) {
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
		shapleyValue = new ShapleyValue(cfunction);
	}
	
	/**
	 * Builder for TaxiCalculation class
	 * 
	 * @author Franck Benault
	 *
	 */
	public static class TaxiApplicationBuilder {
		private int nbPlayers;
		private Map<Integer, Double> v;
		private Map<Integer, String> range;

		public TaxiApplicationBuilder() {
			nbPlayers = 0;
			v = new HashMap<>();
			range = new HashMap<>();
		}

		public TaxiApplicationBuilder addUser(double value, String userName) {
			nbPlayers ++;
			v.put(nbPlayers, value);
			range.put(nbPlayers, userName);
			return this;
		}

		public TaxiApplication build() {
			return new TaxiApplication(this);
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

	@Override
	public Map<String, Double> calculate() {
		shapleyValue.calculate(); 
		Map<Integer, Double> tempRes = shapleyValue.getResult(); 
		Map<String, Double> res = new HashMap<>();
		for(Integer i : tempRes.keySet()) {
			res.put(range.get(i), tempRes.get(i));
		}
		return res;
		
	}


	@Override
	public Map<String, Double> calculate(long nbCoalitions) throws ShapleyApplicationException {
		shapleyValue.calculate(nbCoalitions, false); 
		Map<Integer, Double> tempRes = shapleyValue.getResult(); 
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
