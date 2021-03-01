
package dto.submodules.manage.approverManagement.getActiveApproversAvailable;

import java.util.List;

/**
 * @author german.massello
 *
 */
public class Content {

    private String totalApprovers;
    private List<ActiveApprover> activeApprovers = null;

    public String getTotalApprovers() {
        return totalApprovers;
    }

    public void setTotalApprovers(String totalApprovers) {
        this.totalApprovers = totalApprovers;
    }

    public List<ActiveApprover> getActiveApprovers() {
        return activeApprovers;
    }

    public void setActiveApprovers(List<ActiveApprover> activeApprovers) {
        this.activeApprovers = activeApprovers;
    }

}
