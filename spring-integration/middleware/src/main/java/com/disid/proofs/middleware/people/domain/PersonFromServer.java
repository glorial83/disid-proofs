package com.disid.proofs.middleware.people.domain;

/**
 * = Person from client
 *
 * DTO that represents a Person in the Server side
 *
 */
public class PersonFromServer {

	/**
	 * TODO Auto-generated attribute documentation
	 *
	 */
	private Long id;

	/**
	 * TODO Auto-generated attribute documentation
	 *
	 */
	private Integer version;

	/**
	 * TODO Auto-generated attribute documentation
	 *
	 */
	private String name;

	/**
	 * TODO Auto-generated attribute documentation
	 *
	 */
	private String lastName;

	/**
	 * TODO Auto-generated attribute documentation
	 *
	 */
	private String age;

	/**
	 * TODO Auto-generated attribute documentation
	 *
	 */
	private String nif;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}
}
