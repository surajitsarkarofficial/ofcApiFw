package dto.submodules.myGrowth.features.workingEcosystem.workingEcosystemSkills;

import java.util.List;

/**
 * @author christian.chacon
 */
public class Details {

    private Integer workingEcosystemId;
    private String workingEcosystemName;
    private Integer alberthaReference;
    private Integer globerId;
    private String globerName;
    private Integer countCurrentLevel;
    private Integer countNextLevel;
    private List<SubjectDto> subjects = null;

    public Integer getWorkingEcosystemId() {
        return workingEcosystemId;
    }

    public void setWorkingEcosystemId(Integer workingEcosystemId) {
        this.workingEcosystemId = workingEcosystemId;
    }

    public String getWorkingEcosystemName() {
        return workingEcosystemName;
    }

    public void setWorkingEcosystemName(String workingEcosystemName) {
        this.workingEcosystemName = workingEcosystemName;
    }

    public Integer getAlberthaReference() {
        return alberthaReference;
    }

    public void setAlberthaReference(Integer alberthaReference) {
        this.alberthaReference = alberthaReference;
    }

    public Integer getGloberId() {
        return globerId;
    }

    public void setGloberId(Integer globerId) {
        this.globerId = globerId;
    }

    public String getGloberName() {
        return globerName;
    }

    public void setGloberName(String globerName) {
        this.globerName = globerName;
    }

    public Integer getCountCurrentLevel() {
        return countCurrentLevel;
    }

    public void setCountCurrentLevel(Integer countCurrentLevel) {
        this.countCurrentLevel = countCurrentLevel;
    }

    public Integer getCountNextLevel() {
        return countNextLevel;
    }

    public void setCountNextLevel(Integer countNextLevel) {
        this.countNextLevel = countNextLevel;
    }

    public List<SubjectDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectDto> subjects) {
        this.subjects = subjects;
    }

}
