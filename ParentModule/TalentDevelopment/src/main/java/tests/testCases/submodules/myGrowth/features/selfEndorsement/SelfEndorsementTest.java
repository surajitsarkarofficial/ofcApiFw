package tests.testCases.submodules.myGrowth.features.selfEndorsement;

import static utils.GlowEnums.ExecutionSuiteGroups.ExeGroups.Regression;
import static utils.GlowEnums.ExecutionSuiteGroups.ExeGroups.Sanity;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import database.submodules.myGrowth.MyGrowthDBHelper;
import dataproviders.submodules.myGrowth.MyGrowthDataProdivers;
import dto.submodules.myGrowth.features.selfEndorsement.SelfEndorsementDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import payloads.submodules.myGrowth.selfEndorsement.SelfEndorsementPayload;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.selfEndorsementHelper.SelfEndorsementHelper;

/**
 * @author nadia.silva
 */
public class SelfEndorsementTest extends MyGrowthBaseTest {

    @BeforeClass(alwaysRun = true)
    public void beforeClass()
    {
        featureTest=subModuleTest.createNode("Self Endorsement test");
    }

    /** Perform a POST for the self endorsement service and validate it is successful
     * @param mapValue
     * @throws Exception
     */
    @Test(dataProvider = "usersWithTalentModel", dataProviderClass = MyGrowthDataProdivers.class
            ,groups = {Regression, Sanity})
    public void validateSelfEndorsementCreation(Map<String,String> mapValue) throws Exception {
        SelfEndorsementHelper selfEndorsementHelper = new SelfEndorsementHelper(mapValue.get("username"));
        Response response = selfEndorsementHelper.createSelfEndorsementOfGlober(new SelfEndorsementPayload(mapValue.get("globerId")), mapValue.get("username"));
        validateResponseToContinueTest(response, 200, "Create self endorsement api call was not successful.", true);
        SelfEndorsementDTO selfEndorsementDTO = response.as(SelfEndorsementDTO.class, ObjectMapperType.GSON);
        Assert.assertEquals("STATUS_SUCCESS", selfEndorsementDTO.getStatus(), "Status for self endorsement api was not STATUS_SUCCESS");
    }

    /**
     * Perform a POST for the self endorsement service and validate it is not successful if the glober hasn´t talent model
     * @param username
     * @throws Exception
     */
    @Test(dataProvider = "usersWithoutTalentModel", dataProviderClass = MyGrowthDataProdivers.class
            ,groups = {Regression, Sanity})
    public void validateSelfEndorsementCreationFails(String username) throws Exception {
        MyGrowthDBHelper myGrowthDBHelper=new MyGrowthDBHelper();
        System.out.println(username);
        SelfEndorsementHelper selfEndorsementHelper = new SelfEndorsementHelper(username);
        Response response = selfEndorsementHelper.createSelfEndorsementOfGlober(new SelfEndorsementPayload(myGrowthDBHelper.getGloberId(username)), username);
        validateResponseToContinueTest(response, 403, "Create self endorsement api call was not successful.", true);
        Assert.assertEquals(response.getStatusCode(), 403);
    }

}