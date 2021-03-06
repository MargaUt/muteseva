package it.akademija.user;

import it.akademija.role.Role;

public class UserInfo {

	private Role role;
	private String name;
	private String surname;
	private String personalCode;
	private String address;
	private String phone;
	private String email;
	private String username;

	public UserInfo() {

	}

	public UserInfo(Role role, String username) {
		this.role = role;
		this.username = username;

	}

	public UserInfo(Role role, String name, String surname, String username, String email) {
		this.role = role;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.email = email;
	}

	public UserInfo(Role role, String name, String surname, String personalCode, String address, String phone,
			String email, String username) {
		super();
		this.role = role;
		this.name = name;
		this.surname = surname;
		this.address = address;
		this.personalCode = personalCode;
		this.phone = phone;
		this.email = email;
		this.username = username;
	}

	public UserInfo(User user) {
		super();
		this.role = user.getRole();
		this.name = user.getName();
		this.surname = user.getSurname();
		this.address = user.getParentDetails().getAddress();
		this.personalCode = user.getPersonalCode();
		this.phone = user.getPhone();
		this.email = user.getEmail();
		this.username = user.getUsername();
	}

	/**
	 * Create UserInfo from User
	 *
	 * @param User
	 * @return
	 */

	public static UserInfo from(User user) {
		return new UserInfo(user);
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPersonalCode() {
		return personalCode;
	}

	public void setPersonalCode(String personalCode) {
		this.personalCode = personalCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}