
package dto.submodules.manage.approverManagement.getCecoByApprover;

import java.util.List;

/**
 * @author german.massello
 *
 */
public class Glober {

    private String globerId;
    private String globerName;
    private String maxAmount;
    private String globerPosition;
    private String role;
    private List<String> cecos = null;

    public String getGloberId() {
        return globerId;
    }

    public void setGloberId(String globerId) {
        this.globerId = globerId;
    }

    public String getGloberName() {
        return globerName;
    }

    public void setGloberName(String globerName) {
        this.globerName = globerName;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getGloberPosition() {
        return globerPosition;
    }

    public void setGloberPosition(String globerPosition) {
        this.globerPosition = globerPosition;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getCecos() {
        return cecos;
    }

    public void setCecos(List<String> cecos) {
        this.cecos = cecos;
    }

}
