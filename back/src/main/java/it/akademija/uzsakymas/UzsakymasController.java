package it.akademija.uzsakymas;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.akademija.category.CategoryService;

@RestController
@Api(value = "meniu")
@RequestMapping(path = "/api/uzsakymas")
public class UzsakymasController {

	private static final Logger LOG = LoggerFactory.getLogger(UzsakymasController.class);

	@Autowired
	private UzsakymasService uzsakymoService;
	



//
////	/**
////	 * Get specified MaitinimoIstaiga information page
////	 *
////	 * @return page of maitinimoIstaiga information
////	 */
////	@GetMapping("/user/page")
////	@ApiOperation(value = "Get maitinimo istaigu information pages")
////	public ResponseEntity<Page<MeniuDTO>> getMaitinimoIstaigaPage(
////
////			@RequestParam("page") int page, @RequestParam("size") int size,
////			@RequestParam(value = "search", required = false) String search) {
////
////		Sort.Order order = new Sort.Order(Sort.Direction.ASC, "pavadinimas").ignoreCase();
////		String decodedName = "";
////
////		Pageable pageable = PageRequest.of(page, size, Sort.by(order));
////		if (search != null) {
////			try {
////				search = search.replaceAll("%", "%25");
////				decodedName = URLDecoder.decode(search, "UTF-8");
////				decodedName = decodedName.replaceAll("%", "%75[%]%");
////				decodedName = decodedName.replaceAll("_", "[_]");
////
////				return new ResponseEntity<>(maitinimoIstaiga.getIstaigosPage(pageable, decodedName),
////						HttpStatus.OK);
////			} catch (UnsupportedEncodingException e) {
////				e.printStackTrace();
////			}
////		}
////		return new ResponseEntity<>(maitinimoIstaiga.getIstaigosPage(pageable, null), HttpStatus.OK);
////
////	}
//	
//
//	
//	@GetMapping("/{istaigosId}")
//	public List<UzsakymasDTO> getMenius(@ApiParam(value = "Istaigos id", required = true)
//	@PathVariable String istaigosId) {
//		return meniuService.getMeniuOfIstaiga(istaigosId);
//	}
//	
//	
//	
//	@RequestMapping(method = RequestMethod.GET)
//	public List<UzsakymasDTO> getMenius() {
//		return meniuService.getmenius();
//	}
//
//	/**
//	 * Create new Meniu entity
//	 *
//	 * @param Meniu entity
//	 * @return message
//	 */
//	@PostMapping("/admin/createMeniu")
//	@ApiOperation(value = "Create new maitinimo istaigos meniu")
//	@RequestMapping(method = RequestMethod.POST)
//	@ResponseStatus(HttpStatus.CREATED)
//	public void createMeniu(@RequestBody @Valid UzsakymasDTO meniu) {
//		meniuService.createNewMeniu(meniu);
//
//	}
//	
//	
////	@PostMapping("/admin/createIstaiga")
////	@ApiOperation(value = "Create new maitinimo istaiga")
////	public ResponseEntity<String> createNewIstaiga(
////			@ApiParam(value = "Istaiga", required = true) @Valid @RequestBody MeniuDTO istaiga) {
////
////		String id = istaiga.getId();
////
////		if (maitinimoIstaiga.findById(id) != null) {
////
////			LOG.warn("Kuriama maitinimo istaiga jau egzistuojančiu įstaigos kodu [{}]", id);
////
////			return new ResponseEntity<>("Maitinimo įstaiga su tokiu įstaigos kodu jau yra", HttpStatus.CONFLICT);
////
////		} else {
////
////			maitinimoIstaiga.createNewIstaiga(istaiga);
////
////			LOG.info("**MaitinimoIstaigaController: kuriamas darzelis pavadinimu [{}] **", istaiga.getPavadinimas());
////
////			return new ResponseEntity<>("Maitinimo įstaiga sukurta sėkmingai", HttpStatus.OK);
////		}
////
////	}
//
//	/**
//	 *
//	 * Delete Meniu entity with specified id
//	 *
//	 * @param id
//	 * @return message if entity was deleted or if it does not exist in the database
//	 */
//	@DeleteMapping("/admin/deleteMeniu/{id}")
//	@ApiOperation(value = "Delete meniu by ID")
//	public ResponseEntity<String> deleteMeniu(
//			@ApiParam(value = "Meniu id", required = true) @PathVariable String id) {
//
//
//		return meniuService.deleteMeniu(id);
//	}
//
//	//@Secured({ "ROLE_ADMIN" })
//	@PutMapping("/admin/updateMeniu/{id}")
//	@ApiOperation(value = "Update meniu by ID")
//	public ResponseEntity<String> updateMeniu(
//			@ApiParam(value = "Meniu", required = true) @Valid @RequestBody UzsakymasDTO updated,
//			@PathVariable String id) {
//
//		if (meniuService.findById(id) == null) {
//
//			LOG.warn("Meniu tokiu kodu [{}] nėra", id);
//
//			return new ResponseEntity<>("Meniu su tokiu įstaigos kodu nerasta", HttpStatus.NOT_FOUND);
//
//		} else {
//
//			meniuService.updateMeniu(id, updated);
//
//			LOG.info("** Usercontroller: atnaujinamas meniu ID [{}] **", id);
//
//
//			return new ResponseEntity<>("Meniu duomenys atnaujinti sėkmingai", HttpStatus.OK);
//		}
//
//	}
//



