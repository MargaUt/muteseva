package it.akademija.meniu;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.akademija.maitinimoIstaiga.MaitinimoIstaiga;

public interface MeniuDAO extends JpaRepository<Meniu, String> {

	void deleteByMeniuName(String meniuName);

	Meniu findByMeniuName(String meniuName);
	
	List<Meniu> findAllByMaitinimoIstaiga( MaitinimoIstaiga istaiga);

//	@Query("SELECT new Meniu(k.id, k.kodas, k.pavadinimas, k.address, k.meniu) FROM Meniu k WHERE LOWER(k.pavadinimas) LIKE LOWER(concat('%', ?1,'%'))")
//	Page<Meniu> findByNameContainingIgnoreCase(String pavadinimas, Pageable pageable);
//
//	@Query("SELECT new Meniu(k.id, k.kodas, k.pavadinimas, k.address, k.meniu) FROM Meniu k")
//	Page<Meniu> findAllMeniu(Pageable pageable);

}
