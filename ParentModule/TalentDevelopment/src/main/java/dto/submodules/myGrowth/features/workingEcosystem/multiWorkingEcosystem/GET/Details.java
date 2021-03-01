package dto.submodules.myGrowth.features.workingEcosystem.multiWorkingEcosystem.GET;

import java.util.List;

/**
 * @author nadia.silva
 */
public class Details {
    private String familyGroup;

    private List<WorkingEcosystem> workingEcosystems = null;

    public String getFamilyGroup() {
        return familyGroup;
    }

    public void setFamilyGroup(String familyGroup) {
        this.familyGroup = familyGroup;
    }

    public List<WorkingEcosystem> getWorkingEcosystems() {
        return workingEcosystems;
    }

    public void setWorkingEcosystems(List<WorkingEcosystem> workingEcosystems) {
        this.workingEcosystems = workingEcosystems;
    }

}
