
package payloads.submodules.requisition;

import database.submodules.requisition.RequisitionDBHelper;
import parameters.submodules.requisition.PostRequisitionParameters;
import payloads.PurchasesPayLoadHelper;
import tests.testhelpers.submodules.catalogs.features.MaterialTestHelper;
import tests.testhelpers.submodules.catalogs.features.StoreTestHelper;

/**
 * @author german.massello
 *
 */
public class Item extends PurchasesPayLoadHelper{

    private String supplyId;
    private String supplyDescription;
    private String quantity;
    private String currencyId;
    private String currencyCode;
    private String unitCost;
    private String localCurrencySubtotal;
    private String usdRateConversion;
    private String usdSubtotal;
    private String deliveryPlaceId;
    private String expectedDeliveryDate;
    private String technicalSpecifications;
    private String status;
    private String projectId;
    private String requesterComments;

    
    /**
     * Default constructor
     * @param parameters
     * @throws Exception
     */
    public Item(PostRequisitionParameters parameters) throws Exception {
    	this.supplyId = new MaterialTestHelper(parameters.getUserName()).getRandomMaterial(parameters.getGroupName(), parameters.getCountryName()).getId();
    	this.quantity = "1";
    	this.currencyId = new RequisitionDBHelper().getSapCurrency(parameters.getCurrencyCode()).get("id");
    	this.unitCost = parameters.getTotalAmount();
    	this.localCurrencySubtotal = parameters.getTotalAmount();
    	this.usdRateConversion = "1";
    	this.usdSubtotal = parameters.getTotalAmount();
    	this.deliveryPlaceId = new StoreTestHelper(parameters.getUserName()).getRandomStore(parameters.getCountryName()).getId().toString();
    	this.expectedDeliveryDate = parameters.getTodayInMs();
    	this.technicalSpecifications = "technicalSpecifications for supply id" + this.supplyId;
    	this.status = "PENDING_FOR_APPROVAL";
    	this.projectId = new RequisitionDBHelper().getProjectIdFromProjectName(parameters.getSelectedProject());
    	this.requesterComments = "requester comments for supply id" + this.supplyId;
    }

	public String getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(String supplyId) {
        this.supplyId = supplyId;
    }

    public Item withSupplyId(String supplyId) {
        this.supplyId = supplyId;
        return this;
    }

    public String getSupplyDescription() {
        return supplyDescription;
    }

    public void setSupplyDescription(String supplyDescription) {
        this.supplyDescription = supplyDescription;
    }

    public Item withSupplyDescription(String supplyDescription) {
        this.supplyDescription = supplyDescription;
        return this;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Item withQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Item withCurrencyId(String currencyId) {
        this.currencyId = currencyId;
        return this;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Item withCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public String getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(String unitCost) {
        this.unitCost = unitCost;
    }

    public Item withUnitCost(String unitCost) {
        this.unitCost = unitCost;
        return this;
    }

    public String getLocalCurrencySubtotal() {
        return localCurrencySubtotal;
    }

    public void setLocalCurrencySubtotal(String localCurrencySubtotal) {
        this.localCurrencySubtotal = localCurrencySubtotal;
    }

    public Item withLocalCurrencySubtotal(String localCurrencySubtotal) {
        this.localCurrencySubtotal = localCurrencySubtotal;
        return this;
    }

    public String getUsdRateConversion() {
        return usdRateConversion;
    }

    public void setUsdRateConversion(String usdRateConversion) {
        this.usdRateConversion = usdRateConversion;
    }

    public Item withUsdRateConversion(String usdRateConversion) {
        this.usdRateConversion = usdRateConversion;
        return this;
    }

    public String getUsdSubtotal() {
        return usdSubtotal;
    }

    public void setUsdSubtotal(String usdSubtotal) {
        this.usdSubtotal = usdSubtotal;
    }

    public Item withUsdSubtotal(String usdSubtotal) {
        this.usdSubtotal = usdSubtotal;
        return this;
    }

    public String getDeliveryPlaceId() {
        return deliveryPlaceId;
    }

    public void setDeliveryPlaceId(String deliveryPlaceId) {
        this.deliveryPlaceId = deliveryPlaceId;
    }

    public Item withDeliveryPlaceId(String deliveryPlaceId) {
        this.deliveryPlaceId = deliveryPlaceId;
        return this;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public Item withExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
        return this;
    }

    public String getTechnicalSpecifications() {
        return technicalSpecifications;
    }

    public void setTechnicalSpecifications(String technicalSpecifications) {
        this.technicalSpecifications = technicalSpecifications;
    }

    public Item withTechnicalSpecifications(String technicalSpecifications) {
        this.technicalSpecifications = technicalSpecifications;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Item withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Item withProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getRequesterComments() {
        return requesterComments;
    }

    public void setRequesterComments(String requesterComments) {
        this.requesterComments = requesterComments;
    }

    public Item withRequesterComments(String requesterComments) {
        this.requesterComments = requesterComments;
        return this;
    }

}
