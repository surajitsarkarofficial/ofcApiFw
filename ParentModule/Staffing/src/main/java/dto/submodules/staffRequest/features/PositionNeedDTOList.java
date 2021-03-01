package dto.submodules.staffRequest.features;

public class PositionNeedDTOList {
	int alberthaId;
	String competencyId;
	String competencyName;
	String importance;
	String importanceName;

	public PositionNeedDTOList(int alberthaId, String competencyId, String competencyName, String importance,
			String importanceName) {
		this.alberthaId = alberthaId;
		this.competencyId = competencyId;
		this.competencyName = competencyName;
		this.importance = importance;
		this.importanceName = importanceName;
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
}
