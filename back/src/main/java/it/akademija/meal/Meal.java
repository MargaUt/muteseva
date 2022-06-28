package it.akademija.meal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import it.akademija.book.Book;
import it.akademija.uzsakymas.Uzsakymas;

@Entity
public class Meal {

	@Id
	@Column(name = "meal_id")
	// @Pattern(regexp = "^(?!\\s*$)[0-9\\s]{9}$|", message = "Įstaigos kodas turi
	// būti sudarytas iš 9 skaitmenų")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(name = "name", unique = false)
	@NotBlank(message = "Patiekalo pavadinimas privalomas")
	private String name;

	@NotBlank(message = "Aprašymas privalomas")
	@Column(name = "description", unique = false)
	private String description;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH })
	@JoinColumn(name = "meniu_id")
	private Book book;

	public Meal() {

	}

	public Meal(
			// String id,
			@NotBlank(message = "Patiekalo pavadinimas privalomas") String name,
			@NotBlank(message = "Aprašymas privalomas") String description, Book book) {
		super();
		// this.id = id;
		this.name = name;
		this.description = description;
		this.book = book;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Book getMeniu() {
		return book;
	}

	public void setMeniu(Book book) {
		this.book = book;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Meal other = (Meal) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
