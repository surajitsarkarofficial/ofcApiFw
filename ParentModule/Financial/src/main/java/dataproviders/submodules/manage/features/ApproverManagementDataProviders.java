package dataproviders.submodules.manage.features;

import org.testng.annotations.DataProvider;

import dataproviders.submodules.manage.ManageDataProviders;
import parameters.submodules.manage.features.approverManagement.GetActiveApproversAvailabletParameters;
import parameters.submodules.manage.features.approverManagement.massiveApproverCeco.MassiveApproverCecoParameters;
import parameters.submodules.manage.features.approverManagement.putSwapApprovers.PutSwapApproversParameters;

/**
 * @author german.massello
 *
 */
public class ApproverManagementDataProviders extends ManageDataProviders {

	/**
	 * This data provider will return a GetActiveApproversAvailableParameters parameters object.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "activeApproversAvailable")
	public Object[][] activeApproversAvailable() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new GetActiveApproversAvailabletParameters();
		return dataObject;
	}	

	/**
	 * This data provider will return a PutSwapApproversParameters parameters object.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "swapApprovers")
	public Object[][] swapApprovers() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new PutSwapApproversParameters();
		return dataObject;
	}	

	/**
	 * This data provider will return a PostMassiveApproverCecoParameters parameters object.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "massiveApproverCeco")
	public Object[][] massiveApproverCeco() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new MassiveApproverCecoParameters();
		return dataObject;
	}	

}
