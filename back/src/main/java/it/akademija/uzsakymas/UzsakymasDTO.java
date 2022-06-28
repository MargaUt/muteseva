package it.akademija.uzsakymas;

import java.util.Set;
import java.util.stream.Collectors;

import it.akademija.book.BookDTO;
import it.akademija.meal.Meal;
import it.akademija.meal.MealDTO;

public class UzsakymasDTO {

	private String username;

	private Set<BookDTO> books;

	public UzsakymasDTO() {

	}

	public UzsakymasDTO(String username, Set<BookDTO> books) {
		super();
		this.username = username;
		this.books = books;
	}
	
	public UzsakymasDTO(String username) {
		super();
		this.username = username;
	}

	public UzsakymasDTO(Uzsakymas uzsakymas) {
		super();
		if (uzsakymas.getBooks() != null) {
			this.books = uzsakymas.getBooks().stream().map(BookDTO::from).collect(Collectors.toSet());
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

	public Set<BookDTO> getBooks() {
		return books;
	}

	public void setBooks(Set<BookDTO> books) {
		this.books = books;
	}



}
