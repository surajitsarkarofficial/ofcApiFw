
package dto.submodules.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author nadia.silva
 */
public class Detail {

    @SerializedName("projectId")
    @Expose
    private String projectId;
    @SerializedName("projectName")
    @Expose
    private String projectName;
    @SerializedName("clientId")
    @Expose
    private String clientId;
    @SerializedName("clientName")
    @Expose
    private String clientName;
    @SerializedName("projectState")
    @Expose
    private String projectState;
    @SerializedName("contractType")
    @Expose
    private String contractType;
    @SerializedName("admitExpenseBillables")
    @Expose
    private String admitExpenseBillables;
    @SerializedName("selectable")
    @Expose
    private String selectable;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Detail() {
    }

    /**
     * 
     * @param clientId
     * @param clientName
     * @param contractType
     * @param selectable
     * @param admitExpenseBillables
     * @param projectName
     * @param projectId
     * @param projectState
     */
    public Detail(String projectId, String projectName, String clientId, String clientName, String projectState, String contractType, String admitExpenseBillables, String selectable) {
        super();
        this.projectId = projectId;
        this.projectName = projectName;
        this.clientId = clientId;
        this.clientName = clientName;
        this.projectState = projectState;
        this.contractType = contractType;
        this.admitExpenseBillables = admitExpenseBillables;
        this.selectable = selectable;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Detail withProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Detail withProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Detail withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Detail withClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public String getProjectState() {
        return projectState;
    }

    public void setProjectState(String projectState) {
        this.projectState = projectState;
    }

    public Detail withProjectState(String projectState) {
        this.projectState = projectState;
        return this;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public Detail withContractType(String contractType) {
        this.contractType = contractType;
        return this;
    }

    public String getAdmitExpenseBillables() {
        return admitExpenseBillables;
    }

    public void setAdmitExpenseBillables(String admitExpenseBillables) {
        this.admitExpenseBillables = admitExpenseBillables;
    }

    public Detail withAdmitExpenseBillables(String admitExpenseBillables) {
        this.admitExpenseBillables = admitExpenseBillables;
        return this;
    }

    public String getSelectable() {
        return selectable;
    }

    public void setSelectable(String selectable) {
        this.selectable = selectable;
    }

    public Detail withSelectable(String selectable) {
        this.selectable = selectable;
        return this;
    }

}
