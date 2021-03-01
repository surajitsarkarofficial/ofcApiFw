
package payloads.submodules.manage.features.approverManagement.crossApprover;


/**
 * @author german.massello
 *
 */
public class Approver {

    private String globerId;

    /**
     * Default constructor
     * @param globerId
     */
    public Approver(String globerId) {
		this.globerId = globerId;
	}

	public String getGloberId() {
        return globerId;
    }

    public void setGloberId(String globerId) {
        this.globerId = globerId;
    }

}
