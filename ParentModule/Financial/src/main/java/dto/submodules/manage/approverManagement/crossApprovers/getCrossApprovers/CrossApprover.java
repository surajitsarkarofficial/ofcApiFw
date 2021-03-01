
package dto.submodules.manage.approverManagement.crossApprovers.getCrossApprovers;


/**
 * @author german.massello
 *
 */
public class CrossApprover {

    private String globerId;
    private String globerName;
    private String active;
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

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
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
