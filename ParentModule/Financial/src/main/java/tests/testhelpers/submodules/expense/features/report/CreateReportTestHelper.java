package tests.testhelpers.submodules.expense.features.report;

import com.aventstack.extentreports.Status;

import dto.submodules.expense.report.post.Content;
import dto.submodules.expense.report.post.ReportDTO;
import endpoints.submodules.expense.features.report.CreateReportEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.expense.features.report.ReportPayload;
import tests.testHelper.submodules.project.ProjectTestHelper;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class CreateReportTestHelper extends ExpenseTestHelper {

	private String userName;
	
	public CreateReportTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}

	/**
	 * This method will create a payload in order to create a report.
	 * 
	 * @return basicReportPayload
	 * @throws Exception
	 * @author german.massello
	 */
	public ReportPayload createBasicReportPayload() throws Exception {
		ProjectTestHelper projectTestHelper = new ProjectTestHelper(userName);
		ReportPayload basicReportPayload = new ReportPayload();
		basicReportPayload.withOriginId(projectTestHelper.getRandomProject().getProjectId());
		return basicReportPayload;
	}
	
	/**
	 * This method will perform a POST in order to create a report.
	 * 
	 * @param reportPayload
	 * @return reportDTO
	 * @throws Exception
	 * @author german.massello
	 */
	public ReportDTO createReport(ReportPayload reportPayload) throws Exception {
		RestUtils restUtils = new RestUtils();
		String requestURL = ExpenseBaseTest.baseUrl + CreateReportEndpoints.report;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON).body(reportPayload);
		Response response = restUtils.executePOST(requestSpecification, requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "POST - User: " + userName);
		ExpenseBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(reportPayload));		
		ReportDTO createdReport = response.as(ReportDTO.class, ObjectMapperType.GSON);
		createdReport.setReportPayload(reportPayload);
		return createdReport;
	}	
	
	/**
	 * This method will create a Report from scratch.
	 * @return reportDTO
	 * @throws Exception
	 * @author german.massello
	 */
	public ReportDTO createBasicReport() throws Exception {
		ReportPayload reportPayload = createBasicReportPayload();
		ReportDTO reportDTO = createReport(reportPayload);
		return reportDTO;
	}
	
	/**
	 * This method will convert a payload in to an ReportDTO.
	 * 
	 * @param reportPayload
	 * @return reportDTO
	 * @author german.massello
	 */
	public ReportDTO convertAPayloadIntoAnReportDTO(ReportPayload reportPayload) {
		ReportDTO reportDTO = new ReportDTO();
		Content content = new Content();
		content.setTitle(reportPayload.getTitle());
		content.setStatus("Draft Expense");
		content.setOriginId(reportPayload.getOriginId());
		reportDTO.setContent(content);
		return reportDTO;
	}
	
}
