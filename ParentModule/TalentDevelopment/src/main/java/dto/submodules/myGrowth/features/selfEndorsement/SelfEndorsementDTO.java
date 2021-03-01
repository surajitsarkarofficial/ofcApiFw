package dto.submodules.myGrowth.features.selfEndorsement;

/**
 * @author nadia.silva
 */
public class SelfEndorsementDTO {
    private String exportToExcel;

    private String service;

    private String validData;

    private Details[] details;

    private String rowCount;

    private String message;

    private String statusCode;

    private String status;

    public String getExportToExcel ()
    {
        return exportToExcel;
    }

    public void setExportToExcel (String exportToExcel)
    {
        this.exportToExcel = exportToExcel;
    }

    public String getService ()
{
    return service;
}

    public void setService (String service)
    {
        this.service = service;
    }

    public String getValidData ()
    {
        return validData;
    }

    public void setValidData (String validData)
    {
        this.validData = validData;
    }

    public Details[] getDetails ()
    {
        return details;
    }

    public void setDetails (Details[] details)
    {
        this.details = details;
    }

    public String getRowCount ()
{
    return rowCount;
}

    public void setRowCount (String rowCount)
    {
        this.rowCount = rowCount;
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
			
			