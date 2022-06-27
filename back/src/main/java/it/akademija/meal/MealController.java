package it.akademija.meal;

import java.net.URLDecoder;
import java.util.List;

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
import it.akademija.meniu.MeniuDTO;

@RestController
@Api(value = "meal")
@RequestMapping(path = "/api/meals")
public class MealController {

	private static final Logger LOG = LoggerFactory.getLogger(MealController.class);

	@Autowired
	private MealService mealService;


	
	
	@GetMapping("/{meniuId}")
	public List<MealDTO> getMenius(@ApiParam(value = "Meniu id", required = true)
	@PathVariable String meniuId) {
		return mealService.getMealOfMeniu(meniuId);
	}

	/**
	 * Get specified Meal information page
	 *
	 * @return page of meal information
	 */
	@GetMapping("/user/page")
	@ApiOperation(value = "Get meal information pages")
	public ResponseEntity<Page<MealDTO>> getMealPage(

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

				return new ResponseEntity<>(mealService.getMealPage(pageable, search),
						HttpStatus.OK);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<>(mealService.getMealPage(pageable, null), HttpStatus.OK);

	}

	/**
	 * Create new maitinimoIstaiga entity
	 *
	 * @param maitinimoIstaiga entity
	 * @return message
	 */
	@PostMapping("/admin/createMeal")
	@ApiOperation(value = "Create new meal")
	public ResponseEntity<String> createNewMeal(
			@ApiParam(value = "Meal", required = true) @Valid @RequestBody MealDTO meal) {

		String id = meal.getId();

		if (mealService.findById(id) != null) {

			LOG.warn("Kuriamas patiekalas jau egzistuojančiu kodu [{}]", id);

			return new ResponseEntity<>("Patiekalas su tokiu įstaigos kodu jau yra", HttpStatus.CONFLICT);

		} else {

			mealService.createNewMeal(meal);

			LOG.info("**MealController: kuriamas patiekalas pavadinimu [{}] **", meal.getName());

			return new ResponseEntity<>("Patiekalas sukurtas sėkmingai", HttpStatus.OK);
		}

	}

	/**
	 *
	 * Delete Meal entity with specified id
	 *
	 * @param id
	 * @return message if entity was deleted or if it does not exist in the database
	 */
	@DeleteMapping("/admin/delete/{id}")
	@ApiOperation(value = "Delete Meal by ID")
	public ResponseEntity<String> deleteMeal(
			@ApiParam(value = "Meal id", required = true) @PathVariable String id) {


		return mealService.deleteMeal(id);
	}

	//@Secured({ "ROLE_ADMIN" })
	@PutMapping("/admin/update/{id}")
	@ApiOperation(value = "Update meal by ID")
	public ResponseEntity<String> updateMeal(
			@ApiParam(value = "Meal", required = true) @Valid @RequestBody MealDTO updated,
			@PathVariable String id) {

		if (mealService.findById(id) == null) {

			LOG.warn("Patiekalo tokiu kodu [{}] nėra", id);

			return new ResponseEntity<>("Patiekalas tokiu kodu nerastas", HttpStatus.NOT_FOUND);

		} else {

			mealService.updateMeal(id, updated);

			LOG.info("** Usercontroller: atnaujinama istaiga ID [{}] **", id);


			return new ResponseEntity<>("Įstaigos duomenys atnaujinti sėkmingai", HttpStatus.OK);
		}

	}

	public MealService getMeealService() {
		return mealService;
	}

	public void setMeealService(MealService meealService) {
		this.mealService = meealService;
	}
}
