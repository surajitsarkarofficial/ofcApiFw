package tests.testCases.submodules.myGrowth.features.capabilities;

import static utils.GlowEnums.ExecutionSuiteGroups.ExeGroups.Sanity;

import java.util.HashMap;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import database.submodules.myGrowth.MyGrowthDBHelper;
import dataproviders.submodules.myGrowth.MyGrowthDataProdivers;
import dto.submodules.myGrowth.features.capabilities.GET.Area;
import dto.submodules.myGrowth.features.capabilities.GET.CapabilitiesStatusDTO;
import dto.submodules.myGrowth.features.capabilities.GET.Data;
import dto.submodules.myGrowth.features.capabilities.GET.Subject;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.capabilities.CapabilitiesHelper;

/**
 * @author nadia.silva
 */
public class CapabilitiesStatusTest extends MyGrowthBaseTest {
    @BeforeClass
    public void beforeClass() {
        featureTest = subModuleTest.createNode("Status capabilities test");
    }

    /**
     * Validate scores from service against database
     *
     * @param username
     * @throws Exception
     */
    @Test(dataProvider = "getGlobersRandomWithSelfEndorsement", dataProviderClass = MyGrowthDataProdivers.class, groups = {Sanity})
    public void validateScoresAgainstDatabase(String username) throws Exception {

        CapabilitiesHelper capabilitiesHelper = new CapabilitiesHelper(username);
        MyGrowthDBHelper myGrowthDBHelper = new MyGrowthDBHelper();
        String globerId = myGrowthDBHelper.getGloberId(username);

        List<String> workingEcosystemsFromDB = myGrowthDBHelper.getWorkingEcosystemsOfGlober(globerId);

        SoftAssert softAssert = new SoftAssert();
        for (String workingEcosystem : workingEcosystemsFromDB) {

            Response response = capabilitiesHelper.getStatusCapabilities(globerId, workingEcosystem);
            validateResponseToContinueTest(response, 200, "Get status capabilities api call was not successful.", true);
            CapabilitiesStatusDTO capabilitiesStatusDTO = response.as(CapabilitiesStatusDTO.class, ObjectMapperType.GSON);

            String level = myGrowthDBHelper.getLevelOfGloberInWorkingEcosystem(globerId, workingEcosystem);

            HashMap<String, String> areasWithLevel = myGrowthDBHelper.getCurrentLevelAndAreaIdBySeniority(level);
            Integer next = (Integer.parseInt(level)+1);
            String nextLevel = next.toString();
            HashMap<String, String> nextAreasWithLevel = myGrowthDBHelper.getCurrentLevelAndAreaIdBySeniority(nextLevel);

            for (Data data : capabilitiesStatusDTO.getDetails().get(0).getData()){

                for(Area area: data.getAreas()){

                    if (areasWithLevel.containsKey(area.getId()) ) {
                        String currentAreaLevel = (String)areasWithLevel.get(area.getId());
                        String nextAreaLevel =(String)nextAreasWithLevel.get(area.getId());

                        softAssert.assertEquals(currentAreaLevel, area.getScores().getCurrent(), "Current area level was not correct!");
                        softAssert.assertEquals(Integer.parseInt(nextAreaLevel), Integer.parseInt(area.getScores().getNext()), "Next area level was not correct!");
                    }

                    for(Subject subject : area.getSubjects()){
                        HashMap<String, String> subjectsWithLevel = myGrowthDBHelper.getCurrentLevelAndSubjectIdByAreaAndSeniority(level, area.getId());
                        HashMap<String, String> nextSubjectsWithLevel = myGrowthDBHelper.getCurrentLevelAndSubjectIdByAreaAndSeniority(nextLevel, area.getId());

                        if (subjectsWithLevel.containsKey(subject.getId())) {

                            String currentSubjectLevel = (String)subjectsWithLevel.get(subject.getId());
                            String nextSubjectLevel =(String)nextSubjectsWithLevel.get(subject.getId());
                            softAssert.assertEquals(currentSubjectLevel, subject.getScores().getCurrent(), "Current subject level was not correct!");
                            softAssert.assertEquals(nextSubjectLevel, subject.getScores().getNext(), "Next subject level was not correct!");
                        }
                    }

                }
            }
        }

        softAssert.assertAll();

    }
}