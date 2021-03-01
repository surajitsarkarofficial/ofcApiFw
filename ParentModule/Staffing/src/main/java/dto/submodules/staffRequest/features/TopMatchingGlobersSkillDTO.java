package dto.submodules.staffRequest.features;

public class TopMatchingGlobersSkillDTO {

	String skill_id;
	String stars;

	public TopMatchingGlobersSkillDTO(String skill_id,String stars) {
		this.skill_id = skill_id;
		this.stars = stars;
	}

	public String getSkill_id() {
		return skill_id;
	}

	public void setSkill_id(String skill_id) {
		this.skill_id = skill_id;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
	}
}