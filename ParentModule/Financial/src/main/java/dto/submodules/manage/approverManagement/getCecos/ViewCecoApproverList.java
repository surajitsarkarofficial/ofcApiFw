
package dto.submodules.manage.approverManagement.getCecos;

import java.util.List;

/**
 * @author german.massello
 *
 */
public class ViewCecoApproverList {

    private String cecoNumber;
    private String totalApprovers;
    private List<CecoApprover> cecoApprovers = null;

    public String getCecoNumber() {
        return cecoNumber;
    }

    public void setCecoNumber(String cecoNumber) {
        this.cecoNumber = cecoNumber;
    }

    public String getTotalApprovers() {
        return totalApprovers;
    }

    public void setTotalApprovers(String totalApprovers) {
        this.totalApprovers = totalApprovers;
    }

    public List<CecoApprover> getCecoApprovers() {
        return cecoApprovers;
    }

    public void setCecoApprovers(List<CecoApprover> cecoApprovers) {
        this.cecoApprovers = cecoApprovers;
    }

}
