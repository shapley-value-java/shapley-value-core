package org.shapleyvalue.application.impl.fraud.v2;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tpfnfp {
	
	private final Logger logger = LoggerFactory.getLogger(Tpfnfp.class);

	
	private int truePositif;
	private int falseNegatif;
	private int falsePositif;
	
	public Tpfnfp(List<RuledTransaction> tx, List<Integer> rules) {

		for(RuledTransaction t : tx) {
			boolean isFired = false;
			for(Integer rule : rules) {
				if(t.getRules().contains(rule)) isFired =true;
			}
			if(t.isFraud()) {
				if(isFired) {
					truePositif++;
				} else {
					falseNegatif++;
				}
			} else {
				if(isFired) {
					falsePositif++;
				}
			}
		}
		
		logger.info("{} {} {} ", truePositif, falseNegatif, falsePositif);
	}
	
	public Tpfnfp(int tp, int fn, int fp) {
		truePositif = tp;
		falseNegatif = fn;
		falsePositif = fp;
	}
	
	public double precision() {
		
		return ((double)truePositif)/(truePositif+falsePositif);
	}
	
	public double recall() {
		return ((double) truePositif)/(truePositif+falseNegatif);
	}
	
	public double score() {
		
		double precision = precision();
		double recall = recall();
		
		if((precision + recall) ==0) return 0;
		
		return (precision*recall*2) / (precision + recall); 
		
	}

}
