
package dto.submodules.manage.approverManagement.crossApprovers.getCrossApprovers;

import java.util.List;

/**
 * @author german.massello
 *
 */
public class Content {

    private String totalElements;
    private List<CrossApprover> crossApprovers = null;

    public String getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(String totalElements) {
        this.totalElements = totalElements;
    }

    public List<CrossApprover> getCrossApprovers() {
        return crossApprovers;
    }

    public void setCrossApprovers(List<CrossApprover> crossApprovers) {
        this.crossApprovers = crossApprovers;
    }

}
