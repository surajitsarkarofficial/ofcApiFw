package dto.submodules.globers;

import java.util.ArrayList;

public class SeniorityFilterSoonToBeGloberObjects {

	private String id;
	private String name;
	private String candidateId;
	private ArrayList<String> subFilterList;
	
	/**
	 * @param candidateId
	 */
	public SeniorityFilterSoonToBeGloberObjects(String candidateId) {
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

	/**
	 * 
	 */
	public SeniorityFilterSoonToBeGloberObjects() {
		super();
	}
}
