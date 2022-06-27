package it.akademija.uzsakymas;

import java.util.Set;
import java.util.stream.Collectors;

import it.akademija.meal.Meal;
import it.akademija.meal.MealDTO;

public class UzsakymasDTO {

	private String username;

	private Set<MealDTO> meals;

	public UzsakymasDTO() {

	}

	public UzsakymasDTO(String username, Set<MealDTO> meals) {
		super();
		this.username = username;
		this.meals = meals;
	}
	
	public UzsakymasDTO(String username) {
		super();
		this.username = username;
	}

	public UzsakymasDTO(Uzsakymas uzsakymas) {
		super();
		if (uzsakymas.getMeals() != null) {
			this.meals = uzsakymas.getMeals().stream().map(MealDTO::from).collect(Collectors.toSet());
		}
		this.username = uzsakymas.getUsername();

	}

	/**
	 * Create MealDTO from Meal
	 *
	 * @param Uzsakymas
	 * @return
	 */

	public static UzsakymasDTO from(Uzsakymas meniu) {
		return new UzsakymasDTO(meniu);
	}
//
	
	/**
	 * Convert to Cart
	 * 
	 * @return
	 */
	public Uzsakymas toUzsakymas(String username) {
		return new Uzsakymas(username);
	}
	
	
//	public Meniu toMeniu(MaitinimoIstaiga maitinimoIstaiga) {
//		return new Meniu(id, meniuName, maitinimoIstaiga);
//	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<MealDTO> getMeals() {
		return meals;
	}

	public void setMeals(Set<MealDTO> meals) {
		this.meals = meals;
	}

}
