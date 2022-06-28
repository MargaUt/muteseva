package it.akademija.category;

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
import it.akademija.book.Book;

@Service
public class CategoryService {

	private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	private CategoryDAO categoryDAO;

	/**
	 *
	 * Returns a page of Category with specified page number and page size
	 *
	 * @param pageable
	 * @return page from Category database
	 */
	@Transactional(readOnly = true)
	public Page<CategoryDTO> getCategorypage(Pageable pageable, String search) {
		if (search == null) {
			return categoryDAO.findAllCategory(pageable).map(CategoryDTO::from);
		}
		return categoryDAO.findByNameContainingIgnoreCase(search, pageable).map(CategoryDTO::from);
	}

	/**
	 * Save new maitinimoIstaiga to database
	 *
	 * @param maitinimoIstaiga
	 */
	@Transactional
	public void createCategory(CategoryDTO category) {

		categoryDAO.save(new Category(
				// maitinimoIstaiga.getId(),
				category.getName()));

	}

	/**
	 * Find category by id. Read only
	 *
	 * @param id
	 * @return category or null if not found
	 */
	@Transactional(readOnly = true)
	public Category findById(String id) {

		return categoryDAO.findById(id).orElse(null);
	}

	/**
	 *
	 * Delete category with specified id. Also deletes all related
	 * category
	 * 
	 *
	 * @param id
	 */
	@Transactional
	public ResponseEntity<String> deleteCategoryService(String id) {

		Category category = categoryDAO.findById(id).orElse(null);

		if (category != null) {

			categoryDAO.deleteById(id);

			LOG.info("** CategoryService: trinama kategorija ID [{}] **", id);

			return new ResponseEntity<>("Knygų kategorija sėkmingai", HttpStatus.OK);

		} else {

			return new ResponseEntity<>("Knygų kategorija nerasta", HttpStatus.NOT_FOUND);
		}
	}

//	
//	/**
//	 * Removes additional meniu who has no other meniu connected to them.
//	 *
//	 * @param id
//	 * @param application
//	 */
//	public void detachAdditionalMeniu(MaitinimoIstaiga istaiga) {
//		Meniu istaigosMeniu = istaiga.getIstaigosMeniu();
//
//		if (additionalGuardian != null) {
//			int numberOfAdditionalGuardianApplications = additionalGuardian.removeApplication(application);
//
//			if (numberOfAdditionalGuardianApplications == 0) {
//				parentDetailsDao.delete(additionalGuardian);
//			}
//
//			application.setAdditionalGuardian(null);
//
//		}
//	}

	/**
	 * Update category
	 *
	 * @param currentInfo
	 * @param category
	 */
	@Transactional
	public void updateCategory(String id, CategoryDTO updatedInfo) {

		Category current = categoryDAO.findById(id).orElse(null);

		current.setName(updatedInfo.getName());

		categoryDAO.save(current);
	}

//	/**
//	 * Delete kindergarten by name. Used during DB setup
//	 *
//	 * @param name
//	 */
//	@Transactional
//	public void deleteByName(String pavadinimas) {
//
//		maitinimoIstaigaDAO.deleteByPavadinimas(pavadinimas);
//	}

	public CategoryDAO getCategoryDAO() {
		return categoryDAO;
	}

	public void setCategoryDAO(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

}
