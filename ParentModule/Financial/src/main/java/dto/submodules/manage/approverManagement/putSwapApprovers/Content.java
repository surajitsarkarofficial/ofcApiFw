
package dto.submodules.manage.approverManagement.putSwapApprovers;

import java.util.List;

/**
 * @author german.massello
 *
 */
public class Content {

    private List<Response> response = null;
    private String newApproverAmount;

    public List<Response> getResponse() {
        return response;
    }

    public void setResponse(List<Response> response) {
        this.response = response;
    }

    public String getNewApproverAmount() {
        return newApproverAmount;
    }

    public void setNewApproverAmount(String newApproverAmount) {
        this.newApproverAmount = newApproverAmount;
    }

}
