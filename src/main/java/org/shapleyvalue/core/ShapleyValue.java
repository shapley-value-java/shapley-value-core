package org.shapleyvalue.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.shapleyvalue.util.FactorialUtil;
import org.shapleyvalue.util.Permutations;

public class ShapleyValue {
	
	//private Map<Set<Integer>, Double> input; 
	private CharacteristicFunction cfunction;
	private Map<Integer,Double> output;
	
	private final Logger logger = LoggerFactory.getLogger(ShapleyValue.class);
	
	
	public ShapleyValue(CharacteristicFunction cfunction) {
		this.cfunction = cfunction;

		this.output = new HashMap<>();
		
		//this.input.putAll(input);
		if(logger.isDebugEnabled()) logger.debug("ShapleyValue cfunction={}",this.cfunction);
	}
	
	public Map<Integer,Double> calculate() {
		return calculate(false);
	}
	
	
	public Map<Integer,Double> calculate(boolean normalized) {
		if(logger.isDebugEnabled()) logger.debug("ShapleyValue calculate started");
		
		int size = cfunction.getNbPlayers();
		int factorielSize = FactorialUtil.factorial(size);
		List<List<Integer>> permutations = Permutations.getAllPermutation(size);
		
		for(int i=1; i<=size; i++) {
			output.put(i, 0.0);
		}
		
		for(List<Integer> coalition : permutations) {
			Set<Integer> set = new HashSet<>();
			double prevVal =0;
			for(Integer element : coalition) {
				set.add(element);
				double val = cfunction.getValue(set)-prevVal;
				output.put(element,val+output.get(element));
				prevVal += val;
			}
		}
		
		double total = 0;
		for(int i=1; i<=size; i++) {
			total += output.get(i)/factorielSize;
			output.put(i, output.get(i)/factorielSize);
		}
		
		if(normalized) {
			for(int i=1; i<=size; i++) {
				output.put(i, output.get(i)/total);
			}
		}
		
		
		if(logger.isDebugEnabled()) logger.debug("ShapleyValue calculate output={}",output);
		return output;
		
	}
	
}
