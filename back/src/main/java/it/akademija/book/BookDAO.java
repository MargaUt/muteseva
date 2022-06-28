package it.akademija.book;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.akademija.category.Category;

public interface BookDAO extends JpaRepository<Book, String> {

	void deleteByBookName(String bookName);

	Book findByBookName(String bookName);
	
	List<Book> findAllByBookCategory( Category bookCategory);

//	@Query("SELECT new Meniu(k.id, k.kodas, k.pavadinimas, k.address, k.meniu) FROM Meniu k WHERE LOWER(k.pavadinimas) LIKE LOWER(concat('%', ?1,'%'))")
//	Page<Meniu> findByNameContainingIgnoreCase(String pavadinimas, Pageable pageable);
//
//	@Query("SELECT new Meniu(k.id, k.kodas, k.pavadinimas, k.address, k.meniu) FROM Meniu k")
//	Page<Meniu> findAllMeniu(Pageable pageable);

}
