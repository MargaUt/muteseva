package it.akademija.category;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryDAO extends JpaRepository<Category, String> {
	

	void deleteByName(String name);

	Category findByName(String name);

	//@Query("SELECT new MaitinimoIstaiga(k.id, k.kodas, k.pavadinimas, k.address, SELECT * FROM Meniu m WHERE m.maitinimoIstaiga = k) FROM MaitinimoIstaiga k WHERE LOWER(k.pavadinimas) LIKE LOWER(concat('%', ?1,'%'))")
	@Query("SELECT k FROM Category k LEFT OUTER JOIN k.books WHERE LOWER(k.name) LIKE LOWER(concat('%', ?1,'%'))")
	Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);

	@Query("SELECT k FROM Category k LEFT OUTER JOIN k.books")
	Page<Category> findAllCategory(Pageable pageable);

}
