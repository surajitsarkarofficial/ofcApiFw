
package dto.submodules.manage.approverManagement.getCecoDetails;

import java.util.List;

/**
 * @author german.massello
 *
 */
public class Content {

    private List<Approver> approvers = null;
    private String currentLevel;
    private String nextLevel;
    private String minLevel;
    private String maxLevel;
    private String cecoId;
    private String cecoNumber;
    private String requiredAllLevels;

    public List<Approver> getApprovers() {
        return approvers;
    }

    public void setApprovers(List<Approver> approvers) {
        this.approvers = approvers;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(String nextLevel) {
        this.nextLevel = nextLevel;
    }

    public String getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(String minLevel) {
        this.minLevel = minLevel;
    }

    public String getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(String maxLevel) {
        this.maxLevel = maxLevel;
    }

    public String getCecoId() {
        return cecoId;
    }

    public void setCecoId(String cecoId) {
        this.cecoId = cecoId;
    }

    public String getCecoNumber() {
        return cecoNumber;
    }

    public void setCecoNumber(String cecoNumber) {
        this.cecoNumber = cecoNumber;
    }

	public String getRequiredAllLevels() {
		return requiredAllLevels;
	}

	public void setRequiredAllLevels(String requiredAllLevels) {
		this.requiredAllLevels = requiredAllLevels;
	}

}
