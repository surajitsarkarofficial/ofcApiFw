
package dto.submodules.project;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectDTOList {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("validData")
    @Expose
    private Boolean validData;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProjectDTOList() {
    }

    /**
     * 
     * @param validData
     * @param details
     * @param message
     * @param status
     */
    public ProjectDTOList(String status, Object message, Boolean validData, List<Detail> details) {
        super();
        this.status = status;
        this.message = message;
        this.validData = validData;
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProjectDTOList withStatus(String status) {
        this.status = status;
        return this;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public ProjectDTOList withMessage(Object message) {
        this.message = message;
        return this;
    }

    public Boolean getValidData() {
        return validData;
    }

    public void setValidData(Boolean validData) {
        this.validData = validData;
    }

    public ProjectDTOList withValidData(Boolean validData) {
        this.validData = validData;
        return this;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public ProjectDTOList withDetails(List<Detail> details) {
        this.details = details;
        return this;
    }

}
