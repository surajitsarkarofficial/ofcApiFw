
package dto.submodules.purchaseOrder.get;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author german.massello
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "itemNumber",
    "reqxpoStatus",
    "itemDeliveryDate",
    "itemSupplyName",
    "itemSupplyNumber",
    "itemQuantity",
    "itemTotalItemsReceived",
    "reqxpoPurchasedBySAP",
    "rexpoReceivedItemsByPoNumber",
    "itemTechnicalSpecifications",
    "countryName",
    "symbolized",
    "vendorPurchaseName",
    "poNumber",
    "project",
    "projectName",
    "requisitionNumber",
    "solpe",
    "requesterNumber",
    "requesterName",
    "groupName",
    "groupNumber",
    "currencyPurchaseNumber",
    "requisitionTotalAmount",
    "clienName",
    "purchaserName"
})
public class Content_ {

    @JsonProperty("itemNumber")
    private String itemNumber;
    @JsonProperty("reqxpoStatus")
    private String reqxpoStatus;
    @JsonProperty("itemDeliveryDate")
    private String itemDeliveryDate;
    @JsonProperty("itemSupplyName")
    private String itemSupplyName;
    @JsonProperty("itemSupplyNumber")
    private String itemSupplyNumber;
    @JsonProperty("itemQuantity")
    private String itemQuantity;
    @JsonProperty("itemTotalItemsReceived")
    private String itemTotalItemsReceived;
    @JsonProperty("reqxpoPurchasedBySAP")
    private String reqxpoPurchasedBySAP;
    @JsonProperty("rexpoReceivedItemsByPoNumber")
    private String rexpoReceivedItemsByPoNumber;
    @JsonProperty("itemTechnicalSpecifications")
    private String itemTechnicalSpecifications;
    @JsonProperty("countryName")
    private String countryName;
    @JsonProperty("symbolized")
    private Boolean symbolized;
    @JsonProperty("vendorPurchaseName")
    private String vendorPurchaseName;
    @JsonProperty("poNumber")
    private String poNumber;
    @JsonProperty("project")
    private String project;
    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("requisitionNumber")
    private String requisitionNumber;
    @JsonProperty("solpe")
    private String solpe;
    @JsonProperty("requesterNumber")
    private String requesterNumber;
    @JsonProperty("requesterName")
    private String requesterName;
    @JsonProperty("groupName")
    private Object groupName;
    @JsonProperty("groupNumber")
    private String groupNumber;
    @JsonProperty("currencyPurchaseNumber")
    private String currencyPurchaseNumber;
    @JsonProperty("requisitionTotalAmount")
    private String requisitionTotalAmount;
    @JsonProperty("clienName")
    private String clienName;
    @JsonProperty("purchaserName")
    private String purchaserName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("itemNumber")
    public String getItemNumber() {
        return itemNumber;
    }

    @JsonProperty("itemNumber")
    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    @JsonProperty("reqxpoStatus")
    public String getReqxpoStatus() {
        return reqxpoStatus;
    }

    @JsonProperty("reqxpoStatus")
    public void setReqxpoStatus(String reqxpoStatus) {
        this.reqxpoStatus = reqxpoStatus;
    }

    @JsonProperty("itemDeliveryDate")
    public String getItemDeliveryDate() {
        return itemDeliveryDate;
    }

    @JsonProperty("itemDeliveryDate")
    public void setItemDeliveryDate(String itemDeliveryDate) {
        this.itemDeliveryDate = itemDeliveryDate;
    }

    @JsonProperty("itemSupplyName")
    public String getItemSupplyName() {
        return itemSupplyName;
    }

    @JsonProperty("itemSupplyName")
    public void setItemSupplyName(String itemSupplyName) {
        this.itemSupplyName = itemSupplyName;
    }

    @JsonProperty("itemSupplyNumber")
    public String getItemSupplyNumber() {
        return itemSupplyNumber;
    }

    @JsonProperty("itemSupplyNumber")
    public void setItemSupplyNumber(String itemSupplyNumber) {
        this.itemSupplyNumber = itemSupplyNumber;
    }

    @JsonProperty("itemQuantity")
    public String getItemQuantity() {
        return itemQuantity;
    }

    @JsonProperty("itemQuantity")
    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @JsonProperty("itemTotalItemsReceived")
    public String getItemTotalItemsReceived() {
        return itemTotalItemsReceived;
    }

    @JsonProperty("itemTotalItemsReceived")
    public void setItemTotalItemsReceived(String itemTotalItemsReceived) {
        this.itemTotalItemsReceived = itemTotalItemsReceived;
    }

