package it.akademija.kindergarten;

import java.util.List;
import java.util.Set;
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
import org.springframework.security.access.annotation.Secured;
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
import it.akademija.journal.JournalService;
import it.akademija.journal.ObjectType;
import it.akademija.journal.OperationType;

@RestController
@Api(value = "kindergarten")
@RequestMapping(path = "/api/darzeliai")
public class KindergartenController {

	private static final Logger LOG = LoggerFactory.getLogger(KindergartenController.class);

	@Autowired
	private KindergartenService kindergartenService;

	@Autowired
	private JournalService journalService;

	/**
	 * Get list of all Kindergarten names and addresses with capacity of more than
	 * zero
	 *
	 * @return list of kindergarten
	 */
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER" })
	@GetMapping
	@ApiOperation(value = "Get all kindergarten names and addresses with available places > 0")
	public List<KindergartenInfo> getAllWithNonZeroCapacity() {

		return kindergartenService.getAllWithNonZeroCapacity();
	}
	
	/**
	 * Get list of all Kindergarten names and addresses where capacity of both
	 * age groups is zero
	 *
	 * @return list of kindergarten
	 */
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER" })
	@GetMapping("/emptyKindergartens")
	@ApiOperation(value = "Get all kindergarten names and addresses with available places = 0")
	public List<KindergartenInfo> getAllWithZeroCapacity() {

		return kindergartenService.getAllWithZeroCapacity();
	}

	/**
	 * Get list of all elderates
	 *
	 * @return list of kindergarten
	 */
	@Secured({ "ROLE_MANAGER", "ROLE_USER" })
	@GetMapping("/elderates")
	@ApiOperation(value = "Get all elderates")
	public Set<String> getAllElderates() {

		return kindergartenService.getAllElderates();
	}

	/**
	 * Get specified Kindergarten information page
	 *
	 * @return page of kindergarten information
	 */
	@Secured({ "ROLE_MANAGER", "ROLE_USER" })
	@GetMapping("/manager/page")
	@ApiOperation(value = "Get kindergarten information pages")
	public ResponseEntity<Page<KindergartenDTO>> getKindergartenPage(

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

				return new ResponseEntity<>(kindergartenService.getKindergartenPage(pageable, decodedName),
						HttpStatus.OK);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<>(kindergartenService.getKindergartenPage(pageable, null), HttpStatus.OK);

	}
	
