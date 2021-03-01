package tests.testhelpers.submodules.expense.features.expense;

import org.testng.Assert;

import dto.submodules.expense.expense.get.ExpenseDTOList;
import dto.submodules.expense.expense.post.Content;
import dto.submodules.expense.expense.post.ExpenseDTO;
import endpoints.submodules.expense.features.expense.GetExpenseEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import utils.RestUtils;
import utils.UtilsBase;

/**
 * @author german.massello
 *
 */
public class GetExpensesListTestHelper extends ExpenseTestHelper {

	private String userName;
	
	public GetExpensesListTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}

	/**
	 * This method will perform a GET in order to fetch all expenses, after that is going to perform 
	 * a search for a particular expense and return it.
	 * 
	 * @param createdExpense
	 * @return
	 * @throws Exception
	 */
	public ExpenseDTO getExpenseFromTheExpensesList(ExpenseDTO createdExpense) throws Exception {
		ExpenseDTO fetchedExpense = null;
		RestUtils restUtils = new RestUtils();
		String requestURL = ExpenseBaseTest.baseUrl + String.format(GetExpenseEndpoints.getExpensesList
				,"false","BILLABLE_AND_NO_BILLABLE","1","100","0");
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType(ContentType.JSON);
		Response response = restUtils.executeGET(requestSpecification, requestURL);
		new ExpenseBaseTest().validateResponseToContinueTest(response, 200,
				"Get Expenses List api call was not successful.", true);
		ExpenseDTOList fetchedExpensesList = response.as(ExpenseDTOList.class, ObjectMapperType.GSON);
		for (int i=0;i<fetchedExpensesList.getContent().getTotalItems();i++) {
			if (fetchedExpensesList.getContent().getExpenseList().get(i).getId().equals(createdExpense.getContent().getId())){
				fetchedExpense = fetchAParticularExpense(fetchedExpensesList, i);
				break;
			}
		}
		if (fetchedExpense==null) Assert.fail("The expense was not found");
		UtilsBase.log("User: " + userName);
		UtilsBase.log("RequestURL: " + requestURL);
		UtilsBase.log("The expense was fetched successful");		
		return fetchedExpense;
	}
	
	/**
	 * This method will return a particular ExpenseDTO.
	 * 
	 * @param fetchedExpenses
	 * @param position
	 * @return
	 */
	private ExpenseDTO fetchAParticularExpense (ExpenseDTOList fetchedExpenses, int position) {
		ExpenseDTO fetchedExpense = new ExpenseDTO();
		Content content = new Content();
		content.setAmount(fetchedExpenses.getContent().getExpenseList().get(position).getAmount());
		content.setCategoryId(fetchedExpenses.getContent().getExpenseList().get(position).getCategoryId());
		content.setCurrencyId(fetchedExpenses.getContent().getExpenseList().get(position).getCurrencyId());
		content.setProvider(fetchedExpenses.getContent().getExpenseList().get(position).getProvider());
		content.setTitle(fetchedExpenses.getContent().getExpenseList().get(position).getTitle());
		content.setPaymentType("Cash");
		fetchedExpense.setMessage("Success");
		fetchedExpense.setStatus("OK");
		fetchedExpense.setContent(content);
		return fetchedExpense;
	}
	
}
