package it.akademija.kindergarten;

/**
 *
 * Kindergarten data transfer class to return partial info from the database
 *
 */
public class KindergartenInfo {

	private String id;
	private String name;
	private String address;
	private String elderate;
	private String director;
	private int capacityAgeGroup2to3;
	private int capacityAgeGroup3to6;
	private int placesTakenAgeGroup2to3;
	private int placesTakenAgeGroup3to6;

	public KindergartenInfo() {
	}

	public KindergartenInfo(String id, String name, String address, String elderate, String director,
			int capacityAgeGroup2to3, int capacityAgeGroup3to6, int placesTakenAgeGroup2to3,
			int placesTakenAgeGroup3to6) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.elderate = elderate;
		this.director = director;
		this.capacityAgeGroup2to3 = capacityAgeGroup2to3;
		this.capacityAgeGroup3to6 = capacityAgeGroup3to6;
		this.placesTakenAgeGroup2to3 = placesTakenAgeGroup2to3;
		this.placesTakenAgeGroup3to6 = placesTakenAgeGroup3to6;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getElderate() {
		return elderate;
	}

	public void setElderate(String elderate) {
		this.elderate = elderate;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getCapacityAgeGroup2to3() {
		return capacityAgeGroup2to3;
	}

	public void setCapacityAgeGroup2to3(int capacityAgeGroup2to3) {
		this.capacityAgeGroup2to3 = capacityAgeGroup2to3;
	}

	public int getCapacityAgeGroup3to6() {
		return capacityAgeGroup3to6;
	}

	public void setCapacityAgeGroup3to6(int capacityAgeGroup3to6) {
		this.capacityAgeGroup3to6 = capacityAgeGroup3to6;
	}

	public int getPlacesTakenAgeGroup2to3() {
		return placesTakenAgeGroup2to3;
	}

	public void setPlacesTakenAgeGroup2to3(int placesTakenAgeGroup2to3) {
		this.placesTakenAgeGroup2to3 = placesTakenAgeGroup2to3;
	}

	public int getPlacesTakenAgeGroup3to6() {
		return placesTakenAgeGroup3to6;
	}

	public void setPlacesTakenAgeGroup3to6(int placesTakenAgeGroup3to6) {
		this.placesTakenAgeGroup3to6 = placesTakenAgeGroup3to6;
	}
}