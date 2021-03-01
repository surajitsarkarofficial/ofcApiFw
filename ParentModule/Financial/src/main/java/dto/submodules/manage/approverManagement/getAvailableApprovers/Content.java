
package dto.submodules.manage.approverManagement.getAvailableApprovers;

import java.util.List;

/**
 * @author german.massello
 *
 */
public class Content {

    private List<AvailableApprover> availableApprovers = null;

    public List<AvailableApprover> getAvailableApprovers() {
        return availableApprovers;
    }

    public void setAvailableApprovers(List<AvailableApprover> availableApprovers) {
        this.availableApprovers = availableApprovers;
    }

}
