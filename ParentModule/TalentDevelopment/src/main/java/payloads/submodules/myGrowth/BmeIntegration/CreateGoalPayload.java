package payloads.submodules.myGrowth.BmeIntegration;

import java.util.Random;

public class CreateGoalPayload {

    public Integer missionId;
    public String missionTitle;

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public String getMissionTitle() {
        return missionTitle;
    }

    public void setMissionTitle(String missionTitle) {
        this.missionTitle = missionTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGloberId() {
        return globerId;
    }

    public void setGloberId(String globerId) {
        this.globerId = globerId;
    }

    public String description;
    public String globerId;

    /**
     * Build payload to create a goal
     * @param userId
     */
    public CreateGoalPayload(String userId){
        Random random = new Random();
        this.missionId = random.nextInt(999);
        this.missionTitle = "Goal created by automated test on "+ java.time.LocalDate.now().toString();
        this.description = "Goal created by automated test";
        this.globerId = userId;

    }





}
