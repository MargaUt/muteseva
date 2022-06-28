package it.akademija.book;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;

import it.akademija.category.Category;

@Entity
public class Book {

	@Id
	@Column(name = "book_id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(name = "bookName", unique = false)
	@NotBlank(message = "Knygos pavadinimas privalomas")
	private String bookName;

	@Column(name = "santrauka", unique = false)
	@NotBlank(message = "Knygos santrauka privalomas")
	private String santrauka;

	@Column(name = "isbn", unique = false)
	@Pattern(regexp = "^(?!\\s*$)[0-9\\s]{13}$|", message = "Knygos identifikatorius turi būti sudarytas iš 13 skaitmenų")
	@NotBlank(message = "Knygos ISBN privalomas")
	private String isbn;

	@Lob
	private String picture;

	@Column(name = "booksPages", unique = false)
	private int booksPages;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id")
	private Category bookCategory;

	public Book() {

	}

	public String getId() {
		return id;
	}

	public Book(@NotBlank(message = "Knygos pavadinimas privalomas") String bookName,
			@NotBlank(message = "Knygos santrauka privalomas") String santrauka,
			@Pattern(regexp = "^(?!\\s*$)[0-13\\s]{13}$|", message = "Knygos identifikatorius turi būti sudarytas iš 13 skaitmenų") @NotBlank(message = "Knygos ISBN privalomas") String isbn,
			String picture, int booksPages, Category bookCategory) {
		super();
		this.bookName = bookName;
		this.santrauka = santrauka;
		this.isbn = isbn;
		this.picture = picture;
		this.booksPages = booksPages;
		this.bookCategory = bookCategory;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getSantrauka() {
		return santrauka;
	}

	public void setSantrauka(String santrauka) {
		this.santrauka = santrauka;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public int getBooksPages() {
		return booksPages;
	}

	public void setBooksPages(int booksPages) {
		this.booksPages = booksPages;
	}

	public Category getBookCategory() {
		return bookCategory;
	}

	public void setBookCategory(Category bookCategory) {
		this.bookCategory = bookCategory;
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
		Book other = (Book) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
