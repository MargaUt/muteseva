package it.akademija.compensationapplication;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import it.akademija.privatekindergarten.PrivateKindergarten;
import it.akademija.user.User;

@Entity
public class CompensationApplication {

	@Id
	@Column(name = "application_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "date_of_submition", columnDefinition = "DATE")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate submitedAt;

	@NotEmpty(message = "Vardas privalomas!")
	@Size(min = 2, max = 70)
	@Pattern(regexp = "^\\p{L}+(( )+(?:\\p{L}+))*$")
	private String childName;

	@NotEmpty(message = "Pavardė privaloma!")
	@Size(min = 2, max = 70)
	@Pattern(regexp = "^\\p{L}+((-)+(?:\\p{L}+))*$")
	private String childSurname;

	@NotEmpty(message = "Vaiko asmens kodas privalomas!")
	@Pattern(regexp = "^(?!\\s*$)[0-9\\s]{11}$|", message = "Asmens kodą turi sudaryti 11 skaitmenų")
	private String childPersonalCode;

	@NotNull(message = "Vaiko gimimo data privaloma!")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthdate;

	@NotNull(message = "Darželio kodas privalomas!")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "private_kindergarten_id")
	private PrivateKindergarten privateKindergarten;

	@NotNull(message = "Privaloma nurodyti vaiko atstovą!")
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH })
	@JoinColumn(name = "user_id")
	private User mainGuardian;

	public CompensationApplication() {

	}

	public CompensationApplication(
			@NotEmpty(message = "Vardas privalomas!") @Size(min = 2, max = 70) @Pattern(regexp = "^\\p{L}+(?: \\p{L}+)*$") String childName,
			@NotEmpty(message = "Pavardė privaloma!") @Size(min = 2, max = 70) @Pattern(regexp = "^\\p{L}+(?: \\p{L}+)*$") String childSurname,
			@NotEmpty(message = "Vaiko asmens kodas privalomas!") @Pattern(regexp = "^(?!\\s*$)[0-9\\s]{11}$|", message = "Asmens kodą turi sudaryti 11 skaitmenų") String childPersonalCode,
			@NotNull(message = "Vaiko gimimo data privaloma!") LocalDate birthdate,
			@NotNull(message = "Darželio kodas privalomas!") PrivateKindergarten privateKindergarten,
			@NotNull(message = "Privaloma nurodyti vaiko atstovą!") User mainGuardian) {
		super();
		this.childName = childName;
		this.childSurname = childSurname;
		this.childPersonalCode = childPersonalCode;
		this.birthdate = birthdate;
		this.privateKindergarten = privateKindergarten;
		this.mainGuardian = mainGuardian;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getSubmitedAt() {
		return submitedAt;
	}

	public void setSubmitedAt() {
		this.submitedAt = LocalDate.now();
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

	public PrivateKindergarten getPrivateKindergarten() {
		return privateKindergarten;
	}

	public void setPrivateKindergarten(PrivateKindergarten privateKindergarten) {
		this.privateKindergarten = privateKindergarten;
	}

	public User getMainGuardian() {
		return mainGuardian;
	}

	public void setMainGuardian(User mainGuardian) {
		this.mainGuardian = mainGuardian;
	}

}
