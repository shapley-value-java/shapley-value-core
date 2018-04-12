package org.shapleyvalue.util;

import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

public class Powerset {
	
	private static final Logger logger = LoggerFactory.getLogger(Powerset.class);
	
	public static final  Set<Integer> nullSet = new HashSet<Integer>();
	
	public static Set<Set<Integer>> calculate(int nbElements) {
		
		Set<Integer> inputSet = new HashSet<>();
		for(int i=1; i<=nbElements; i++) inputSet.add(i);
		logger.debug("inputSet {}", inputSet);
		
		Set<Set<Integer>> result = Sets.powerSet(inputSet);
	    logger.debug("Powerset for {} element, result {}",nbElements, result);
		return result;
	}

}