package it.akademija.privatekindergarten;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrivateKindergartenService {
	

	@Autowired
	private PrivateKindergartenDAO gartenDao;

	/**
	 * Save new kindergarten to database, 
	 * returns PrivateKindergartens unique ID
	 *
	 * @param kindergarten
	 * @return ID
	 */
	@Transactional
	public Long createNewPrivateKindergarten(PrivateKindergartenDTO kindergarten) {

		PrivateKindergarten garten = new PrivateKindergarten(kindergarten.getKindergartenCode(), kindergarten.getKindergartenName(), kindergarten.getKindergartenAddress(),
				kindergarten.getKindergartenPhone(), kindergarten.getKindergartenEmail(),
				kindergarten.getKindergartenBankName(), kindergarten.getKindergartenAccountNumber(),kindergarten.getKindergartenBankCode());
		gartenDao.save(garten);
		return garten.getId();
	}


	public PrivateKindergartenDAO getGartenDao() {
		return gartenDao;
	}


	public void setGartenDao(PrivateKindergartenDAO gartenDao) {
		this.gartenDao = gartenDao;
	}


}
