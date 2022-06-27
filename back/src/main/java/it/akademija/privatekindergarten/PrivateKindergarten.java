package it.akademija.privatekindergarten;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
public class PrivateKindergarten {

	@Id
	@Column(name = "private_kindergarten_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	@Pattern(regexp = "^(?!\\s*$)[0-9\\s]{2,20}$|", message = "Įstaigos kodas turi būti sudarytas iš 2-20 skaitmenų")
	private String kindergartenCode;

	@NotBlank(message = "Pavadinimas privalomas")
	@Column
	private String kindergartenName;

	@Column
	@NotBlank(message = "Adresas privalomas")
	private String kindergartenAddress;

	@NotBlank(message = "Telefono numeris privalomas")
	@Pattern(regexp = "^\\+(?!\\s*$)[0-9\\s]{4,19}$|")
	@Column
	private String kindergartenPhone;

	@Email
	@NotBlank(message = "El. paštas privalomas!")
	@Column
	private String kindergartenEmail;

	@NotBlank(message = "Banko pavadinimas privalomas")
	@Column
	private String kindergartenBankName;

	@NotBlank(message = "Sąskaitos numeris privalomas")
	@Column
	@Pattern(regexp = "[L]{1}[T]{1}[0-9]{2}-[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}", message = "Sąskaitos numeris turi būti sudarytas iš dviženklio šalies kodo ir 18 skaitmenų")
	private String kindergartenAccountNumber;

	@NotBlank(message = "Banko kodas privalomas")
	@Column
	@Pattern(regexp = "^(?!\\s*$)[0-9\\s]{5}$|", message = "Įstaigos kodas turi būti sudarytas iš 5 skaitmenų")
	private String kindergartenBankCode;

	public PrivateKindergarten() {

	}

	public PrivateKindergarten(
			@Pattern(regexp = "^(?!\\s*$)[0-9\\s]{2,20}$|", message = "Įstaigos kodas turi būti sudarytas iš 2-20 skaitmenų") String kindergartenCode,
			@NotBlank(message = "Pavadinimas privalomas") String kindergartenName,
			@NotBlank(message = "Adresas privalomas") String kindergartenAddress,
			@NotBlank(message = "Telefono numeris privalomas") @Pattern(regexp = "^\\+(?!\\s*$)[0-9\\s]{4,19}$|") String kindergartenPhone,
			@Email @NotBlank(message = "El. paštas privalomas!") String kindergartenEmail,
			@NotBlank(message = "Banko pavadinimas privalomas") String kindergartenBankName,
			@NotBlank(message = "Sąskaitos numeris privalomas") @Pattern(regexp = "[L]{1}[T]{1}[0-9]{2}-[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}", message = "Sąskaitos numeris turi būti sudarytas iš dviženklio šalies kodo ir 18 skaitmenų") String kindergartenAccountNumber,
			@NotBlank(message = "Banko kodas privalomas") @Pattern(regexp = "^(?!\\s*$)[0-9\\s]{5}$|", message = "Įstaigos kodas turi būti sudarytas iš 5 skaitmenų") String kindergartenBankCode) {
		super();
		this.kindergartenCode = kindergartenCode;
		this.kindergartenName = kindergartenName;
		this.kindergartenAddress = kindergartenAddress;
		this.kindergartenPhone = kindergartenPhone;
		this.kindergartenEmail = kindergartenEmail;
		this.kindergartenBankName = kindergartenBankName;
		this.kindergartenAccountNumber = kindergartenAccountNumber;
		this.kindergartenBankCode = kindergartenBankCode;
	}

	public Long getId() {
		return id;
	}

	public String getKindergartenCode() {
		return kindergartenCode;
	}

	public void setKindergartenCode(String kindergartenCode) {
		this.kindergartenCode = kindergartenCode;
	}

	public String getKindergartenName() {
		return kindergartenName;
	}

	public void setKindergartenName(String kindergartenName) {
		this.kindergartenName = kindergartenName;
	}

	public String getKindergartenAddress() {
		return kindergartenAddress;
	}

	public void setKindergartenAddress(String kindergartenAddress) {
		this.kindergartenAddress = kindergartenAddress;
	}

	public String getKindergartenPhone() {
		return kindergartenPhone;
	}

	public void setKindergartenPhone(String kindergartenPhone) {
		this.kindergartenPhone = kindergartenPhone;
	}

	public String getKindergartenEmail() {
		return kindergartenEmail;
	}

	public void setKindergartenEmail(String kindergartenEmail) {
		this.kindergartenEmail = kindergartenEmail;
	}

	public String getKindergartenBankName() {
		return kindergartenBankName;
	}

	public void setKindergartenBankName(String kindergartenBankName) {
		this.kindergartenBankName = kindergartenBankName;
	}

	public String getKindergartenAccountNumber() {
		return kindergartenAccountNumber;
	}

	public void setKindergartenAccountNumber(String kindergartenAccountNumber) {
		this.kindergartenAccountNumber = kindergartenAccountNumber;
	}

	public String getKindergartenBankCode() {
		return kindergartenBankCode;
	}

	public void setKindergartenBankCode(String kindergartenBankCode) {
		this.kindergartenBankCode = kindergartenBankCode;
	}

}
