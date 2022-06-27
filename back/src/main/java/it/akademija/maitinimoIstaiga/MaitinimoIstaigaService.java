package it.akademija.maitinimoIstaiga;

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
import it.akademija.meniu.Meniu;

@Service
public class MaitinimoIstaigaService {

	private static final Logger LOG = LoggerFactory.getLogger(MaitinimoIstaigaService.class);

	@Autowired
	private MaitinimoIstaigaDAO maitinimoIstaigaDAO;

	@Autowired
	private ApplicationDAO applicationDao;

	/**
	 *
	 * Returns a page of MaitinimoIstaiga with specified page number and page size
	 *
	 * @param pageable
	 * @return page from maitinimoIstaiga database
	 */
	@Transactional(readOnly = true)
	public Page<MaitinimoIstaigaDTO> getIstaigosPage(Pageable pageable, String search) {
		if (search == null) {
			return maitinimoIstaigaDAO.findAllMaitinimoIstaiga(pageable).map(MaitinimoIstaigaDTO::from);
		}
		return maitinimoIstaigaDAO.findByNameContainingIgnoreCase(search, pageable).map(MaitinimoIstaigaDTO::from);
	}

	/**
	 * Save new maitinimoIstaiga to database
	 *
	 * @param maitinimoIstaiga
	 */
	@Transactional
	public void createNewIstaiga(MaitinimoIstaigaDTO maitinimoIstaiga) {

		maitinimoIstaigaDAO.save(new MaitinimoIstaiga( 
				//maitinimoIstaiga.getId(), 
				maitinimoIstaiga.getKodas(), maitinimoIstaiga.getPavadinimas(),
				maitinimoIstaiga.getAddress()));

	}

	/**
	 * Find maitinimoIstaiga by id. Read only
	 *
	 * @param id
	 * @return maitinimoIstaiga or null if not found
	 */
	@Transactional(readOnly = true)
	public MaitinimoIstaiga findById(String id) {

		return maitinimoIstaigaDAO.findById(id).orElse(null);
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
	public ResponseEntity<String> deleteMaitinimoIstaiga(String id) {

		MaitinimoIstaiga maitinimoIstaiga = maitinimoIstaigaDAO.findById(id).orElse(null);

		if (maitinimoIstaiga != null) {

			maitinimoIstaigaDAO.deleteById(id);

			LOG.info("** UserService: trinama maitinimo įstaiga ID [{}] **", id);

			return new ResponseEntity<>("Maitinimo įstaiga ištrinta sėkmingai", HttpStatus.OK);

		} else {

			return new ResponseEntity<>("Maitinimo įstaiga įstaigos kodu nerastas", HttpStatus.NOT_FOUND);
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
	 * Update maitinimoIstaiga
	 *
	 * @param currentInfo
	 * @param maitinimoIstaiga
	 */
	@Transactional
	public void updateMaitinimoIstaiga(String id, MaitinimoIstaigaDTO updatedInfo) {

		MaitinimoIstaiga current = maitinimoIstaigaDAO.findById(id).orElse(null);

		current.setKodas(updatedInfo.getKodas());
		current.setPavadinimas(updatedInfo.getPavadinimas());
		current.setAddress(updatedInfo.getAddress());

		maitinimoIstaigaDAO.save(current);
	}

	/**
	 * Delete kindergarten by name. Used during DB setup
	 *
	 * @param name
	 */
	@Transactional
	public void deleteByName(String pavadinimas) {

		maitinimoIstaigaDAO.deleteByPavadinimas(pavadinimas);
	}

	public MaitinimoIstaigaDAO getMaitinimoIstaigaDAO() {
		return maitinimoIstaigaDAO;
	}

	public void setMaitinimoIstaigaDAO(MaitinimoIstaigaDAO maitinimoIstaigaDAO) {
		this.maitinimoIstaigaDAO = maitinimoIstaigaDAO;
	}

	public ApplicationDAO getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDAO applicationDao) {
		this.applicationDao = applicationDao;
	}

}
