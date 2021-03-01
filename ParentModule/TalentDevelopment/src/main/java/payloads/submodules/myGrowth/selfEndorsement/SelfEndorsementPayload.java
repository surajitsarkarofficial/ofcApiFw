package payloads.submodules.myGrowth.selfEndorsement;

import java.util.LinkedList;
import java.util.List;

public class SelfEndorsementPayload {

    private String globerId;

    private List<ListAreaDato> listAreaData;

    public String getGloberId() {
        return globerId;
    }

    public void setGloberId(String globerId) {
        this.globerId = globerId;
    }

    public List<ListAreaDato> getListAreaData() {
        return listAreaData;
    }

    /**
     * Create payload for self endorsement service
      * @param globerId
     */
    public SelfEndorsementPayload(String globerId){
        this.globerId = globerId;
        this.listAreaData = new LinkedList<>();
        this.listAreaData.add(new ListAreaDato());
    }

    public void setListAreaData(List<ListAreaDato> listAreaData) {
        this.listAreaData = listAreaData;
    }

}