    @JsonProperty("reqxpoPurchasedBySAP")
    public String getReqxpoPurchasedBySAP() {
        return reqxpoPurchasedBySAP;
    }

    @JsonProperty("reqxpoPurchasedBySAP")
    public void setReqxpoPurchasedBySAP(String reqxpoPurchasedBySAP) {
        this.reqxpoPurchasedBySAP = reqxpoPurchasedBySAP;
    }

    @JsonProperty("rexpoReceivedItemsByPoNumber")
    public String getRexpoReceivedItemsByPoNumber() {
        return rexpoReceivedItemsByPoNumber;
    }

    @JsonProperty("rexpoReceivedItemsByPoNumber")
    public void setRexpoReceivedItemsByPoNumber(String rexpoReceivedItemsByPoNumber) {
        this.rexpoReceivedItemsByPoNumber = rexpoReceivedItemsByPoNumber;
    }

    @JsonProperty("itemTechnicalSpecifications")
    public String getItemTechnicalSpecifications() {
        return itemTechnicalSpecifications;
    }

    @JsonProperty("itemTechnicalSpecifications")
    public void setItemTechnicalSpecifications(String itemTechnicalSpecifications) {
        this.itemTechnicalSpecifications = itemTechnicalSpecifications;
    }

    @JsonProperty("countryName")
    public String getCountryName() {
        return countryName;
    }

    @JsonProperty("countryName")
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @JsonProperty("symbolized")
    public Boolean getSymbolized() {
        return symbolized;
    }

    @JsonProperty("symbolized")
    public void setSymbolized(Boolean symbolized) {
        this.symbolized = symbolized;
    }

    @JsonProperty("vendorPurchaseName")
    public String getVendorPurchaseName() {
        return vendorPurchaseName;
    }

    @JsonProperty("vendorPurchaseName")
    public void setVendorPurchaseName(String vendorPurchaseName) {
        this.vendorPurchaseName = vendorPurchaseName;
    }

    @JsonProperty("poNumber")
    public String getPoNumber() {
        return poNumber;
    }

    @JsonProperty("poNumber")
    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    @JsonProperty("project")
    public String getProject() {
        return project;
    }

    @JsonProperty("project")
    public void setProject(String project) {
        this.project = project;
    }

    @JsonProperty("projectName")
    public String getProjectName() {
        return projectName;
    }

    @JsonProperty("projectName")
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @JsonProperty("requisitionNumber")
    public String getRequisitionNumber() {
        return requisitionNumber;
    }

    @JsonProperty("requisitionNumber")
    public void setRequisitionNumber(String requisitionNumber) {
        this.requisitionNumber = requisitionNumber;
    }

    @JsonProperty("solpe")
    public String getSolpe() {
        return solpe;
    }

    @JsonProperty("solpe")
    public void setSolpe(String solpe) {
        this.solpe = solpe;
    }

    @JsonProperty("requesterNumber")
    public String getRequesterNumber() {
        return requesterNumber;
    }

    @JsonProperty("requesterNumber")
    public void setRequesterNumber(String requesterNumber) {
        this.requesterNumber = requesterNumber;
    }

    @JsonProperty("requesterName")
    public String getRequesterName() {
        return requesterName;
    }

    @JsonProperty("requesterName")
    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    @JsonProperty("groupName")
    public Object getGroupName() {
        return groupName;
    }

    @JsonProperty("groupName")
    public void setGroupName(Object groupName) {
        this.groupName = groupName;
    }

    @JsonProperty("groupNumber")
    public String getGroupNumber() {
        return groupNumber;
    }

    @JsonProperty("groupNumber")
    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    @JsonProperty("currencyPurchaseNumber")
    public String getCurrencyPurchaseNumber() {
        return currencyPurchaseNumber;
    }

    @JsonProperty("currencyPurchaseNumber")
    public void setCurrencyPurchaseNumber(String currencyPurchaseNumber) {
        this.currencyPurchaseNumber = currencyPurchaseNumber;
    }

    @JsonProperty("requisitionTotalAmount")
    public String getRequisitionTotalAmount() {
        return requisitionTotalAmount;
    }

    @JsonProperty("requisitionTotalAmount")
    public void setRequisitionTotalAmount(String requisitionTotalAmount) {
        this.requisitionTotalAmount = requisitionTotalAmount;
    }

    @JsonProperty("clienName")
    public String getClienName() {
        return clienName;
    }

    @JsonProperty("clienName")
    public void setClienName(String clienName) {
        this.clienName = clienName;
    }

    @JsonProperty("purchaserName")
    public String getPurchaserName() {
        return purchaserName;
    }

    @JsonProperty("purchaserName")
    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
