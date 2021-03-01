package dto.myTeam.features;

import java.util.ArrayList;
import java.util.List;

import dto.myTeam.MyTeamDTO;

/**
 * @author dnyaneshwar.waghmare
 *
 */

public class FilterObject extends MyTeamDTO{
	private String id;
	private String name;
	private String assignmentId;
	private String projectId;
	private String projectName;
	private String clientId;
	private String clientName;
	private String studioId;
	private String studioName;
	private String startDate;
	private String endDate;
	private String projectState;
	private String contractType;
	private String projectManager;
	private String hasMultipleAssignments;
	private String globerId;
	private String componentKey;
	private String active;
	private String value;
	private String assignmentPercentage;
	private String podName;
	private Integer podId;
	private String firstName;
	private String lastName;
	private String email;
	private String position;
	private String gender;
	private String projectPosition;
	private String city;
	private String country;
	private String assignmentStartDate;
	private String assignmentEndDate;
	private String podType;
	private String podTypeName;
	private String podMaturityId;
	private String podMaturityValue;
	private boolean isPm;
	private boolean isTl;
	private boolean isMainPm;
	private String tempAssignmentEndDate;
	private String globerType;
	private String projectRole;
	private String staffPlanId;
	private String projectRoleDetails;
	private String roleStartDate;
	private String roleEndDate;
	private String comments;
	private String replacementId;
	private String ReplacementName;
	private String positionId;
	private String userName;
	private String tag;
	private String businessUnitId;

	/**
	 * 
	 */
	public FilterObject() {
	}

	/**
	 * @param id
	 * @param name
	 */
	public FilterObject(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	/**
	 * @param id
	 */
	public FilterObject(String id) {
		super();
		this.id = id;
	}

	public FilterObject(String globerId, String componentKey, String value, String active) {
		super();
		this.globerId = globerId;
		this.componentKey = componentKey;
		this.value = value;
		this.active = active;

	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void startDate(String startDate) {
		this.setStartDate(startDate);
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getStudioId() {
		return studioId;
	}

	public void setStudioId(String studioId) {
		this.studioId = studioId;
	}

	public String getStudioName() {
		return studioName;
	}

	public void setStudioName(String studioName) {
		this.studioName = studioName;
	}

	public String getProjectState() {
		return projectState;
	}

	public void setProjectState(String projectState) {
		this.projectState = projectState;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getHasMultipleAssignments() {
		return hasMultipleAssignments;
	}

	public void setHasMultipleAssignments(String hasMultipleAssignments) {
		this.hasMultipleAssignments = hasMultipleAssignments;
	}

	public String getGloberId() {
		return globerId;
	}

	public void setGloberId(String globerId) {
		this.globerId = globerId;
	}

	public String getComponentKey() {
		return componentKey;
	}

	public void setComponentKey(String componentKey) {
		this.componentKey = componentKey;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAssignmentPercentage() {
		return assignmentPercentage;
	}

	public void setAssignmentPercentage(String assignmentPercentage) {
		this.assignmentPercentage = assignmentPercentage;
	}

	public String getPodName() {
		return podName;
	}

	public void setPodName(String podName) {
		this.podName = podName;
	}

	public Integer podName() {
		return podId;
	}

	public Integer getPodId() {
		return podId;
	}

	public void setPodId(Integer podId) {
		this.podId = podId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getProjectPosition() {
		return projectPosition;
	}

	public void setProjectPosition(String projectPosition) {
		this.projectPosition = projectPosition;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAssignmentStartDate() {
		return assignmentStartDate;
	}

	public void setAssignmentStartDate(String assignmentStartDate) {
		this.assignmentStartDate = assignmentStartDate;
	}

	public String getAssignmentEndDate() {
		return assignmentEndDate;
	}

	public void setAssignmentEndDate(String assignmentEndDate) {
		this.assignmentEndDate = assignmentEndDate;
	}

	public String getPodType() {
		return podType;
	}

	public void setPodType(String podType) {
		this.podType = podType;
	}

	public String getPodTypeName() {
		return podTypeName;
	}

	public void setPodTypeName(String podTypeName) {
		this.podTypeName = podTypeName;
	}

	public String getPodMaturityId() {
		return podMaturityId;
	}

	public void setPodMaturityId(String podMaturityId) {
		this.podMaturityId = podMaturityId;
	}

	public String getPodMaturityValue() {
		return podMaturityValue;
	}

	public void setPodMaturityValue(String podMaturityValue) {
		this.podMaturityValue = podMaturityValue;
	}

	public boolean isPm() {
		return isPm;
	}

	public void setPm(boolean isPm) {
		this.isPm = isPm;
	}

	public boolean isTl() {
		return isTl;
	}

	public void setTl(boolean isTl) {
		this.isTl = isTl;
	}

	public boolean isMainPm() {
		return isMainPm;
	}

	public void setMainPm(boolean isMainPm) {
		this.isMainPm = isMainPm;
	}

	public String getTempAssignmentEndDate() {
		return tempAssignmentEndDate;
	}

	public void setTempAssignmentEndDate(String tempAssignmentEndDate) {
		this.tempAssignmentEndDate = tempAssignmentEndDate;
	}

	public String getGloberType() {
		return globerType;
	}

	public void setGloberType(String globerType) {
		this.globerType = globerType;
	}

	public String getProjectRole() {
		return projectRole;
	}

	public void setProjectRole(String projectRole) {
		this.projectRole = projectRole;
	}

	public String getStaffPlanId() {
		return staffPlanId;
	}

	public void setStaffPlanId(String staffPlanId) {
		this.staffPlanId = staffPlanId;
	}

	public String getProjectRoleDetails(String roleStartDate, String roleEndDate, String projectId, String globerId,
			String projectRole, String comments) {
		this.roleStartDate = roleStartDate;
		this.roleEndDate = roleEndDate;
		this.projectRole = projectRole;
		this.projectId = projectId;
		this.globerId = globerId;
		this.comments = comments;

		return projectRoleDetails;

	}

	public List<String> setProjectRoleDetails(String roleStartDate, String roleEndDate, String projectId,
			String globerId, String projectRole, String comments) {
		List<String> projectRoleDetailsData = new ArrayList<String>();
		String[] data = { this.roleStartDate, this.roleEndDate, this.projectRole, this.projectId, this.globerId,
				this.comments };

		for (int i = 0; i < data.length; i++) {
			projectRoleDetailsData.add(data[i]);
		}
		return projectRoleDetailsData;
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

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getBusinessUnitId() {
		return businessUnitId;
	}

	public void setBusinessUnitId(String businessUnitId) {
		this.businessUnitId = businessUnitId;
	}
	
}
