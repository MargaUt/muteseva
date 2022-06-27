package it.akademija.maitinimoIstaiga;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MaitinimoIstaigaDAO extends JpaRepository<MaitinimoIstaiga, String> {
	

	void deleteByPavadinimas(String pavadinimas);

	MaitinimoIstaiga findByPavadinimas(String pavadinimas);

	//@Query("SELECT new MaitinimoIstaiga(k.id, k.kodas, k.pavadinimas, k.address, SELECT * FROM Meniu m WHERE m.maitinimoIstaiga = k) FROM MaitinimoIstaiga k WHERE LOWER(k.pavadinimas) LIKE LOWER(concat('%', ?1,'%'))")
	@Query("SELECT k FROM MaitinimoIstaiga k LEFT OUTER JOIN k.istaigosMeniu WHERE LOWER(k.pavadinimas) LIKE LOWER(concat('%', ?1,'%'))")
	Page<MaitinimoIstaiga> findByNameContainingIgnoreCase(String pavadinimas, Pageable pageable);

	@Query("SELECT k FROM MaitinimoIstaiga k LEFT OUTER JOIN k.istaigosMeniu")
	Page<MaitinimoIstaiga> findAllMaitinimoIstaiga(Pageable pageable);

}
