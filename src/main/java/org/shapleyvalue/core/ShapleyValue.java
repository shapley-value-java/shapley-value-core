package org.shapleyvalue.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.shapleyvalue.util.FactorialUtil;
import org.shapleyvalue.util.permutation.PermutationLinkList;
import org.shapleyvalue.util.permutation.RandomPermutations;

public class ShapleyValue {

	private CharacteristicFunction cfunction;
	private Map<Integer, Double> output;
	private PermutationLinkList permutations;
	private long currentRange;
	private int size;

	private final Logger logger = LoggerFactory.getLogger(ShapleyValue.class);

	public ShapleyValue(CharacteristicFunction cfunction) {
		this.cfunction = cfunction;
		size = cfunction.getNbPlayers();
		currentRange = 0;

		this.output = new HashMap<>();
		for (int i = 1; i <= size; i++) {
			output.put(i, 0.0);
		}

		// if(logger.isDebugEnabled()) logger.debug("ShapleyValue
		// cfunction={}",this.cfunction);
	}

	public Map<Integer, Double> calculate() {
		return calculate(false, 0,false);
	}

	public Map<Integer, Double> calculate(boolean normalized, long sampleSize, boolean randomValue) {
		if (logger.isDebugEnabled())
			logger.debug("ShapleyValue calculate started");
		// System.out.println("currentRange "+currentRange);
		long factorialSize = FactorialUtil.factorial(size);
		// System.out.println("factorialSize "+factorialSize);

		// int size = cfunction.getNbPlayers();
		// long factorialSize = FactorialUtil.factorial(size);

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
				output.put(element, val + output.get(element));
				prevVal += val;
			}
			// System.out.println("output "+output);
		}

		double total = 0;
		if (isLastReached()) {
			for (int i = 1; i <= size; i++) {
				total += output.get(i) / factorialSize;
				output.put(i, output.get(i) / factorialSize);
			}

			if (normalized) {
				Map<Integer, Double> normalizedOutput = new HashMap<>();
				for (int i = 1; i <= size; i++) {
					normalizedOutput.put(i, output.get(i) / total);
				}
				if (logger.isDebugEnabled())
					logger.debug("ShapleyValue calculate normalizedOutput={}", normalizedOutput);
				return normalizedOutput;
			}
		}
		if (logger.isDebugEnabled())
			logger.debug("ShapleyValue calculate output={}", output);
		return output;

	}

	public boolean isLastReached() {
		if (currentRange < FactorialUtil.factorial(size))
			return false;
		else
			return true;
	}

}
