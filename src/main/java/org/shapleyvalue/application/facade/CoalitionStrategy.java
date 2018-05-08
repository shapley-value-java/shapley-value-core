package org.shapleyvalue.application.facade;

/**
 * 
 * @author Franck Benault
 * 
 * @version	0.0.2
 * @since 0.0.2
 *
 */
public enum CoalitionStrategy {

	SEQUENTIAL,
	
	RANDOM;
	
    public boolean isSequential(){
        return this.equals(SEQUENTIAL);
    }
}
