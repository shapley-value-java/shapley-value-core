package org.shapleyvalue.application.impl.fraud.v2;

import java.util.HashSet;
import java.util.Set;

public class RuledTransaction {
	

	private boolean isFraud;
	private boolean isFired;
	
	private Set<Integer> rules;
	
	private int nbRules;
	
	public RuledTransaction(String line) {
		
		rules = new HashSet<>();
		String[] vals = line.split(",");
		if("1".equals(vals[0])) {
			isFraud = true;
		}
		for(int i =1; i<vals.length; i++) {
			if("1".equals(vals[i])) {
				rules.add(i);
			}
		}
		nbRules = vals.length-1;
	}
	
	public RuledTransaction(RuledTransaction toClone) {
		
		isFraud = toClone.isFraud();
		rules = new HashSet<>();
		nbRules = toClone.getNbRules();

		rules.addAll(toClone.getRules());
		

	}
	
	public boolean isFraud() {
		return isFraud;
	}

	public Set<Integer> getRules() {
		return rules;
	}

	public void setRules(Set<Integer> rules) {
		this.rules = rules;
	}

	public int getNbRules() {
		return nbRules;
	}

	public boolean isFired() {
		return isFired;
	}

	public void setFired(boolean isFired) {
		this.isFired = isFired;
	}

}
