package dto.submodules.globers;

public class LeadersObjects {

	private String email;
	private String leaderId;
	private String leader;
	private String id;
	private String name;
	
	/**
	 * 
	 */
	public LeadersObjects() {
	}

	/**
	 * @param id
	 * @param name
	 */
	public LeadersObjects(String id, String name) {
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
	 * @param email
	 * @param leaderId
	 * @param leader
	 */
	public LeadersObjects(String email, String leaderId, String leader) {
		super();
		this.email = email;
		this.leaderId = leaderId;
		this.leader = leader;
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
	 * @return the leaderId
	 */
	public String getLeaderId() {
		return leaderId;
	}

	/**
	 * @param leaderId the leaderId to set
	 */
	public void setLeaderId(String leaderId) {
		this.leaderId = leaderId;
	}

	/**
	 * @return the leader
	 */
	public String getLeader() {
		return leader;
	}

	/**
	 * @param leader the leader to set
	 */
	public void setLeader(String leader) {
		this.leader = leader;
	}
}
