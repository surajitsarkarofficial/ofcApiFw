
package dto.submodules.manage.approverManagement.postMassiveApproverCeco;


/**
 * @author german.massello
 *
 */
public class Content {

    private Approver approver;
    private String totalItemsUpdated;

    public Approver getApprover() {
        return approver;
    }

    public void setApprover(Approver approver) {
        this.approver = approver;
    }

    public String getTotalItemsUpdated() {
        return totalItemsUpdated;
    }

    public void setTotalItemsUpdated(String totalItemsUpdated) {
        this.totalItemsUpdated = totalItemsUpdated;
    }

}
