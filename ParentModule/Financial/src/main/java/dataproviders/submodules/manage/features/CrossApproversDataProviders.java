package dataproviders.submodules.manage.features;

import org.testng.annotations.DataProvider;

import dataproviders.submodules.manage.ManageDataProviders;
import parameters.submodules.manage.features.approverManagement.crossApprovers.GetAvailableCrossApproversParameters;
import parameters.submodules.manage.features.approverManagement.crossApprovers.GetCrossApproversParameters;
import parameters.submodules.manage.features.approverManagement.crossApprovers.PostCrossApproverParameters;

/**
 * @author german.massello
 *
 */
public class CrossApproversDataProviders extends ManageDataProviders {

	/**
	 * This data provider will return a GetAvailableCrossApproversParameters parameters object.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "getAvailableCrossApprovers")
	public Object[][] getAvailableCrossApprovers() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new GetAvailableCrossApproversParameters();
		return dataObject;
	}	
	
	/**
	 * This data provider will return a PostCrossApproverParameters parameters object.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "newCrossApprover")
	public Object[][] newCrossApprover() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new PostCrossApproverParameters();
		return dataObject;
	}

	/**
	 * This data provider will return a GetCrossApproversParameters parameters object.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "getCrossApprovers")
	public Object[][] getCrossApprovers() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new GetCrossApproversParameters();
		return dataObject;
	}

}
