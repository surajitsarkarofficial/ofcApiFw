
package dto.submodules.manage.approverManagement.crossApprovers.postCrossApprover;

import java.util.List;

/**
 * @author german.massello
 *
 */
public class Content {

    private String totalItems;
    private List<Approver> approvers = null;

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public List<Approver> getApprovers() {
        return approvers;
    }

    public void setApprovers(List<Approver> approvers) {
        this.approvers = approvers;
    }

}
