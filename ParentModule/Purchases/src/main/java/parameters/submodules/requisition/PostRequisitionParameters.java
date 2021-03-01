package parameters.submodules.requisition;

import java.util.Random;

import constants.submodules.RequisitionConstants;
import parameters.submodules.PurchasesParameters;

/**
 * @author german.massello
 *
 */
public class PostRequisitionParameters extends PurchasesParameters implements RequisitionConstants {

	private String countryName;
	private String groupName;
	private String societyCode;
	private String selectedProject;
	private String currencyCode;
	private String totalAmount;
	
	/**
	 * Default constructor
	 */
	public PostRequisitionParameters() {
		this.countryName = "Argentina";
		this.societyCode = "AR20";
		this.selectedProject = "Avengers TP-BH";
		this.groupName = "Hardware";
		this.currencyCode = "USD";
		this.totalAmount = amountOptions[new Random().nextInt(amountOptions.length)];
		
	}
	public String getCountryName() {
		return countryName;
	}
	public PostRequisitionParameters setCountryName(String countryName) {
		this.countryName = countryName;
		return this;
	}
	public String getGroupName() {
		return groupName;
	}
	public PostRequisitionParameters setGroupName(String groupName) {
		this.groupName = groupName;
		return this;
	}
	public String getSocietyCode() {
		return societyCode;
	}
	public PostRequisitionParameters setSocietyCode(String societyCode) {
		this.societyCode = societyCode;
		return this;
	}
	public String getSelectedProject() {
		return selectedProject;
	}
	public PostRequisitionParameters setSelectedProject(String selectedProject) {
		this.selectedProject = selectedProject;
		return this;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public PostRequisitionParameters setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
		return this;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public PostRequisitionParameters setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
		return this;
	}
	
}
