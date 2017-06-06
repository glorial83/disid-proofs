package com.disid.proofs.middleware.tools.domain;

/**
 * = Tool
 *
 * TODO Auto-generated class documentation
 *
 */
public class ToolFromClient {

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
	private Integer size;

	/**
	 * Default empty constructor
	 */
	public ToolFromClient() {
		// Nothing to do here
	}

	/**
	 * Constructor that receives all the parameters
	 * 
	 * @param id
	 * @param version
	 * @param name
	 * @param size
	 */
	public ToolFromClient(Long id, Integer version, String name, Integer size) {
		super();
		this.id = id;
		this.version = version;
		this.name = name;
		this.size = size;
	}

	/**
	 * Constructor that receives an existing tool from the server side
	 * 
	 * @param toolFromServer
	 */
	public ToolFromClient(ToolFromServer toolFromServer) {
		super();
		this.id = toolFromServer.getId();
		this.version = toolFromServer.getVersion();
		this.name = toolFromServer.getName();
		this.size = toolFromServer.getSize();
	}

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

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

}
