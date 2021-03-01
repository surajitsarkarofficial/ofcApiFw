package dto.submodules.myGrowth.features.bmeIntegration;

/**
 * @author christian.chacon
 */
public class ResponseBodySendFeedbackRequest {

    private Integer statusCode;
    private String status;
    private String message;
    private Object details;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

}
