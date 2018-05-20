package org.shapleyvalue.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.shapleyvalue.util.FactorialUtil;
import org.shapleyvalue.util.permutation.PermutationLinkList;
import org.shapleyvalue.util.permutation.RandomPermutations;

public class ShapleyValue {

	private CharacteristicFunction cfunction;
	private List<Double> output;
	private PermutationLinkList permutations;
	private long currentRange;
	private int size;
	private long factorialSize;

	private final Logger logger = LoggerFactory.getLogger(ShapleyValue.class);

	public ShapleyValue(CharacteristicFunction cfunction) {
		this.cfunction = cfunction;
		size = cfunction.getNbPlayers();
		currentRange = 0;
		factorialSize = FactorialUtil.factorial(size);

		this.output = new ArrayList<>(size+1);
		for (int i = 0; i <= size; i++) {
			output.add(0.0);
		}
	}

	public void calculate() {
		calculate(0,false);
	}

	public void calculate(long sampleSize, boolean randomValue) {
		if (logger.isDebugEnabled())
			logger.debug("ShapleyValue calculate started");


		if (permutations == null) {
			permutations = new PermutationLinkList(size);
		}

		int count = 1;
		if (sampleSize <= 0) {
			sampleSize = factorialSize;
		}

		while (!isLastReached() && count <= sampleSize) {
			List<Integer> coalition = null;
			if(!randomValue) { 
				coalition = permutations.getNextPermutation();
			} else  {
				coalition = RandomPermutations.getRandom(size);
			}
				
			currentRange++;
			// System.out.println("currentRange "+currentRange);
			count++;
			Set<Integer> set = new HashSet<>();
			double prevVal = 0;
			for (Integer element : coalition) {
				set.add(element);
				double val = cfunction.getValue(set) - prevVal;
				output.set(element, val + output.get(element));
				prevVal += val;
			}

		}

	}
	
	public List<Double> getResult() {
		return getResult(0) ;
	}
	
	public List<Double> getResult(int normalizedValue) {
		
		List<Double> res = new ArrayList<>();
		res.add(0.0);
		double total = 0;
		for (int i = 1; i <= size; i++) {
			total += output.get(i) / factorialSize;
			res.add(output.get(i) / factorialSize);
		}

		if (normalizedValue!=0) {
			List<Double> normalizedRes = new ArrayList<>();
			normalizedRes.add(0.0);
			for (int i = 1; i <= size; i++) {
				normalizedRes.add(res.get(i) / total);
			}
			if (logger.isDebugEnabled())
				logger.debug("ShapleyValue calculate normalizedOutput={}", normalizedRes);
			return normalizedRes;
		}
		if (logger.isDebugEnabled())
			logger.debug("ShapleyValue getResult output={}", output);
		return res;
	}
	
	

	public boolean isLastReached() {
		if (currentRange < FactorialUtil.factorial(size))
			return false;
		else
			return true;
	}
	
	public int getSize() {
		return size;
	}

}
