package dto.submodules.myGrowth.features.workingEcosystem.workingEcosystemSkills;

/**
 * @author christian.chacon
 */
public class WorkingEcosystemSkillsDto {

    private Integer statusCode;
    private String status;
    private String message;
    private Details details;

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

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

}
