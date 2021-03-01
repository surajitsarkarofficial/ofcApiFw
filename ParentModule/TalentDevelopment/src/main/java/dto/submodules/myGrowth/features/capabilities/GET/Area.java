package dto.submodules.myGrowth.features.capabilities.GET;

import java.util.List;

/**
 * @author nadia.silva
 */
public class Area {
    private String id;
    private String name;
    private Integer idPositionCapability;
    private String description;
    private String level;
    private Scores scores;
    private List<Subject> subjects = null;
    private Integer alberthaReferenceId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdPositionCapability() {
        return idPositionCapability;
    }

    public void setIdPositionCapability(Integer idPositionCapability) {
        this.idPositionCapability = idPositionCapability;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Scores getScores() {
        return scores;
    }

    public void setScores(Scores scores) {
        this.scores = scores;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Integer getAlberthaReferenceId() {
        return alberthaReferenceId;
    }

    public void setAlberthaReferenceId(Integer alberthaReferenceId) {
        this.alberthaReferenceId = alberthaReferenceId;
    }

}