package com.disid.proofs.middleware.people.domain;

/**
 * = PersonFromClient
 *
 * DTO that represents a Person in the client side
 *
 */
public class PersonFromClient {

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
	private String age;

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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	/**
	 * Empty constructor
	 */
	public PersonFromClient() {

	}

	/**
	 * Constructor that receives all the parameters
	 * 
	 * @param id
	 * @param version
	 * @param name
	 * @param age
	 */
	public PersonFromClient(Long id, Integer version, String name, String age) {
		super();
		this.id = id;
		this.version = version;
		this.name = name;
		this.age = age;
	}

	/**
	 * Constructor that receives all the parameters
	 * 
	 * @param id
	 * @param version
	 * @param name
	 * @param age
	 */
	public PersonFromClient(PersonFromServer personFromServer) {
		super();
		this.id = personFromServer.getId();
		this.version = personFromServer.getVersion();
		this.name = personFromServer.getName();
		this.age = personFromServer.getAge();
	}

}
