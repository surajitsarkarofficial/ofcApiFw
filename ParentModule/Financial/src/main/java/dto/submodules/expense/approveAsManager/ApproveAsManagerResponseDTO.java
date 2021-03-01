
package dto.submodules.expense.approveAsManager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApproveAsManagerResponseDTO {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ApproveAsManagerResponseDTO() {
    }

    /**
     * 
     * @param description
     * @param status
     */
    public ApproveAsManagerResponseDTO(String status, String description) {
        super();
        this.status = status;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ApproveAsManagerResponseDTO withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ApproveAsManagerResponseDTO withDescription(String description) {
        this.description = description;
        return this;
    }

}
