package org.shapleyvalue.application.impl.glovegame;

import java.util.HashMap;
import java.util.HashSet;
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
 * Application of the Shapley value for the glove game
 * 
 * @author Franck Benault
 * 
 * @version	0.0.2
 * @since 0.0.2
 *
 */
public class GloveGameApplication implements ShapleyApplication {
	
	private CharacteristicFunction cfunction;
	private ShapleyValue shapleyValue;
	private Map<Integer, String> range;

	public static class GloveGameApplicationBuilder {
		
		private int nbPlayers;
		private Map<Integer, String> range;
		private Map<Integer, String> v;
		
		public GloveGameApplicationBuilder() {
			nbPlayers = 0;		
			range = new HashMap<>();
			v = new HashMap<>();
		}

		public GloveGameApplicationBuilder  addPlayer(String playerName, String hand) {
			nbPlayers++;
			range.put(nbPlayers, playerName);
			v.put(nbPlayers, hand);
			return this;
		}
		
		public GloveGameApplication build() {

			return new GloveGameApplication(this);
		}
		
		public int getNbPlayers() {
			return nbPlayers;
		}
		
		public Map<Integer, String> getRange() {
			return range;
		}
		
		public  Map<Integer, String> getV() {
			return v;
		}

	}
	
	public GloveGameApplication(GloveGameApplicationBuilder builder) {
		Set<Set<Integer>> sets = Powerset.calculate(builder.getNbPlayers());

		CharacteristicFunctionBuilder cfunctionBuilder = 
				new CharacteristicFunction.CharacteristicFunctionBuilder(builder.getNbPlayers());
		
		for(Set<Integer> set : sets) {
			Set<String> handFound = new HashSet<>();
			for(Integer i : set) {	
				handFound.add(builder.getV().get(i));			
			}
			if(handFound.size()==2)
				cfunctionBuilder.addCoalition(1, set.toArray(new Integer[set.size()]));
			else
				cfunctionBuilder.addCoalition(0, set.toArray(new Integer[set.size()]));
		}
		range = builder.getRange();
		cfunction = cfunctionBuilder.build();
		shapleyValue = new ShapleyValue(cfunction);
	}

	@Override
	public Map<String, Double> calculate() {
		shapleyValue.calculate(0, false);
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
		Map<Integer, Double> tempRes = shapleyValue.getResult(0);
		Map<String, Double> res = new HashMap<>();
		for(Integer i : tempRes.keySet()) {
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
		
		Map<Integer, Double> tempRes = shapleyValue.getResult();
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
