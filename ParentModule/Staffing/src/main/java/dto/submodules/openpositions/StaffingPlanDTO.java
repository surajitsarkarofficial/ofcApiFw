package dto.submodules.openpositions;

public class StaffingPlanDTO {

	private String positionId;
	private String globerId;
	private String globalId;
	private String srGridPage;
	private String type;
	
	public StaffingPlanDTO() {
		
	}
	
	public StaffingPlanDTO(String positionID, String globerId, String globalID, String srGridPage, String type) {
		super();
		this.positionId = positionID;
		this.globerId = globerId;
		this.globalId = globalID;
		this.srGridPage = srGridPage;
		this.type = type;
	}
	
	public String getPositionID() {
		return positionId;
	}

	public void setPositionID(String positionID) {
		this.positionId = positionID;
	}

	public String getGloberId() {
		return globerId;
	}

	public void setGloberId(String globerId) {
		this.globerId = globerId;
	}

	public String getGlobalID() {
		return globalId;
	}

	public void setGlobalID(String globalID) {
		this.globalId = globalID;
	}

	public String getSrGridPage() {
		return srGridPage;
	}

	public void setSrGridPage(String srGridPage) {
		this.srGridPage = srGridPage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
