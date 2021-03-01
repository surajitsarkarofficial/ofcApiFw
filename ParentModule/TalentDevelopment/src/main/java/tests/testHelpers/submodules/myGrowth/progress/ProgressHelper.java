package tests.testHelpers.submodules.myGrowth.progress;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.submodules.myGrowth.features.progress.GET.Item;
import endpoints.submodules.myGrowth.features.missions.MissionEndpoints;
import io.restassured.response.Response;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.MyGrowthTestHelper;

public class ProgressHelper extends MyGrowthTestHelper {
    public ProgressHelper(String userName) throws Exception {
        super(userName);
    }

    /**
     * Performs GET for MyGrowth progress service
     * @param workingEcosystemToBringItsMissions
     * @param globerId
     * @return Response
     */
    public Response getProgressInfo(String workingEcosystemToBringItsMissions, String globerId){

        String requestURL = MyGrowthBaseTest.baseUrl + String.format(MissionEndpoints.progressMissionsUrl, workingEcosystemToBringItsMissions, globerId);
        Response response = MyGrowthTestHelper.getMethod(requestURL, globerId);
        return response;
    }

    /**
     * Extract from progress service the missions for a given working ecosystem
     * @param missionsList
     * @return string list
     */
    public List<String> getMissionsNameList(List<Item> missionsList) throws SQLException {
        ArrayList<String> missionsNamesList = new ArrayList<String>();
        for (Item item : missionsList){
            missionsNamesList.add(item.getName());
        }

        return missionsNamesList;
    }

}
