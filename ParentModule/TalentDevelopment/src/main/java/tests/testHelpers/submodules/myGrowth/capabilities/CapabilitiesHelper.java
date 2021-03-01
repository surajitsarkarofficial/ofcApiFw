package tests.testHelpers.submodules.myGrowth.capabilities;

import endpoints.submodules.myGrowth.features.capabilities.CapabilitiesEndpoints;
import io.restassured.response.Response;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.MyGrowthTestHelper;

/**
 * @author nadia.silva
 */
public class CapabilitiesHelper extends MyGrowthTestHelper {
    public CapabilitiesHelper(String userName) throws Exception {
        super(userName);
    }

    /**
     * Performs GET for Status Capabilities service
     * @param globerId
     * @param workingEcosystem
     * @return Response
     */
    public Response getStatusCapabilities(String globerId, String workingEcosystem){
        String requestUrl = MyGrowthBaseTest.baseUrl + String.format(CapabilitiesEndpoints.statusCapabilitiesUrl, workingEcosystem, globerId);
        return getMethod(requestUrl, globerId);
    }


}
