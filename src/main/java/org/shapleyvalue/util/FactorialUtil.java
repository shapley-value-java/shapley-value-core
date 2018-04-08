package org.shapleyvalue.util;

public class FactorialUtil {

	public static int factorial(int n) {
		return n > 1 ? n * factorial(n - 1) : 1;
	}



}
