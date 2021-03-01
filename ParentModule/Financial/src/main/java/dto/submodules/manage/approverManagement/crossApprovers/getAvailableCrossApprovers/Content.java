
package dto.submodules.manage.approverManagement.crossApprovers.getAvailableCrossApprovers;

import java.util.List;

/**
 * @author german.massello
 *
 */
public class Content {

    private String totalItems;
    private List<AvailableApprover> availableApprovers = null;

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public List<AvailableApprover> getAvailableApprovers() {
        return availableApprovers;
    }

    public void setAvailableApprovers(List<AvailableApprover> availableApprovers) {
        this.availableApprovers = availableApprovers;
    }

}
