package tests.testCases.submodules.myGrowth.features.missions;

import static utils.GlowEnums.ExecutionSuiteGroups.ExeGroups.Regression;
import static utils.GlowEnums.ExecutionSuiteGroups.ExeGroups.Sanity;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import database.submodules.myGrowth.MyGrowthDBHelper;
import dataproviders.submodules.myGrowth.MyGrowthDataProdivers;
import io.restassured.response.Response;
import payloads.submodules.myGrowth.missions.UpdateMissionGloberPayload;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.mentor.MentorHelper;

/**
 * @author nadia.silva
 */
public class StartMentoringMissionTest extends MyGrowthBaseTest {
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        featureTest = subModuleTest.createNode("Start Mentoring Mission Test");
    }
    /**
     * Validate status changes from "AVAILABLE" to "STARTED" for a mentoring mission
     *
     * @param mapValue
     * @throws Exception
     */
    @Test(priority = 2, dataProvider = "mentoringMissionWithStatusAvailable", dataProviderClass = MyGrowthDataProdivers.class, groups = {Regression, Sanity})
    public void closeMissionTest(Map<String, String> mapValue) throws Exception {
        MyGrowthDBHelper myGrowthDBHelper = new MyGrowthDBHelper();
        MentorHelper mentorHelper = new MentorHelper(mapValue.get("username"));
        Response response = mentorHelper.updateMentoringMission(new UpdateMissionGloberPayload(Integer.parseInt(mapValue.get("globerId")), Integer.parseInt(mapValue.get("missionId")), "STARTED"));
        validateResponseToContinueTest(response, 200, "Actual response status code is " + response.getStatusCode() + " and expected respose status code is 200", true);
        String statusMission = myGrowthDBHelper.getCurrentMentoringMissionStatus(Long.parseLong(mapValue.get("missionId")));
        Assert.assertEquals(statusMission, "STARTED");
    }

    /**
     * Validate when trying to update mission status from "CLOSED" to "STARTED", it throws 500
     * @param mapValue
     * @throws Exception
     */
    @Test(dataProvider = "mentoringMissionWithStatusClosed", dataProviderClass = MyGrowthDataProdivers.class)
    public void validateStartingMissionAlreadyStarted(Map<String,String> mapValue) throws Exception {
        MyGrowthDBHelper myGrowthDBHelper=new MyGrowthDBHelper();
        MentorHelper mentorHelper = new MentorHelper(mapValue.get("username"));
        Response response=mentorHelper.updateMentoringMission(new UpdateMissionGloberPayload(Integer.parseInt(mapValue.get("globerId")), Integer.parseInt(mapValue.get("missionId")), "STARTED"));
        validateResponseToContinueTest(response, 500, "Actual response status code is "+response.getStatusCode()+" and expected respose status code is 500", true);
        String statusMission=myGrowthDBHelper.getCurrentMentoringMissionStatus(Long.parseLong(mapValue.get("missionId")));
        Assert.assertEquals(statusMission, "CLOSED");
    }
}