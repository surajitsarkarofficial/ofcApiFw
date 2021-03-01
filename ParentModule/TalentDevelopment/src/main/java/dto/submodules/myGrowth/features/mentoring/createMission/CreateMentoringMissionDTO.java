package dto.submodules.myGrowth.features.mentoring.createMission;

public class CreateMentoringMissionDTO {
    /**
     * @author nadia.silva
     */
    private int statusCode;
    private String status;
    private String message;
    private String details;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