	/**
	 * Get specified Kindergarten information page
	 *
	 * @return page of kindergarten information
	 */
	@Secured({ "ROLE_MANAGER", "ROLE_USER" })
	@GetMapping("/manager/search")
	@ApiOperation(value = "Get kindergarten information pages")
	public ResponseEntity<Page<KindergartenDTO>> getKindergartenPageByNameAndAddress(

			@RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam("search") String search) {

		Sort.Order order = new Sort.Order(Sort.Direction.ASC, "name").ignoreCase();
		String decodedSearch = "";

		Pageable pageable = PageRequest.of(page, size, Sort.by(order));

			try {
				search = search.replaceAll("%", "%25");
				decodedSearch = URLDecoder.decode(search, "UTF-8");
				decodedSearch = decodedSearch.replaceAll("%", "%75[%]%");
				decodedSearch = decodedSearch.replaceAll("_", "[_]");

				return new ResponseEntity<>(kindergartenService.getKindergartenPageByNameAndAddress(pageable, decodedSearch),
						HttpStatus.OK);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		
		return new ResponseEntity<>(kindergartenService.getKindergartenPage(pageable, null), HttpStatus.OK);

	}

	/**
	 * Create new kindergarten entity
	 *
	 * @param kindergarten entity
	 * @return message
	 */
	@Secured({ "ROLE_MANAGER" })
	@PostMapping("/manager/createKindergarten")
	@ApiOperation(value = "Create new kindergarten")
	public ResponseEntity<String> createNewKindergarten(
			@ApiParam(value = "Kindergarten", required = true) @Valid @RequestBody KindergartenDTO kindergarten) {

		String id = kindergarten.getId();

		if (kindergartenService.findById(id) != null) {

			LOG.warn("Kuriamas dar??elis su jau egzistuojan??iu ??staigos kodu [{}]", id);

			return new ResponseEntity<>("Dar??elis su tokiu ??staigos kodu jau yra", HttpStatus.CONFLICT);

		} else if (kindergartenService.nameAlreadyExists(kindergarten.getName().trim(), id)) {

			LOG.warn("Kuriamas dar??elis su jau egzistuojan??iu ??staigos pavadinimu [{}]", kindergarten.getName().trim());

			return new ResponseEntity<>("Dar??elis su tokiu ??staigos pavadinimu jau yra", HttpStatus.CONFLICT);

		} else {

			kindergartenService.createNewKindergarten(kindergarten);

			LOG.info("**KindergartenController: kuriamas darzelis pavadinimu [{}] **", kindergarten.getName());

			journalService.newJournalEntry(OperationType.KINDERGARTEN_CREATED, Long.parseLong(id),
					ObjectType.KINDERGARTEN, "Sukurtas naujas dar??elis");

			return new ResponseEntity<>("Dar??elis sukurtas s??kmingai", HttpStatus.OK);
		}

	}

	/**
	 *
	 * Delete kindergarten entity with specified id
	 *
	 * @param id
	 * @return message if entity was deleted or if it does not exist in the database
	 */
	@Secured({ "ROLE_MANAGER" })
	@DeleteMapping("/manager/delete/{id}")
	@ApiOperation(value = "Delete kindergarten by ID")
	public ResponseEntity<String> deleteKindergarten(
			@ApiParam(value = "Kindergarten id", required = true) @PathVariable String id) {

		journalService.newJournalEntry(OperationType.KINDERGARTEN_DELETED, Long.parseLong(id), ObjectType.KINDERGARTEN,
				"I??trintas dar??elis");

		return kindergartenService.deleteKindergarten(id);
	}

	@Secured({ "ROLE_MANAGER" })
	@PutMapping("/manager/update/{id}")
	@ApiOperation(value = "Update kindergarten by ID")
	public ResponseEntity<String> updateKindergarten(
			@ApiParam(value = "Kindergarten", required = true) @Valid @RequestBody KindergartenDTO updated,
			@PathVariable String id) {

		if (kindergartenService.findById(id) == null) {

			LOG.warn("Dar??elio ??staigos kodu [{}] n??ra", id);

			return new ResponseEntity<>("Dar??elis su tokiu ??staigos kodu nerastas", HttpStatus.NOT_FOUND);

		} else if (kindergartenService.nameAlreadyExists(updated.getName().trim(), id)) {

			LOG.warn("Dar??elis pavadinimu [{}] jau egzituoja", updated.getName().trim());

			return new ResponseEntity<>("Dar??elis su tokiu ??staigos pavadinimu jau yra", HttpStatus.CONFLICT);

		} else {

			kindergartenService.updateKindergarten(id, updated);

			LOG.info("** Usercontroller: atnaujinamas dar??elis ID [{}] **", id);

			journalService.newJournalEntry(OperationType.KINDERGARTEN_UPDATED, Long.parseLong(id),
					ObjectType.KINDERGARTEN, "Atnaujinti dar??elio duomenys");

			return new ResponseEntity<>("Dar??elio duomenys atnaujinti s??kmingai", HttpStatus.OK);
		}

	}

	/**
	 * Get Kindergarten statistics
	 *
	 * @return kindergarten statistics
	 */
	@Secured({ "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_USER" })
	@GetMapping("/statistics")
	@ApiOperation(value = "Get kindergarten statistics")
	public Page<KindergartenStatistics> getKindergartenStatistics(@RequestParam("page") int page,
			@RequestParam("size") int size) {

		Sort.Order order = new Sort.Order(Sort.Direction.ASC, "name").ignoreCase();

		Pageable pageable = PageRequest.of(page, size, Sort.by(order));

		return kindergartenService.getKindergartenStatistics(pageable);
	}

	public KindergartenService getGartenService() {
		return kindergartenService;
	}

	public void setGartenService(KindergartenService gartenService) {
		this.kindergartenService = gartenService;
	}

}
