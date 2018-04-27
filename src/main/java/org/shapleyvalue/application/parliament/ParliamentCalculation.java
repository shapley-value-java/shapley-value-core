package org.shapleyvalue.application.parliament;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.shapleyvalue.core.CharacteristicFunction;
import org.shapleyvalue.core.ShapleyValue;
import org.shapleyvalue.core.CharacteristicFunction.CharacteristicFunctionBuilder;
import org.shapleyvalue.util.Powerset;

/**
 * Application of the Shapley value for a parliament
 * 
 * 
 * @author Franck Benault
 *
 */
public class ParliamentCalculation {
	
	private CharacteristicFunction cfunction;
	private ShapleyValue shapleyValue;
	private Map<Integer, String> range;

	
	private ParliamentCalculation(ParliamentCalculationBuilder builder) {

		Set<Set<Integer>> sets = Powerset.calculate(builder.getNbPlayers());

		CharacteristicFunctionBuilder cfunctionBuilder = 
				new CharacteristicFunction.CharacteristicFunctionBuilder(builder.getNbPlayers());
		
		for(Set<Integer> set : sets) {
			int nbRepresentants =0;
			for(Integer i : set) {
				int val = builder.getV().get(i);
				nbRepresentants += val;
			}
			if(nbRepresentants>=(1+(builder.getNbRepresentants()/2)))
				cfunctionBuilder.addCoalition(1, set.toArray(new Integer[set.size()]));
			else
				cfunctionBuilder.addCoalition(0, set.toArray(new Integer[set.size()]));

		}
		range = builder.getRange();
		cfunction = cfunctionBuilder.build();
		shapleyValue = new ShapleyValue(cfunction);
	}
	
	/**
	 * 
	 * Builder for ParliamentCalculation class
	 * 
	 * @author Franck Benault
	 *
	 */
	public static class ParliamentCalculationBuilder {
		
		private int nbPlayers;
		private Map<Integer, Integer> v;
		private Map<Integer, String> range;
		private int nbRepresentants;

		public ParliamentCalculationBuilder() {
			nbPlayers = 0;
			nbRepresentants =0;
			v = new HashMap<>();
			range = new HashMap<>();
		}

		public ParliamentCalculationBuilder addParty(String partyName , int nbRepresentants) {
			nbPlayers ++;
			this.nbRepresentants += nbRepresentants;
			v.put(nbPlayers, nbRepresentants);
			range.put(nbPlayers, partyName);
			return this;
		}
		
		public ParliamentCalculation build() {
			return new ParliamentCalculation(this);
		}
		
		public int getNbPlayers() {
			return nbPlayers;
		}
		
		public  Map<Integer, Integer> getV() {
			return v;
		}
		
		public  Map<Integer, String> getRange() {
			return range;
		}

		public int getNbRepresentants() {
			return nbRepresentants;
		}

		
	}
	
	public void calculate(long sampleSize) {
		shapleyValue.calculate(sampleSize, false);	
	}
	
	public void calculate(long sampleSize, boolean random) {
		shapleyValue.calculate(sampleSize, random);	
	}
	
	public Map<String, Double> getResult() {
		Map<Integer, Double> tempRes = shapleyValue.getResult();
		Map<String, Double> res = new HashMap<>();
		double total =0;
		for(Integer i : tempRes.keySet()) {
			total += tempRes.get(i);
		}
		for(Integer i : tempRes.keySet()) {
			res.put(range.get(i), tempRes.get(i)/total);
		}
		return res;
	}
	

	
	public boolean isLastReached() {
		return shapleyValue.isLastReached();
	}
	

}
