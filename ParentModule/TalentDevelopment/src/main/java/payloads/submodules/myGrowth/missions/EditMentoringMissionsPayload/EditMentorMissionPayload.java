package payloads.submodules.myGrowth.missions.EditMentoringMissionsPayload;

import java.util.List;

/**
 * @author christian.chacon
 */
public class EditMentorMissionPayload {

    private String description;
    private Integer mentee;
    private Integer mentor;
    private Integer missionId;
    private List<Subject> subjects = null;
    private String title;
    private Integer workingEcosystem;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMentee() {
        return mentee;
    }

    public void setMentee(Integer mentee) {
        this.mentee = mentee;
    }

    public Integer getMentor() {
        return mentor;
    }

    public void setMentor(Integer mentor) {
        this.mentor = mentor;
    }

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getWorkingEcosystem() {
        return workingEcosystem;
    }

    public void setWorkingEcosystem(Integer workingEcosystem) {
        this.workingEcosystem = workingEcosystem;
    }

    /**
     * Default Constructor
     * @param description
     * @param mentee
     * @param mentor
     * @param missionId
     * @param subjects
     * @param title
     * @param workingEcosystem
     */
    public EditMentorMissionPayload(String description, Integer mentee, Integer mentor, Integer missionId, List<Subject> subjects, String title, Integer workingEcosystem) {
        this.description = description;
        this.mentee = mentee;
        this.mentor = mentor;
        this.missionId = missionId;
        this.subjects = subjects;
        this.title = title;
        this.workingEcosystem = workingEcosystem;
    }
}
