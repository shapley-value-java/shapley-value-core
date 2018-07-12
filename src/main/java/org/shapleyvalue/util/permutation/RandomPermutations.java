package org.shapleyvalue.util.permutation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.shapleyvalue.util.FactorialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomPermutations {
	
	private static final Logger logger = LoggerFactory.getLogger(RandomPermutations.class);

	
	public static List<Integer> getRandomOld(long size) {
		//long n= 13;
		//System.out.println(FactorialUtil.factorial(size));
		
		long random= (long )(Math.random() * FactorialUtil.factorial(size));
		//random = FactorialUtil.factorial(n) -1;
		//random = 1;
		//System.out.println("random="+random);
		List<Integer> ints = new ArrayList<>();
		List<Integer> outputs = new ArrayList<>();
		for(int i=1; i<=size; i++) {
			ints.add(i);
		}
		
		long reste =0;
		for(long divisor = (size-1); divisor >1; divisor--) {
			//System.out.println("divisor "+divisor);
			long result = random / FactorialUtil.factorial(divisor);
			reste = random % FactorialUtil.factorial(divisor);
			//System.out.println(" "+divisor+"!*"+result);
			random = reste;
			Integer temp = ints.get((int) result);
			outputs.add(temp);
			ints.remove(temp);
			
		}
		//System.out.println(" 1!*"+reste);
		Integer temp = ints.get((int) reste);
		outputs.add(temp);
		ints.remove(temp);
		outputs.add(ints.get(0));

		
		return outputs;
	}
	
	private static int getRandom(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}
	
	public static List<Integer> getRandom(long size) {
		
		List<Integer> res = new ArrayList<>();
		List<Integer> temp = new ArrayList<>();
		for(int i=1; i<=size; i++) {
			temp.add(i);
		}

		while(!temp.isEmpty()) {
			int random = getRandom(0, temp.size()-1);
			res.add(temp.get(random));
			temp.remove(random);
		}
		
		logger.debug("res {}", res);
		
		return res;
	}

}
