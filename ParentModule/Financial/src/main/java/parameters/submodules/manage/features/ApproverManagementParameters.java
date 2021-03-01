package parameters.submodules.manage.features;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import database.submodules.manage.features.approverManagement.CecoDBHelper;
import parameters.FinancialParameters;
import properties.manage.ManageProperties;

/**
 * @author german.massello
 *
 */
public class ApproverManagementParameters extends FinancialParameters{
	protected String username;
	protected String crossApprover;
	protected String crossApproverId;
	protected String approver;
	protected String approverId;
	protected String cecoNumber;
	protected String searchValue;
	protected List<String> cecoNumbers;


	public ApproverManagementParameters() throws SQLException {
		this.username = new CecoDBHelper().getRandomGloberByRol(ManageProperties.rolOptions[new Random().nextInt(ManageProperties.rolOptions.length)]);
		this.cecoNumbers = new ArrayList<String>();
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getCrossApprover() {
		return crossApprover;
	}
	
	public void setCrossApprover(String crossApprover) {
		this.crossApprover = crossApprover;
	}
	
	public String getCrossApproverId() {
		return crossApproverId;
	}
	
	public void setCrossApproverId(String crossApproverId) {
		this.crossApproverId = crossApproverId;
	}
	
	public String getApprover() {
		return approver;
	}
	
	public void setApprover(String approver) {
		this.approver = approver;
	}
	
	public String getApproverId() {
		return approverId;
	}
	
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
	
	public String getCecoNumber() {
		return cecoNumber;
	}
	
	public void setCecoNumber(String cecoNumber) {
		this.cecoNumber = cecoNumber;
	}

	public List<String> getCecoNumbers() {
		return cecoNumbers;
	}

	public void setCecoNumbers(List<String> cecoNumbers) {
		this.cecoNumbers = cecoNumbers;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	
}
