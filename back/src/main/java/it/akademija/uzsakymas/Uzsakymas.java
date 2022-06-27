package it.akademija.uzsakymas;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;

import it.akademija.meal.Meal;

@Entity
public class Uzsakymas {

	@Id
	@Column(name = "uzsakymo_id")
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(unique = true)
	private String username;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.DETACH })
	private Set<Meal> meals = new HashSet<>();

	public void addMeal(Meal meal) {
		this.meals.add(meal);
	}

	public Uzsakymas() {

	}

	public Uzsakymas(String username, Set<Meal> meals) {
		super();
		this.username = username;
		this.meals = meals;
	}
	
	
	public Uzsakymas(String username) {
		super();
		this.username = username;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<Meal> getMeals() {
		return meals;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setMeals(Set<Meal> meals) {
		this.meals = meals;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Uzsakymas other = (Uzsakymas) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
