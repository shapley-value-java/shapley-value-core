package org.shapleyvalue.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CharacteristicFunction {
	
	private final Logger logger = LoggerFactory.getLogger(CharacteristicFunction.class);

	private int nbPlayers;
	private Map<Set<Integer>, Double> v;

	private CharacteristicFunction(CharacteristicFunctionBuilder builder) {
		nbPlayers = builder.nbPlayers;
		v = new HashMap<>();
		v = builder.v;
	}


	public int getNbPlayers() {
		return nbPlayers;
	}

	public double getValue(Integer... coalition) {
		Set<Integer> coalitionSet = new HashSet<>();
		
		for(Integer player : coalition) coalitionSet.add(player);
		
		return this.v.get(coalitionSet);
	}
	
	public double getValue(Set<Integer> coalitionSet) {
		
		return this.v.get(coalitionSet);
	}

	@Override
	public String toString() {
		return "CharacteristicFunction [nbPlayers=" + nbPlayers + ", v=" + v + "]";
	}

	public static class CharacteristicFunctionBuilder {
		private final int nbPlayers;
		private Map<Set<Integer>, Double> v;

		public CharacteristicFunctionBuilder(int nbPlayers) {
			this.nbPlayers = nbPlayers;
			v = new HashMap<>();
		}

		public CharacteristicFunctionBuilder addCoalition(double value, Integer... coalition) {
			Set<Integer> set = new HashSet<>();
			for (Integer player : coalition) {
				set.add(player);
			}
			v.put(set, value);
			return this;
		}

		public CharacteristicFunction build() {
			return new CharacteristicFunction(this);
		}

	}

	public void addDummyUser() {
		if(logger.isDebugEnabled()) logger.debug("begin addDummyUser {}", this);
		
		Map<Set<Integer>, Double> newV = new HashMap<>();

		
		Set<Set<Integer>> coalitions = v.keySet();
		for(Set<Integer> coalition : coalitions) {
			
			Set<Integer> coalitionSet = new HashSet<>();
			coalitionSet.addAll(coalition);
			coalitionSet.add(nbPlayers+1);
			
			newV.put(coalitionSet, v.get(coalition));
		}
		
		Set<Integer> coalitionSet = new HashSet<>();
		coalitionSet.add(nbPlayers+1);
		v.put(coalitionSet, 0.0);
		
		for(Set<Integer> coalition : newV.keySet()) {
			v.put(coalition, newV.get(coalition));
		}
		nbPlayers++;
		
		if(logger.isDebugEnabled()) logger.debug("end addDummyUser {}", this);
		
	}

}
