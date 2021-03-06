package tests.testHelper.submodules.glober.features.basicinfo;

import dto.submodules.glober.features.basicinfo.BasicInfoDTO;
import dto.submodules.glober.features.basicinfo.Roles;
import endpoints.GlowEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.GlowBaseTest;
import tests.GlowBaseTestHelper;
import utils.RestUtils;

public class BasicInfoTestHelper extends GlowBaseTestHelper {

    public BasicInfoTestHelper(String userName) throws Exception {
        new GlowBaseTest().createSession(userName);
    }

    /**GET Method for globers/basicInfo method
     * @param username
     * @return
     * @throws Exception
     */
    public BasicInfoDTO getBasicInfo(String username) throws Exception {

        RestUtils restUtils = new RestUtils();

        String requestURL = GlowBaseTest.baseUrl + GlowEndpoints.basciInfo;
        RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
                .sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON);

        Response response = restUtils.executeGET(requestSpecification, requestURL);
        new GlowBaseTest().validateResponseToContinueTest(response, 200,
                "Get BasicInfo has failed.", true);
        BasicInfoDTO basicInfoDTO = response.as(BasicInfoDTO.class, ObjectMapperType.GSON);
        return basicInfoDTO;
    }

    /**
     * Returns whether a role exists for that person or not
     * @param getBasicInfoResponse
     * @param role
     * @return
     */
    public boolean getRoleExistence(BasicInfoDTO getBasicInfoResponse, String role){
        for (Roles elem: getBasicInfoResponse.getRoles()){
            if (elem.getName().equals(role)){
                return true;
            }
        }
        return false;
    }
}