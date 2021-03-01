package dto.submodules.myGrowth.features.workingEcosystem.workingEcosystemSkills;

/**
 * @author christian.chacon
 */
public class SubjectDto {

    private Integer id;
    private String name;
    private String description;
    private Boolean suggested;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getSuggested() {
        return suggested;
    }

    public void setSuggested(Boolean suggested) {
        this.suggested = suggested;
    }

}
