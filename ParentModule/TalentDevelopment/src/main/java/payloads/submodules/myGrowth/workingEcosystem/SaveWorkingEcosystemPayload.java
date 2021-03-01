package payloads.submodules.myGrowth.workingEcosystem;

public class SaveWorkingEcosystemPayload {
    /**
     * @author nadia.silva
     */
    private String globerId;
    private Boolean primary;
    private String workingEcosystemId;
    private String workingEcosystemName;

    public String getGloberId() {
        return globerId;
    }

    public void setGloberId(String globerId) {
        this.globerId = globerId;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public String getWorkingEcosystemId() {
        return workingEcosystemId;
    }

    public void setWorkingEcosystemId(String workingEcosystemId) {
        this.workingEcosystemId = workingEcosystemId;
    }

    public String getWorkingEcosystemName() {
        return workingEcosystemName;
    }

    public void setWorkingEcosystemName(String workingEcosystemName) {
        this.workingEcosystemName = workingEcosystemName;
    }

    /**
     * Creation of working ecosystem for payload
     * @param glober
     * @param isPrimary
     * @param weID
     * @param weName
     */
    public SaveWorkingEcosystemPayload(String glober, Boolean isPrimary, String weID, String weName){
        this.globerId = glober;
        this.primary = isPrimary;
        this.workingEcosystemId = weID;
        this.workingEcosystemName = weName;
    }
}