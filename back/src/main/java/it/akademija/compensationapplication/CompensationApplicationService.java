package it.akademija.compensationapplication;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Set;
import java.util.regex.Pattern;
import org.springframework.data.domain.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.akademija.privatekindergarten.PrivateKindergarten;
import it.akademija.privatekindergarten.PrivateKindergartenDAO;
import it.akademija.privatekindergarten.PrivateKindergartenService;
import it.akademija.user.User;
import it.akademija.user.UserService;

@Service
public class CompensationApplicationService {

	@Autowired
	private CompensationApplicationDAO applicationDao;

	@Autowired
	private UserService userService;

	@Autowired
	private PrivateKindergartenService gartenService;

	@Autowired
	private PrivateKindergartenDAO gartenDAO;

	/**
	 *
	 * Get information about submitted compensation applications for logged in user
	 *
	 * @param currentUsername
	 * @return list of user compensation applications
	 */
	@Transactional(readOnly = true)
	public Set<CompensationApplicationInfoUser> getAllUserCompensationApplications(String currentUsername) {

		return applicationDao.findAllUserCompensationApplications(currentUsername);
	}

	/**
	 * Create an compensation application for logged in user's child with specified
	 * child data. Creates a private kindergarten with specified data. Receives and
	 * updates user data. Sets received main guardian and private kindergarten to
	 * application.
	 *
	 * 
	 * @param currentUsername
	 * @param data
	 * @return compensationApplication
	 */
	@Transactional
	public CompensationApplication createNewCompensationApplication(String currentUsername,
			CompensationApplicationDTO data) {

		CompensationApplication application = new CompensationApplication();

		User firstParent = userService.updateUserDataInfo(data.getMainGuardian(), currentUsername);
		Long id = gartenService.createNewPrivateKindergarten(data.getPrivateKindergarten());
		PrivateKindergarten garten = gartenDAO.getById(id);

		application.setSubmitedAt();
		application.setChildName(capitalize(data.getChildName()));
		application.setChildSurname(capitalize(data.getChildSurname()));
		application.setChildPersonalCode(data.getChildPersonalCode());
		application.setBirthdate(data.getBirthdate());
		application.setMainGuardian(firstParent);
		application.setPrivateKindergarten(garten);
		application = applicationDao.saveAndFlush(application);

		return application;

	}

	/**
	 * Create an compensation application for logged in user's child with specified
	 * child data. Creates a private kindergarten with specified data. Receives and
	 * updates user data. Sets received main guardian and private kindergarten to
	 * application.
	 *
	 *
	 * @param currentUsername
	 * @param data
	 * @return compensationApplication
	 */
	@Transactional
	public CompensationApplication createNewCompensationApplicationDTO(String currentUsername,
			CompensationApplicationDTO data) {

		CompensationApplication application = new CompensationApplication();

		User firstParent = userService.updateUserData(data.getMainGuardianSec(), currentUsername);
		Long id = gartenService.createNewPrivateKindergarten(data.getPrivateKindergarten());
		PrivateKindergarten garten = gartenDAO.getById(id);

		application.setSubmitedAt();
		application.setChildName(capitalize(data.getChildName()));
		application.setChildSurname(capitalize(data.getChildSurname()));
		application.setChildPersonalCode(data.getChildPersonalCode());
		application.setBirthdate(data.getBirthdate());
		application.setMainGuardian(firstParent);
		application.setPrivateKindergarten(garten);
		application = applicationDao.saveAndFlush(application);

		return application;

	}

	/**
	 *
	 * Capitalize first letter of word, other letters to lowercase
	 *
	 * @param str
	 * @return
	 */
	private String capitalize(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}

		return Pattern.compile("\\b(.)(.*?)\\b").matcher(str)
				.replaceAll(match -> match.group(1).toUpperCase() + match.group(2).toLowerCase());
	}

	/**
	 * Delete user compensation application by its id. Additionally deletes private
	 * kindergarten that was created with the submission of application. Accessible
	 * to User only
	 *
	 * @param id
	 * @return message indicating whether deletion was successful
	 */
	@Transactional
	public ResponseEntity<String> deleteCompensationApplication(Long id) {

		CompensationApplication application = applicationDao.getById(id);

		User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

		if (application != null && application.getMainGuardian().equals(user)) {

			applicationDao.delete(application);

			return new ResponseEntity<>("Ištrinta sėkmingai", HttpStatus.OK);
		}

		return new ResponseEntity<>("Prašymas nerastas", HttpStatus.NOT_FOUND);
	}

	/**
	 *
	 * Returns a page of Compensation application with specified page number and
	 * page size
	 *
	 * @param pageable
	 * @return page from compensation application database
	 */
	@Transactional(readOnly = true)
	public Page<CompensationApplicationDTO> getCompensationApplicationPage(Pageable pageable, LocalDate search) {
		if (search == null) {
			return applicationDao.orderdByDocumentId(pageable).map(CompensationApplicationDTO::from);
		}
		return applicationDao.findBySubmitedAt(search, pageable).map(CompensationApplicationDTO::from);

	}

	public LocalDate checkSearchIsValidDate(String search) {

		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setLenient(false);
			df.parse(search);
			LocalDate date = LocalDate.parse(search);
			return date;
		} catch (ParseException e) {
			return null;
		}
	}

}
