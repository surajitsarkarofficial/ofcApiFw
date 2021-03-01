package dto.submodules.staffRequest.features;

public class PositionSeniorityMappingDTO {
	String positionName;
	int positionRoleId; 
	int seniorityId;
	String seniorityName;

	public PositionSeniorityMappingDTO(int positionRoleId, String positionName,String seniorityName,int seniorityId) {
		this.positionRoleId = positionRoleId;
		this.positionName = positionName;
		this.seniorityName = seniorityName;
		this.seniorityId = seniorityId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionRoleName(String positionRoleName) {
		this.positionName = positionRoleName;
	}

	public int getPositionRoleId() {
		return positionRoleId;
	}

	public void setPositionRoleId(int positionRoleId) {
		this.positionRoleId = positionRoleId;
	}

	public int getSeniorityId() {
		return seniorityId;
	}

	public void setSeniorityId(int seniorityId) {
		this.seniorityId = seniorityId;
	}

	public String getSeniorityName() {
		return seniorityName;
	}

	public void setSeniorityName(String seniorityName) {
		this.seniorityName = seniorityName;
	}


}