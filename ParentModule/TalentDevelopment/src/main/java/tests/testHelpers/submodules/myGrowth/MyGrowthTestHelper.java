package tests.testHelpers.submodules.myGrowth;

import com.aventstack.extentreports.Status;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.TalentDevelopmentTestHelper;
import utils.RestUtils;

public class MyGrowthTestHelper extends TalentDevelopmentTestHelper {

    public MyGrowthTestHelper(String userName) throws Exception {
        new MyGrowthBaseTest().createSession(userName);

    }

    /**
     * Performs the get method with myGrowth sessions id and returns the response
     * @param requestURL
     * @param globerId
     * @return Response
     */
    public static Response getMethod(String requestURL, String globerId){
        RestUtils restUtils = new RestUtils();
        RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
                .sessionId(MyGrowthBaseTest.sessionId).contentType(ContentType.JSON);
        Response response = restUtils.executeGET(requestSpecification, requestURL);

        MyGrowthBaseTest.test.log(Status.INFO, "User: " + globerId);
        MyGrowthBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
        MyGrowthBaseTest.test.log(Status.INFO, "Get method was successful.");
        return response;
    }
}