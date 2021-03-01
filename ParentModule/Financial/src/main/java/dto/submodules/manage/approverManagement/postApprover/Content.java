
package dto.submodules.manage.approverManagement.postApprover;

import java.util.List;

/**
 * @author german.massello
 *
 */
public class Content {

    private List<Approver> approvers = null;
    private String cecoNumber;

    public List<Approver> getApprovers() {
        return approvers;
    }

    public void setApprovers(List<Approver> approvers) {
        this.approvers = approvers;
    }

    public String getCecoNumber() {
        return cecoNumber;
    }

    public void setCecoNumber(String cecoNumber) {
        this.cecoNumber = cecoNumber;
    }

}
