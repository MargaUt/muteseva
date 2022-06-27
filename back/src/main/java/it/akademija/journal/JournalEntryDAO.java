package it.akademija.journal;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JournalEntryDAO extends JpaRepository<JournalEntry, Long>{

	@Query("SELECT j FROM JournalEntry j")
	Page<JournalEntry> getAllJournalEntries(Pageable pageable);

	List<JournalEntry> findByUserName(String username);
}
