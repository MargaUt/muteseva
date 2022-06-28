package it.akademija.book;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.category.Category;
import it.akademija.category.CategoryDAO;
import it.akademija.meal.Meal;

@Service
public class BookService {

	private static final Logger LOG = LoggerFactory.getLogger(BookService.class);

	@Autowired
	private BookDAO bookDAO;
	@Autowired
	private CategoryDAO categorysDao;

//	/**
//	 *
//	 * Returns a page of Meniu with specified page number and page size
//	 *
//	 * @param pageable
//	 * @return page from Meniu database
//	 */
//	@Transactional(readOnly = true)
//	public Page<MeniuDTO> getMeniuPage(Pageable pageable, String search) {
//		if (search == null) {
//			return meniuDAO.findAllIstaigas(pageable).map(MeniuDTO::from);
//		}
//		return maitinimoIstaigaDAO.findByNameContainingIgnoreCase(search, pageable).map(MeniuDTO::from);
//	}

	/**
	 *
	 * Returns a List of Book
	 *
	 *
	 * @return List from Book database
	 */
	@Transactional(readOnly = true)
	public List<BookDTO> getBooks() {
		return bookDAO.findAll().stream().map(BookDTO::from).collect(Collectors.toList());
	}

	/**
	 *
	 * Returns Book by category id
	 *
	 *
	 * @return List from Book database
	 */
	@Transactional(readOnly = true)
	public List<BookDTO> getgetBookOfVCategory(String categoryId) {
		var category = categorysDao.findById(categoryId).get();
		return bookDAO.findAllByBookCategory(category).stream().map(BookDTO::from).collect(Collectors.toList());
	}

	/**
	 * Save new Book to database
	 *
	 * @param book
	 */
	@Transactional
	public void createBook(BookDTO book) {

		var category = categorysDao.findById(book.getCategoryid()).get();

		bookDAO.save(new Book(
				// meniu.getId(),
				book.getBookName(), book.getSantrauka(), book.getIsbn(), book.getPicture(), book.getBooksPages(),
				category));

	}

	/**
	 * Find book by id. Read only
	 *
	 * @param id
	 * @return category or null if not found
	 */
	@Transactional(readOnly = true)
	public Book findById(String id) {

		return bookDAO.findById(id).orElse(null);
	}

	/**
	 *
	 * Delete maitinimoIstaiga with specified id. Also deletes all related
	 * maitinimoIstaiga
	 * 
	 *
	 * @param id
	 */
	@Transactional
	public ResponseEntity<String> deleteBook(String id) {

		Book book = bookDAO.findById(id).orElse(null);

		if (book != null) {

			bookDAO.deleteById(id);

			LOG.info("**BookService: trinama knyga ID [{}] **", id);

			return new ResponseEntity<>("Knyga ištrinta sėkmingai", HttpStatus.OK);

		} else {

			return new ResponseEntity<>("Maitinimo įstaiga įstaigos kodu nerastas", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Update book
	 *
	 * @param currentInfo
	 * @param book
	 */
	@Transactional
	public void updateBook(String id, BookDTO updatedInfo) {

		Book current = bookDAO.findById(id).orElse(null);

		current.setBookName(updatedInfo.getBookName());
		current.setSantrauka(updatedInfo.getSantrauka());
		current.setIsbn(updatedInfo.getIsbn());
		current.setPicture(updatedInfo.getPicture());
		current.setBooksPages(updatedInfo.getBooksPages());

		bookDAO.save(current);
	}

	/**
	 * Delete Meniu by name. Used during DB setup
	 *
	 * @param name
	 */
	@Transactional
	public void deleteByName(String bookName) {

		bookDAO.deleteByBookName(bookName);
	}

	public BookDAO getMeniuDAO() {
		return bookDAO;
	}

	public void setMeniuDAO(BookDAO bookDAO) {
		this.bookDAO = bookDAO;
	}

	public CategoryDAO getCategorysDao() {
		return categorysDao;
	}

	public void setCategorysDao(CategoryDAO categorysDao) {
		this.categorysDao = categorysDao;
	}

}
