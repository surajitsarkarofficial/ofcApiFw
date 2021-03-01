package tests.testhelpers.submodules.expense.features.addExpenseToReport;

import java.util.LinkedList;
import java.util.List;

import com.aventstack.extentreports.Status;

import database.submodules.expense.ExpenseDBHelper;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import dto.submodules.expense.expense.post.ExpenseDTO;
import dto.submodules.expense.report.post.Content;
import dto.submodules.expense.report.post.ReportDTO;
import endpoints.submodules.expense.features.addExpenseToReport.AddExpenseToReportEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.expense.features.addExpenseToReport.AddRemoveExpenseToReportPayload;
import payloads.submodules.expense.features.addExpenseToReport.Expense;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import tests.testhelpers.submodules.expense.features.expense.CreateNewExpenseTestHelper;
import tests.testhelpers.submodules.expense.features.report.CreateReportTestHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class AddRemoveExpenseToReportTestHelper extends ExpenseTestHelper {

	private String userName;
	
	public AddRemoveExpenseToReportTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}

	/**
	 * This method will create a payload in order to add or remove expense from a report.
	 * 
	 * @param createdExpense
	 * @param createdReport
	 * @return
	 * @throws Exception
	 */
	public AddRemoveExpenseToReportPayload createBasicAddExpenseToReportPayload (ExpenseDTO createdExpense, ReportDTO createdReport) throws Exception {
		AddRemoveExpenseToReportPayload addExpenseToReportPayload = new AddRemoveExpenseToReportPayload();
		addExpenseToReportPayload.setId(createdReport.getContent().getId());
		List<Expense> expenses = new LinkedList<>();
		Expense expense = new Expense(createdExpense.getContent().getId());
		expenses.add(expense);
		addExpenseToReportPayload.setExpenses(expenses);
		return addExpenseToReportPayload;
	}
	
	/**
	 * This method will perform a PUT in order to add or remove expense from a report.
	 * 
	 * @param addExpenseToReportPayload
	 * @return
	 * @throws Exception
	 */
	public Response addRemoveExpenseToReport(AddRemoveExpenseToReportPayload addExpenseToReportPayload) throws Exception {
		String requestURL = ExpenseBaseTest.baseUrl + AddExpenseToReportEndpoints.addExpenseToReport;
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON).body(addExpenseToReportPayload);
		Response response = new RestUtils().executePUT(requestSpecification, requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "User: " + userName);
		ExpenseBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ExpenseBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(addExpenseToReportPayload));
		ExpenseBaseTest.test.log(Status.INFO, "The expense "+addExpenseToReportPayload.getExpenses().get(0).getId()+
				" was updated to the report " + addExpenseToReportPayload.getId());
		return response;
	}
	
	/**
	 * This method will create a Report with one Expense. 
	 * The expense has a receipt.
	 * 
	 * @return
	 * @throws Exception
	 */
	public ReportWithExpenseDTO createReportWithExpense() throws Exception {
		CreateNewExpenseTestHelper createNewExpenseTestHelper = new CreateNewExpenseTestHelper(userName);
		CreateReportTestHelper createNewReportTestHelper = new CreateReportTestHelper(userName);
		ExpenseDTO createdExpense = createNewExpenseTestHelper.createExpenseWithReceipt();
		ReportDTO createdReport = createNewReportTestHelper.createBasicReport();
		AddRemoveExpenseToReportPayload addExpenseToReportPayload = createBasicAddExpenseToReportPayload(createdExpense, createdReport);
		Response response = addRemoveExpenseToReport(addExpenseToReportPayload);
		validateResponseToContinueTest(response, 200, "Add Expense To Report api call was not successful.", true);
		ReportWithExpenseDTO reportWithExpenseDTO = response.as(ReportWithExpenseDTO.class, ObjectMapperType.GSON);
		reportWithExpenseDTO.setAddExpenseToReportPayload(addExpenseToReportPayload);
		reportWithExpenseDTO.setAuthorId(new ExpenseDBHelper().getGloberId(userName));	
		return reportWithExpenseDTO;
	}
	
	/**
	 * This method will extract a ReportDTO from a ReportWithExpenseDTO.
	 * 
	 * @param reportWithExpenseDTO
	 * @return
	 * @throws Exception
	 */
	public ReportDTO extractReportDTOFromReportWithExpenseDTO (ReportWithExpenseDTO reportWithExpenseDTO) throws Exception {
		ReportDTO reportDTO = new ReportDTO();
		Content content = new Content();
		content.setId(reportWithExpenseDTO.getContent().getId());
		content.setTitle(reportWithExpenseDTO.getContent().getTitle());
		content.setOriginId(reportWithExpenseDTO.getContent().getOriginId());
		content.setStatus(reportWithExpenseDTO.getContent().getStatus());
		content.setClientName(reportWithExpenseDTO.getContent().getClientName());
		reportDTO.setContent(content);
		return reportDTO;
	}

}
