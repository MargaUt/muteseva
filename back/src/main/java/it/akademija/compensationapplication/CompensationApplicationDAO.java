package it.akademija.compensationapplication;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;

public interface CompensationApplicationDAO extends JpaRepository<CompensationApplication, Long> {

	boolean existsCompensationApplicationByChildPersonalCode(String childPersonalCode);

	@Query("SELECT new it.akademija.compensationapplication.CompensationApplicationInfoUser(a.id, a.childName, a.childSurname, a.submitedAt, a.privateKindergarten.kindergartenName, a.privateKindergarten.kindergartenAccountNumber) FROM CompensationApplication a WHERE a.mainGuardian.username=?1")
	Set<CompensationApplicationInfoUser> findAllUserCompensationApplications(String currentUsername);

	@Query("SELECT c FROM CompensationApplication c ORDER BY c.id DESC")
	Page<CompensationApplication> orderdByDocumentId(Pageable pageable);

	@Query("SELECT c FROM CompensationApplication c WHERE c.submitedAt=?1 ORDER BY c.id DESC")
	Page<CompensationApplication> findBySubmitedAt(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate submitedAt,
			Pageable pageable);

}
