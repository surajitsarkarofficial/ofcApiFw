package dto.submodules.myGrowth.features.bmeIntegration;

public class CreateGoalDTO {
    public String statusCode;
    public String status;
    public String message;
    public String rowCount;
    public Boolean exportToExcel;
    public Boolean validData;
    public String details;
    public String service;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
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

    public String getRowCount() {
        return rowCount;
    }

    public void setRowCount(String rowCount) {
        this.rowCount = rowCount;
    }

    public Boolean getExportToExcel() {
        return exportToExcel;
    }

    public void setExportToExcel(Boolean exportToExcel) {
        this.exportToExcel = exportToExcel;
    }

    public Boolean getValidData() {
        return validData;
    }

    public void setValidData(Boolean validData) {
        this.validData = validData;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

}
