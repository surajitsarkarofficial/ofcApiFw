package dto.submodules.myGrowth.features.missionTab.hasLeader;

/**
 * @author christian.chacon
 */
public class LeaderResponseDTO {

    private Integer id;
    private String title;
    private String status;
    private String description;
    private Integer globerId;
    private Boolean hasLeader;
    private Boolean active;
    private String origin;
    private String parentMission;
    private String priority;
    private String guide;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getGloberId() {
        return globerId;
    }

    public void setGloberId(Integer globerId) {
        this.globerId = globerId;
    }

    public Boolean getHasLeader() {
        return hasLeader;
    }

    public void setHasLeader(Boolean hasLeader) {
        this.hasLeader = hasLeader;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getParentMission() {
        return parentMission;
    }

    public void setParentMission(String parentMission) {
        this.parentMission = parentMission;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }
}
