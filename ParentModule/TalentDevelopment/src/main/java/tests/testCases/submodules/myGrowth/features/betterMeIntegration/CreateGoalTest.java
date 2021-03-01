package tests.testCases.submodules.myGrowth.features.betterMeIntegration;

import static utils.GlowEnums.ExecutionSuiteGroups.ExeGroups.Sanity;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import database.submodules.myGrowth.MyGrowthDBHelper;
import dataproviders.submodules.myGrowth.MyGrowthDataProdivers;
import dto.submodules.myGrowth.features.bmeIntegration.CreateGoalDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.myGrowth.BmeIntegration.CreateGoalPayload;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.bmeIntegrationHelper.CreateGoalHelper;

public class CreateGoalTest extends MyGrowthBaseTest {

    @BeforeClass(alwaysRun = true)
    public void beforeClass()
    {
        featureTest=subModuleTest.createNode("BetterMe Goal creation test");
    }

    /** For users that are in BME database, validate that the goals get created successfully(Status code returns 201 and "Created")
     * @author nadia.silva
     * @param username
     * @throws Exception
     */
    @Test(dataProvider = "usersInBetterMe", dataProviderClass = MyGrowthDataProdivers.class
            ,groups = {Sanity})
    public void betterMeGoalCreation(String username) throws Exception {
        MyGrowthDBHelper myGrowthDBHelper = new MyGrowthDBHelper();
        String globerId= myGrowthDBHelper.getGloberId(username);
        CreateGoalHelper createGoal = new CreateGoalHelper(username);
        Response response = createGoal.createGoalBme(new CreateGoalPayload(globerId));
        validateResponseToContinueTest(response, 201, "Create Goal api call was not successful.", true);
        CreateGoalDTO createGoalDTO = response.as(CreateGoalDTO.class, ObjectMapperType.GSON);
        Assert.assertEquals(createGoalDTO.getStatusCode(), "CREATED", "Status code was not 'CREATED'");
    }
}