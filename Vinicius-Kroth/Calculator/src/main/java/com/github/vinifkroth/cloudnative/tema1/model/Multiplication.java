package com.github.vinifkroth.cloudnative.tema1.model;

public class Multiplication implements Operation {

	@Override
	public double calculate(double firstElement, double secondElement) {
		return firstElement * secondElement;
	}

}
