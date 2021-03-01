package dto.submodules.globers;

public class SkillsObjects {
	private String id;
	private String name;
	private String email;

	/**
	 * 
	 */
	public SkillsObjects() {
	}

	/**
	 * @param id
	 * @param name
	 */
	public SkillsObjects(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public SkillsObjects(String email) {
		super();
		this.email = email;
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
}
