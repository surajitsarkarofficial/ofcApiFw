
package dto.submodules.expense.receipt.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReceiptDTO {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private Details details;
    @SerializedName("uploadedFileName")
    @Expose
    private Object uploadedFileName;
    @SerializedName("fileLinesCount")
    @Expose
    private String fileLinesCount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ReceiptDTO() {
    }

    /**
     * 
     * @param uploadedFileName
     * @param fileLinesCount
     * @param details
     * @param message
     * @param status
     */
    public ReceiptDTO(String status, String message, Details details, Object uploadedFileName, String fileLinesCount) {
        super();
        this.status = status;
        this.message = message;
        this.details = details;
        this.uploadedFileName = uploadedFileName;
        this.fileLinesCount = fileLinesCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ReceiptDTO withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ReceiptDTO withMessage(String message) {
        this.message = message;
        return this;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public ReceiptDTO withDetails(Details details) {
        this.details = details;
        return this;
    }

    public Object getUploadedFileName() {
        return uploadedFileName;
    }

    public void setUploadedFileName(Object uploadedFileName) {
        this.uploadedFileName = uploadedFileName;
    }

    public ReceiptDTO withUploadedFileName(Object uploadedFileName) {
        this.uploadedFileName = uploadedFileName;
        return this;
    }

    public String getFileLinesCount() {
        return fileLinesCount;
    }

    public void setFileLinesCount(String fileLinesCount) {
        this.fileLinesCount = fileLinesCount;
    }

    public ReceiptDTO withFileLinesCount(String fileLinesCount) {
        this.fileLinesCount = fileLinesCount;
        return this;
    }

}
