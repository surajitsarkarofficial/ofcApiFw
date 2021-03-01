package dataproviders.submodules.reception;

import org.testng.annotations.DataProvider;

import database.submodules.reception.features.GetReceptionDBHelper;
import dataproviders.PurchasesDataProviders;
import parameters.submodules.reception.GetReceptionParameters;

/**
 * @author german.massello
 *
 */
public class ReceptionsForRequesterDataProviders extends PurchasesDataProviders {

	/**
	 * Global view
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "globalView")
	public Object[][] globalView() throws Exception {
		GetReceptionParameters parameters = (GetReceptionParameters) new GetReceptionParameters().setIsGlobalView("true").
				setUserName(new GetReceptionDBHelper().getReception(RECEPTION_COMPLETED_DB, REQUESTER_PUMA_ROLE).get("username")).
				setStatus(EMPTY_STATUS_REST);
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}

	/**
	 * Completed
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "completed")
	public Object[][] completed() throws Exception {
		GetReceptionParameters parameters = (GetReceptionParameters) new GetReceptionParameters().
				setUserName(new GetReceptionDBHelper().getReception(RECEPTION_COMPLETED_DB, REQUESTER_PUMA_ROLE).get("username")).
				setStatus(RECEPTION_COMPLETED_REST);
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}

	/**
	 * In Progress
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "inProgress")
	public Object[][] inProgress() throws Exception {
		GetReceptionParameters parameters = (GetReceptionParameters) new GetReceptionParameters().
				setUserName(new GetReceptionDBHelper().getReception(RECEPTION_IN_PROGRESS_DB, REQUESTER_PUMA_ROLE).get("username")).
				setStatus(RECEPTION_IN_PROGRESS_REST);
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}
	
	/**
	 * My receptions
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "myReceptions")
	public Object[][] myReceptions() throws Exception {
		GetReceptionParameters parameters = (GetReceptionParameters) new GetReceptionParameters().
				setUserName(new GetReceptionDBHelper().getReception(RECEPTION_IN_PROGRESS_DB, REQUESTER_PUMA_ROLE).get("username")).
				setStatus(EMPTY_STATUS_REST);
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}
}
