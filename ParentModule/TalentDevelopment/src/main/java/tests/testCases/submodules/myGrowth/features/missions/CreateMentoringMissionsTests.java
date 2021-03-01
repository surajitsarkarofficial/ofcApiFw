package tests.testCases.submodules.myGrowth.features.missions;

import static utils.GlowEnums.ExecutionSuiteGroups.ExeGroups.Regression;
import static utils.GlowEnums.ExecutionSuiteGroups.ExeGroups.Sanity;

import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.myGrowth.MyGrowthDataProdivers;
import dto.submodules.myGrowth.features.mentoring.createMission.CreateMentoringMissionDTO;
import dto.submodules.myGrowth.features.workingEcosystem.getMissions.GetGloberMissionsDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.myGrowth.missions.CreateMentoringMissionPayload.CreateMentoringMissionPayload;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.mentor.MentorHelper;
import tests.testHelpers.submodules.myGrowth.workingEcosystem.WorkingEcosystemHelper;

/**
 * @author nadia.silva
 */
public class CreateMentoringMissionsTests extends MyGrowthBaseTest {
    @BeforeClass(alwaysRun = true)
    public void beforeClass(){
        featureTest=subModuleTest.createNode("Create mentoring Missions test");
    }

    @Test(priority = 1, dataProvider = "getMentorMocked", dataProviderClass = MyGrowthDataProdivers.class, groups = {Regression, Sanity})
    public void createMentoringMission(Map<Object, Object> map) throws Exception {
        String user = map.get("username").toString();
        String mentorId = map.get("mentor").toString();
        String menteeId = map.get("mentee").toString();
        String we = map.get("workingEcosystem").toString();

        WorkingEcosystemHelper workingEcosystemHelper = new WorkingEcosystemHelper(user);
        Response missionsResponse = workingEcosystemHelper.getMissions(menteeId, we);
        GetGloberMissionsDTO getGloberMissionsDTO = missionsResponse.as(GetGloberMissionsDTO.class, ObjectMapperType.GSON);
        validateResponseToContinueTest(missionsResponse, 200, "Get mission api failed.", true);

        List<String> subject = workingEcosystemHelper.getSubjectIdAndNameOfPrimaryWE(getGloberMissionsDTO);
        String subjectId = getGloberMissionsDTO.getWorkingEcosystems()[0].getPositionCapabilities()[0].getAreas()[0].getSubjects()[0].getId();
        String subjectName = getGloberMissionsDTO.getWorkingEcosystems()[0].getPositionCapabilities()[0].getAreas()[0].getSubjects()[0].getName();

        MentorHelper mentorHelper = new MentorHelper(user);
        Response createMissionResponse = mentorHelper.createMentoringMission(new CreateMentoringMissionPayload(subjectId, subjectName, mentorId, menteeId, we), user);
        validateResponseToContinueTest(createMissionResponse, 201, "Create mission api failed.", true);
        CreateMentoringMissionDTO createMentoringMissionDTO = createMissionResponse.as(CreateMentoringMissionDTO.class, ObjectMapperType.GSON);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createMentoringMissionDTO.getStatus(), "SUCCESS", "Response status was not 'SUCCESS'.");
        softAssert.assertEquals(createMentoringMissionDTO.getMessage(), "OK", "Response message was not 'OK'");
        softAssert.assertAll();
    }

    /**
     *  Create mentoring mission with fake subject
     * @param map
     * @throws Exception
     */
    @Test(dataProvider = "getMentorMocked", dataProviderClass = MyGrowthDataProdivers.class, groups = {Regression, Sanity})
    public void createMentoringMissionWithFakeSubject(Map<Object, Object> map) throws Exception {
        String user = map.get("username").toString();
        String mentorId = map.get("mentor").toString();
        String menteeId = map.get("mentee").toString();
        String we = map.get("workingEcosystem").toString();

        MentorHelper mentorHelper = new MentorHelper(user);
        Response createMissionResponse = mentorHelper.createMentoringMission(new CreateMentoringMissionPayload(mentorId, menteeId, we), user);
        validateResponseToContinueTest(createMissionResponse, 201, "Create mission api failed.", true);
        CreateMentoringMissionDTO createMentoringMissionDTO = createMissionResponse.as(CreateMentoringMissionDTO.class, ObjectMapperType.GSON);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createMentoringMissionDTO.getStatus(), "SUCCESS", "Response status was not 'SUCCESS'.");
        softAssert.assertEquals(createMentoringMissionDTO.getMessage(), "OK", "Response message was not 'OK'");
        softAssert.assertAll();
    }

    /**
     *  Create mentoring mission with a non existing working ecosystem
     * @param map
     * @throws Exception
     */
    @Test(dataProvider = "getMentorMocked", dataProviderClass = MyGrowthDataProdivers.class, groups = {Regression, Sanity})
    public void createMentoringMissionWithWrongWorkingEcosystemId(Map<Object, Object> map) throws Exception {
        String user = map.get("username").toString();
        String mentorId = map.get("mentor").toString();
        String menteeId = map.get("mentee").toString();
        String we = map.get("workingEcosystem").toString();

        MentorHelper mentorHelper = new MentorHelper(user);
        Response createMissionResponse = mentorHelper.createMentoringMission(new CreateMentoringMissionPayload(mentorId, menteeId, "10"), user);
        validateResponseToContinueTest(createMissionResponse, 500, "Create mission api failed.", true);
        CreateMentoringMissionDTO createMentoringMissionDTO = createMissionResponse.as(CreateMentoringMissionDTO.class, ObjectMapperType.GSON);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createMentoringMissionDTO.getStatus(), "500");
        softAssert.assertEquals(createMentoringMissionDTO.getMessage(), "Could not get data of mentor given the globerId: ");
        softAssert.assertAll();
    }

}
