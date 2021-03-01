package dataproviders;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;

import constants.submodules.ReceptionConstants;
import constants.submodules.RequisitionConstants;
import database.PurchasesDBHelper;
import database.submodules.requisition.features.GetRequisitionDBHelper;
import parameters.submodules.quotation.GetQuotationParameters;

/**
 * @author german.massello
 *
 */
public class PurchasesDataProviders extends GlowDataProviders implements RequisitionConstants, ReceptionConstants {

	protected static Map<String,String> requisition = new HashMap<String,String>();
	
	/**
	 * This data provider will return an user that have the ServiceDesk rol.
	 * 
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "serviceDeskUser")
	public Object[][] serviceDeskUser() throws Exception {
		PurchasesDBHelper purchasesDBHelper = new PurchasesDBHelper();
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = purchasesDBHelper.getRandomGloberByRol("ServiceDesk");
		return dataObject;
	}

	/**
	 * This data provider will return a user with the facilities rol.
	 * @return dataObject
	 * @throws Exception
	 * @author german.massello
	 */
	@DataProvider(name = "facilitiesUser")
	public Object[][] facilitiesUser() throws Exception {
		GetQuotationParameters parameters = new GetQuotationParameters("QUOTATION", "", "true");
		parameters.setUserName( new PurchasesDBHelper().getRandomGloberByRol("Facilities"));
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = parameters;
		return dataObject;
	}
	
	/**
	 * Set requisition
	 * @throws SQLException
	 */
	protected void setRequisition() throws SQLException {
		requisition = new GetRequisitionDBHelper().getPumaRequester(RECEPTION_COMPLETE_STATUS_DB, REQUISITION_STATE);
		requisition.put("username", new PurchasesDBHelper().getRandomGloberByRol("Purchases"));
	}

}
