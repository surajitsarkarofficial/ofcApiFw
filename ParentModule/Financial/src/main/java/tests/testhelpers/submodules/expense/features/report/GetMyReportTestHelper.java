package tests.testhelpers.submodules.expense.features.report;

import org.testng.Assert;

import com.aventstack.extentreports.Status;

import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import dto.submodules.expense.report.get.GetReportDTOList;
import dto.submodules.expense.report.post.Content;
import dto.submodules.expense.report.post.ReportDTO;
import endpoints.submodules.expense.features.report.GetReportReportEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.expense.features.getReports.GetExpenseReportParameters;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import utils.RestUtils;

/**
 * @author german.massello
 *
 */
public class GetMyReportTestHelper extends ExpenseTestHelper {

	private String userName;
	
	public GetMyReportTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}
	
	/**
	 * This method will perform a GET in order to fetch all reports, after that is going to perform
	 *  a search for a particular report and return it.
	 *  
	 * @param reportWithExpense
	 * @param parameters
	 * @return ReportDTO
	 * @throws Exception
	 */
	public ReportDTO getReportFromTheReportList(ReportWithExpenseDTO reportWithExpense, GetExpenseReportParameters parameters) throws Exception {
		ReportDTO fetchedReport = null;
		RestUtils restUtils = new RestUtils();
		String requestURL = ExpenseBaseTest.baseUrl + String.format(GetReportReportEndpoints.getReports
				,parameters.getStatus(),parameters.getIsEditable(),parameters.getIsApprovalView(),parameters.getIsGlobalView(),
				parameters.getPageSize(),parameters.getPageNum(),parameters.getType(),parameters.getSortAscending(),
				parameters.getSortCriteria(),parameters.getUsdFrom(),parameters.getUsdTo(),parameters.getIsBenefit(),parameters.getReportId());
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON);
		Response response = restUtils.executeGET(requestSpecification, requestURL);
		new ExpenseBaseTest().validateResponseToContinueTest(response, 200,
				"Get Report List api call was not successful.", true);
		GetReportDTOList fetchedReportList = response.as(GetReportDTOList.class, ObjectMapperType.GSON);
		for (int i=0;i<fetchedReportList.getContent().getTotalItems();i++) {
			if (fetchedReportList.getContent().getExpensesReportList().get(i).getId().equals(reportWithExpense.getContent().getId())){
				fetchedReport = fetchAParticularReport(fetchedReportList, i);
				break;
			}
		}
		if (fetchedReport==null) Assert.fail("The report "+reportWithExpense.getContent().getId()+" was not founded");
		fetchedReport.getContent().setClientName(reportWithExpense.getContent().getClientName());
		fetchedReport.setGetReportDTOList(fetchedReportList);
		ExpenseBaseTest.test.log(Status.INFO, "User: " + userName);
		ExpenseBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "The reports were fetched successful.");
		return fetchedReport;
	}
	
	/**
	 * This method will return a particular ReportDTO.
	 * 
	 * @param fetchedReports
	 * @param position
	 * @return ReportDTO
	 */
	private ReportDTO fetchAParticularReport (GetReportDTOList fetchedReports, int position) {
		ReportDTO fetchedReport = new ReportDTO();
		Content content = new Content();
		content.setId(fetchedReports.getContent().getExpensesReportList().get(position).getId());
		content.setTitle(fetchedReports.getContent().getExpensesReportList().get(position).getTitle());
		content.setOriginId(fetchedReports.getContent().getExpensesReportList().get(position).getOriginId());
		content.setStatus(fetchedReports.getContent().getExpensesReportList().get(position).getStatus());
		fetchedReport.setContent(content);
		fetchedReport.setMessage("Success");
		fetchedReport.setStatus("OK");
		return fetchedReport;
	}

}
