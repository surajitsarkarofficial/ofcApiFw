
package dto.submodules.manage.approverManagement.getCecoDetails;


/**
 * @author german.massello
 *
 */
public class Approver {

    private Owner owner;
    private Object delegated;
    private String role;
    private String level;
    private String maxAmount;
    private String crossApprover;
    private String cecoApprover;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Object getDelegated() {
        return delegated;
    }

    public void setDelegated(Object delegated) {
        this.delegated = delegated;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

	public String getCrossApprover() {
		return crossApprover;
	}

	public void setCrossApprover(String crossApprover) {
		this.crossApprover = crossApprover;
	}

	public String getCecoApprover() {
		return cecoApprover;
	}

	public void setCecoApprover(String cecoApprover) {
		this.cecoApprover = cecoApprover;
	}

}
