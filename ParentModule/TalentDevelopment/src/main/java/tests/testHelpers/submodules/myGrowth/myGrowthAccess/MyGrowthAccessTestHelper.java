package tests.testHelpers.submodules.myGrowth.myGrowthAccess;

import endpoints.GlowEndpoints;
import io.restassured.response.Response;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.MyGrowthTestHelper;

/**
 * @author christian.chacon
 */
public class MyGrowthAccessTestHelper extends MyGrowthTestHelper {

    public MyGrowthAccessTestHelper(String userName) throws Exception {
        super(userName);
    }

    /**
     * Get the Basic Info to verify the access to My Growth for every Glober
     * @param globerId
     * @return
     */
    public Response getBasicInfoToVerifyTheRoleMyGrowth(String globerId){
        String requestUrl = MyGrowthBaseTest.baseUrl+ GlowEndpoints.basciInfo;
        return getMethod(requestUrl, globerId);
    }

}
