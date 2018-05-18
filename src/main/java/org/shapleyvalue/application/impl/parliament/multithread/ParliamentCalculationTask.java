package org.shapleyvalue.application.impl.parliament.multithread;

import java.util.Map;
import java.util.concurrent.Callable;


import org.shapleyvalue.core.CharacteristicFunction;
import org.shapleyvalue.core.ShapleyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class ParliamentCalculationTask implements Callable<Map<Integer, Double>> {
	
	private final Logger logger = LoggerFactory.getLogger(ParliamentCalculationTask.class);

	
	private ShapleyValue shapleyValue;
	private long nbCoalitions;
	private int threadNumber;

	
	public ParliamentCalculationTask(CharacteristicFunction cfunction, long nbCoalitions, int threadNumber) {
		this.nbCoalitions = nbCoalitions;
		this.threadNumber = threadNumber;
		shapleyValue = new ShapleyValue(cfunction);
	}
	


	@Override
	public Map<Integer, Double> call() throws Exception {
		logger.info("thread {} started", threadNumber);
		
		shapleyValue.calculate(nbCoalitions, true);
		logger.info("thread {} end", threadNumber);
		
		return shapleyValue.getResult();
	}
	

}