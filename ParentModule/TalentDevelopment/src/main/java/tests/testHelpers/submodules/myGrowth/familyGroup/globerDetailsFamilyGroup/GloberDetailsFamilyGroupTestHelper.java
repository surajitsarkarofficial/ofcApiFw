package tests.testHelpers.submodules.myGrowth.familyGroup.globerDetailsFamilyGroup;

import database.GlowDBHelper;
import endpoints.submodules.project.globerDetails.GloberDetailsEndpoint;
import io.restassured.response.Response;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.MyGrowthTestHelper;

/**
 * @author christian.chacon
 */
public class GloberDetailsFamilyGroupTestHelper extends MyGrowthTestHelper {

    @SuppressWarnings("unused")
	private String globerId;

    public GloberDetailsFamilyGroupTestHelper(String userName) throws Exception {
        super(userName);
        this.globerId= new GlowDBHelper().getGloberId(userName);
    }

    /**
     * Get the response from the service
     * @param globerId
     * @return Response
     * @author christian.chacon
     */
    public Response getGloberFamilyGroupResponse(String globerId){
        String requestUrl= MyGrowthBaseTest.baseUrl+String.format(GloberDetailsEndpoint.globerSearchUrl, globerId);
        return getMethod(requestUrl, globerId);
    }

}
