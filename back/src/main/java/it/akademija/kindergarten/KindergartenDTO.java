package it.akademija.kindergarten;

public class KindergartenDTO {

	private String id;

	private String name;

	private String address;

	private String director;

	private String elderate;

	private int capacityAgeGroup2to3;

	private int capacityAgeGroup3to6;

	public KindergartenDTO() {

	}

	public KindergartenDTO(String id, String name, String address, String director, String elderate,
			int capacityAgeGroup2to3, int capacityAgeGroup3to6) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.director = director;
		this.elderate = elderate;
		this.capacityAgeGroup2to3 = capacityAgeGroup2to3;
		this.capacityAgeGroup3to6 = capacityAgeGroup3to6;
	}

	public KindergartenDTO(Kindergarten kindergarten) {
		super();
		this.id = kindergarten.getId();
		this.name = kindergarten.getName();
		this.address = kindergarten.getAddress();
		this.director = kindergarten.getDirector();
		this.elderate = kindergarten.getElderate();
		this.capacityAgeGroup2to3 = kindergarten.getCapacityAgeGroup2to3();
		this.capacityAgeGroup3to6 = kindergarten.getCapacityAgeGroup3to6();
	}

	/**
	 * Create KindergartenDTO from Kindergarten
	 *
	 * @param MaitinimoIstaiga
	 * @return
	 */

	public static KindergartenDTO from(Kindergarten kindergarten) {
		return new KindergartenDTO(kindergarten);
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

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getElderate() {
		return elderate;
	}

	public void setElderate(String elderate) {
		this.elderate = elderate;
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

}
