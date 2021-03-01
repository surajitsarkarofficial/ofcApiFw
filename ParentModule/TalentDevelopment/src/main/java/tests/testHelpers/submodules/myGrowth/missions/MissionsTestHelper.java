package tests.testHelpers.submodules.myGrowth.missions;

import endpoints.submodules.myGrowth.features.missions.MissionEndpoints;
import io.restassured.response.Response;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.MyGrowthTestHelper;

/**
 * @author christian.chacon
 */
public class MissionsTestHelper extends MyGrowthTestHelper {


    public MissionsTestHelper(String userName) throws Exception {
        super(userName);
    }

    /**
     * get the list of the skills for a glober and the working ecosystems
     * @param workingEcosystemId
     * @param globerId
     * @return Response
     * @author christian.chacon
     */
    public Response getSkillsbyWorkingEcosystemandGloberid(String workingEcosystemId, String globerId){
        String requestUrl= MyGrowthBaseTest.baseUrl+String.format(MissionEndpoints.listMenteeSkills, workingEcosystemId, globerId);
        return getMethod(requestUrl, globerId);
    }

    /**
     * get the list of the missions for a glober
     * @param workingEcosystemId
     * @param globerId
     * @return Response
     * @author christian.chacon
     *
     */
    public Response getMissionListbyWEIdAndgloberId(String workingEcosystemId, String globerId){
        String requestUrl= MyGrowthBaseTest.baseUrl+String.format(MissionEndpoints.missionListEndpoint, workingEcosystemId, globerId);
        return getMethod(requestUrl, globerId);
    }

}
