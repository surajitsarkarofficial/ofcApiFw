package dto.submodules.globers;

import java.util.ArrayList;

public class HandlersObjects {
	
	Handlers handler; 
	private String email;
	private String id;
	private String name;
	private String candidateId;
	private ArrayList<String> subFilterList;
	

	public HandlersObjects() {
	}
	
	/**
	 * @param id
	 * @param name
	 */
	public HandlersObjects(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the handler
	 */
	public Handlers getHandler() {
		return handler;
	}

	/**
	 * @param handler the handler to set
	 */
	public void setHandler(Handlers handler) {
		this.handler = handler;
	}

	/**
	 * @param handler
	 * @param email
	 * @param candidateId
	 */
	public HandlersObjects(Handlers handler, String email, String candidateId) {
		super();
		this.handler = handler;
		this.email = email;
		this.candidateId = candidateId;
	}

	/**
	 * @param candidateId
	 */
	public HandlersObjects(String candidateId) {
		super();
		this.candidateId = candidateId;
	}

	/**
	 * @return the candidateId
	 */
	public String getCandidateId() {
		return candidateId;
	}

	/**
	 * @param candidateId the candidateId to set
	 */
	public void setCandidateId(String candidateId) {
		this.candidateId = candidateId;
	}

	/**
	 * @return the subFilterList
	 */
	public ArrayList<String> getSubFilterList() {
		return subFilterList;
	}

	/**
	 * @param subFilterList the subFilterList to set
	 */
	public void setSubFilterList(ArrayList<String> subFilterList) {
		this.subFilterList = subFilterList;
	}
}
