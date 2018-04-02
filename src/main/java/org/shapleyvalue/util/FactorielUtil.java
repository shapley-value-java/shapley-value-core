package org.shapleyvalue.util;

public class FactorielUtil {

	public static int factoriel(int n) {
		return n > 1 ? n * factoriel(n - 1) : 1;
	}



}
