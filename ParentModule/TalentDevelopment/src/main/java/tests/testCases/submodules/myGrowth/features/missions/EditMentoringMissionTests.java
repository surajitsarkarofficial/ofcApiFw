package tests.testCases.submodules.myGrowth.features.missions;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import database.submodules.myGrowth.MyGrowthDBHelper;
import dataproviders.submodules.myGrowth.MyGrowthDataProdivers;
import dto.submodules.myGrowth.features.mentoring.editMentoringMission.ResponseEditMentoringMissionDto;
import dto.submodules.myGrowth.features.mentoring.editMentoringMission.ResponseFailEditMentorMissionDto;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.myGrowth.missions.EditMentoringMissionsPayload.EditMentorMissionPayload;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.mentor.MentorHelper;

/**
 * @author christian.chacon
 */
public class EditMentoringMissionTests extends MyGrowthBaseTest {
    @BeforeClass(alwaysRun = true)
    public void beforeClass(){
        featureTest=subModuleTest.createNode("Edit Mentoring Mission Test");
    }

    /**
     * Edit a Mentoring mission with status available
     * @param mapValue
     * @throws Exception
     */
    @Test(dataProvider = "mentorMissionAvailabletoEdit", dataProviderClass = MyGrowthDataProdivers.class)
    public void editMentoringMissionAvailable(Map<String,String> mapValue) throws Exception {
        MyGrowthDBHelper myGrowthDBHelper=new MyGrowthDBHelper();
        MentorHelper mentorHelper=new MentorHelper(mapValue.get("mentorusername"));
        Response response=mentorHelper.editMentoringMission(new EditMentorMissionPayload(
                "Edit mentorin mission prueba description prueba auto 4",
                Integer.parseInt(mapValue.get("globeridmentee")),
                Integer.parseInt(mapValue.get("globeridmentor")),
                Integer.parseInt(mapValue.get("missionid")),
                mentorHelper.getListofSubjects(mapValue.get("mentorusername"), mapValue.get("globeridmentee")),
                "Edit mentoring mission description automation",
                Integer.parseInt(myGrowthDBHelper.getPrimaryWorkingEcosystemOfGlober(mapValue.get("globeridmentee")))
        ));
        validateResponseToContinueTest(response, 201, "Actual response status code is "+response.getStatusCode()+" and expected respose status code is 201", true);
        ResponseEditMentoringMissionDto responseEditMentoringMissionDto=response.as(ResponseEditMentoringMissionDto.class, ObjectMapperType.GSON);
        Assert.assertEquals(responseEditMentoringMissionDto.getStatus(), "SUCCESS");
    }

    /**
     * Edit a Mentoring mission with status started
     * @param mapValue
     * @throws Exception
     */
    @Test(dataProvider = "mentorMissionStartedtoEdit", dataProviderClass = MyGrowthDataProdivers.class)
    public void editMentoringMissionStarted(Map<String,String> mapValue) throws Exception {
        MyGrowthDBHelper myGrowthDBHelper=new MyGrowthDBHelper();
        MentorHelper mentorHelper=new MentorHelper(mapValue.get("mentorusername"));
        Response response=mentorHelper.editMentoringMission(new EditMentorMissionPayload(
                "Edit mentorin mission prueba description prueba auto 4",
                Integer.parseInt(mapValue.get("globeridmentee")),
                Integer.parseInt(mapValue.get("globeridmentor")),
                Integer.parseInt(mapValue.get("missionid")),
                mentorHelper.getListofSubjects(mapValue.get("mentorusername"), mapValue.get("globeridmentee")),
                "Edit mentoring mission description automation",
                Integer.parseInt(myGrowthDBHelper.getPrimaryWorkingEcosystemOfGlober(mapValue.get("globeridmentee")))
        ));
        validateResponseToContinueTest(response, 422, "Actual response status code is "+response.getStatusCode()+" and expected respose status code is 422", true);
        ResponseFailEditMentorMissionDto responseFailEditMentorMissionDto=response.as(ResponseFailEditMentorMissionDto.class, ObjectMapperType.GSON);
        Assert.assertEquals(responseFailEditMentorMissionDto.getMessage(), "This mission has already been started. It can no longer be edited");
    }

}
