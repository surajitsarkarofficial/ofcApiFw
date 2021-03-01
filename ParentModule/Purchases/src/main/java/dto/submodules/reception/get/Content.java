
package dto.submodules.reception.get;

import java.util.List;

/**
 * @author german.massello
 *
 */
public class Content {

    private String totalItems;
    private List<ReceptionList> receptionList = null;

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public List<ReceptionList> getReceptionList() {
        return receptionList;
    }

    public void setReceptionList(List<ReceptionList> receptionList) {
        this.receptionList = receptionList;
    }

}
