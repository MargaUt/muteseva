package it.akademija.privatekindergarten;

public class PrivateKindergartenDTO {

	private String kindergartenCode;

	private String kindergartenName;

	private String kindergartenAddress;

	private String kindergartenPhone;

	private String kindergartenEmail;

	private String kindergartenBankName;

	private String kindergartenAccountNumber;

	private String kindergartenBankCode;

	public PrivateKindergartenDTO(String kindergartenCode, String kindergartenName, String kindergartenAddress,
			String kindergartenPhone, String kindergartenEmail, String kindergartenBankName,
			String kindergartenAccountNumber, String kindergartenBankCode) {
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

	public PrivateKindergartenDTO(PrivateKindergarten privateKindergarten) {
		super();
		this.kindergartenCode = privateKindergarten.getKindergartenBankCode();
		this.kindergartenName = privateKindergarten.getKindergartenName();
		this.kindergartenAddress = privateKindergarten.getKindergartenAddress();
		this.kindergartenPhone = privateKindergarten.getKindergartenPhone();
		this.kindergartenEmail = privateKindergarten.getKindergartenEmail();
		this.kindergartenBankName = privateKindergarten.getKindergartenBankName();
		this.kindergartenAccountNumber = privateKindergarten.getKindergartenAccountNumber();
		this.kindergartenBankCode = privateKindergarten.getKindergartenBankCode();
	}

	/**
	 * Create PrivateKindergartenDTO from PrivateKindergarten
	 *
	 * @param PrivateKindergarten
	 * @return
	 */

	public static PrivateKindergartenDTO from(PrivateKindergarten privateKindergarten) {
		return new PrivateKindergartenDTO(privateKindergarten);
	}

	/**
	 * Convert to PrivateKindergarten
	 *
	 * @return
	 */
	public PrivateKindergarten toPrivateKindergarten() {
		return new PrivateKindergarten(kindergartenCode, kindergartenName, kindergartenAddress, kindergartenPhone,
				kindergartenEmail, kindergartenBankName, kindergartenAccountNumber, kindergartenBankCode);
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
