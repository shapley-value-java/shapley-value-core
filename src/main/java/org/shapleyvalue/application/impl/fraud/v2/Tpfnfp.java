package org.shapleyvalue.application.impl.fraud.v2;

public class Tpfnfp {
	
	private int truePositif;
	private int falseNegatif;
	private int falsePositif;
	
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
		
		return (precision*recall*2) / (precision + recall); 
		
	}

}
