package dto.submodules.myGrowth.features.capabilities.GET;

import java.util.List;
/**
 * @author nadia.silva
 */
public class CapabilitiesStatusDTO {private Integer statusCode;

    private String status;
    private String message;
    private List<Detail> details = null;
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

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

}
