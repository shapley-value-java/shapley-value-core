package org.shapleyvalue.util.permutation;

import java.util.List;

public class Node {
	
	@Override
	public String toString() {
		return "Node [value=" + value + ", nextNode=" + nextNode + "]";
	}

	private NodeValue value;
	
	private Node nextNode;
	private Node prevNode;
	
	public Node(NodeValue nodeValue, Node prevNode) {
		this.value = nodeValue;
		this.prevNode = prevNode;
		
		if(!value.getNextValues().isEmpty()) {
			List<Integer> values = value.getNextValues();
			NodeValue nextValue = new NodeValue(values);
			nextNode = new Node(nextValue, this);
		}
	}
	
	public NodeValue getValue() {
		return value;
	}

	public Node getNextNode() {
		return nextNode;
	}
	
	public void updateValue() {
		value.updateValue();
	}

	public void setNextNode(Node currentNode) {
		nextNode = currentNode;
		
	}

	public Node getPrevNode() {
		return prevNode;
	}

	public void setPrevNode(Node prevNode) {
		this.prevNode = prevNode;
	}

}
