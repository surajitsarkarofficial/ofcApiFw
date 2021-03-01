package payloads.submodules.myGrowth.missions.EditMentoringMissionsPayload;

/**
 * @author christian.chacon
 */
public class Subject {

    private Boolean blocked;
    private Integer id;
    private String name;

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

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
}
