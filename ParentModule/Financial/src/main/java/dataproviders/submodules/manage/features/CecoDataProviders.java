package dataproviders.submodules.manage.features;

import org.testng.annotations.DataProvider;

import dataproviders.submodules.manage.ManageDataProviders;
import parameters.submodules.manage.features.approverManagement.getCecoByApproverParameters.GetCecoByApproverParameters;
import parameters.submodules.manage.features.approverManagement.getCecoDetails.CrossApprover;
import parameters.submodules.manage.features.approverManagement.getCecoDetails.GetCecoDetailsParameters;
import parameters.submodules.manage.features.approverManagement.putCecoRequiredAllLevels.CecoRequiredAllLevelsParameters;

/**
 * @author german.massello
 *
 */
public class CecoDataProviders extends ManageDataProviders {

	/**
	 * This data provider will return a GetCecoDetailsParameters parameters object.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "getCecoWithCrossApproverDetails")
	public Object[][] getCecoWithCrossApproverDetails() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new GetCecoDetailsParameters(new CrossApprover());
		return dataObject;
	}	

	/**
	 * This data provider will return a PutCecoRequiredAllLevels parameters object.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "cecoRequiredAllLevels")
	public Object[][] cecoRequiredAllLevels() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new CecoRequiredAllLevelsParameters();
		return dataObject;
	}

	/**
	 * This data provider will return a GetCecoByApproverParameters parameters object.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "cecoByApproverParameters")
	public Object[][] cecoByApproverParameters() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new GetCecoByApproverParameters();
		return dataObject;
	}

}
