package org.shapleyvalue.util.permutation;

import java.util.ArrayList;
import java.util.List;

import org.shapleyvalue.util.FactorialUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PermutationLinkList {
	
	private final Logger logger = LoggerFactory.getLogger(PermutationLinkList.class);
	
	@Override
	public String toString() {
		String res = "PermutationLinkList [root=" + root + "]";
		return res;
		
	}

	private Node root;
	private long permutationRange;
	private long permutationSize;
	
	private Node getLastNode() {
		Node tempNode = root;
		while(tempNode.getNextNode()!=null) {
			//System.out.println("tempNode "+tempNode);
			tempNode = tempNode.getNextNode();
		}
		return tempNode;
	}
	
	public PermutationLinkList(int size) {
		permutationRange = 0;
		permutationSize = size;
		
		List<Integer> ints = new ArrayList<>();
		for(int i=1; i<=size; i++) {
			ints.add(i);
		}
		NodeValue nodeValue = new NodeValue(ints);
		//System.out.println(nodeValue);
		root = new Node(nodeValue, null);
	}
	
	public List<Integer> getNextPermutation() {
		if(root==null) {
			return new ArrayList<Integer>();
		}
		
		List<Integer> list = new ArrayList<>();
		
		List<Integer> ints = new ArrayList<>();
		for(int i=1; i<=permutationSize; i++) {
			ints.add(i);
		}
		
		Node node = root;
		while(node != null) {
			//System.out.println(node.getValue().getValue());
			list.add(node.getValue().getValue());
			node = node.getNextNode();
		}
		
		


		
		
		Node currentNode = getLastNode();
		//System.out.println("lastNode ="+currentNode);
		Node previousNode = currentNode.getPrevNode();
		
		
		while(previousNode!=null && currentNode.getValue().getNextValues().isEmpty()) {
			/*if(previousNode!=null) {
				//System.out.println("ffo");
				ints.remove(previousNode.getValue().getValue());
			}	*/	
			currentNode = previousNode;
			previousNode = currentNode.getPrevNode();
		}
		//System.out.println("currentNode ="+currentNode);
		

		
		if(!currentNode.getValue().getNextValues().isEmpty())    {
			currentNode.updateValue();
			ints.remove(currentNode.getValue().getValue());
			Node tempNode = currentNode.getPrevNode();
			while(tempNode!=null) {
				ints.remove(tempNode.getValue().getValue());
				tempNode = tempNode.getPrevNode();
			}
			//System.out.println("ints"+ints);

			NodeValue nodeValue = new NodeValue(ints);
			Node newNode = new Node(nodeValue, currentNode);
			currentNode.setNextNode(newNode);
		} else {
			root = null;
		}
		
		
		
		permutationRange++;
		
		if(permutationRange% 1000000 ==0) {
			//System.out.println(permutationRange);
			logger.debug("percentage calculation {}", 100*permutationRange/FactorialUtil.factorial(permutationSize));
		}
		return list;
	}

	public long getPermutationRange() {
		return permutationRange;
	}
	

}
