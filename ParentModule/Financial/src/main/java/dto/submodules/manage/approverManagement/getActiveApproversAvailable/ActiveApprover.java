
package dto.submodules.manage.approverManagement.getActiveApproversAvailable;


/**
 * @author german.massello
 *
 */
public class ActiveApprover {

    private String globerId;
    private String globerName;
    private String totalCecoAssigned;
    private String maxAmount;
    private String globerPosition;
    private String role;

    public String getGloberId() {
        return globerId;
    }

    public void setGloberId(String globerId) {
        this.globerId = globerId;
    }

    public String getGloberName() {
        return globerName;
    }

    public void setGloberName(String globerName) {
        this.globerName = globerName;
    }

    public String getTotalCecoAssigned() {
        return totalCecoAssigned;
    }

    public void setTotalCecoAssigned(String totalCecoAssigned) {
        this.totalCecoAssigned = totalCecoAssigned;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getGloberPosition() {
        return globerPosition;
    }

    public void setGloberPosition(String globerPosition) {
        this.globerPosition = globerPosition;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
