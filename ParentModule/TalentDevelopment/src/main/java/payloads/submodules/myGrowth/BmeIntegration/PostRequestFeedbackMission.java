package payloads.submodules.myGrowth.BmeIntegration;

/**
 * @author christian.chacon
 */
public class PostRequestFeedbackMission {

    private Integer globerId;
    private String missionId;
    private Integer workingEcosystemId;

    public Integer getGloberId() {
        return globerId;
    }

    public void setGloberId(Integer globerId) {
        this.globerId = globerId;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public Integer getWorkingEcosystemId() {
        return workingEcosystemId;
    }

    public void setWorkingEcosystemId(Integer workingEcosystemId) {
        this.workingEcosystemId = workingEcosystemId;
    }

    /**
     *
     * @param globerId
     * @param missionId
     * @param workingEcosystemId
     */
    public PostRequestFeedbackMission(Integer globerId, String missionId, Integer workingEcosystemId) {
        this.globerId = globerId;
        this.missionId = missionId;
        this.workingEcosystemId = workingEcosystemId;
    }
}
