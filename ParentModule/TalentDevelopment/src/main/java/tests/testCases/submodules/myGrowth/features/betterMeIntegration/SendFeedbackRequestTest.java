package tests.testCases.submodules.myGrowth.features.betterMeIntegration;

import static utils.GlowEnums.ExecutionSuiteGroups.ExeGroups.Regression;
import static utils.GlowEnums.ExecutionSuiteGroups.ExeGroups.Sanity;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.submodules.myGrowth.MyGrowthDataProdivers;
import dto.submodules.myGrowth.features.bmeIntegration.ResponseBodySendFeedbackRequest;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.myGrowth.BmeIntegration.PostRequestFeedbackMission;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.mentor.MentorHelper;

/**
 * @author christian.chacon
 */
public class SendFeedbackRequestTest extends MyGrowthBaseTest {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(){
        featureTest=subModuleTest.createNode("SendFeedback request Test");
    }

    /**
     * Send a feedback request to mentor when a mentoring mission is closed
     * @param mapValue
     * @throws Exception
     * @author christian.chacon
     */
    @Test(dataProvider = "usersWithClosedMission", dataProviderClass = MyGrowthDataProdivers.class, groups = {Sanity, Regression})
    public void sendFeedbackRequestToMentor(Map<Object, Object> mapValue) throws Exception {
        MentorHelper mentorHelper=new MentorHelper(mapValue.get("username").toString());
        Response response=mentorHelper.postSendFeedbackRequest(new PostRequestFeedbackMission(Integer.parseInt(mapValue.get("globerId").toString()), mapValue.get("missionId").toString(), Integer.parseInt(mapValue.get("workingEcosistemId").toString())));
        validateResponseToContinueTest(response, 201, "Actual response status code is "+response.getStatusCode()+" and expected response status code is 201", true);
        ResponseBodySendFeedbackRequest responseBodySendFeedbackRequest=response.as(ResponseBodySendFeedbackRequest.class, ObjectMapperType.GSON);
        Assert.assertEquals(responseBodySendFeedbackRequest.getStatus(), "SUCCESS");
    }

    /**
     * Send a feedback request to mentor when a mentoring mission is closed with a glober who is not present in BME
     * @param mapValue
     * @throws Exception
     * @author christian.chacon
     */
    @Test(dataProvider = "usersWithClosedMissionAndNoExistInBME", dataProviderClass = MyGrowthDataProdivers.class, groups = {Sanity, Regression})
    public void sendFeedbackRequestToMentorWithAMenteeIsNotPresentinBME(Map<Object, Object> mapValue) throws Exception {
        MentorHelper mentorHelper=new MentorHelper(mapValue.get("username").toString());
        Response response=mentorHelper.postSendFeedbackRequest(new PostRequestFeedbackMission(Integer.parseInt(mapValue.get("globerId").toString()), mapValue.get("missionId").toString(), Integer.parseInt(mapValue.get("workingEcosistemId").toString())));
        validateResponseToContinueTest(response, 500, "Actual response status code is "+response.getStatusCode()+" and expected response status code is 500", true);
        Assert.assertEquals(response.getStatusCode(), 500);
    }

}
