package tests.testHelpers.submodules.myGrowth.workingEcosystem;

import java.util.ArrayList;
import java.util.List;

import com.aventstack.extentreports.Status;

import dto.submodules.myGrowth.features.workingEcosystem.getMissions.GetGloberMissionsDTO;
import dto.submodules.myGrowth.features.workingEcosystem.getMissions.WorkingEcosystems;
import endpoints.submodules.myGrowth.features.workingEcosystem.WorkingEcosystemEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.myGrowth.workingEcosystem.SaveWorkingEcosystemPayload;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.MyGrowthTestHelper;
import utils.RestUtils;
import utils.Utilities;

public class WorkingEcosystemHelper extends MyGrowthTestHelper {
    /**
     * @author nadia.silva
     * @param userName
     * @throws Exception
     */
    public WorkingEcosystemHelper(String userName) throws Exception {
        super(userName);
    }
    /**
     * Performs GET Mehtod to multi working ecosystem service
     * @param globerId
     * @return Response
     */
    public Response getMultiWorkingEcosystems(String globerId){
        String requestUrl = MyGrowthBaseTest.baseUrl + WorkingEcosystemEndpoints.multiWorkingEcosystemUrl + globerId;
        return getMethod(requestUrl, globerId);
    }
    /**
     * Performs POST to save working ecosystem service
     * @param globerId
     * @param weID
     * @param weName
     * @return Response
     */
    public Response addNewWorkingEcosystemToGlober(String globerId, String weID, String weName){
        String requestUrl = MyGrowthBaseTest.baseUrl + WorkingEcosystemEndpoints.addWorkingEcosystemUrl;
        SaveWorkingEcosystemPayload saveWorkingEcosystemPayload = new SaveWorkingEcosystemPayload(globerId, false, weID, weName);
        RestUtils restUtils = new RestUtils();
        RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
                .sessionId(MyGrowthBaseTest.sessionId).contentType(ContentType.JSON).body(saveWorkingEcosystemPayload);
        Response response = restUtils.executePOST(requestSpecification, requestUrl);

        MyGrowthBaseTest.test.log(Status.INFO, "User: " + globerId);
        MyGrowthBaseTest.test.log(Status.INFO, "RequestURL: " + requestUrl);
        MyGrowthBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(saveWorkingEcosystemPayload));
        MyGrowthBaseTest.test.log(Status.INFO, "Working ecosystem of glober was created successfully.");
        return response;
    }

    /**
     * Performs GET to get glober missions for the working ecosystem given
     * @param globerId
     * @param we
     * @return Response
     */
    public Response getMissions (String globerId, String we){
        String requestUrl = MyGrowthBaseTest.baseUrl + String.format(WorkingEcosystemEndpoints.globerMissionUrl, globerId, we);
        Response response = getMethod(requestUrl, globerId);
        return response;
    }

    /**
     * Get a subject id and name from the primary working ecosystem of the glober
     * @return
     */
    public List<String> getSubjectIdAndNameOfPrimaryWE(GetGloberMissionsDTO getGloberMissionsDTO){
        List<String> subject = new ArrayList<>();
            for (WorkingEcosystems workingEcosystems : getGloberMissionsDTO.getWorkingEcosystems()){
                if(workingEcosystems.getPrimary()){
                    subject.add(workingEcosystems.getPositionCapabilities()[0].getAreas()[0].getSubjects()[0].getId());
                    subject.add(workingEcosystems.getPositionCapabilities()[0].getAreas()[0].getSubjects()[0].getName());
                }
            }
        return subject;
    }

}
