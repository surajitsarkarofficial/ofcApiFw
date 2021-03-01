package tests.testhelpers.submodules.expense.features.receipt;

import java.io.File;

import org.testng.SkipException;

import com.aventstack.extentreports.Status;

import database.submodules.expense.ExpenseDBHelper;
import dto.submodules.expense.receipt.post.ReceiptDTO;
import endpoints.submodules.expense.features.receipt.ReceiptEndpoints;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import properties.expense.ExpenseProperties;
import tests.testcases.submodules.expense.ExpenseBaseTest;
import tests.testhelpers.submodules.expense.ExpenseTestHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class UploadReceiptTestHelper extends ExpenseTestHelper {

	String userName;
	
	public UploadReceiptTestHelper(String userName) throws Exception {
		super(userName);
		this.userName = userName;
	}

	/**
	 * This method will perform a POST in order to upload a receipt file.
	 * 
	 * @return
	 * @throws Exception
	 */
	public ReceiptDTO uploadReceipt() throws Exception {
		RestUtils restUtils = new RestUtils();
		ExpenseDBHelper expenseDBHelper = new ExpenseDBHelper();
		
		String requestURL = ExpenseBaseTest.baseUrl + ReceiptEndpoints.uploadReceipt;
		File file = new File(new ExpenseProperties().recieptPath + "receipt.png");
		
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(ExpenseBaseTest.sessionId).contentType("multipart/form-data")
				.multiPart("file", file)
				.formParam("id", expenseDBHelper.getGloberId(userName))
				.formParam("fileName", "receipt.png")
				.formParam("date", Utilities.getTodayInMilliseconds())
				.formParam("disabled", "false")	;
		
		Response response = restUtils.executePOST(requestSpecification, requestURL);
		if (response.getStatusCode()!=201) throw new SkipException("upload file services is not working, status:"+response.getStatusCode()+" message:"+response.getBody().asString());
		ReceiptDTO updloadedReceipt = response.as(ReceiptDTO.class, ObjectMapperType.GSON);
		ExpenseBaseTest.test.log(Status.INFO, "User: " + userName);
		ExpenseBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
		ExpenseBaseTest.test.log(Status.INFO,"Receipt was uploaded successfully.");
		return updloadedReceipt;
	}
	
}
