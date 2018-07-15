package org.shapleyvalue.application.impl.fraud.v2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.shapleyvalue.util.permutation.RandomPermutations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCallable implements Callable<List<Double>> {
	
	private long sampleSize;
	private long size;
	private CharacteristicFunctionV2 cfunction;
	private List<Double> output;
	
	private final Logger logger = LoggerFactory.getLogger(MyCallable.class);

	
	public MyCallable(long sampleSize, long size, CharacteristicFunctionV2 cfunction) {
		this.sampleSize =sampleSize;
		this.size = size;
		this.cfunction = new CharacteristicFunctionV2(cfunction);
		this.output = new ArrayList<Double>();
		for (int i = 0; i <= size; i++) {
			output.add(0.0);
		}
	}

	@Override
	public List<Double> call() throws Exception {
		long count = 1;


		
		while (count <= sampleSize) {
			List<Integer> coalition = null;
		
	
			coalition = RandomPermutations.getRandom(size);
			
			if(logger.isDebugEnabled())
				logger.debug("coalition {}", coalition);
				
			

			count++;

			double prevVal = 0.0;
			cfunction.resetIsFired();
			for (Integer element : coalition) {
				double newVal = cfunction.getValue(element);
				double contribution = newVal - prevVal;
				output.set(element, contribution + output.get(element));
				prevVal = newVal;
			}

		}
		return output;
	}

}
