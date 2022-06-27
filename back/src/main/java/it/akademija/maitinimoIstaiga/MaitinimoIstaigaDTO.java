package it.akademija.maitinimoIstaiga;

import java.util.Set;
import java.util.stream.Collectors;

import it.akademija.meniu.MeniuDTO;

public class MaitinimoIstaigaDTO {
	
	private String id;

	private String kodas;

	private String address;

	private String pavadinimas;

	private Set<MeniuDTO> meniuDto;

	public MaitinimoIstaigaDTO() {

	}

	public MaitinimoIstaigaDTO(
			//String id,
			String kodas, String address, String pavadinimas) {
		super();
		//this.id = id;
		this.kodas = kodas;
		this.address = address;
		this.pavadinimas = pavadinimas;
	}

	public MaitinimoIstaigaDTO(MaitinimoIstaiga maitinimoIstaiga) {
		super();
		this.id  = maitinimoIstaiga.getId();
		this.kodas = maitinimoIstaiga.getKodas();
		this.pavadinimas = maitinimoIstaiga.getPavadinimas();
		this.address = maitinimoIstaiga.getAddress();
		this.meniuDto = maitinimoIstaiga.getIstaigosMeniu().stream().map(MeniuDTO::from).collect(Collectors.toSet());

	}

	/**
	 * Create KindergartenDTO from Kindergarten
	 *
	 * @param Meal
	 * @return
	 */

	public static MaitinimoIstaigaDTO from(MaitinimoIstaiga maitinimoIstaiga) {
		return new MaitinimoIstaigaDTO(maitinimoIstaiga);
	}

	public String getKodas() {
		return kodas;
	}

	public void setKodas(String kodas) {
		this.kodas = kodas;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPavadinimas() {
		return pavadinimas;
	}

	public void setPavadinimas(String pavadinimas) {
		this.pavadinimas = pavadinimas;
	}

	public Set<MeniuDTO> getMeniuDto() {
		return meniuDto;
	}

	public void setMeniuDto(Set<MeniuDTO> meniuDto) {
		this.meniuDto = meniuDto;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
