package tests.testHelpers.submodules.myGrowth.familyGroup.globerSearchFamilyGroup;

import database.GlowDBHelper;
import endpoints.submodules.project.globerSearch.GloberSearchEndpoint;
import io.restassured.response.Response;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.MyGrowthTestHelper;

/**
 * @author christian.chacon
 */
public class GloberSearchTestHelper extends MyGrowthTestHelper {

    private String globerId;

    public GloberSearchTestHelper(String userName) throws Exception {
        super(userName);
        this.globerId= new GlowDBHelper().getGloberId(userName);
    }

    /**
     * Get the response from the service
     * @param key
     * @param role
     * @return Response
     * @author christian.chacon
     */
    public Response getGloberSearch(String key, String role){
        String requestUrl= MyGrowthBaseTest.baseUrl+String.format(GloberSearchEndpoint.globerSearchUrl, key, "0", "5", role);
        return getMethod(requestUrl, globerId);
    }

}
