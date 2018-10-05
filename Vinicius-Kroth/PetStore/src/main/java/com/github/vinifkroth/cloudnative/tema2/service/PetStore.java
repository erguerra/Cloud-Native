package com.github.vinifkroth.cloudnative.tema2.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.github.vinifkroth.cloudnative.tema2.exception.IdNotFoundException;
import com.github.vinifkroth.cloudnative.tema2.model.Pet;
import com.github.vinifkroth.cloudnative.tema2.util.IdGenerator;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class PetStore {
	@Inject
	private DryBathWithPerfume dryBathWithPerfume;
	@Inject
	private DryBathWithoutPerfume dryBathWithoutPerfume;
	@Inject
	private WaterBathWithoutPerfume waterBathWithoutPerfume;
	@Inject
	private WaterBathWithPerfume waterBathWithPerfume;
	@Inject
	private LongTrim longTrim;
	@Inject
	private ShortTrim shortTrim;
	@Inject
	private AddPet addPet;
	@Inject
	private RemovePet removePet;
	@Inject
	private IdGenerator idGenerator;
	private List<Pet> petList;
	private List<String> servicesHistory;

	public PetStore() {
		petList = new ArrayList<>();
		servicesHistory = new ArrayList<>();
	}

	public Object[] getPetByAge(int age) {
		return petList.stream().filter(pet -> pet.getAge() == age).toArray();
	}

	public boolean addPet(String name, String race, int age) {
		Pet pet = new Pet(idGenerator.generateId(),name, race, age);
		return addPet.add(petList, pet);
	}

	public boolean removePetById(String id) {
		return removePet.remove(petList, id);
	}

	public String trimHair(boolean fullTrim, Integer id) throws IdNotFoundException {
		Pet pet = retrievePetById(id);
		if (pet == null)
			throw new IdNotFoundException("Id was not found in our database");

		String serviceMessage;
		if (fullTrim) {
			serviceMessage = longTrim.doService(pet);
			pet.setTotalRevenue(pet.getTotalRevenue() + longTrim.getPrice());
		} else {
			serviceMessage = shortTrim.doService(pet);
			pet.setTotalRevenue(pet.getTotalRevenue() + shortTrim.getPrice());
		}

		servicesHistory.add(serviceMessage);
		return serviceMessage;

	}

	public String washPet(boolean dry, boolean perfume, Integer petId) throws IdNotFoundException {
		Pet pet = retrievePetById(petId);
		if (pet == null)
			throw new IdNotFoundException("Id was not found in our database");
		String serviceMessage;

		if (dry) {
			if (perfume) {
				serviceMessage = dryBathWithPerfume.doService(pet);
				pet.setTotalRevenue(pet.getTotalRevenue() + dryBathWithPerfume.getPrice());
			} else {
				serviceMessage = dryBathWithoutPerfume.doService(pet);
				pet.setTotalRevenue(pet.getTotalRevenue() + dryBathWithoutPerfume.getPrice());
			}
		} else {
			if (perfume) {
				serviceMessage = waterBathWithPerfume.doService(pet);
				pet.setTotalRevenue(pet.getTotalRevenue() + waterBathWithPerfume.getPrice());
			} else {
				serviceMessage = waterBathWithoutPerfume.doService(pet);
				pet.setTotalRevenue(pet.getTotalRevenue() + waterBathWithoutPerfume.getPrice());
			}

		}
		servicesHistory.add(serviceMessage);
		return serviceMessage;
	}

	public List<String> getHistory() {
		return servicesHistory;
	}

	private Pet retrievePetById(Integer id) {
		return petList.stream().filter(pet -> pet.getId().equals(id)).findFirst().orElse(null);
	}

	public List<Pet> getTop10PetRevenue() {
		Collections.sort(petList, (pet1, pet2) -> pet1.getTotalRevenue().compareTo(pet2.getTotalRevenue()));
		Collections.reverse(petList);

		if (petList.size() < 10)
			return petList;

		return petList.subList(0, 9);
	}
}
