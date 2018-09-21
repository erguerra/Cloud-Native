package com.github.vinifkroth.cloudnative.tema1.model;

import org.springframework.lang.NonNull;

public class Subtraction implements Operation {
	@NonNull
	double firstElement;
	@NonNull
	double secondElement;

	public Subtraction(double firstElement, double secondElement) {
		this.firstElement = firstElement;
		this.secondElement = secondElement;
	}

	@Override
	public double calculate() {
		return firstElement - secondElement;
	}

}
