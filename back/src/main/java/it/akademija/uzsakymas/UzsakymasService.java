package it.akademija.uzsakymas;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.maitinimoIstaiga.MaitinimoIstaigaDAO;
import it.akademija.meal.Meal;
import it.akademija.meal.MealDAO;
import it.akademija.meal.MealDTO;

@Service
public class UzsakymasService {

	private static final Logger LOG = LoggerFactory.getLogger(UzsakymasService.class);

	@Autowired
	private UzsakymasDAO uzsakymasDAO;
	@Autowired
	private MealDAO mealDao;

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

//	/**
//	 *
//	 * Returns a List of Meniu
//	 *
//	 *
//	 * @return List from Meniu database
//	 */
//	@Transactional(readOnly = true)
//	public List<UzsakymasDTO> getmenius() {
//		return meniuDAO.findAll().stream().map(UzsakymasDTO::from).collect(Collectors.toList());
//	}
//	
	@Transactional(readOnly = true)
	public List<UzsakymasDTO> findAllUzsakymai() {
		return uzsakymasDAO.findAll().stream().map(uzsakyamasIsDb -> new UzsakymasDTO(uzsakyamasIsDb.getUsername(), uzsakyamasIsDb.getMeals().stream().map(MealDTO::from).collect(Collectors.toSet())))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public UzsakymasDTO getOneUzsakyma(String username) {
		var uzsakymas = uzsakymasDAO.findByUsername(username);
		if (uzsakymas == null) {
			throw new ValidationException("Neradom užsakymo");
		}
		return UzsakymasDTO.from(uzsakymas);
	}

	@Transactional(readOnly = true)
	public Integer countUzsakymus(String username) {
		return uzsakymasDAO.countMeals(username);
	}

//
//	@Transactional(readOnly = true)
//	public Integer sumProductsPrice(String username) {
//		return cartDao.sumProductsPrice(username);
//	}

	@Transactional
	public void createUzsakyma(UzsakymasDTO uzsakymasDTO) {
		if (uzsakymasDAO.findByUsername(uzsakymasDTO.getUsername()) == null) {
			uzsakymasDAO.save(uzsakymasDTO.toUzsakymas(uzsakymasDTO.getUsername()));
		}

	}

	@Transactional
	public Uzsakymas updateUzsakyma(String username, UzsakymasDTO uzsakymas) {
		Uzsakymas uzsakymasToUpdate = uzsakymasDAO.findByUsername(username);
		// uzsakymasToUpdate.setAdresas(cart.getAdresas());
		return uzsakymasDAO.save(uzsakymasToUpdate);
	}

	@Transactional
	public void deleteUzsakyma(String username) {
		uzsakymasDAO.deleteByUsername(username);

	}

	@Transactional
	public void addToUzsakyma(String patiekaloId, String username) {
		var uzsakymas = uzsakymasDAO.findByUsername(username);
		if (uzsakymas == null) {
			throw new ValidationException("Neradom Užsakymo");
		}
		if (uzsakymas.getMeals() == null) {
			uzsakymas.setMeals(new HashSet<Meal>());
		}
		var meal = mealDao.findById(patiekaloId).get();
		if (meal == null) {
			throw new ValidationException("Neradom patiekalo");
		}
		uzsakymas.getMeals().add(meal);
		uzsakymasDAO.save(uzsakymas);

	}

	@Transactional
	public void removeFromUzsakymo(String pavadinimas, String username) {
		var uzsakymas = uzsakymasDAO.findByUsername(username);
		if (uzsakymas == null) {
			throw new ValidationException("Neradom užsakymo");
		}
		if (uzsakymas.getMeals() == null) {
			return;
		}
		var meal = uzsakymas.getMeals().stream().filter(p -> p.getName().equals(pavadinimas)).findFirst()
				.orElse(null);
		if (meal == null) {
			return;
		} else {
			uzsakymas.getMeals().remove(meal);
			uzsakymasDAO.save(uzsakymas);
		}

	}
	
	
////	/**
////	 *
////	 * Returns Meniu by maitinimoIstaigos id
////	 *
////	 *
////	 * @return List from Meniu database
////	 */
////	@Transactional(readOnly = true)
////	public List<UzsakymasDTO> getMeniuOfIstaiga(String istaigosId) {
////		var istaiga = istaigosDao.findById(istaigosId).get();
////		return meniuDAO.findAllByMaitinimoIstaiga(istaiga).stream().map(UzsakymasDTO::from).collect(Collectors.toList());
////	}
//
//	/**
//	 * Save new meniu to database
//	 *
//	 * @param meniu
//	 */
//	@Transactional
//	public void createNewMeniu(UzsakymasDTO meniu) {
//
//		var istaiga = istaigosDao.findById(meniu.getIstaigosid()).get();
//
//		meniuDAO.save(new Uzsakymas(
//				// meniu.getId(),
//				meniu.getMeniuName(), istaiga));
//
//	}
//
//	/**
//	 * Find Meniu by id. Read only
//	 *
//	 * @param id
//	 * @return maitinimoIstaiga or null if not found
//	 */
//	@Transactional(readOnly = true)
//	public Uzsakymas findById(String id) {
//
//		return meniuDAO.findById(id).orElse(null);
//	}
//
//	/**
//	 *
//	 * Delete maitinimoIstaiga with specified id. Also deletes all related
//	 * maitinimoIstaiga
//	 * 
//	 *
//	 * @param id
//	 */
//	@Transactional
//	public ResponseEntity<String> deleteMeniu(String id) {
//
//		Uzsakymas meniu = meniuDAO.findById(id).orElse(null);
//
//		if (meniu != null) {
//
//			meniuDAO.deleteById(id);
//
//			LOG.info("** UserService: trinamas meniu ID [{}] **", id);
//
//			return new ResponseEntity<>("Meniu ištrinta sėkmingai", HttpStatus.OK);
//
//		} else {
//
//			return new ResponseEntity<>("Maitinimo įstaiga įstaigos kodu nerastas", HttpStatus.NOT_FOUND);
//		}
//	}
//
//	/**
//	 * Update maitinimoIstaiga
//	 *
//	 * @param currentInfo
//	 * @param maitinimoIstaiga
//	 */
//	@Transactional
//	public void updateMeniu(String id, UzsakymasDTO updatedInfo) {
//
//		Uzsakymas current = meniuDAO.findById(id).orElse(null);
//
//		current.setMeniuName(updatedInfo.getMeniuName());
//
//		meniuDAO.save(current);
//	}
//
//	/**
//	 * Delete Meniu by name. Used during DB setup
//	 *
//	 * @param name
//	 */
//	@Transactional
//	public void deleteByName(String meniuName) {
//
//		meniuDAO.deleteByMeniuName(meniuName);
//	}
//
//	public MaitinimoIstaigaDAO getIstaigosDao() {
//		return istaigosDao;
//	}
//
//	public void setIstaigosDao(MaitinimoIstaigaDAO istaigosDao) {
//		this.istaigosDao = istaigosDao;
//	}
//
//	public UzsakymasDAO getMeniuDAO() {
//		return meniuDAO;
//	}
//
//	public void setMeniuDAO(UzsakymasDAO meniuDAO) {
//		this.meniuDAO = meniuDAO;
//	}

	public UzsakymasDAO getUzsakymasDAO() {
		return uzsakymasDAO;
	}

	public void setUzsakymasDAO(UzsakymasDAO uzsakymasDAO) {
		this.uzsakymasDAO = uzsakymasDAO;
	}

	public MealDAO getMealDao() {
		return mealDao;
	}

	public void setMealDao(MealDAO mealDao) {
		this.mealDao = mealDao;
	}

}
