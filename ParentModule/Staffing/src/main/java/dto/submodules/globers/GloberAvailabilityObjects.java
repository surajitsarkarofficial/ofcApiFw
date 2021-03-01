package dto.submodules.globers;

import java.util.ArrayList;

public class GloberAvailabilityObjects {

	private String globerId;
	private String email;
	private String location;
	private String studio;
	Handlers handler;
	private String position;
	private ArrayList<String> availability;
	private String seniority;
	private String benchStartDate;
	private String candidateId;
	
	/**
	 * @param availability
	 */
	public GloberAvailabilityObjects(ArrayList<String> availability) {
		super();
		this.availability = availability;
	}

	/**
	 * @return the availability
	 */
	public ArrayList<String> getAvailability() {
		return availability;
	}

	/**
	 * @param availability the availability to set
	 */
	public void setAvailability(ArrayList<String> availability) {
		this.availability = availability;
	}

	/**
	 * @return the globerId
	 */
	public String getGloberId() {
		return globerId;
	}

	/**
	 * @param globerId the globerId to set
	 */
	public void setGloberId(String globerId) {
		this.globerId = globerId;
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
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the seniority
	 */
	public String getSeniority() {
		return seniority;
	}

	/**
	 * @param seniority the seniority to set
	 */
	public void setSeniority(String seniority) {
		this.seniority = seniority;
	}

	/**
	 * @return the benchStartDate
	 */
	public String getBenchStartDate() {
		return benchStartDate;
	}

	/**
	 * @param benchStartDate the benchStartDate to set
	 */
	public void setBenchStartDate(String benchStartDate) {
		this.benchStartDate = benchStartDate;
	}

	/**
	 * @param handler
	 */
	public GloberAvailabilityObjects(Handlers handler) {
		super();
		this.handler = handler;
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
	 * @param globerId
	 * @param email
	 * @param location
	 * @param studio
	 * @param handler
	 * @param position
	 * @param seniority
	 * @param benchStartDate
	 */
	public GloberAvailabilityObjects(String globerId, String email, String location, String studio,
			Handlers handler, String position, String seniority, String benchStartDate,ArrayList<String> availability) {
		super();
		this.globerId = globerId;
		this.email = email;
		this.location = location;
		this.studio = studio;
		this.handler = handler;
		this.position = position;
		this.seniority = seniority;
		this.benchStartDate = benchStartDate;
		this.availability =availability;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GloberAvailabilityObjects [globerId=" + globerId + ", email=" + email + ", location=" + location
				+ ", studio=" + studio + ", handler=" + handler + ", position=" + position + ", availability="
				+ availability + ", seniority=" + seniority + ", benchStartDate=" + benchStartDate + "]";
	}

	/**
	 * @param globerId
	 */
	public GloberAvailabilityObjects(String globerId) {
		super();
		this.globerId = globerId;
	}

	/**
	 * @return the candidateId
	 */
	public String getCandidateId() {
		return candidateId;
	}

	/**
	 * @param globerId
	 * @param candidateId
	 */
	public GloberAvailabilityObjects(String globerId, String candidateId) {
		super();
		this.globerId = globerId;
		this.candidateId = candidateId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((availability == null) ? 0 : availability.hashCode());
		result = prime * result + ((benchStartDate == null) ? 0 : benchStartDate.hashCode());
		result = prime * result + ((candidateId == null) ? 0 : candidateId.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((globerId == null) ? 0 : globerId.hashCode());
		result = prime * result + ((handler == null) ? 0 : handler.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((seniority == null) ? 0 : seniority.hashCode());
		result = prime * result + ((studio == null) ? 0 : studio.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GloberAvailabilityObjects other = (GloberAvailabilityObjects) obj;
		if (availability == null) {
			if (other.availability != null)
				return false;
		} else if (!availability.equals(other.availability))
			return false;
		if (benchStartDate == null) {
			if (other.benchStartDate != null)
				return false;
		} else if (!benchStartDate.equals(other.benchStartDate))
			return false;
		if (candidateId == null) {
			if (other.candidateId != null)
				return false;
		} else if (!candidateId.equals(other.candidateId))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (globerId == null) {
			if (other.globerId != null)
				return false;
		} else if (!globerId.equals(other.globerId))
			return false;
		if (handler == null) {
			if (other.handler != null)
				return false;
		} else if (!handler.equals(other.handler))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (seniority == null) {
			if (other.seniority != null)
				return false;
		} else if (!seniority.equals(other.seniority))
			return false;
		if (studio == null) {
			if (other.studio != null)
				return false;
		} else if (!studio.equals(other.studio))
			return false;
		return true;
	}
}
