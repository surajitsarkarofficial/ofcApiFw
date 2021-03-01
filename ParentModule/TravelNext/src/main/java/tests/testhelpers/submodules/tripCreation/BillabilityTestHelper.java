package tests.testhelpers.submodules.tripCreation;

import com.aventstack.extentreports.Status;
import dto.submodules.tripCreation.billability.Billability;
import dto.submodules.tripCreation.tripDetail.Trip;
import endpoints.submodules.tripCreation.BillabilityEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import properties.TravelNextProperties;
import tests.GlowBaseTest;
import tests.testcases.TravelNextBaseTest;
import tests.testcases.submodules.tripCreation.TripCreationTest;
import tests.testhelpers.TravelNextBaseHelper;
import utils.RestUtils;

/**
 * @author josealberto.gomez
 *
 */
public class BillabilityTestHelper extends TravelNextBaseHelper {
    /**
     * This method will perform a GET to get Billabilities
     *
     * @return Response
     * @throws Exception
     */
    public Response getBillabilities() throws Exception {
        String endpointURL = TravelNextBaseTest.baseUrl + BillabilityEndpoints.getBillabilities;
        RequestSpecification requestSpecification = RestAssured.with().sessionId(GlowBaseTest.sessionId);
        Response response = restUtils.executeGET(requestSpecification, endpointURL);
        TripCreationTest.test.log(Status.INFO, "GET - User: " + TravelNextProperties.loggedInUser);
        TripCreationTest.test.log(Status.INFO, "RequestURL: " + endpointURL);
        return response;
    }

    /**
     * This method will perform a PUT to create new Billability
     *
     * @param trip
     * @param billability
     * @return Response
     * @throws Exception
     */
    public Response putBillability(Trip trip, Billability billability) throws Exception {
        String endpointURL = TravelNextBaseTest.baseUrl + String.format(BillabilityEndpoints.putBillability,
                trip.getTripId(), billability.getId());
        RequestSpecification requestSpecification = RestAssured.with().sessionId(GlowBaseTest.sessionId);
        Response response = new RestUtils().executePUT(requestSpecification, endpointURL);
        TripCreationTest.test.log(Status.INFO, "PUT - User: " + TravelNextProperties.loggedInUser);
        TripCreationTest.test.log(Status.INFO, "RequestURL: " + endpointURL);
        return response;
    }
}
