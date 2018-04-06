package org.shapleyvalue.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubSets {
	
	private static final Logger logger = LoggerFactory.getLogger(SubSets.class);
	
	public static Set<Set<Integer>> getAllSubSetsNonEmpty(int nbElements) {
		Set<Set<Integer>> result = new HashSet<>();
		

	    for (int i=1 ; i<=nbElements; i++) {
	    	result.add(new HashSet<>(Arrays.asList(i)));

	    	List<Set<Integer>> temp = new ArrayList<>();
	        for (Set<Integer> innerSet : result) {
	        	
	        	Set<Integer> newSet = new HashSet<Integer>(innerSet);
	            newSet.add(i);
	            temp.add(newSet);
	        }
	        result.addAll(temp);
	        
	    }

	    logger.debug("getAllSubSetsNonEmpty for {} element, result {}",nbElements, result);
		return result;
	}
	


}
