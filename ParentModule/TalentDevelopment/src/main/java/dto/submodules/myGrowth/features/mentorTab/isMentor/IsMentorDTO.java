package dto.submodules.myGrowth.features.mentorTab.isMentor;

/**
 * @author christian.chacon
 */
public class IsMentorDTO {

    private Details[] details;

    private String message;

    private String statusCode;

    private String status;

    public Details[] getDetails ()
    {
        return details;
    }

    public void setDetails (Details[] details)
    {
        this.details = details;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStatusCode ()
    {
        return statusCode;
    }

    public void setStatusCode (String statusCode)
    {
        this.statusCode = statusCode;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }
}
