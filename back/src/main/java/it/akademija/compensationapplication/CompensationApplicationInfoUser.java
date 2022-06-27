package it.akademija.compensationapplication;

import java.time.LocalDate;

public class CompensationApplicationInfoUser {

	private Long id;

	private String childName;

	private String childSurname;

	private LocalDate submitedAt;

	private String kindergartenName;

	private String kindergartenAccountNumber;

	public CompensationApplicationInfoUser() {

	}

	public CompensationApplicationInfoUser(Long id, String childName, String childSurname, LocalDate submitedAt,
			String kindergartenName, String kindergartenAccountNumber) {
		super();
		this.id = id;
		this.childName = childName;
		this.childSurname = childSurname;
		this.submitedAt = submitedAt;
		this.kindergartenName = kindergartenName;
		this.kindergartenAccountNumber = kindergartenAccountNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

	public String getChildSurname() {
		return childSurname;
	}

	public void setChildSurname(String childSurname) {
		this.childSurname = childSurname;
	}

	public LocalDate getSubmitedAt() {
		return submitedAt;
	}

	public void setSubmitedAt(LocalDate submitedAt) {
		this.submitedAt = submitedAt;
	}

	public String getKindergartenName() {
		return kindergartenName;
	}

	public void setKindergartenName(String kindergartenName) {
		this.kindergartenName = kindergartenName;
	}

	public String getKindergartenAccountNumber() {
		return kindergartenAccountNumber;
	}

	public void setKindergartenAccountNumber(String kindergartenAccountNumber) {
		this.kindergartenAccountNumber = kindergartenAccountNumber;
	}
}
