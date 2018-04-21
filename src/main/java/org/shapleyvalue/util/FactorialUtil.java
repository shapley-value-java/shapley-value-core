package org.shapleyvalue.util;

public class FactorialUtil {

	public static long factorial(long n) {
		return n > 1 ? n * factorial(n - 1) : 1;
	}



}
