package org.shapleyvalue.application.facade;

import java.util.Map;

/**
 * facade of Shapley application
 * 
 * @author Franck Benault
 * 
 * @version	0.0.2
 * @since 0.0.2
 *
 */
public interface ShapleyApplication {
	
	/**
	 * full calculation of the Shapley value
	 * 
	 * @return partial result of the Shapley value
	 */
	Map<String,Double> calculate();
	
	/**
	 * complete the calculation of the Shapley value 
	 * with the next set of coalitions choosen sequentially 
	 * 
	 * @param nbCoalitions number of coalition taken in account for this calculation
     *	
	 * @return partial result of the Shapley value
	 * 
	 * @throws ShapleyApplicationException when different strategy are mixed
	 * 
	 */
	Map<String,Double> calculate(long nbCoalitions) throws ShapleyApplicationException;
	
	/**
	 * complete the calculation of the Shapley value 
	 * with the next set of coalitions which may be choosen randomly
	 * 
	 * @param nbCoalitions number of coalition taken in account for this calculation
	 * @param strategy way to choose the next coalitions (sequential or random)  
	 *
	 * @return partial result of the Shapley value
	 * 
	 * @throws ShapleyApplicationException when different strategy are mixed
	 * 
	 */
	Map<String,Double> calculate(long nbCoalitions, CoalitionStrategy strategy) throws ShapleyApplicationException;
	
	/**
	 * check in the case of sequential strategy if the last coalition possible is reached
	 * 
	 * @return
	 * @throws ShapleyApplicationException in the cas of non sequential strategy
	 */
	boolean isLastCoalitionReached() throws ShapleyApplicationException;

}
