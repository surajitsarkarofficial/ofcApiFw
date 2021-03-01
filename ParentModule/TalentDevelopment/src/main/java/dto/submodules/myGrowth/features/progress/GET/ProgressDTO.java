package dto.submodules.myGrowth.features.progress.GET;

import java.util.List;

/**
 * @author nadia.silva
 */
public class ProgressDTO {

    private List<Detail> details = null;

    private String message;

    private String status;

    private Integer statusCode;

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

}

