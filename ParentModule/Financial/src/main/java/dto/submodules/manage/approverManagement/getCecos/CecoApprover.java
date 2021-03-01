
package dto.submodules.manage.approverManagement.getCecos;


/**
 * @author german.massello
 *
 */
public class CecoApprover {

    private String cecoNumber;
    private String approverName;
    private String approverAmount;
    private String approverLevel;
    private Object totalApproversLevel;

    public String getCecoNumber() {
        return cecoNumber;
    }

    public void setCecoNumber(String cecoNumber) {
        this.cecoNumber = cecoNumber;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getApproverAmount() {
        return approverAmount;
    }

    public void setApproverAmount(String approverAmount) {
        this.approverAmount = approverAmount;
    }

    public String getApproverLevel() {
        return approverLevel;
    }

    public void setApproverLevel(String approverLevel) {
        this.approverLevel = approverLevel;
    }

    public Object getTotalApproversLevel() {
        return totalApproversLevel;
    }

    public void setTotalApproversLevel(Object totalApproversLevel) {
        this.totalApproversLevel = totalApproversLevel;
    }

}
