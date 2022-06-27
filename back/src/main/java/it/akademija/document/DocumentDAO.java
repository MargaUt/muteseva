package it.akademija.document;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.akademija.user.User;

public interface DocumentDAO extends JpaRepository<DocumentEntity, Long> {

	DocumentEntity getDocumentById(long id);

	void deleteByUploader(User uploader);

	@Query("SELECT d FROM DocumentEntity d LEFT JOIN d.uploader u WHERE LOWER(u.name ||' '|| u.surname) LIKE LOWER(concat('%', ?1,'%')) ORDER BY d.id DESC")
	Page<DocumentEntity> orderdByDocumentId(String search, Pageable pageable);

}
