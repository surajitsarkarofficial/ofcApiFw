package dto.submodules.globers;

import java.util.List;

public class PositionsObjects {
	private String id;
	private String name;
	private List<String> subFilterList;
	private String candidateId;
	
	public PositionsObjects() {
	}
	
	/**
	 * @param id
	 * @param name
	 */
	public PositionsObjects(String id, String name) {
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
	 * @param name
	 */
	public PositionsObjects(String name) {
		super();
		this.name = name;
	}

	/**
	 * @return the subFilterList
	 */
	public List<String> getSubFilterList() {
		return subFilterList;
	}

	/**
	 * @param subFilterList the subFilterList to set
	 */
	public void setSubFilterList(List<String> subFilterList) {
		this.subFilterList = subFilterList;
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
}
