package dto.submodules.myGrowth.features.mentoring.editMentoringMission;

/**
 * @author christian.chacon
 */
public class ResponseEditMentoringMissionDto {

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
