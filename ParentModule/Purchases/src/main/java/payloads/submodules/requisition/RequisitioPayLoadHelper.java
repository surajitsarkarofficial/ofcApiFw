
package payloads.submodules.requisition;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import database.submodules.requisition.RequisitionDBHelper;
import parameters.submodules.requisition.PostRequisitionParameters;
import payloads.PurchasesPayLoadHelper;
import tests.testhelpers.submodules.catalogs.features.CountryTestHelper;
import tests.testhelpers.submodules.catalogs.features.GroupTestHelper;

/**
 * @author german.massello
 *
 */
public class RequisitioPayLoadHelper extends PurchasesPayLoadHelper {

    private String number;
    private String title;
    private String requesterId;
    private String type;
    private String totalAmount;
    private String state;
    private String status;
    private String countryId;
    private String societyCode;
    private String symbolized;
    private String baseProjectId;
    private String baseProjectName;
    private String groupId;
    private String groupName;
    private List<Item> items = null;

    /**
     * Default constructor
     * @param parameters
     * @throws Exception
     */
    public RequisitioPayLoadHelper(PostRequisitionParameters parameters) throws Exception {
    	this.title = "Requisition N:" + todayInMs;
    	this.type = "Good";
    	this.totalAmount = amountOptions[new Random().nextInt(amountOptions.length)];
    	this.state = "REQUISITION";
    	this.status = "PENDING_FOR_APPROVAL";
    	this.countryId = new CountryTestHelper(parameters.getUserName()).getSelectedCountry(parameters.getCountryName()).getId();
    	this.societyCode = parameters.getSocietyCode();
    	this.symbolized = "true";
    	this.baseProjectId = new RequisitionDBHelper().getProjectIdFromProjectName(parameters.getSelectedProject());
    	this.groupId = new GroupTestHelper(parameters.getUserName()).getSelectedGroup(parameters.getGroupName()).getId();
    	this.groupName = parameters.getGroupName();
    	this.items = new LinkedList<>();  
    	this.items.add(new Item(parameters));
	}

	public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public RequisitioPayLoadHelper withNumber(String number) {
        this.number = number;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RequisitioPayLoadHelper withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public RequisitioPayLoadHelper withRequesterId(String requesterId) {
        this.requesterId = requesterId;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RequisitioPayLoadHelper withType(String type) {
        this.type = type;
        return this;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public RequisitioPayLoadHelper withTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public RequisitioPayLoadHelper withState(String state) {
        this.state = state;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RequisitioPayLoadHelper withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public RequisitioPayLoadHelper withCountryId(String countryId) {
        this.countryId = countryId;
        return this;
    }

    public String getSocietyCode() {
        return societyCode;
    }

    public void setSocietyCode(String societyCode) {
        this.societyCode = societyCode;
    }

    public RequisitioPayLoadHelper withSocietyCode(String societyCode) {
        this.societyCode = societyCode;
        return this;
    }

    public String getSymbolized() {
        return symbolized;
    }

    public void setSymbolized(String symbolized) {
        this.symbolized = symbolized;
    }

    public RequisitioPayLoadHelper withSymbolized(String symbolized) {
        this.symbolized = symbolized;
        return this;
    }

    public String getBaseProjectId() {
        return baseProjectId;
    }

    public void setBaseProjectId(String baseProjectId) {
        this.baseProjectId = baseProjectId;
    }

    public RequisitioPayLoadHelper withBaseProjectId(String baseProjectId) {
        this.baseProjectId = baseProjectId;
        return this;
    }

    public String getBaseProjectName() {
        return baseProjectName;
    }

    public void setBaseProjectName(String baseProjectName) {
        this.baseProjectName = baseProjectName;
    }

    public RequisitioPayLoadHelper withBaseProjectName(String baseProjectName) {
        this.baseProjectName = baseProjectName;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public RequisitioPayLoadHelper withGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public RequisitioPayLoadHelper withGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public RequisitioPayLoadHelper withItems(List<Item> items) {
        this.items = items;
        return this;
    }

}
