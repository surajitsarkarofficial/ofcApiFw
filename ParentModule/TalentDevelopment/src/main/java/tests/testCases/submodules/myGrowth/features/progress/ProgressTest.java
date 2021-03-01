package tests.testCases.submodules.myGrowth.features.progress;

import static utils.GlowEnums.ExecutionSuiteGroups.ExeGroups.Regression;
import static utils.GlowEnums.ExecutionSuiteGroups.ExeGroups.Sanity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import database.submodules.myGrowth.MyGrowthDBHelper;
import dataproviders.submodules.myGrowth.MyGrowthDataProdivers;
import dto.submodules.myGrowth.features.progress.GET.ProgressDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.progress.ProgressHelper;

/**
 * @author nadia.silva
 */
public class ProgressTest extends MyGrowthBaseTest {
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        featureTest=subModuleTest.createNode("Progress test");
    }
    /**
     * Performs GET, validates against database that the missions returned in service are correct
     * @param mapValue
     * @throws Exception
     */
    @Test(dataProvider = "usersWithTalentModel", dataProviderClass = MyGrowthDataProdivers.class
            ,groups = {Regression, Sanity})
    public void validateGloberMissionsFromServiceAgainstDatabase(Map<String,String> mapValue) throws Exception {
        String username = mapValue.get("username");
        String globerId= mapValue.get("globerId");
        ProgressHelper progressHelper = new ProgressHelper(username);
        MyGrowthDBHelper myGrowthDBHelper = new MyGrowthDBHelper();

        //First gets the working ecosystems
        List<String> workingEcosystemsList = myGrowthDBHelper.getWorkingEcosystemsOfGlober(globerId);

        SoftAssert softAssert = new SoftAssert();

        //Then for each working ecosystem, we perform a GET to validate each mission comparing to database
        for(String workingEcosystem : workingEcosystemsList){ //So we check for all working ecosystems of globers
            //Performs GET method
            Response response = progressHelper.getProgressInfo(workingEcosystem, globerId);
            //validates that the response is correct before moving forward with the test
            validateResponseToContinueTest(response, 200, "GET method for progress missions failed..", true);
            ProgressDTO getProgressDTO = response.as(ProgressDTO.class, ObjectMapperType.GSON);

            //Get mission list from DB for that working ecosystem and glober
            List<String> missionsFromDB = myGrowthDBHelper.getGloberMissionsForWorkingEcosystem(globerId, workingEcosystem);

            //Extracts the missions list from service
            List<String> missionsListFromService = progressHelper.getMissionsNameList(getProgressDTO.getDetails().get(0).getData().get(0).getItems());
           // List<String> missionsListFromService = getProgressDTO.getDetails().get(0).getData().get(0).getItems();

            if(missionsFromDB.size() == missionsListFromService.size()){
                Collections.sort(missionsFromDB);
                Collections.sort(missionsListFromService);

                for(int i=0; i < missionsFromDB.size(); i++){
                    softAssert.assertEquals(missionsFromDB.get(i) , missionsListFromService.get(i), "Missions: '"+missionsFromDB.get(i)+"' and '"+missionsListFromService.get(i)+"' are not the same!");
                }
            }else if(missionsFromDB.size() > missionsListFromService.size()){
                throw new SkipException("There are missions missing for this working ecosystem: '" + workingEcosystem + "'");
            }else{
                throw new SkipException("There are more missions than expected for this working ecosystem: '" + workingEcosystem + "'");
            }

        }
        softAssert.assertAll();
    }
}