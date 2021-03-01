package tests.testhelpers.submodules.expense.features.category;

import org.testng.SkipException;

import com.aventstack.extentreports.Status;

import dto.submodules.expense.category.CategoryDTOList;
import endpoints.submodules.expense.features.category.CategoryEndpoints;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class CategoryTestHelper extends ExpenseTestHelper {

	public CategoryTestHelper(String userName) throws Exception {
		super(userName);
	}
	
	/**
	 * This method will fetch all available categories in order to create a new expense.
	 * 
	 * @return
	 * @throws Exception
	 */
	public CategoryDTOList getCategories() throws Exception {
		RestUtils restUtils = new RestUtils();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId);
		String url = ExpenseBaseTest.baseUrl + CategoryEndpoints.categories;
		Response response = restUtils.executeGET(requestSpecification, url);
		new ExpenseBaseTest().validateResponseToContinueTest(response, 200, "Unable to fetch Available Categories Details.", true);
		ExpenseBaseTest.test.log(Status.INFO, "Available Categories details fetched successfully.");
		
		CategoryDTOList categoryDTOList = response.as(CategoryDTOList.class, ObjectMapperType.GSON);
		if (categoryDTOList.getContent().getTotalItems() == 0) {
			throw new SkipException("THERE IS NO ANY CATEGORY AVAILABLE ");}
		return categoryDTOList;
	}
	
	/**
	 * This method will fetch a  random category in order to create a new expense.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getRandomCategoryId() throws Exception {
		CategoryDTOList categoryDTOList = getCategories();
		return categoryDTOList.getContent().getContableCodeList().get(Utilities.
				getRandomNumberBetween(0, categoryDTOList.getContent().getTotalItems())).getId();
	}	

}
