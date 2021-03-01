package payloads.submodules.myGrowth.missions;

/**
 * @author christian.chacon
 */
public class UpdateMissionGloberPayload {

    private Integer globerId;
    private Integer id;
    private String status;

    public Integer getGloberId() {
        return globerId;
    }

    public void setGloberId(Integer globerId) {
        this.globerId = globerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UpdateMissionGloberPayload(Integer globerId, Integer id, String status) {
        this.globerId = globerId;
        this.id = id;
        this.status = status;
    }
}
