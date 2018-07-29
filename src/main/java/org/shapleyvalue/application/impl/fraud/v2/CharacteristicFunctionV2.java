package org.shapleyvalue.application.impl.fraud.v2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * represent the following function often written by v
 * 2^N to R
 * for each subset of the set {1.. N} is associated a value
 * per convention v(empty set) = 0
 * 
 * @author Franck Benault
 *
 */
public class CharacteristicFunctionV2 {
	


	private final Logger logger = LoggerFactory.getLogger(CharacteristicFunctionV2.class);

	private int nbPlayers;
	private List<RuledTransaction> ruledTransactions;


	private CharacteristicFunctionV2(CharacteristicFunctionBuilderV2 builder) {
		nbPlayers = builder.nbPlayers;
		ruledTransactions = builder.getRuledTransactions();

	}


	public CharacteristicFunctionV2(CharacteristicFunctionV2 toClone) {
		nbPlayers = toClone.getNbPlayers();
		ruledTransactions = new ArrayList<RuledTransaction>();
		
		for(RuledTransaction t : toClone.getRuledTransactions())
			ruledTransactions.add(new RuledTransaction(t));
	}


	public Collection<? extends RuledTransaction> getRuledTransactions() {
		return ruledTransactions;
	}


	public int getNbPlayers() {
		return nbPlayers;
	}

	
	public double getValue(Set<Integer> coalitionSet) {
			
		Tpfnfp v = new Tpfnfp(ruledTransactions, coalitionSet);
		return v.score();
	}
	
	public double getValue(Integer element) {
		
		Tpfnfp v = new Tpfnfp(ruledTransactions, element);
		return v.score();
	}
	
	public void setRuledTransactions(List<RuledTransaction> ruledTransactions) {
		this.ruledTransactions.addAll(ruledTransactions);
	}

	@Override
	public String toString() {
		return "CharacteristicFunction [nbPlayers=" + nbPlayers + "]";
	}

	public static class CharacteristicFunctionBuilderV2 {
		private final int nbPlayers;		
		private List<RuledTransaction> ruledTransactions;

		public CharacteristicFunctionBuilderV2(int nbPlayers) {
			this.nbPlayers = nbPlayers;
			this.ruledTransactions = new ArrayList<>(); 
			
		}

		public CharacteristicFunctionV2 build() {
			return new CharacteristicFunctionV2(this);
		}
		
		public List<RuledTransaction> getRuledTransactions() {
			return ruledTransactions;
		}

	}

	public void resetIsFired() {
		for(RuledTransaction ruledTransaction : ruledTransactions)
			ruledTransaction.setFired(false);
	}


}
