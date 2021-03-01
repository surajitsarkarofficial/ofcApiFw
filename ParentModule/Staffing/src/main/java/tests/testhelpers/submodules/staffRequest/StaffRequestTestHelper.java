package tests.testhelpers.submodules.staffRequest;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.aventstack.extentreports.Status;

import dto.submodules.staffRequest.features.PositionNeedDTOListSrRevamp;
import endpoints.submodules.staffRequest.features.GlobalStaffRequestAPIEndPoints;
import endpoints.submodules.staffRequest.features.MarkGloberFitEndPoints;
import endpoints.submodules.staffRequest.features.SuggestGloberEndPoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.staffRequest.features.SuggestGloberPayloadHelper;
import tests.GlowBaseTest;
import tests.testcases.StaffingBaseTest;
import tests.testcases.submodules.staffRequest.StaffRequestBaseTest;
import tests.testhelpers.StaffingTestHelper;
import utils.ExtentHelper;

public class StaffRequestTestHelper extends StaffingTestHelper{
	
	public StaffRequestTestHelper(String userName) throws Exception {
		new StaffRequestBaseTest().createSession(userName);
	}
	
	/**

	 * This method will get SR details from grid
	 * 
	 * @param name
	 * @param globerType
	 * @return Response
	 * @throws Exception
	 */
	public Response getSRDetailsFromGrid(String globerId) throws Exception {
		int viewId = 7, parentViewId = 7, offset = 0, limit = 50;
		String sorting = "Astage";

		String requestUrl = StaffingBaseTest.baseUrl + String.format(GlobalStaffRequestAPIEndPoints.srGrid, viewId,
				globerId, parentViewId, offset, limit, sorting);

		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);

		Response response = restUtils.executeGET(requestSpecification, requestUrl);

		ExtentHelper.test.log(Status.INFO, "Fetched all SR Details from Grid");
		ExtentHelper.test.log(Status.INFO, "Able to load SR Grid.");
		return response;
	}

	/**
	 * Get Albertha skills for new SR 
	 * @param jsonpath
	 * @return
	 */
	public List<PositionNeedDTOListSrRevamp> getPositionSRRevampDTOList(JsonPath jsonpath){
		int skillCount = 8;
		PositionNeedDTOListSrRevamp positionNeedDTOListSrRevamp;
		List<PositionNeedDTOListSrRevamp> listOfSkills = new ArrayList<PositionNeedDTOListSrRevamp>();
		for(int i=3; i<skillCount; i++) {
			int alberthaId = jsonpath.get("data[" + i + "].id");
			String competencyId = null;
			String competencyName = jsonpath.get("data[" + i + "].name");
			int evidenceValue = 3;
			String importance = "2";
			String importanceName = "Required";
			positionNeedDTOListSrRevamp = new PositionNeedDTOListSrRevamp(alberthaId, competencyId, competencyName, evidenceValue, importance, importanceName);
			listOfSkills.add(positionNeedDTOListSrRevamp);
		}
		return listOfSkills;
	}

	 /** This method will return position id from the json which is used to suggest glober
	 * @param globerType
	 * @param planType
	 * @return string
	 * @throws Exception
	 * @author akshata.dongare
	 */
	public String getPositionIdFromGloberSuggestion(String globerType, String planType)
			throws Exception {
		String positionIdForStaffPlanId = null;
		String jsonBody = new SuggestGloberPayloadHelper().getSuggestGloberValidPayload(globerType, planType);
		if (jsonBody == null) {
			StaffRequestBaseTest.test.log(Status.SKIP, "Json body is null hence can not suggest glober to a SR");
		} else {
			RequestSpecification reqSpec = RestAssured.with().sessionId(StaffRequestBaseTest.sessionId).contentType(ContentType.JSON)
					.body(jsonBody);
			String suggestGloberUrl = StaffRequestBaseTest.baseUrl + SuggestGloberEndPoints.suggestGloberUrl;
			restUtils.executePOST(reqSpec, suggestGloberUrl);
		}
		JSONObject jo = new JSONObject(jsonBody);
		positionIdForStaffPlanId = jo.getString("positionId");
		return positionIdForStaffPlanId;
	}
	
	/**
	 * Get staff plan id from position id
	 * @return String
	 * @throws Exception
	 */
	public String getStaffPlanId(String globerType) throws Exception {
		String positionIdForStaffPlanId = getPositionIdFromGloberSuggestion(globerType,"Low");
		String staffPlanIdUrl = StaffRequestBaseTest.baseUrl + String.format(MarkGloberFitEndPoints.getStaffPlanId, positionIdForStaffPlanId,true);
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(StaffRequestBaseTest.sessionId);
		
		Response response = restUtils.executeGET(requestSpecification, staffPlanIdUrl);
		new StaffRequestBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch staff plan Id", true);
		
		String staffPlanIdJsonPath = "details[0].staffPlanId";
		String staffPlanId = restUtils.getValueFromResponse(response, staffPlanIdJsonPath).toString();
		return staffPlanId;
	}
}
