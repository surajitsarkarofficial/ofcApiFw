package tests.testhelpers.submodules.expense.features.report;

import com.aventstack.extentreports.Status;

import dto.submodules.expense.report.post.ReportDTO;
import endpoints.submodules.expense.features.report.CreateReportEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.expense.features.report.ReportPayload;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class EditReportTestHelper extends ExpenseTestHelper {

	private String userName;
	
	public EditReportTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}
	
	/**
	 * This method will perform a PUT in order to edit a report.
	 * 
	 * @param reportPayload
	 * @return
	 * @throws Exception
	 */
	public ReportDTO editReport(ReportPayload reportPayload) throws Exception {
		RestUtils restUtils = new RestUtils();
		String requestURL = ExpenseBaseTest.baseUrl + CreateReportEndpoints.report;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON).body(reportPayload);
		Response response = restUtils.executePUT(requestSpecification, requestURL);
		new ExpenseBaseTest().validateResponseToContinueTest(response, 200,
				"Edit Report api call was not successful.", true);
		ExpenseBaseTest.test.log(Status.INFO, "User: " + userName);
		ExpenseBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(reportPayload));		
		ExpenseBaseTest.test.log(Status.INFO, "The report was edited successful.");
		ReportDTO editedReport = response.as(ReportDTO.class, ObjectMapperType.GSON);
		editedReport.setReportPayload(reportPayload);
		return editedReport;
	}
	

}
