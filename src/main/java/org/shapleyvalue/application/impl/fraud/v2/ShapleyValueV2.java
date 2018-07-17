package org.shapleyvalue.application.impl.fraud.v2;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.shapleyvalue.util.FactorialUtil;
import org.shapleyvalue.util.permutation.PermutationLinkList;
import org.shapleyvalue.util.permutation.RandomPermutations;

public class ShapleyValueV2 {

	private CharacteristicFunctionV2 cfunction;
	private List<Double> output;
	private PermutationLinkList permutations;
	private long currentRange;
	private int size;
	private long factorialSize;

	private final Logger logger = LoggerFactory.getLogger(ShapleyValueV2.class);

	public ShapleyValueV2(CharacteristicFunctionV2 cfunction) {
		this.cfunction = cfunction;
		size = cfunction.getNbPlayers();
		/*currentRange = 0;*/
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

		long count = 1;
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
			if(logger.isDebugEnabled())
				logger.debug("coalition {}", coalition);
				
			currentRange++;

			count++;
			//Set<Integer> set = new HashSet<>();
			double prevVal = 0.0;
			cfunction.resetIsFired();
			for (Integer element : coalition) {
				//set.add(element);
				double newVal = cfunction.getValue(element);
				double contribution = newVal - prevVal;
				output.set(element, contribution + output.get(element));
				prevVal = newVal;
			}

		}

	}
	
	public void randomCalculateWithThread(long sampleSize) {
		if (logger.isDebugEnabled())
			logger.debug("ShapleyValue calculate started");

		long nbThreads = 4;
		ExecutorService executor = Executors.newFixedThreadPool((int) nbThreads);
		
        List<Future<List<Double>>> list = new ArrayList<Future<List<Double>>>();
        //Create MyCallable instance
        
        for(int i=0; i< nbThreads; i++){
            //submit Callable tasks to be executed by thread pool
        	Callable<List<Double>> callable = new MyCallable(sampleSize/nbThreads, size, cfunction);
            Future<List<Double>> future = executor.submit(callable);
            //add Future to the list, we can get return value using Future
            list.add(future);
        }
        for(Future<List<Double>> fut : list){
            try {
                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                List<Double> output2 = fut.get();
                //merge
                for(int element=1; element<=cfunction.getNbPlayers(); element++ )
                	output.set(element, output.get(element)+ output2.get(element));
                
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

		/*long count = 1;
		if (sampleSize <= 0) {
			sampleSize = factorialSize;
		}*/

		
		/*while (count <= sampleSize) {
			List<Integer> coalition = null;
		
	
			coalition = RandomPermutations.getRandom(size);
			
			if(logger.isDebugEnabled())
				logger.debug("coalition {}", coalition);
				
			currentRange++;

			count++;

			double prevVal = 0.0;
			cfunction.resetIsFired();
			for (Integer element : coalition) {
				//set.add(element);
				double newVal = cfunction.getValue(element);
				double contribution = newVal - prevVal;
				output.set(element, contribution + output.get(element));
				prevVal = newVal;
			}

		}*/

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
		logger.debug("currentRange {}", currentRange);
		logger.debug("size {} {}", size, FactorialUtil.factorial(size));
		if (currentRange < FactorialUtil.factorial(size)) {
			logger.debug("false");
			return false;
		} else
			return true;
	}
	
	public int getSize() {
		return size;
	}

}
