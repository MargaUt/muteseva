package it.akademija.meniu;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.maitinimoIstaiga.MaitinimoIstaiga;
import it.akademija.maitinimoIstaiga.MaitinimoIstaigaDAO;
import it.akademija.meal.Meal;

@Service
public class MeniuService {

	private static final Logger LOG = LoggerFactory.getLogger(MeniuService.class);

	@Autowired
	private MeniuDAO meniuDAO;
	@Autowired
	private MaitinimoIstaigaDAO istaigosDao;

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
	 * Returns a List of Meniu
	 *
	 *
	 * @return List from Meniu database
	 */
	@Transactional(readOnly = true)
	public List<MeniuDTO> getmenius() {
		return meniuDAO.findAll().stream().map(MeniuDTO::from).collect(Collectors.toList());
	}
	
	/**
	 *
	 * Returns Meniu by maitinimoIstaigos id
	 *
	 *
	 * @return List from Meniu database
	 */
	@Transactional(readOnly = true)
	public List<MeniuDTO> getMeniuOfIstaiga(String istaigosId) {
		var istaiga = istaigosDao.findById(istaigosId).get();
		return meniuDAO.findAllByMaitinimoIstaiga(istaiga).stream().map(MeniuDTO::from).collect(Collectors.toList());
	}


	/**
	 * Save new meniu to database
	 *
	 * @param meniu
	 */
	@Transactional
	public void createNewMeniu(MeniuDTO meniu) {

		var istaiga = istaigosDao.findById(meniu.getIstaigosid()).get();
		
		meniuDAO.save(new Meniu(meniu.getId(), meniu.getMeniuName(), istaiga));

	}

	/**
	 * Find Meniu by id. Read only
	 *
	 * @param id
	 * @return maitinimoIstaiga or null if not found
	 */
	@Transactional(readOnly = true)
	public Meniu findById(String id) {

		return meniuDAO.findById(id).orElse(null);
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
	public ResponseEntity<String> deleteMeniu(String id) {

		Meniu meniu = meniuDAO.findById(id).orElse(null);

		if (meniu != null) {

			meniuDAO.deleteById(id);

			LOG.info("** UserService: trinamas meniu ID [{}] **", id);

			return new ResponseEntity<>("Meniu ištrinta sėkmingai", HttpStatus.OK);

		} else {

			return new ResponseEntity<>("Maitinimo įstaiga įstaigos kodu nerastas", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Update maitinimoIstaiga
	 *
	 * @param currentInfo
	 * @param maitinimoIstaiga
	 */
	@Transactional
	public void updateMeniu(String id, MeniuDTO updatedInfo) {

		Meniu current = meniuDAO.findById(id).orElse(null);

		current.setMeniuName(updatedInfo.getMeniuName());

		meniuDAO.save(current);
	}

	
	/**
	 * Delete Meniu by name. Used during DB setup
	 *
	 * @param name
	 */
	@Transactional
	public void deleteByName(String meniuName) {

		meniuDAO.deleteByMeniuName(meniuName);
	}

	public MaitinimoIstaigaDAO getIstaigosDao() {
		return istaigosDao;
	}

	public void setIstaigosDao(MaitinimoIstaigaDAO istaigosDao) {
		this.istaigosDao = istaigosDao;
	}

	public MeniuDAO getMeniuDAO() {
		return meniuDAO;
	}

	public void setMeniuDAO(MeniuDAO meniuDAO) {
		this.meniuDAO = meniuDAO;
	}

}
