package tests.testCases.submodules.myGrowth.features.missions;

import dataproviders.submodules.myGrowth.MyGrowthDataProdivers;
import dto.submodules.myGrowth.features.missionTab.hasLeader.LeaderResponseDTO;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.missions.MissionsTestHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static utils.GlowEnums.ExecutionSuiteGroups.ExeGroups.Regression;
import static utils.GlowEnums.ExecutionSuiteGroups.ExeGroups.Sanity;

/**
 * @author christian.chacon
 */
public class MissionTabLeaderFlagTest extends MyGrowthBaseTest {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(){
        featureTest=subModuleTest.createNode("Flag Leader Mission List");
    }

    /**
     * Verify if the service returns the flag in true to know if the glober has a leader
     * @param mapValue
     * @throws Exception
     */
    @Test(priority = 3, dataProvider = "usersWithLeaderInBME", dataProviderClass = MyGrowthDataProdivers.class, groups = {Regression, Sanity})
    public void globerHasLeader(Map<String,String> mapValue) throws Exception {
        MissionsTestHelper missionsTestHelper=new MissionsTestHelper(mapValue.get("username"));
        Response response=missionsTestHelper.getMissionListbyWEIdAndgloberId(mapValue.get("workingEcosystemId"), mapValue.get("globerId"));
        validateResponseToContinueTest(response, 200, "Actual response status code is "+response.getStatusCode()+" and expected respose status code is 200", true);
        List<LeaderResponseDTO> leaderResponseDTOS= Arrays.asList(response.body().as(LeaderResponseDTO[].class));
        Assert.assertEquals(String.valueOf(leaderResponseDTOS.get(0).getHasLeader()), "true");
    }

    /**
     * Verify if the service returns the flag in false to know if the glober doesn't have a leader
     * @param mapValue
     * @throws Exception
     */
    @Test(priority = 3, dataProvider = "usersWithoutLeaderInBME", dataProviderClass = MyGrowthDataProdivers.class, groups = {Regression, Sanity})
    public void globerHasNotLeader(Map<String,String> mapValue) throws Exception {
        MissionsTestHelper missionsTestHelper=new MissionsTestHelper(mapValue.get("username"));
        Response response=missionsTestHelper.getMissionListbyWEIdAndgloberId(mapValue.get("workingEcosystemId"), mapValue.get("globerId"));
        validateResponseToContinueTest(response, 200, "Actual response status code is "+response.getStatusCode()+" and expected respose status code is 200", true);
        List<LeaderResponseDTO> leaderResponseDTOS= Arrays.asList(response.body().as(LeaderResponseDTO[].class));
        Assert.assertEquals(String.valueOf(leaderResponseDTOS.get(0).getHasLeader()), "false");
    }


}
