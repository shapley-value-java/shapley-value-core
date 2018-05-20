package org.shapleyvalue.application.impl.parliament.multithread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import org.shapleyvalue.core.CharacteristicFunction;
import org.shapleyvalue.core.CharacteristicFunction.CharacteristicFunctionBuilder;
import org.shapleyvalue.util.Powerset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application of the Shapley value for a parliament
 * with parallelism
 * Executor Service
 * 
 * 
 * @author Franck Benault
 * 
 * @version	0.0.2
 * @since 0.0.2
 *
 */
public class ParliamentCalculationService  {
	
	private final Logger logger = LoggerFactory.getLogger(ParliamentCalculationService.class);

	
	private CharacteristicFunction cfunction;
	private Map<Integer, String> range;

	
	private ParliamentCalculationService(ParliamentCalculationBuilder builder) {

		Set<Set<Integer>> sets = Powerset.calculate(builder.getNbPlayers());

		CharacteristicFunctionBuilder cfunctionBuilder = 
				new CharacteristicFunction.CharacteristicFunctionBuilder(builder.getNbPlayers());
		
		for(Set<Integer> set : sets) {
			int nbRepresentants =0;
			for(Integer i : set) {
				int val = builder.getV().get(i);
				nbRepresentants += val;
			}
			if(nbRepresentants>=(1+(builder.getNbRepresentants()/2)))
				cfunctionBuilder.addCoalition(1, set.toArray(new Integer[set.size()]));
			else
				cfunctionBuilder.addCoalition(0, set.toArray(new Integer[set.size()]));

		}
		range = builder.getRange();
		cfunction = cfunctionBuilder.build();
	}
	
	/**
	 * 
	 * Builder for ParliamentCalculation class
	 * 
	 * @author Franck Benault
	 *
	 */
	public static class ParliamentCalculationBuilder {
		
		private int nbPlayers;
		private Map<Integer, Integer> v;
		private Map<Integer, String> range;
		private int nbRepresentants;

		public ParliamentCalculationBuilder() {
			nbPlayers = 0;
			nbRepresentants =0;
			v = new HashMap<>();
			range = new HashMap<>();
		}

		public ParliamentCalculationBuilder addParty(String partyName , int nbRepresentants) {
			nbPlayers ++;
			this.nbRepresentants += nbRepresentants;
			v.put(nbPlayers, nbRepresentants);
			range.put(nbPlayers, partyName);
			return this;
		}
		
		public ParliamentCalculationService build() {
			return new ParliamentCalculationService(this);
		}
		
		public int getNbPlayers() {
			return nbPlayers;
		}
		
		public  Map<Integer, Integer> getV() {
			return v;
		}
		
		public  Map<Integer, String> getRange() {
			return range;
		}

		public int getNbRepresentants() {
			return nbRepresentants;
		}

		
	}
	

	
	private Map<String, Double> getResult(List<Double> tempRes, int nbThreads) {


	
		
		Map<String, Double> res = new HashMap<>();
		double total =0;
		for(int i=1; i<tempRes.size(); i++) {
			total += tempRes.get(i);
		}
		for(int i=1; i<tempRes.size(); i++) {
			res.put(range.get(i), tempRes.get(i)/total);
		}
		return res;
		
		
		
	}
	



	public Map<String, Double> calculate(long nbCoalitions, int nbThreads) {
		
		
		
		ExecutorService executor = Executors.newFixedThreadPool(nbThreads);

		List<Future<List<Double>>> res = new ArrayList<>();
		
		for(int i=1; i<=nbThreads; i++) {
			ParliamentCalculationTask task = new ParliamentCalculationTask(cfunction, nbCoalitions/nbThreads, i);
			Future<List<Double>> restask = executor.submit(task);
			res.add(restask);
		}
		
		
		
		
		List<Double> listResult = null;
		try {
			for(Future<List<Double>> rest : res) {
				listResult=mergeList(listResult, rest.get());
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		executor.shutdown();
		logger.debug("listResult {}",listResult);

		return getResult(listResult,nbThreads);
	}



	private List<Double> mergeList(List<Double> listResult, List<Double> list) {
		if (listResult==null) return list;
		
		for(int i=0; i<listResult.size(); i++) {
			Double val1 = listResult.get(i);
			Double val2 = list.get(i);
			listResult.set(i, val1+val2);
		}
		
		return listResult;
	}

}
