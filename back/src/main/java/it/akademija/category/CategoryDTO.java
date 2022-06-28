package it.akademija.category;

import java.util.Set;
import java.util.stream.Collectors;

import it.akademija.book.BookDTO;

public class CategoryDTO {
	
	private String id;
	
	private String name;

	private Set<BookDTO> bookDTO;

	public CategoryDTO() {

	}

	public CategoryDTO(
			//String id,
			String name) {
		super();
		//this.id = id;
	}

	public CategoryDTO(Category category) {
		super();
		this.id  = category.getId();
		this.name = category.getName();
		this.bookDTO = category.getBooks().stream().map(BookDTO::from).collect(Collectors.toSet());

	}

	/**
	 * Create KindergartenDTO from Kindergarten
	 *
	 * @param Meal
	 * @return
	 */

	public static CategoryDTO from(Category category) {
		return new CategoryDTO(category);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<BookDTO> getBookDTO() {
		return bookDTO;
	}

	public void setBookDTO(Set<BookDTO> bookDTO) {
		this.bookDTO = bookDTO;
	}

}
