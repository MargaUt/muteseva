package it.akademija.uzsakymas;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UzsakymasDAO extends JpaRepository<Uzsakymas, String> {

//	void deleteByMeniuName(String meniuName);
//
//	Uzsakymas findByMeniuName(String meniuName);
//	
//	List<Uzsakymas> findAllByMaitinimoIstaiga( MaitinimoIstaiga istaiga);
//
////	@Query("SELECT new Meniu(k.id, k.kodas, k.pavadinimas, k.address, k.meniu) FROM Meniu k WHERE LOWER(k.pavadinimas) LIKE LOWER(concat('%', ?1,'%'))")
////	Page<Meniu> findByNameContainingIgnoreCase(String pavadinimas, Pageable pageable);
////
////	@Query("SELECT new Meniu(k.id, k.kodas, k.pavadinimas, k.address, k.meniu) FROM Meniu k")
////	Page<Meniu> findAllMeniu(Pageable pageable);
	
	Uzsakymas findByUsername(String username);

	void deleteByUsername(String username);

	@Query("SELECT COUNT(p) FROM Uzsakymas c INNER JOIN c.meals p WHERE c.username = ?1")
	Integer countMeals(String username);
	
//	@Query("SELECT SUM(p.kaina) FROM Cart c INNER JOIN c.produktai p WHERE c.username = ?1")
//	Integer sumProductsPrice(String username);

}
