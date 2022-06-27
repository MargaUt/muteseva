package it.akademija.compensationapplication;

import java.time.LocalDate;
import java.util.Set;
import java.net.URLDecoder;
import java.io.UnsupportedEncodingException;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.akademija.journal.JournalService;
import it.akademija.journal.ObjectType;
import it.akademija.journal.OperationType;

@RestController
@Api(value = "compensation application")
@RequestMapping(path = "/api/naujas_kompensacija")
public class CompensationApplicationController {

	private static final Logger LOG = LoggerFactory.getLogger(CompensationApplicationController.class);

	@Autowired
	private CompensationApplicationService service;

	@Autowired
	private JournalService journalService;

	/**
	 *
	 * Create new compensation application for logged user
	 *
	 * @param data
	 * @return message
	 */
	@Secured({ "ROLE_USER" })
	@PostMapping("/user/new")

	@ApiOperation(value = "Create new compensation application")
	public ResponseEntity<String> createNewCompensationApplication(
			@ApiParam(value = "Compensation application", required = true) @Valid @RequestBody CompensationApplicationDTO data) {

		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		CompensationApplication application = service.createNewCompensationApplication(currentUsername, data);

		if (application != null) {
			journalService.newJournalEntry(OperationType.COMPENSATION_APPLICATION_SUBMITED, 123L,
					ObjectType.COMPENSATION_APPLICATION, "Sukurtas naujas kompensacijos prašymas");
			return new ResponseEntity<>("Prašymas sukurtas sėkmingai", HttpStatus.OK);
		}
		return new ResponseEntity<>("Prašymo sukurti nepavyko", HttpStatus.BAD_REQUEST);
	}

	/**
	 * Get a list of all compensation applications for logged user
	 *
	 * @return list applications
	 */
	@Secured({ "ROLE_USER" })
	@GetMapping("/user")
	@ApiOperation(value = "Get all user compensation applications")
	public Set<CompensationApplicationInfoUser> getAllUserApplications() {

		String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

		return service.getAllUserCompensationApplications(currentUsername);
	}

	/**
	 * Get specified CompensationApplication information page
	 *
	 * @return page of compensationApplication information
	 */
	@Secured({ "ROLE_MANAGER" })
	@GetMapping("/manager/page")
	@ApiOperation(value = "Get compensationApplication information pages")
	public ResponseEntity<Page<CompensationApplicationDTO>> getCompensationApplicationPage(

			@RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam(value = "search", required = false) String search) {

		Sort.Order order = new Sort.Order(Sort.Direction.ASC, "submitedAt");
		Pageable pageable = PageRequest.of(page, size, Sort.by(order));
		String decodedDate = "";
		if (search != null) {
			try {
				search = search.replaceAll("%", "%25");
				decodedDate = URLDecoder.decode(search, "UTF-8");

				if (decodedDate.matches("^\\d{4}-\\d{2}-\\d{2}$")
						&& service.checkSearchIsValidDate(decodedDate) != null) {
					return new ResponseEntity<>(service.getCompensationApplicationPage(pageable,
							service.checkSearchIsValidDate(decodedDate)), HttpStatus.OK);
				}
				LocalDate dateNonExistant = LocalDate.now().plusYears(1);
				return new ResponseEntity<>(service.getCompensationApplicationPage(pageable, dateNonExistant),
						HttpStatus.OK);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		return new ResponseEntity<>(service.getCompensationApplicationPage(pageable, null), HttpStatus.OK);
	}

	/**
	 *
	 * Delete user compensation application by id
	 *
	 * @param id
	 * @return message
	 */

	@Secured({ "ROLE_USER" })
	@DeleteMapping("/user/delete/{id}")
	@ApiOperation("Delete user compensation application by id")
	public ResponseEntity<String> deleteCompensationApplication(
			@ApiParam(value = "Application id", required = true) @PathVariable Long id) {

		LOG.info("**CompensationApplicationController: trinamas kompensacijos prasymas [{}] **", id);

		return service.deleteCompensationApplication(id);

	}

	public CompensationApplicationService getService() {
		return service;
	}

	public void setService(CompensationApplicationService service) {
		this.service = service;
	}
}
