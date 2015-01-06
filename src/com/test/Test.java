package com.test;


public class Test {
	public static void main(String[] args) {
		Double edmRatio = 0.000402196262738124;
		Double bbgRatio = 0.000406715321870002;
		
		Double ratio = ((edmRatio - bbgRatio) / bbgRatio);
		
		System.out.println(ratio);
	}
}
