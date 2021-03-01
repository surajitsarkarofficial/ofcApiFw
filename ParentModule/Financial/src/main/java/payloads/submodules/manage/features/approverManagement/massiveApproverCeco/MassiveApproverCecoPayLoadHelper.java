
package payloads.submodules.manage.features.approverManagement.massiveApproverCeco;

import java.util.List;

import parameters.submodules.manage.features.approverManagement.massiveApproverCeco.MassiveApproverCecoParameters;
import tests.testhelpers.submodules.manage.features.approverManagement.GetAvailableApproversTestHelper;

/**
 * @author german.massello
 *
 */
public class MassiveApproverCecoPayLoadHelper {

    private String approverId;
    private List<String> cecoNumbers = null;
    private String isEntireCecos;

    
    /**
     * Default constructor.
     * @param parameter
     * @throws Exception 
     */
    public MassiveApproverCecoPayLoadHelper(MassiveApproverCecoParameters parameter) throws Exception {
    	this.cecoNumbers = parameter.getCecoNumbers();
    	this.approverId = new GetAvailableApproversTestHelper(parameter.getUsername()).getAvailableApproverForAllCecos().getId();
    	this.isEntireCecos = "false";
	}

    /**
     * This constructor will create a payload in order to add an approver to all cecos.
     * @param parameter
     * @param isEntireCecos
     * @throws Exception 
     */
    public MassiveApproverCecoPayLoadHelper(MassiveApproverCecoParameters parameter, IsEntireCecos isEntireCecos) throws Exception {
    	this.approverId = new GetAvailableApproversTestHelper(parameter.getUsername()).getAvailableApproverForAllCecos().getId();
    	this.isEntireCecos = "true";
	}
    
	public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public List<String> getCecoNumbers() {
        return cecoNumbers;
    }

    public void setCecoNumbers(List<String> cecoNumbers) {
        this.cecoNumbers = cecoNumbers;
    }

    public String getIsEntireCecos() {
        return isEntireCecos;
    }

    public void setIsEntireCecos(String isEntireCecos) {
        this.isEntireCecos = isEntireCecos;
    }

}
