package dto.myTeam.features;

import dto.myTeam.MyTeamDTO;

/**
 * @author imran.khan
 *
 */

public class RotateAndReplaceDTOList extends MyTeamDTO{
	private String globerId;
	private String userName;
	private String projectId;
	private String positionId;
	private String id;
	private String name;
	private String replacementId;
	private String ReplacementName;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGloberId() {
		return globerId;
	}

	public void setGloberId(String globerId) {
		this.globerId = globerId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	
	public RotateAndReplaceDTOList() {
		
	}
	
	public RotateAndReplaceDTOList(String globerId, String userName) {
		super();
		this.globerId = globerId;
		this.userName = userName;
	}
	
	public RotateAndReplaceDTOList(String globerId, String userName,String projectId,String positionId) {
		super();
		this.globerId = globerId;
		this.userName = userName;
		this.projectId=projectId;
		this.positionId=positionId;
	}
	
	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId=positionId;
		
	}

	public String getReplacementId() {
		return replacementId;
	}

	public void setReplacementId(String replacementId) {
		this.replacementId = replacementId;
	}

	public String getReplacementName() {
		return ReplacementName;
	}

	public void setReplacementName(String replacementName) {
		ReplacementName = replacementName;
	}
	
	
	}
