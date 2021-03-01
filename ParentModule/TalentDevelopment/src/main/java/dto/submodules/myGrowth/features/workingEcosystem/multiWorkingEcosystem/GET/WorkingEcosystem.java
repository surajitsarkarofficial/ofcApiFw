package dto.submodules.myGrowth.features.workingEcosystem.multiWorkingEcosystem.GET;

/**
 * @author nadia.silva
 */
public class WorkingEcosystem {
    private Integer id;

    private String name;

    private Integer alberthaIdReference;

    private Boolean primary;

    private Boolean belongsToGlober;

    private Integer currentLevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAlberthaIdReference() {
        return alberthaIdReference;
    }

    public void setAlberthaIdReference(Integer alberthaIdReference) {
        this.alberthaIdReference = alberthaIdReference;
    }

    public Boolean getPrimary() {
        return primary;
    }

    public void setPrimary(Boolean primary) {
        this.primary = primary;
    }

    public Boolean getBelongsToGlober() {
        return belongsToGlober;
    }

    public void setBelongsToGlober(Boolean belongsToGlober) {
        this.belongsToGlober = belongsToGlober;
    }

    public Integer getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Integer currentLevel) {
        this.currentLevel = currentLevel;
    }

}