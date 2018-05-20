package org.shapleyvalue.application.impl.parliament.multithread;

import java.util.List;
import java.util.concurrent.Callable;


import org.shapleyvalue.core.CharacteristicFunction;
import org.shapleyvalue.core.ShapleyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Application of the Shapley value for a parliament
 * with parallelism
 * Task
 * 
 * 
 * @author Franck Benault
 * 
 * @version	0.0.2
 * @since 0.0.2
 *
 */
public class ParliamentCalculationTask implements Callable<List<Double>> {
	
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
	public List<Double> call() throws Exception {
		logger.info("thread {} started", threadNumber);
		
		shapleyValue.calculate(nbCoalitions, true);
		logger.info("thread {} end", threadNumber);
		
		return shapleyValue.getResult();
	}
	

}
