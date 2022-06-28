package it.akademija.meal;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import it.akademija.book.Book;
import it.akademija.category.Category;

public interface MealDAO extends JpaRepository<Meal, String> {

	void deleteByName(String name);

	Meal findByName(String name);

	@Query("SELECT k FROM Meal k WHERE LOWER(k.name) LIKE LOWER(concat('%', ?1,'%'))")
	Page<Meal> findByNameContainingIgnoreCase(String name, Pageable pageable);

	@Query("SELECT k FROM Meal k")
	Page<Meal> findAllIstaigas(Pageable pageable);
	
	List<Meal> findAllByBook( Book book);

}
