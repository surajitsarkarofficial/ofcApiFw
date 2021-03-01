package dto.submodules.myGrowth.features.capabilities.GET;
/**
 * @author nadia.silva
 */
public class Subject {
    private String id;
    private String name;
    private Integer idArea;
    private Scores scores;
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

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public Scores getScores() {
        return scores;
    }

    public void setScores(Scores scores) {
        this.scores = scores;
    }

    public Integer getAlberthaReferenceId() {
        return alberthaReferenceId;
    }

    public void setAlberthaReferenceId(Integer alberthaReferenceId) {
        this.alberthaReferenceId = alberthaReferenceId;
    }

}
