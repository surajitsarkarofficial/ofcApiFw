package tests.testhelpers.submodules.tripCreation;

import com.aventstack.extentreports.Status;
import dto.submodules.tripCreation.costAllocation.CostAllocation;
import dto.submodules.tripCreation.costAllocation.CostAllocationRequestDTO;
import dto.submodules.tripCreation.tripDetail.Trip;
import endpoints.submodules.tripCreation.CostAllocationEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.tripCreation.tripCostAllocation.CostAllocationPayloadHelper;
import properties.TravelNextProperties;
import tests.GlowBaseTest;
import tests.testcases.TravelNextBaseTest;
import tests.testcases.submodules.tripCreation.TripCreationTest;
import tests.testhelpers.TravelNextBaseHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * @author josealberto.gomez
 *
 */
public class CostAllocationTestHelper extends TravelNextBaseHelper {
    /**
     * This method will perform a POST to create new CostAllocation
     *
     * @param trip
     * @return Response
     * @throws Exception
     */
    public Response postCostAllocation(Trip trip) throws Exception {
        CostAllocationRequestDTO request = CostAllocationPayloadHelper.getCostAllocationDTO();
        String endpointURL = TravelNextBaseTest.baseUrl + String.format(CostAllocationEndpoints.postCostAllocation,
                trip.getTripId());
        RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
                .sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(request);
        Response response = new RestUtils().executePOST(requestSpecification, endpointURL);
        TripCreationTest.test.log(Status.INFO, "POST - User: " + TravelNextProperties.loggedInUser);
        TripCreationTest.test.log(Status.INFO, "RequestURL: " + endpointURL);
        TripCreationTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(request));
        return response;
    }

    /**
     * This method will perform a PUT to update a CostAllocation
     *
     * @param costAllocation
     * @return Response
     * @throws Exception
     */
    public Response putCostAllocation(CostAllocation costAllocation) throws Exception {
        CostAllocationRequestDTO request = CostAllocationPayloadHelper.getCostAllocationDTO();
        String endpointURL = TravelNextBaseTest.baseUrl + String.format(CostAllocationEndpoints.putCostAllocation,
                costAllocation.getTripId(), costAllocation.getCostAllocationId());
        RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
                .sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(request);
        Response response = new RestUtils().executePUT(requestSpecification, endpointURL);
        TripCreationTest.test.log(Status.INFO, "PUT - User: " + TravelNextProperties.loggedInUser);
        TripCreationTest.test.log(Status.INFO, "RequestURL: " + endpointURL);
        TripCreationTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(request));
        return response;
    }


    /**
     * This method will perform a GET to get CostAllocations for trip
     *
     * @param costAllocation
     * @return Response
     * @throws Exception
     */
    public Response getCostAllocations(CostAllocation costAllocation) throws Exception {
        String endpointURL = TravelNextBaseTest.baseUrl + String.format(CostAllocationEndpoints.getCostAllocation, costAllocation.getTripId());
        RequestSpecification requestSpecification = RestAssured.with().sessionId(GlowBaseTest.sessionId);
        Response response = restUtils.executeGET(requestSpecification, endpointURL);
        TripCreationTest.test.log(Status.INFO, "GET - User: " + TravelNextProperties.loggedInUser);
        TripCreationTest.test.log(Status.INFO, "RequestURL: " + endpointURL);
        return response;
    }

    /**
     * This method will perform a DELETE to delete a CostAllocation
     *
     * @param costAllocation
     * @return Response
     * @throws Exception
     */
    public Response deleteCostAllocation(CostAllocation costAllocation) throws Exception {
        String endpointURL = TravelNextBaseTest.baseUrl + String.format(CostAllocationEndpoints.deleteCostAllocation, costAllocation.getCostAllocationId());
        RequestSpecification requestSpecification = RestAssured.with().sessionId(GlowBaseTest.sessionId);
        Response response = restUtils.executeDELETE(requestSpecification, endpointURL);
        TripCreationTest.test.log(Status.INFO, "DELETE - User: " + TravelNextProperties.loggedInUser);
        TripCreationTest.test.log(Status.INFO, "RequestURL: " + endpointURL);
        return response;
    }
}
