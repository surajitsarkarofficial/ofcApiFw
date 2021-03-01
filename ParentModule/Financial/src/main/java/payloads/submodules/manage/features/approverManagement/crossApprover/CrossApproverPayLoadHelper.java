
package payloads.submodules.manage.features.approverManagement.crossApprover;

import java.util.ArrayList;
import java.util.List;

import parameters.submodules.manage.features.approverManagement.crossApprovers.PostCrossApproverParameters;

/**
 * @author german.massello
 *
 */
public class CrossApproverPayLoadHelper {

    private List<Approver> approvers = new ArrayList<Approver>();

    /**
     * Default constructor
     * @param parameter
     */
    public CrossApproverPayLoadHelper(PostCrossApproverParameters parameter) {
    	approvers.add(new Approver(parameter.getNewCrossApproverId()));
	}

	public List<Approver> getApprovers() {
        return approvers;
    }

    public void setApprovers(List<Approver> approvers) {
        this.approvers = approvers;
    }

}
