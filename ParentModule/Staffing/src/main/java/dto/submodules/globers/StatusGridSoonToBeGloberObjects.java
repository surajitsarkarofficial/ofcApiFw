package dto.submodules.globers;

import java.util.ArrayList;

public class StatusGridSoonToBeGloberObjects {
	private String candidateId;
	public ArrayList<Statuses> statuses;
	/**
	 * @param candidateId
	 */
	public StatusGridSoonToBeGloberObjects(String candidateId) {
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
	 * @return the statuses
	 */
	public ArrayList<Statuses> getStatuses() {
		return statuses;
	}
	/**
	 * @param statuses the statuses to set
	 */
	public void setStatuses(ArrayList<Statuses> statuses) {
		this.statuses = statuses;
	}
	/**
	 * @param candidateId
	 * @param statuses
	 */
	public StatusGridSoonToBeGloberObjects(String candidateId, ArrayList<Statuses> statuses) {
		super();
		this.candidateId = candidateId;
		this.statuses = statuses;
	}
}

