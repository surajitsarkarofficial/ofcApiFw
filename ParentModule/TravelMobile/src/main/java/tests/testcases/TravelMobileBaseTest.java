package tests.testcases;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.Status;

import endpoints.TravelMobileEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import properties.GlowProperties;
import properties.TravelMobileProperties;
import tests.GlowBaseTest;
import utils.ExtentHelper;
import utils.RestUtils;

public class TravelMobileBaseTest extends GlowBaseTest{
	public static String token;
	
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(ITestContext context) throws Exception {
		moduleTest=extent.createTest("Travel-Mobile");
		RestAssured.useRelaxedHTTPSValidation();
		new TravelMobileProperties().configureProperties();
		baseUrl = GlowProperties.baseURL;
	}
	@BeforeClass(alwaysRun = true)
	public void fakeLoginAndRegisterToken() throws Exception
	{		
		registertokenForFakeLogin(TravelMobileProperties.loggedInUser);
	}
	/**
	 * This method will perform fake login and register the token of the specified user
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public String registertokenForFakeLogin(String username) throws Exception
	{
		
		String endPointURL = baseUrl + TravelMobileEndpoints.registerTokenFakeLogin;
		RequestSpecification requestSpec = RestAssured.with().header("token", username).urlEncodingEnabled(false);
		Response response = new RestUtils().executeGET(requestSpec,endPointURL);
		validateResponseToContinueTest(response, 200, "Unable to register token for user "+username, false);
		token = new RestUtils().getValueFromResponse(response, "session_token").toString();
		ExtentHelper.getExtentTest().log(Status.INFO, "Token registered for user.Token is - "+token);
		return token;
	}

}
