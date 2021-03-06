package it.akademija.uzsakymas;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;

import it.akademija.book.Book;
import it.akademija.meal.Meal;

@Entity
public class Uzsakymas {

	@Id
	@Column(name = "uzsakymo_id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(unique = true)
	private String username;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.DETACH })
	private Set<Book> books = new HashSet<>();

	public void addBook(Book book) {
		this.books.add(book);
	}

	public Uzsakymas() {

	}

	public Uzsakymas(String username, Set<Book> books) {
		super();
		this.username = username;
		this.books = books;
	}

	public Uzsakymas(String username) {
		super();
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
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
		Uzsakymas other = (Uzsakymas) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
