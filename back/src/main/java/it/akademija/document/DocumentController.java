package it.akademija.document;

import java.util.ArrayList;
import java.util.List;
import java.net.URLDecoder;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.akademija.application.ApplicationController;
import it.akademija.journal.JournalService;
import it.akademija.journal.ObjectType;
import it.akademija.journal.OperationType;
import it.akademija.user.UserService;

@RestController
@Api(value = "Documents")
@RequestMapping(path = "/api/documents")
public class DocumentController {

	private static final Logger LOG = LoggerFactory.getLogger(ApplicationController.class);

	@Autowired
	DocumentService documentService;

	@Autowired
	UserService userService;

	@Autowired
	private JournalService journalService;

	@Secured({ "ROLE_MANAGER", "ROLE_USER" })
	@GetMapping(path = "/get/{id}")
	public byte[] getDocumentFileById(@ApiParam(value = "id") @PathVariable Long id) {

		journalService.newJournalEntry(OperationType.MEDICAL_RECORD_DOWNLOADED, id, ObjectType.MEDICAL_RECORD,
				"Atsisiųsta medicininė pažyma");

		if (documentService.getDocumentById(id) == null) {
			return null;
		}

		return documentService.getDocumentById(id).getData();
	}

	@Secured("ROLE_USER")
	@PostMapping(path = "/upload")
	public ResponseEntity<String> UploadDocument(@RequestParam("file") MultipartFile file,
			@RequestParam("name") String name) {

		if (documentService.uploadDocument(file, name)) {

			journalService.newJournalEntry(OperationType.MEDICAL_RECORD_SUBMITED, userService
					.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getUserId(),
					ObjectType.MEDICAL_RECORD, "Įkelta medicininė pažyma");

			return new ResponseEntity<>("Dokumentas buvo įkeltas sėkmingai", HttpStatus.CREATED);

		} else {

			LOG.warn("Įvyko klaida įkeliant dokumentą");
			return new ResponseEntity<>("Įvyko klaida", HttpStatus.BAD_REQUEST);
		}
	}

	@Secured({ "ROLE_MANAGER", "ROLE_USER" })
	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<String> deleteDocument(@ApiParam(value = "id") @PathVariable final long id) {

		documentService.deleteDocument(id);

		return new ResponseEntity<>("Dokumentas su tokiu id buvo ištrintas.", HttpStatus.OK);
	}

	@Secured({ "ROLE_USER" })
	@GetMapping(path = "/documents")
	public List<DocumentViewmodel> getLoggedUserDocuments() {

		List<DocumentEntity> docEntityList = documentService.getDocumentsByUser(
				userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

		List<DocumentViewmodel> docViewmodelList = new ArrayList<>();

		for (DocumentEntity doc : docEntityList) {

			docViewmodelList.add(new DocumentViewmodel(doc.getId(), doc.getName(), doc.getUploadDate(),
					doc.getUploader().getName() + " " + doc.getUploader().getSurname()));
		}
		return docViewmodelList;
	}

	/**
	 * Get specified Document information page
	 *
	 * @return page of documentViewmodel
	 */
	@Secured({ "ROLE_MANAGER" })
	@GetMapping("/documents/page")
	@ApiOperation(value = "Get document pages")
	public ResponseEntity<Page<DocumentViewmodel>> getDocumentViewmodelPage(

			@RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam(value = "search", required = false) String search) {

		Sort.Order order = new Sort.Order(Sort.Direction.ASC, "uploadDate");
		String decodedSearch = "";

		Pageable pageable = PageRequest.of(page, size, Sort.by(order));
		if (search != null) {
			try {
				search = search.replaceAll("%", "%25");
				decodedSearch = URLDecoder.decode(search, "UTF-8");
				decodedSearch = decodedSearch.replaceAll("%", "%75[%]%");
				decodedSearch = decodedSearch.replaceAll("_", "[_]");

				return new ResponseEntity<>(documentService.getDocumentViewmodelPage(pageable, decodedSearch),
						HttpStatus.OK);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();

			}
		}
		return new ResponseEntity<>(documentService.getDocumentViewmodelPage(pageable, null), HttpStatus.OK);
	}

}
