
package dto.submodules.manage.approverManagement.postApprover;

/**
 * @author german.massello
 *
 */
public class Approver {

    private String fullName;
    private Object urlAvatar;
    private String approverRole;
    private String maxAmount;
    private String globerId;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Object getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(Object urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public String getApproverRole() {
        return approverRole;
    }

    public void setApproverRole(String approverRole) {
        this.approverRole = approverRole;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getGloberId() {
        return globerId;
    }

    public void setGloberId(String globerId) {
        this.globerId = globerId;
    }

}
