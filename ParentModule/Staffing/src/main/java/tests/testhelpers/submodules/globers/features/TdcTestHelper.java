package tests.testhelpers.submodules.globers.features;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;
import com.google.gson.Gson;

import database.submodules.globers.features.TdcDBHelper;
import endpoints.submodules.globers.features.TdcAPIEndPoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.globers.GlobersBaseTest;
import tests.testhelpers.submodules.globers.GlobersTestHelper;
import utils.ExtentHelper;
import utils.RestUtils;
import utils.Utilities;

public class TdcTestHelper extends GlobersTestHelper {

	String message = null;
	int viewId,parentViewId,offset,limit;
	String sorting = "Astage";
	Gson gson;
	SoftAssert soft_assert;
	TdcDBHelper tdcDBHelper;
	RestUtils restUtils = new RestUtils();

	public TdcTestHelper(String userName) throws Exception {
		super(userName);
		soft_assert = new SoftAssert();
		tdcDBHelper = new TdcDBHelper();
	}

	/**
	 * This method will fetch TDC filter
	 * 
	 * @param name
	 * @param response
	 * @return
	 */
	public Response getTdcFilter(String name, Response response) {
		String todayDate = Utilities.getTodayDate("dd-MM-yyyy");
		String futureDate = Utilities.getFutureDate("dd-MM-yyyy", 30);
		viewId = 2;

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String requestUrl = GlobersBaseTest.baseUrl
				+ String.format(TdcAPIEndPoints.getTDCFilter,viewId ,name, todayDate, futureDate);

		response = restUtils.executeGET(requestSpecification, requestUrl);

		ExtentHelper.test.log(Status.INFO, "Request URL : " + requestUrl);
		return response;
	}

	/**
	 * This method will fetch TDC value
	 * @param name
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Response getTdcValue(String name, Response response) throws Exception {
		String todayDate = Utilities.getTodayDate("dd-MM-yyyy");
		String futureDate = Utilities.getFutureDate("dd-MM-yyyy", 30);

		int i = Utilities.getRandomDay();

		int parentViewId = 2, offset = 0;
		String sorting = "Aposition", benchDateRange = "";
		viewId = 2;

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String requestUrl = GlobersBaseTest.baseUrl + String.format(TdcAPIEndPoints.getPartialNameForStgUrl,
				viewId ,name, parentViewId, offset, sorting, benchDateRange, todayDate, futureDate);

		response = restUtils.executeGET(requestSpecification, requestUrl);

		String jsonPathForStgType = ("details.globerDTOList[" + i + "].type");
		String StgType = restUtils.getValueFromResponse(response, jsonPathForStgType).toString();

		String jsonPathForStgTdcName = ("details.globerDTOList[" + i + "].tdcName");
		String StgTdcName = restUtils.getValueFromResponse(response, jsonPathForStgTdcName).toString();

		String jsonPathForGloberId = ("details.globerDTOList[" + i + "].globerId");
		String globerId = restUtils.getValueFromResponse(response, jsonPathForGloberId).toString();

		String tdcFromDb = tdcDBHelper.getGloberTdc(globerId);

		if (StgType.equals("In Pipe") || StgType.equals("Candidate") || StgType.equals("New Hire")) {
			soft_assert.assertEquals(tdcFromDb, StgTdcName, "TDC Name and TDC value from DB are not matching");
		}
		return response;
	}

	/**
	 * This test will check TDC History for each Glober
	 * 
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Response getTdcHistoryDetails(Response response) throws Exception {
		String[] stgTdcDetails = tdcDBHelper.getStgTdcId();
		String globerId = tdcDBHelper.getGloberIdFromGlobalId(stgTdcDetails[0]);
		String stgGloberType = tdcDBHelper.getGloberType(globerId);
		String action = "All";

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String requestUrl = GlobersBaseTest.baseUrl + String.format(TdcAPIEndPoints.stgTDCHistory, stgGloberType,
				globerId, action, stgTdcDetails[0]);

		response = restUtils.executeGET(requestSpecification, requestUrl);

		ExtentHelper.test.log(Status.INFO, "Request URL : " + requestUrl);
		return response;
	}

	/**
	 * This test will check TDC for each Glober based on their location
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response getTdcofGloberBasedOnLocation(Response response, String name) throws Exception {
		String globalId = tdcDBHelper.getGlobalId("Glober");
		String globerId = tdcDBHelper.getGloberIdFromGlobalId(globalId);
		String type = "Glober", isStgFullNameRequired = "true";
		viewId = 5;

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String requestUrl = GlobersBaseTest.baseUrl + String.format(
				TdcAPIEndPoints.getPartialNameForAllGlobersTdc,viewId, globerId, type, name, isStgFullNameRequired);

		response = restUtils.executeGET(requestSpecification, requestUrl);

		return response;
	}

	/**
	 * This test will check all Globers based on their location
	 * @param response
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Response getAllGlobersBasedOnLocation(Response response, String name) throws Exception {
		int parentViewId = 5, offset = 0;
		String globalId = tdcDBHelper.getGlobalId("Glober");
		String globerId = tdcDBHelper.getGloberIdFromGlobalId(globalId);
		viewId = 5;
		
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlobersBaseTest.sessionId);

		String requestAllGloberUrl = GlobersBaseTest.baseUrl + String
				.format(TdcAPIEndPoints.getPartialNameForAllGlobers,viewId, name, parentViewId, offset, globerId);

		response = restUtils.executeGET(requestSpecification, requestAllGloberUrl);

		return response;
	}

	/**
	 * This test will return TDC data from Excel sheet
	 * @return
	 * @throws IOException
	 */
	public HashMap<String, String> readExcel() throws IOException {
		FileInputStream file = new FileInputStream(
				System.getProperty("user.dir") + "/src/main/resources/datafiles/globers/features/tdc/TDC_Mapping.xls");

		HSSFWorkbook workbook = new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);

		HashMap<String, String> hmap = new HashMap<String, String>();
		boolean b = true;
		Iterator<Row> rowIterator = sheet.iterator();
		String key = null, value = null;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.iterator(); // row.cellIterator();-- also works well
			while (cellIterator.hasNext()) {

				Cell cell = cellIterator.next();
				if (b) {
					key = cell.getStringCellValue();
					b = false;
				} else {
					value = cell.getStringCellValue();
					b = true;
				}
			}

			hmap.put(key, value);
		}
		workbook.close();
		return hmap;
	}
}
