package dto.submodules.globers;

import java.util.List;

public class StudiosObjects {
	private String email;
	private String studioId;
	private String studio;
	private String id;
	private String name;
	private String type;
	private String candidateId;
	private List<String> subFilterList;

	public StudiosObjects() {
		
	}

	/**
	 * @param id
	 * @param name
	 * @param type
	 */
	public StudiosObjects(String id, String name, String type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	public StudiosObjects(String name) {
		super();
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
	 * @return the studioId
	 */
	public String getStudioId() {
		return studioId;
	}

	/**
	 * @param studioId the studioId to set
	 */
	public void setStudioId(String studioId) {
		this.studioId = studioId;
	}

	/**
	 * @return the studio
	 */
	public String getStudio() {
		return studio;
	}

	/**
	 * @param studio the studio to set
	 */
	public void setStudio(String studio) {
		this.studio = studio;
	}

	/**
	 * @param email
	 * @param studioId
	 * @param studio
	 * @param candidateId
	 */
	public StudiosObjects(String email, String studioId, String studio, String candidateId) {
		super();
		this.email = email;
		this.studioId = studioId;
		this.studio = studio;
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
	public List<String> getSubFilterList() {
		return subFilterList;
	}

	/**
	 * @param subFilterList the subFilterList to set
	 */
	public void setSubFilterList(List<String> subFilterList) {
		this.subFilterList = subFilterList;
	}
}
