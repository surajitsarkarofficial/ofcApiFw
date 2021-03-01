
package dto.submodules.requisition.post;

import java.util.List;

/**
 * @author german.massello
 *
 */
public class Content {

    private String number;
    private String title;
    private String requisitionDate;
    private String lastModification;
    private String requesterId;
    private String requester;
    private String type;
    private String status;
    private String state;
    private String totalAmount;
    private String countryId;
    private String countryName;
    private String symbolized;
    private String societyId;
    private String societyName;
    private String groupId;
    private String groupName;
    private String comments;
    private List<Item> items = null;
    private String reception;
    private String baseProjectId;
    private String baseProjectName;
    private String quotationId;
    private String solpe;
    private String requisitionHandlers;
    private String societyCode;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRequisitionDate() {
        return requisitionDate;
    }

    public void setRequisitionDate(String requisitionDate) {
        this.requisitionDate = requisitionDate;
    }

    public String getLastModification() {
        return lastModification;
    }

    public void setLastModification(String lastModification) {
        this.lastModification = lastModification;
    }

    public String getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(String requesterId) {
        this.requesterId = requesterId;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getSymbolized() {
        return symbolized;
    }

    public void setSymbolized(String symbolized) {
        this.symbolized = symbolized;
    }

    public String getSocietyId() {
        return societyId;
    }

    public void setSocietyId(String societyId) {
        this.societyId = societyId;
    }

    public String getSocietyName() {
        return societyName;
    }

    public void setSocietyName(String societyName) {
        this.societyName = societyName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getReception() {
        return reception;
    }

    public void setReception(String reception) {
        this.reception = reception;
    }

    public String getBaseProjectId() {
        return baseProjectId;
    }

    public void setBaseProjectId(String baseProjectId) {
        this.baseProjectId = baseProjectId;
    }

    public String getBaseProjectName() {
        return baseProjectName;
    }

    public void setBaseProjectName(String baseProjectName) {
        this.baseProjectName = baseProjectName;
    }

    public String getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(String quotationId) {
        this.quotationId = quotationId;
    }

    public String getSolpe() {
        return solpe;
    }

    public void setSolpe(String solpe) {
        this.solpe = solpe;
    }

    public String getRequisitionHandlers() {
        return requisitionHandlers;
    }

    public void setRequisitionHandlers(String requisitionHandlers) {
        this.requisitionHandlers = requisitionHandlers;
    }

    public String getSocietyCode() {
        return societyCode;
    }

    public void setSocietyCode(String societyCode) {
        this.societyCode = societyCode;
    }

}
