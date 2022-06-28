package it.akademija.category;

import java.net.URLDecoder;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.akademija.meal.MealDTO;

@RestController
@Api(value = "category")
@RequestMapping(path = "/api/category")
public class CategoryController {

	private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	private CategoryService categoryService;



	/**
	 * Get specified Category information page
	 *
	 * @return page of category information
	 */
	@GetMapping("/getCategorypage")
	@ApiOperation(value = "Get category information pages")
	public ResponseEntity<Page<CategoryDTO>> getCategorypage(

			@RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam(value = "search", required = false) String search) {

		Sort.Order order = new Sort.Order(Sort.Direction.ASC, "name").ignoreCase();
		String decodedName = "";

		Pageable pageable = PageRequest.of(page, size, Sort.by(order));
		if (search != null) {
			try {
				search = search.replaceAll("%", "%25");
				decodedName = URLDecoder.decode(search, "UTF-8");
				decodedName = decodedName.replaceAll("%", "%75[%]%");
				decodedName = decodedName.replaceAll("_", "[_]");

				return new ResponseEntity<>(categoryService.getCategorypage(pageable, decodedName),
						HttpStatus.OK);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<>(categoryService.getCategorypage(pageable, null), HttpStatus.OK);

	}
	
	/**
	 * Create new category entity
	 *
	 * @param category entity
	 * @return message
	 */
	@PostMapping("/createCategory")
	@ApiOperation(value = "Create new book category.")
	public ResponseEntity<String> createCategory(
			@ApiParam(value = "Category", required = true) @Valid @RequestBody CategoryDTO category) {

//		String id = istaiga.getId();
//
//		if (maitinimoIstaiga.findById(id) != null) {
//
//			LOG.warn("Kuriama maitinimo istaiga jau egzistuojančiu įstaigos kodu [{}]", id);
//
//			return new ResponseEntity<>("Maitinimo įstaiga su tokiu įstaigos kodu jau yra", HttpStatus.CONFLICT);
//
//		} else {

		categoryService.createCategory(category);

			LOG.info("**CategoryController: kuriama knygų kategorija tokiu pavadinimu [{}] **", category.getName());

			return new ResponseEntity<>("Knygų kategorija sukurta sėkmingai", HttpStatus.OK);
		//}

	}

	/**
	 *
	 * Delete category entity with specified id
	 *
	 * @param id
	 * @return message if entity was deleted or if it does not exist in the database
	 */
	@DeleteMapping("/deleteCategory/{id}")
	@ApiOperation(value = "Delete category by ID")
	public ResponseEntity<String> deleteCategory(
			@ApiParam(value = "category id", required = true) @PathVariable String id) {


		return categoryService.deleteCategoryService(id);
	}

	//@Secured({ "ROLE_ADMIN" })
	@PutMapping("/updateCategory/{id}")
	@ApiOperation(value = "Update category by ID")
	public ResponseEntity<String> updateCategory(
			@ApiParam(value = "Istaiga", required = true) @Valid @RequestBody CategoryDTO updated,
			@PathVariable String id) {

		if (categoryService.findById(id) == null) {

			LOG.warn("Knygų kategorijos tokiu kodu [{}] nėra", id);

			return new ResponseEntity<>("Knygų kategorija su tokiu kodu nerasta", HttpStatus.NOT_FOUND);

		} else {

			categoryService.updateCategory(id, updated);

			LOG.info("** CategoryController: atnaujinama kategorija ID [{}] **", id);


			return new ResponseEntity<>("Knygų kategorijos atnaujinti sėkmingai", HttpStatus.OK);
		}

	}

	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

}
