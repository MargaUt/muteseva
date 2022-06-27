package it.akademija.meniu;

import java.util.Set;
import java.util.stream.Collectors;

import it.akademija.meal.Meal;
import it.akademija.meal.MealDTO;

public class MeniuDTO {
	private String id;

	private String meniuName;
	
	private String istaigosid;
	
	private Set<MealDTO> meals;

	public MeniuDTO() {

	}

	public MeniuDTO(String id, String meniuName, String description, String istaigosid) {
		super();
		this.id = id;
		this.meniuName = meniuName;
		this.setIstaigosid(istaigosid);
	}

	public MeniuDTO(Meniu meniu) {
		super();
		this.id = meniu.getId();
		this.meniuName = meniu.getMeniuName();
		this.setIstaigosid(meniu.getMaitinimoIstaiga().getId());
		this.setMeals(meniu.getMeals().stream().map(MealDTO::from).collect(Collectors.toSet()));
		

	}

	/**
	 * Create MealDTO from Meal
	 *
	 * @param Uzsakymas
	 * @return
	 */

	public static MeniuDTO from(Meniu meniu) {
		return new MeniuDTO(meniu);
	}
//
//	public Meniu toMeniu(MaitinimoIstaiga maitinimoIstaiga) {
//		return new Meniu(id, meniuName, maitinimoIstaiga);
//	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMeniuName() {
		return meniuName;
	}

	public void setMeniuName(String meniuName) {
		this.meniuName = meniuName;
	}


	public Set<MealDTO> getMeals() {
		return meals;
	}

	public void setMeals(Set<MealDTO> meals) {
		this.meals = meals;
	}

	public String getIstaigosid() {
		return istaigosid;
	}

	public void setIstaigosid(String istaigosid) {
		this.istaigosid = istaigosid;
	}


}
