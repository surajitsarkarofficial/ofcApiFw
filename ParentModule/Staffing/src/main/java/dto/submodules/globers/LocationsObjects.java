package dto.submodules.globers;

import java.util.ArrayList;

public class LocationsObjects {

	private String id;
	private String name;
	private String email;
	private String location;
	private String candidateId;
	private ArrayList<String> subFilterList;

	public LocationsObjects() {

	}

	/**
	 * @param id
	 * @param name
	 */
	public LocationsObjects(String id, String name) {
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
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @param email
	 * @param location
	 * @param candidateId
	 */
	public LocationsObjects(String email, String location, String candidateId) {
		super();
		this.email = email;
		this.location = location;
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
	 * @param candidateId
	 */
	public LocationsObjects(String candidateId) {
		super();
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
