package it.akademija.maitinimoIstaiga;

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
import it.akademija.meal.MealDTO;

@RestController
@Api(value = "istaiga")
@RequestMapping(path = "/api/istaigos")
public class MaitinimoIstaigaController {

	private static final Logger LOG = LoggerFactory.getLogger(MaitinimoIstaigaController.class);

	@Autowired
	private MaitinimoIstaigaService maitinimoIstaiga;



	/**
	 * Get specified MaitinimoIstaiga information page
	 *
	 * @return page of maitinimoIstaiga information
	 */
	@GetMapping("/user/page")
	@ApiOperation(value = "Get maitinimo istaigu information pages")
	public ResponseEntity<Page<MaitinimoIstaigaDTO>> getMaitinimoIstaigaPage(

			@RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam(value = "search", required = false) String search) {

		Sort.Order order = new Sort.Order(Sort.Direction.ASC, "pavadinimas").ignoreCase();
		String decodedName = "";

		Pageable pageable = PageRequest.of(page, size, Sort.by(order));
		if (search != null) {
			try {
				search = search.replaceAll("%", "%25");
				decodedName = URLDecoder.decode(search, "UTF-8");
				decodedName = decodedName.replaceAll("%", "%75[%]%");
				decodedName = decodedName.replaceAll("_", "[_]");

				return new ResponseEntity<>(maitinimoIstaiga.getIstaigosPage(pageable, decodedName),
						HttpStatus.OK);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<>(maitinimoIstaiga.getIstaigosPage(pageable, null), HttpStatus.OK);

	}
	
	/**
	 * Create new maitinimoIstaiga entity
	 *
	 * @param maitinimoIstaiga entity
	 * @return message
	 */
	@PostMapping("/admin/createIstaiga")
	@ApiOperation(value = "Create new maitinimo istaiga")
	public ResponseEntity<String> createNewIstaiga(
			@ApiParam(value = "Istaiga", required = true) @Valid @RequestBody MaitinimoIstaigaDTO istaiga) {

		String id = istaiga.getId();

		if (maitinimoIstaiga.findById(id) != null) {

			LOG.warn("Kuriama maitinimo istaiga jau egzistuojančiu įstaigos kodu [{}]", id);

			return new ResponseEntity<>("Maitinimo įstaiga su tokiu įstaigos kodu jau yra", HttpStatus.CONFLICT);

		} else {

			maitinimoIstaiga.createNewIstaiga(istaiga);

			LOG.info("**MaitinimoIstaigaController: kuriamas darzelis pavadinimu [{}] **", istaiga.getPavadinimas());

			return new ResponseEntity<>("Maitinimo įstaiga sukurta sėkmingai", HttpStatus.OK);
		}

	}

	/**
	 *
	 * Delete maitinimoIstaiga entity with specified id
	 *
	 * @param id
	 * @return message if entity was deleted or if it does not exist in the database
	 */
	@DeleteMapping("/admin/delete/{id}")
	@ApiOperation(value = "Delete istaiga by ID")
	public ResponseEntity<String> deleteMaitinimoIstaiga(
			@ApiParam(value = "Istaiga id", required = true) @PathVariable String id) {


		return maitinimoIstaiga.deleteMaitinimoIstaiga(id);
	}

	//@Secured({ "ROLE_ADMIN" })
	@PutMapping("/admin/update/{id}")
	@ApiOperation(value = "Update istaiga by ID")
	public ResponseEntity<String> updateMaitinimoIstaiga(
			@ApiParam(value = "Istaiga", required = true) @Valid @RequestBody MaitinimoIstaigaDTO updated,
			@PathVariable String id) {

		if (maitinimoIstaiga.findById(id) == null) {

			LOG.warn("Maitinimo įstaigos tokii kodu [{}] nėra", id);

			return new ResponseEntity<>("Maitinimo įstaiga su tokiu įstaigos kodu nerasta", HttpStatus.NOT_FOUND);

		} else {

			maitinimoIstaiga.updateMaitinimoIstaiga(id, updated);

			LOG.info("** Usercontroller: atnaujinama istaiga ID [{}] **", id);


			return new ResponseEntity<>("Įstaigos duomenys atnaujinti sėkmingai", HttpStatus.OK);
		}

	}

	public MaitinimoIstaigaService getMaitinimoIstaiga() {
		return maitinimoIstaiga;
	}

	public void setMaitinimoIstaiga(MaitinimoIstaigaService maitinimoIstaiga) {
		this.maitinimoIstaiga = maitinimoIstaiga;
	}

}
