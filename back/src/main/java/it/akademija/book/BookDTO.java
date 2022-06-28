package it.akademija.book;

import java.util.Set;
import java.util.stream.Collectors;

import it.akademija.meal.Meal;
import it.akademija.meal.MealDTO;

public class BookDTO {
	private String id;

	private String bookName;

	private String santrauka;

	private String categoryid;

	private String isbn;

	private String picture;

	private int booksPages;

	public BookDTO() {

	}

	public BookDTO(String id, String bookName, String santrauka, String categoryid, String isbn, String picture,
			int booksPages) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.santrauka = santrauka;
		this.categoryid = categoryid;
		this.isbn = isbn;
		this.picture = picture;
		this.booksPages = booksPages;
	}

	public BookDTO(Book book) {
		super();
		this.id = book.getId();
		this.bookName = book.getBookName();
		this.santrauka = book.getSantrauka();
		this.setCategoryid(book.getBookCategory().getId());
		this.isbn = book.getIsbn();
		this.picture = book.getPicture();
		this.booksPages = book.getBooksPages();
		

	}

	/**
	 * Create MealDTO from Meal
	 *
	 * @param Uzsakymas
	 * @return
	 */

	public static BookDTO from(Book book) {
		return new BookDTO(book);
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

	public String getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
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

}