	@RequestMapping(path = "/getAllUzsakymai", method = RequestMethod.GET)
	public List<UzsakymasDTO> grazintiUzsakymus() {
		return uzsakymoService.findAllUzsakymai();
	}

	@RequestMapping(path = "/{username}", method = RequestMethod.GET)
	public UzsakymasDTO getOneUzsakyma(@PathVariable final String username) {
		return uzsakymoService.getOneUzsakyma(username);
	}
	
	@RequestMapping(path = "/count/{username}", method = RequestMethod.GET)
	public Integer countUzsakymus(@PathVariable final String username) {
		return uzsakymoService.countUzsakymus(username);
	}
	
//	@RequestMapping(path = "/sumPrice/{username}", method = RequestMethod.GET)
//	public Integer sumProductsPrice(@PathVariable final String username) {
//		return cartServisas.sumProductsPrice(username);
//	}

	@RequestMapping(path = "/createUzsakyma",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String createUzsakyma(@RequestBody final UzsakymasDTO uzsakymasDTO) {
		uzsakymoService.createUzsakyma(uzsakymasDTO);
		return "Užsakymas buvo sukurtas";
	}

	@RequestMapping(path = "/delete/{username}/", method = RequestMethod.DELETE)
	public String deleteUzsakyma(@PathVariable final String username) {
		uzsakymoService.deleteUzsakyma(username);
		return "Cartas buvo ištrintas";
	}

	@RequestMapping(path = "/update/{username}", method = RequestMethod.PUT)
	public void updateUzsakyma(@PathVariable final String username, @RequestBody final UzsakymasDTO uzsakyma) {
		uzsakymoService.updateUzsakyma(username, uzsakyma);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/add/{patiekaloId}/{username}")
	public void addToUzsakyma(@PathVariable final String patiekaloId, @PathVariable final String username) {
		uzsakymoService.addToUzsakyma(patiekaloId, username);

	}
	@RequestMapping(method = RequestMethod.DELETE, path = "remove/{pavadinimas}/{username}")
	public void removeFromUzsakymo(@PathVariable final String pavadinimas,  @PathVariable final String username) {
		uzsakymoService.removeFromUzsakymo(pavadinimas, username);
		
	}
	
	
	
	
	public UzsakymasService getUzsakymoService() {
		return uzsakymoService;
	}



	public void setUzsakymoService(UzsakymasService uzsakymoService) {
		this.uzsakymoService = uzsakymoService;
	}



}
