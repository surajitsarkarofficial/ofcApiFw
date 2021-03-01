package dto.submodules.staffRequest.features;

/**
 * 
 * @author akshata.dongare
 *
 */
public class MarkGloberFitUnfitSkillDTO {
	int alberthaId;
	String competencyId;
	String competencyName;
	int evidenceValue;
	String importance;
	String importanceName;
	boolean skillMatching;
	
	public MarkGloberFitUnfitSkillDTO(int alberthaId, String competencyId, String competencyName, int evidenceValue, String importance,
			String importanceName, boolean skillMatching) {
		this.alberthaId = alberthaId;
		this.competencyId = competencyId;
		this.competencyName = competencyName;
		this.evidenceValue = evidenceValue;
		this.importance = importance;
		this.importanceName = importanceName;
		this.skillMatching = skillMatching;
	}

	public int getAlberthaId() {
		return alberthaId;
	}

	public void setAlberthaId(int alberthaId) {
		this.alberthaId = alberthaId;
	}

	public String getCompetencyId() {
		return competencyId;
	}

	public void setCompetencyId(String competencyId) {
		this.competencyId = competencyId;
	}

	public String getCompetencyName() {
		return competencyName;
	}

	public void setCompetencyName(String competencyName) {
		this.competencyName = competencyName;
	}

	public int getEvidenceValue() {
		return evidenceValue;
	}

	public void setEvidenceValue(int evidenceValue) {
		this.evidenceValue = evidenceValue;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getImportanceName() {
		return importanceName;
	}

	public void setImportanceName(String importanceName) {
		this.importanceName = importanceName;
	}

	public boolean getSkillMatching() {
		return skillMatching;
	}

	public void setSkillMatching(boolean skillMatching) {
		this.skillMatching = skillMatching;
	}
}
