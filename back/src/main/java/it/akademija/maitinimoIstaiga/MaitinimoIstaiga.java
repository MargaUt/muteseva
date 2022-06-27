package it.akademija.maitinimoIstaiga;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;

import it.akademija.meniu.Meniu;

@Entity
public class MaitinimoIstaiga {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long id;
	
	@Id
	@Column(name = "maitinimoIstaigos_id")
	// @Pattern(regexp = "^(?!\\s*$)[0-9\\s]{9}$|", message = "Įstaigos kodas turi
	// būti sudarytas iš 9 skaitmenų")
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(name = "kodas", unique = true)
	@NotBlank(message = "Maitinimo įstaigos kodas privalomas")
	private String kodas;

	@NotBlank(message = "Pavadinimas privalomas")
	@Pattern(regexp = "\\S[\\s\\S]{2,49}")
	@Column(name = "pavadinimas", unique = true)
	private String pavadinimas;

	@Column
	@NotBlank(message = "Adresas privalomas")
	private String address;

	@OneToMany(mappedBy = "maitinimoIstaiga", cascade = { CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE })
	private Set<Meniu> istaigosMeniu = new HashSet<>();

	public MaitinimoIstaiga() {

	}

	public MaitinimoIstaiga(
			@NotBlank(message = "Maitinimo įstaigos kodas privalomas") String kodas,
			@NotBlank(message = "Pavadinimas privalomas") @Pattern(regexp = "\\S[\\s\\S]{2,49}") String pavadinimas,
			@NotBlank(message = "Adresas privalomas") String address) {
		super();
		this.kodas = kodas;
		this.pavadinimas = pavadinimas;
		this.address = address;
	}

//	public MaitinimoIstaiga(
//			//String id,
//			@NotBlank(message = "Maitinimo įstaigos kodas privalomas") String kodas,
//			@NotBlank(message = "Pavadinimas privalomas") @Pattern(regexp = "\\S[\\s\\S]{2,49}") String pavadinimas,
//			@NotBlank(message = "Adresas privalomas") String address, Set<Meniu> istaigosMeniu) {
//		super();
//		//this.id = id;
//		this.kodas = kodas;
//		this.pavadinimas = pavadinimas;
//		this.address = address;
//		this.istaigosMeniu = istaigosMeniu;
//	}
	
//	public MaitinimoIstaiga(String id, @NotBlank(message = "Maitinimo įstaigos kodas privalomas") String kodas,
//			@NotBlank(message = "Pavadinimas privalomas") @Pattern(regexp = "\\S[\\s\\S]{2,49}") String pavadinimas,
//			@NotBlank(message = "Adresas privalomas") String address
//			//Set<Meniu> istaigosMeniu
//			) {
//		super();
//		this.id = id;
//		this.kodas = kodas;
//		this.pavadinimas = pavadinimas;
//		this.address = address;
//		//this.istaigosMeniu = istaigosMeniu;
//	}

//
//	public MaitinimoIstaiga(
//			@NotBlank(message = "Maitinimo įstaigos kodas privalomas") String kodas,
//			@NotBlank(message = "Pavadinimas privalomas") @Pattern(regexp = "\\S[\\s\\S]{2,49}") String pavadinimas,
//			@NotBlank(message = "Adresas privalomas") String address, Set<Meniu> istaigosMeniu) {
//		super();
//		this.kodas = kodas;
//		this.pavadinimas = pavadinimas;
//		this.address = address;
//		this.istaigosMeniu = istaigosMeniu;
//	}


	public String getId() {
		return id;
	}

//	public void setId(String id) {
//		this.id = id;
//	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getKodas() {
		return kodas;
	}

	public void setKodas(String kodas) {
		this.kodas = kodas;
	}

	public String getPavadinimas() {
		return pavadinimas;
	}

	public void setPavadinimas(String pavadinimas) {
		this.pavadinimas = pavadinimas;
	}

	public Set<Meniu> getIstaigosMeniu() {
		return istaigosMeniu;
	}

	public void setIstaigosMeniu(Set<Meniu> istaigosMeniu) {
		this.istaigosMeniu = istaigosMeniu;
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
		MaitinimoIstaiga other = (MaitinimoIstaiga) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
