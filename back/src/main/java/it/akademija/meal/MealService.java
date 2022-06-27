package it.akademija.meal;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.application.ApplicationDAO;
import it.akademija.meniu.MeniuDAO;
import it.akademija.meniu.MeniuDTO;

@Service
public class MealService {

	private static final Logger LOG = LoggerFactory.getLogger(MealService.class);

	@Autowired
	private MealDAO mealDao;
	
	@Autowired
	private MeniuDAO meniuDao;


	@Autowired
	private ApplicationDAO applicationDao;
	
	
	/**
	 *
	 * Returns Meal by meniu id
	 *
	 *
	 * @return List from Meal database
	 */
	@Transactional(readOnly = true)
	public List<MealDTO> getMealOfMeniu(String meniuId) {
		var meniu = meniuDao.findById(meniuId).get();
		return mealDao.findAllByMeniu(meniu).stream().map(MealDTO::from).collect(Collectors.toList());
	}

	/**
	 *
	 * Returns a page of MaitinimoIstaiga with specified page number and page size
	 *
	 * @param pageable
	 * @return page from maitinimoIstaiga database
	 */
	@Transactional(readOnly = true)
	public Page<MealDTO> getMealPage(Pageable pageable, String search) {
		if (search == null) {
			return mealDao.findAllIstaigas(pageable).map(MealDTO::from);
		}
		return mealDao.findByNameContainingIgnoreCase(search, pageable).map(MealDTO::from);
	}

	/**
	 * Save new maitinimoIstaiga to database
	 *
	 * @param maitinimoIstaiga
	 */
	@Transactional
	public void createNewMeal(MealDTO mealDto) {
		var meniu = meniuDao.findById(mealDto.getMeniuId()).get();
		mealDao.save(new Meal(
				//mealDto.getId(),
				mealDto.getName(), mealDto.getDescription(), meniu));

	}

	/**
	 * Find maitinimoIstaiga by id. Read only
	 *
	 * @param id
	 * @return maitinimoIstaiga or null if not found
	 */
	@Transactional(readOnly = true)
	public Meal findById(String id) {

		return mealDao.findById(id).orElse(null);
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
	public ResponseEntity<String> deleteMeal(String id) {

		Meal meal = mealDao.findById(id).orElse(null);

		if (meal != null) {

			mealDao.deleteById(id);

			LOG.info("** MealService: trinamas patiekalas ID [{}] **", id);

			return new ResponseEntity<>("Patiekalas ištrintas sėkmingai", HttpStatus.OK);

		} else {

			return new ResponseEntity<>("Patiekalas kodu nerastas", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Update maitinimoIstaiga
	 *
	 * @param currentInfo
	 * @param maitinimoIstaiga
	 */
	@Transactional
	public void updateMeal(String id, MealDTO updatedInfo) {

		Meal current = mealDao.findById(id).orElse(null);

		current.setName(updatedInfo.getName());
		current.setDescription(updatedInfo.getDescription());
		

		mealDao.save(current);
	}

	/**
	 * Delete kindergarten by name. Used during DB setup
	 *
	 * @param name
	 */
	@Transactional
	public void deleteByName(String name) {

		mealDao.deleteByName(name);
	}

	public ApplicationDAO getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDAO applicationDao) {
		this.applicationDao = applicationDao;
	}

	public MealDAO getMealDao() {
		return mealDao;
	}

	public void setMealDao(MealDAO mealDao) {
		this.mealDao = mealDao;
	}

	public MeniuDAO getMeniuDao() {
		return meniuDao;
	}

	public void setMeniuDao(MeniuDAO meniuDao) {
		this.meniuDao = meniuDao;
	}


}
