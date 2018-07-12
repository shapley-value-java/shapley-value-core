package org.shapleyvalue.util;

public class FactorialUtil {

	public static long factorial(long n) {
		if(n>30) return Long.MAX_VALUE;
		return n > 1 ? n * factorial(n - 1) : 1;
	}



}
