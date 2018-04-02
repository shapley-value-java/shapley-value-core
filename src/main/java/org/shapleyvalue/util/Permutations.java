package org.shapleyvalue.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Permutations {
	
	private static final Logger logger = LoggerFactory.getLogger(Permutations.class);
	
	public static List<List<Integer>> getAllPermutation(int size) {
		
		List<List<Integer>> res = new ArrayList<>();
		List<Integer> elements = new ArrayList<>();

		for(int i=1 ; i<=size; i++) {
			elements.add(i);		
		}
		
		List<List<Integer>> emptyListOfList = new ArrayList<List<Integer>>();
		emptyListOfList.add(new ArrayList<Integer>());
		Pair<List<List<Integer>>,List<Integer>> pair = new MutablePair<>(emptyListOfList,elements);
		List<Pair<List<List<Integer>>,List<Integer>>> tempList = new ArrayList<>();
		tempList.add(pair);
		
		for(int i=1 ; i<=size; i++) {
			tempList = getAllPermutation(tempList);		
		}

		for(Pair<List<List<Integer>>,List<Integer>> tempPair : tempList) {
			res.addAll(tempPair.getLeft());
		}
		if(logger.isDebugEnabled()) logger.debug("getAllPermutations res={}",res);
		return res;
	}
	
	private static List<Pair<List<List<Integer>>,List<Integer>>> getAllPermutation(List<Pair<List<List<Integer>>,List<Integer>>> input) {
		
		
		List<Pair<List<List<Integer>>,List<Integer>>> res = new ArrayList<>();
		for(Pair<List<List<Integer>>,List<Integer>> pair : input) {
			for(Integer i : pair.getRight()) {
				for(List<Integer> tempList : pair.getLeft()) {	
			 		List<List<Integer>> left = new ArrayList<>();
					List<Integer> tempList2 = new ArrayList<>();
					tempList2.addAll(tempList);
					tempList2.add(i);
					left.add(tempList2);
					List<Integer> right = new ArrayList<>();
					right.addAll(pair.getRight());
					right.remove(i);
					Pair<List<List<Integer>>,List<Integer>> newPair = new MutablePair<>(left,right);
					res.add(newPair);
				}
			}
		}
		
		return res;
	}

}
