package it.akademija.meniu;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import it.akademija.maitinimoIstaiga.MaitinimoIstaiga;
import it.akademija.meal.Meal;

@Entity
public class Meniu {

	@Id
	@Column(name = "meniu_id")
	private String id;

	@Column(name = "name", unique = false)
	@NotBlank(message = "Meniu pavadinimas privalomas")
	private String meniuName;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH })
	@JoinColumn(name = "maitinimoIstaiga_id")
	private MaitinimoIstaiga maitinimoIstaiga;

	@OneToMany(mappedBy = "meniu", cascade = { CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE })
	private Set<Meal> meals = new HashSet<>();

	public void addMeal(Meal meal) {
		this.meals.add(meal);
		meal.setMeniu(this);
	}

	public Meniu() {

	}

	public Meniu(String id, @NotBlank(message = "Patiekalo pavadinimas privalomas") String meniuName, 
			MaitinimoIstaiga maitinimoIstaiga) {
		super();
		this.id = id;
		this.meniuName = meniuName;
		this.maitinimoIstaiga = maitinimoIstaiga;
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

	public void setMeals(Set<Meal> meals) {
		this.meals = meals;
	}

	public MaitinimoIstaiga getMaitinimoIstaiga() {
		return maitinimoIstaiga;
	}

	public void setMaitinimoIstaiga(MaitinimoIstaiga maitinimoIstaiga) {
		this.maitinimoIstaiga = maitinimoIstaiga;
	}

	public String getMeniuName() {
		return meniuName;
	}

	public void setMeniuName(String meniuName) {
		this.meniuName = meniuName;
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
		Meniu other = (Meniu) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}