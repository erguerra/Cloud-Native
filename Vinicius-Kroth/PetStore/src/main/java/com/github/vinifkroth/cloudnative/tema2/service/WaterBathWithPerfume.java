package com.github.vinifkroth.cloudnative.tema2.service;

import com.github.vinifkroth.cloudnative.tema2.model.Pet;
import com.google.inject.Singleton;

@Singleton
public class WaterBathWithPerfume implements Service {
	private static final int PRICE = 100;

	@Override
	public String doService(Pet pet) {
		return "The pet :" + pet.getName() + " took a water bath and sprayed some perfume, at the cost of: " + PRICE;
	}
	@Override
	public double getPrice() {
		return PRICE;
	}

}