package it.akademija.meal;

public class MealDTO {
	private String id;

	private String name;
	
	private String description;
	
	private String meniuId;

	public MealDTO() {

	}

	public MealDTO(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
	}

	public MealDTO(Meal meal) {
		super();
		this.id = meal.getId();
		this.name = meal.getName();
		this.description = meal.getDescription();
		this.meniuId = meal.getMeniu().getId();
		

	}
	
	/**
	 * Create MealDTO from Meal
	 *
	 * @param Meniu
	 * @return
	 */

	public static MealDTO from(Meal meal) {
		return new MealDTO(meal);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMeniuId() {
		return meniuId;
	}

	public void setMeniuId(String meniuId) {
		this.meniuId = meniuId;
	}


	

}
