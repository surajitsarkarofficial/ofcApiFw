package tests.testHelpers.submodules.myGrowth.bmeIntegrationHelper;

import com.aventstack.extentreports.Status;

import endpoints.submodules.myGrowth.features.bmeIntegration.BetterMeIntegrationEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.myGrowth.BmeIntegration.CreateGoalPayload;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.MyGrowthTestHelper;
import utils.RestUtils;
import utils.Utilities;

public class CreateGoalHelper extends MyGrowthTestHelper {

    private String userName;

    public CreateGoalHelper(String userName) throws Exception {
        super(userName);
        this.userName = userName;
    }

    /**
     * This method will perform a POST for the goal creation
     * @param createGoalPayload
     * @return
     * @throws Exception
     */
    public Response createGoalBme(CreateGoalPayload createGoalPayload) throws Exception {
        RestUtils restUtils = new RestUtils();
        String requestURL = MyGrowthBaseTest.baseUrl + BetterMeIntegrationEndpoints.goalCreationUrl;
        RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
                .sessionId(MyGrowthBaseTest.sessionId).contentType(ContentType.JSON).body(createGoalPayload);
        Response response = restUtils.executePOST(requestSpecification, requestURL);

        MyGrowthBaseTest.test.log(Status.INFO, "User: " + userName);
        MyGrowthBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
        MyGrowthBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(createGoalPayload));
        MyGrowthBaseTest.test.log(Status.INFO, "The goal was created successfully.");
        return response;
    }
}