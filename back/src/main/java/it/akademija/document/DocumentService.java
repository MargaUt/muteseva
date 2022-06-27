package it.akademija.document;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import it.akademija.user.User;
import it.akademija.user.UserDAO;

@Service
public class DocumentService {

	@Autowired
	DocumentDAO documentDao;
	@Autowired
	UserDAO userDao;

	@Transactional
	public DocumentEntity getDocumentById(long id) {

		DocumentEntity doc = documentDao.getDocumentById(id);

		if (doc != null) {

			return doc;
		} else {

			return null;
		}
	}

	@Transactional
	public List<DocumentEntity> getDocumentsByUser(User user) {

		return documentDao.findAll().stream().filter(x -> x.getUploader() == user)
				.collect(Collectors.toList());
	}

	@Transactional
	public Boolean uploadDocument(MultipartFile file, String name) {

		if (file.getSize() <= 1024000 && file.getContentType().equals("application/pdf")) {
			var user = userDao.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

			try {
				DocumentEntity doc = new DocumentEntity(name, file.getContentType(), file.getBytes(), file.getSize(),
						user, LocalDate.now());
				documentDao.save(doc);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public void deleteDocument(long id) {
		documentDao.delete(getDocumentById(id));
	}

	/**
	 *
	 * Returns a page of Document View model with specified page number and page
	 * size
	 *
	 * @param pageable
	 * @return page from Document View model database
	 */

	@Transactional(readOnly = true)
	public Page<DocumentViewmodel> getDocumentViewmodelPage(Pageable pageable, String search) {
		if (search == null) {
			search = "";
		}
		return documentDao.orderdByDocumentId(search, pageable).map(DocumentViewmodel::from);
	}

}
