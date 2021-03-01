
package dto.submodules.manage.approverManagement.getCecos;

import java.util.List;

/**
 * @author german.massello
 *
 */
public class Content {

    private String totalItems;
    private List<ViewCecoApproverList> viewCecoApproverList = null;

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public List<ViewCecoApproverList> getViewCecoApproverList() {
        return viewCecoApproverList;
    }

    public void setViewCecoApproverList(List<ViewCecoApproverList> viewCecoApproverList) {
        this.viewCecoApproverList = viewCecoApproverList;
    }

}
