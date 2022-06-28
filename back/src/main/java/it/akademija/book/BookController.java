package it.akademija.book;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.akademija.category.CategoryService;

@RestController
@Api(value = "book")
@RequestMapping(path = "/api/book")
public class BookController {

	private static final Logger LOG = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private BookService bookService;

	@Autowired
	private CategoryService categoryService;

//	/**
//	 * Get specified MaitinimoIstaiga information page
//	 *
//	 * @return page of maitinimoIstaiga information
//	 */
//	@GetMapping("/user/page")
//	@ApiOperation(value = "Get maitinimo istaigu information pages")
//	public ResponseEntity<Page<MeniuDTO>> getMaitinimoIstaigaPage(
//
//			@RequestParam("page") int page, @RequestParam("size") int size,
//			@RequestParam(value = "search", required = false) String search) {
//
//		Sort.Order order = new Sort.Order(Sort.Direction.ASC, "pavadinimas").ignoreCase();
//		String decodedName = "";
//
//		Pageable pageable = PageRequest.of(page, size, Sort.by(order));
//		if (search != null) {
//			try {
//				search = search.replaceAll("%", "%25");
//				decodedName = URLDecoder.decode(search, "UTF-8");
//				decodedName = decodedName.replaceAll("%", "%75[%]%");
//				decodedName = decodedName.replaceAll("_", "[_]");
//
//				return new ResponseEntity<>(maitinimoIstaiga.getIstaigosPage(pageable, decodedName),
//						HttpStatus.OK);
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}
//		return new ResponseEntity<>(maitinimoIstaiga.getIstaigosPage(pageable, null), HttpStatus.OK);
//
//	}

	@GetMapping("/getBook/{categoryId}")
	public List<BookDTO> getBook(@ApiParam(value = "Category id", required = true) @PathVariable String categoryId) {
		return bookService.getgetBookOfVCategory(categoryId);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<BookDTO> getBooks() {
		return bookService.getBooks();
	}

	/**
	 * Create new Book entity
	 *
	 * @param Book entity
	 * @return message
	 */
	@PostMapping("/createBook")
	@ApiOperation(value = "Create new book")
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void createBook(@RequestBody @Valid BookDTO book) {
		bookService.createBook(book);

	}

//	@PostMapping("/admin/createIstaiga")
//	@ApiOperation(value = "Create new maitinimo istaiga")
//	public ResponseEntity<String> createNewIstaiga(
//			@ApiParam(value = "Istaiga", required = true) @Valid @RequestBody MeniuDTO istaiga) {
//
//		String id = istaiga.getId();
//
//		if (maitinimoIstaiga.findById(id) != null) {
//
//			LOG.warn("Kuriama maitinimo istaiga jau egzistuojančiu įstaigos kodu [{}]", id);
//
//			return new ResponseEntity<>("Maitinimo įstaiga su tokiu įstaigos kodu jau yra", HttpStatus.CONFLICT);
//
//		} else {
//
//			maitinimoIstaiga.createNewIstaiga(istaiga);
//
//			LOG.info("**MaitinimoIstaigaController: kuriamas darzelis pavadinimu [{}] **", istaiga.getPavadinimas());
//
//			return new ResponseEntity<>("Maitinimo įstaiga sukurta sėkmingai", HttpStatus.OK);
//		}
//
//	}

	/**
	 *
	 * Delete Book entity with specified id
	 *
	 * @param id
	 * @return message if entity was deleted or if it does not exist in the database
	 */
	@DeleteMapping("/deleteBook/{id}")
	@ApiOperation(value = "Delete book by ID")
	public ResponseEntity<String> deleteBook(@ApiParam(value = "Book id", required = true) @PathVariable String id) {

		return bookService.deleteBook(id);
	}

	// @Secured({ "ROLE_ADMIN" })
	@PutMapping("/updateBook/{id}")
	@ApiOperation(value = "Update meniu by ID")
	public ResponseEntity<String> updateBook(
			@ApiParam(value = "Book", required = true) @Valid @RequestBody BookDTO updated, @PathVariable String id) {

		if (bookService.findById(id) == null) {

			LOG.warn("Knyga tokiu kodu [{}] nėra", id);

			return new ResponseEntity<>("Knyga su tokiu kodu nerasta", HttpStatus.NOT_FOUND);

		} else {

			bookService.updateBook(id, updated);

			LOG.info("** BookController: atnaujinama knyga ID [{}] **", id);

			return new ResponseEntity<>("Knygos duomenys atnaujinti sėkmingai", HttpStatus.OK);
		}

	}

	public BookService getMeniuService() {
		return bookService;
	}

	public void setMeniuService(BookService bookService) {
		this.bookService = bookService;
	}


	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

}
