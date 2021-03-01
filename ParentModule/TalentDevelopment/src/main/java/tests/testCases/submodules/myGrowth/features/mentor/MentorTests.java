package tests.testCases.submodules.myGrowth.features.mentor;

import static utils.GlowEnums.ExecutionSuiteGroups.ExeGroups.Regression;
import static utils.GlowEnums.ExecutionSuiteGroups.ExeGroups.Sanity;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.submodules.myGrowth.MyGrowthDataProdivers;
import dto.submodules.myGrowth.features.mentorTab.isMentor.IsMentorDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.mentor.MentorHelper;

/**
 * @author nadia.silva
 */
public class MentorTests extends MyGrowthBaseTest {
    @BeforeClass(alwaysRun = true)
    public void beforeClass(){
        featureTest=subModuleTest.createNode("IsMentor tests");
    }

    /**
     * Validate that service returns "mentor" flag in true for a glober who is a mentor.
     * @param mentor
     * @throws Exception
     */
    @Test(dataProvider = "mentorForGivenWorkingEcosystem", dataProviderClass = MyGrowthDataProdivers.class, groups = {Regression, Sanity})
    public void validateIsMentor(Map<String, String> mentor) throws Exception {
        String mentorId = mentor.get("id");
        String mentorUsername = mentor.get("username");
        MentorHelper mentorHelper = new MentorHelper(mentorUsername);
        Response response = mentorHelper.getIsMentor(mentorId);

        validateResponseToContinueTest(response, 200, "Could not perform GET to /isMentor service", true);
        IsMentorDTO isMentorDTO = response.as(IsMentorDTO.class, ObjectMapperType.GSON);

        Assert.assertEquals(isMentorDTO.getDetails()[0].getIsMentor(), "true", "The glober was not a mentor.");
    }

    /**
     * Validate that for a glober who is not a mentor, "isMentor" flag comes with TRUE
     * @param mentor
     * @throws Exception
     */
    @Test(dataProvider = "globerNotBeingMentor", dataProviderClass = MyGrowthDataProdivers.class, groups = {Regression, Sanity})
    public void validateIsNotMentor(Map<String,String> mentor) throws Exception {
        String mentorId = mentor.get("id");
        String mentorUsername = mentor.get("username");
        MentorHelper mentorHelper = new MentorHelper(mentorUsername);
        Response response = mentorHelper.getIsMentor(mentorId);

        validateResponseToContinueTest(response, 200, "Could not perform GET to /isMentor service", true);
        IsMentorDTO isMentorDTO = response.as(IsMentorDTO.class, ObjectMapperType.GSON);

        Assert.assertEquals(isMentorDTO.getDetails()[0].getIsMentor(), "false", "The glober was a mentor.");
    }

    /**
     * Validate for a glober who is TD/TM/SME "HighRole" flag comes in TRUE
     * @param glober
     * @throws Exception
     */
    @Test(dataProvider = "mockedHighRole", dataProviderClass = MyGrowthDataProdivers.class, groups = {Regression, Sanity})
    public void validateIsHighRole(Map<String,String> glober) throws Exception {
        String globerId = glober.get("globerId");
        String globerUsername = glober.get("username");
        MentorHelper mentorHelper = new MentorHelper(globerUsername);
        Response response = mentorHelper.getIsMentor(globerId);

        validateResponseToContinueTest(response, 200, "Could not perform GET to /isMentor service", true);
        IsMentorDTO isMentorDTO = response.as(IsMentorDTO.class, ObjectMapperType.GSON);

        Assert.assertEquals(isMentorDTO.getDetails()[0].getHighRole(), "true", "The glober did not have a high role (TM/TD/SME).");
    }

    /**
     * Validate for a glober who is TD/TM/SME "HighRole" flag comes in FALSE service
     * @param glober
     * @throws Exception
     */
    @Test(dataProvider = "mockedNotHighRole", dataProviderClass = MyGrowthDataProdivers.class, groups = {Regression, Sanity})
    public void validateIsNotHighRole(Map<String,String> glober) throws Exception {
        String globerId = glober.get("globerId");
        String globerUsername = glober.get("username");
        MentorHelper mentorHelper = new MentorHelper(globerUsername);
        Response response = mentorHelper.getIsMentor(globerId);

        validateResponseToContinueTest(response, 200, "Could not perform GET to /isMentor service", true);
        IsMentorDTO isMentorDTO = response.as(IsMentorDTO.class, ObjectMapperType.GSON);

        Assert.assertEquals(isMentorDTO.getDetails()[0].getHighRole(), "false", "The glober had a high role (TM/TD/SME).");
    }
}