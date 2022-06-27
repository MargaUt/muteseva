package it.akademija.compensationapplication;

import java.time.LocalDate;

import it.akademija.privatekindergarten.PrivateKindergartenDTO;
import it.akademija.user.UserDTO;
import it.akademija.user.UserInfo;

public class CompensationApplicationDTO {
	
	private long id;

	private String childName;

	private String childSurname;

	private LocalDate submitedAt;

	private String childPersonalCode;

	private LocalDate birthdate;

	private PrivateKindergartenDTO privateKindergarten;

	private UserInfo mainGuardian;

	private UserDTO mainGuardianSec;

	public CompensationApplicationDTO() {

	}

	public CompensationApplicationDTO(String childName, LocalDate submitedAt, String childSurname,
			String childPersonalCode, LocalDate birthdate, PrivateKindergartenDTO privateKindergarten,
			UserInfo mainGuardian) {
		super();
		this.childName = childName;
		this.childSurname = childSurname;
		this.submitedAt = submitedAt;
		this.childPersonalCode = childPersonalCode;
		this.birthdate = birthdate;
		this.privateKindergarten = privateKindergarten;
		this.mainGuardian = mainGuardian;
	}

	public CompensationApplicationDTO(String childName, LocalDate submitedAt, String childSurname,
			String childPersonalCode, LocalDate birthdate, PrivateKindergartenDTO privateKindergarten,
			UserDTO mainGuardianSec) {
		super();
		this.childName = childName;
		this.childSurname = childSurname;
		this.submitedAt = submitedAt;
		this.childPersonalCode = childPersonalCode;
		this.birthdate = birthdate;
		this.privateKindergarten = privateKindergarten;
		this.mainGuardianSec = mainGuardianSec;
	}

	public CompensationApplicationDTO(CompensationApplication compensationApplication) {
		super();
		this.id = compensationApplication.getId();
		this.childName = compensationApplication.getChildName();
		this.childSurname = compensationApplication.getChildSurname();
		this.childPersonalCode = compensationApplication.getChildPersonalCode();
		this.submitedAt = compensationApplication.getSubmitedAt();
		this.birthdate = compensationApplication.getBirthdate();
		this.privateKindergarten = privateKindergarten.from(compensationApplication.getPrivateKindergarten());
		this.mainGuardian = mainGuardian.from(compensationApplication.getMainGuardian());
	}

	/**
	 * Create CompensationApplicationDTO from CompensationApplication
	 *
	 * @param CompensationApplication
	 * @return
	 */

	public static CompensationApplicationDTO from(CompensationApplication compensationApplication) {
		return new CompensationApplicationDTO(compensationApplication);
	}

	public long getId() {
		return id;
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

	public String getChildPersonalCode() {
		return childPersonalCode;
	}

	public void setChildPersonalCode(String childPersonalCode) {
		this.childPersonalCode = childPersonalCode;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public PrivateKindergartenDTO getPrivateKindergarten() {
		return privateKindergarten;
	}

	public void setPrivateKindergarten(PrivateKindergartenDTO privateKindergarten) {
		this.privateKindergarten = privateKindergarten;
	}

	public UserInfo getMainGuardian() {
		return mainGuardian;
	}

	public void setMainGuardian(UserInfo mainGuardian) {
		this.mainGuardian = mainGuardian;
	}

	public LocalDate getSubmitedAt() {
		return submitedAt;
	}

	public void setSubmitedAt(LocalDate submitedAt) {
		this.submitedAt = submitedAt;
	}

	public UserDTO getMainGuardianSec() {
		return mainGuardianSec;
	}

	public void setMainGuardianSec(UserDTO mainGuardianSec) {
		this.mainGuardianSec = mainGuardianSec;
	}

}
