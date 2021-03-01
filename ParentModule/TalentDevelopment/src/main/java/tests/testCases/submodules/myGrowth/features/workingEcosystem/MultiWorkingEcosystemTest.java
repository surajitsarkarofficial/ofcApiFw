package tests.testCases.submodules.myGrowth.features.workingEcosystem;

import static utils.GlowEnums.ExecutionSuiteGroups.ExeGroups.Sanity;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import database.submodules.myGrowth.MyGrowthDBHelper;
import dataproviders.submodules.myGrowth.MyGrowthDataProdivers;
import dto.submodules.myGrowth.features.workingEcosystem.multiWorkingEcosystem.GET.MultiWorkingEcosystemDTO;
import dto.submodules.myGrowth.features.workingEcosystem.multiWorkingEcosystem.GET.WorkingEcosystem;
import dto.submodules.myGrowth.features.workingEcosystem.saveWE.SaveWorkingEcosystemDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.workingEcosystem.WorkingEcosystemHelper;

/**
 * @author nadia.silva
 */
public class MultiWorkingEcosystemTest extends MyGrowthBaseTest {
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        featureTest=subModuleTest.createNode("MultiWorking Ecosystem test");
    }
    /**Validate belongsToGlober value against database for multiWorkingEcosystem service
     *
     * @param username
     * @throws Exception
     */
    @Test(dataProvider = "getGlobersRandomWithSelfEndorsement", dataProviderClass = MyGrowthDataProdivers.class ,groups = {Sanity})
    public void validateMultiWorkingEcosystemAssignedAgainstDB(String username) throws Exception {
        WorkingEcosystemHelper workingEcosystemHelper = new WorkingEcosystemHelper(username);
        MyGrowthDBHelper myGrowthDBHelper = new MyGrowthDBHelper();
        String globerId = myGrowthDBHelper.getGloberId(username);

        List<String> workingEcosystemsFromDB = myGrowthDBHelper.getWorkingEcosystemsOfGlober(globerId);

        Response response = workingEcosystemHelper.getMultiWorkingEcosystems(globerId);
        validateResponseToContinueTest(response, 200, "Get MultiWorkingEcosystem api call was not successful.", true);

        MultiWorkingEcosystemDTO multiWorkingEcosystemDTO =  response.as(MultiWorkingEcosystemDTO.class, ObjectMapperType.GSON);

        SoftAssert softAssert = new SoftAssert();
        Boolean belongsToGlober = false;
        for(WorkingEcosystem workingEcosystemDto : multiWorkingEcosystemDTO.getDetails().getWorkingEcosystems()){
            if (workingEcosystemsFromDB.contains(workingEcosystemDto.getId().toString())){
                belongsToGlober = true;

            }else{
                belongsToGlober = false;
            }
            softAssert.assertEquals(workingEcosystemDto.getBelongsToGlober(),belongsToGlober, "MultiWorking ecosystem shown for glober are wrong.");

        }
        softAssert.assertAll();
    }
    /**
     * Validates creation of new working ecosystem for glober
     * @param username
     */
    @Test(dataProvider = "getGlobersRandomWithSelfEndorsement", dataProviderClass = MyGrowthDataProdivers.class ,groups = {Sanity})
    public void assignNewWorkingEcosystemToGlober(String username) throws Exception {
        WorkingEcosystemHelper workingEcosystemHelper = new WorkingEcosystemHelper(username);
        MyGrowthDBHelper myGrowthDBHelper = new MyGrowthDBHelper();
        String globerId = myGrowthDBHelper.getGloberId(username);
        List<String> we = myGrowthDBHelper.getWorkingEcosystemThatGlobersDoesNotHaveAlready(globerId);

        Response response = workingEcosystemHelper.addNewWorkingEcosystemToGlober(globerId, we.get(0), we.get(1));
        validateResponseToContinueTest(response, 200, "Add WorkingEcosystem api call was not successful.", true);
        SaveWorkingEcosystemDTO saveWEDto =  response.as(SaveWorkingEcosystemDTO.class, ObjectMapperType.GSON);

        Assert.assertEquals(saveWEDto.getMessage(), "OK");
    }

}
