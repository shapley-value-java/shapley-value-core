package org.shapleyvalue.util.permutation;

import java.util.ArrayList;
import java.util.List;

public class NodeValue {
	
	@Override
	public String toString() {
		return "NodeValue [value=" + value + ", nextValues=" + nextValues + "]";
	}

	private Integer value;
	
	private List<Integer> nextValues;
	
	public NodeValue(List<Integer> nextValues) {
		
		this.nextValues = new ArrayList<>();
		this.nextValues.addAll(nextValues);
		this.value = nextValues.get(0);
		this.nextValues.remove(value);
	}

	public Integer getValue() {
		return value;
	}
	
	public List<Integer> getNextValues() {
		return new ArrayList<>(nextValues);
	}

	public void updateValue() {
		if(!nextValues.isEmpty())
			value = nextValues.get(0);
		nextValues.remove(value);
		
	}



}
