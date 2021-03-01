package tests.testHelpers.submodules.myGrowth.selfEndorsementHelper;

import com.aventstack.extentreports.Status;

import endpoints.submodules.myGrowth.features.capabilities.SelfEndorsementEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.myGrowth.selfEndorsement.SelfEndorsementPayload;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.MyGrowthTestHelper;
import utils.RestUtils;
import utils.Utilities;

public class SelfEndorsementHelper extends MyGrowthTestHelper {

    public SelfEndorsementHelper(String userName) throws Exception {
        super(userName);
    }
    /**
     * This method will perform a POST for the self endorsement service
     * @param selfEndorsementPayload, userName
     * @return Response
     * @throws Exception
     */
    public Response createSelfEndorsementOfGlober(SelfEndorsementPayload selfEndorsementPayload, String userName) throws Exception {
        RestUtils restUtils = new RestUtils();
        String requestURL = MyGrowthBaseTest.baseUrl + SelfEndorsementEndpoints.selfEndorsementUrl;
        RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
                .sessionId(MyGrowthBaseTest.sessionId).contentType(ContentType.JSON).body(selfEndorsementPayload);
        Response response = restUtils.executePOST(requestSpecification, requestURL);

        MyGrowthBaseTest.test.log(Status.INFO, "User: " + userName);
        MyGrowthBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
        MyGrowthBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(selfEndorsementPayload));
        MyGrowthBaseTest.test.log(Status.INFO, "Self endorsement of glober was created successfully.");
        return response;
    }
}
