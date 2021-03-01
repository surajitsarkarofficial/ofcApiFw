package tests.testhelpers.submodules.tripCreation;

import com.aventstack.extentreports.Status;
import dto.submodules.tripCreation.tripDetail.Trip;
import dto.submodules.tripCreation.tripDetail.TripCreationRequestDTO;
import endpoints.submodules.tripCreation.TripCreationEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.tripCreation.tripDetail.TripCreationPayloadHelper;
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
public class TripCreationTestHelper extends TravelNextBaseHelper {
    /**
     * This method will perform a POST to create new Trip
     *
     * @return Response
     * @throws Exception
     */
    public Response postTrip() throws Exception {
        TripCreationRequestDTO request = TripCreationPayloadHelper.getTripCreationDTO();
        String endpointURL = TravelNextBaseTest.baseUrl + TripCreationEndpoints.postCreateTrip;
        RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
                .sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(request);
        Response response = new RestUtils().executePOST(requestSpecification, endpointURL);
        TripCreationTest.test.log(Status.INFO, "POST - User: " + TravelNextProperties.loggedInUser);
        TripCreationTest.test.log(Status.INFO, "RequestURL: " + endpointURL);
        TripCreationTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(request));
        return response;
    }

    /**
     * This method will perform a PUT to update a Trip
     *
     * @param trip
     * @return Response
     * @throws Exception
     */
    public Response putTrip(Trip trip) throws Exception {
        TripCreationRequestDTO request = TripCreationPayloadHelper.getTripUpdateDTO(trip);
        String endpointURL = TravelNextBaseTest.baseUrl + TripCreationEndpoints.putCreateTrip;
        RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
                .sessionId(GlowBaseTest.sessionId).contentType(ContentType.JSON).body(request);
        Response response = new RestUtils().executePUT(requestSpecification, endpointURL);
        TripCreationTest.test.log(Status.INFO, "PUT - User: " + TravelNextProperties.loggedInUser);
        TripCreationTest.test.log(Status.INFO, "RequestURL: " + endpointURL);
        TripCreationTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(request));
        return response;
    }

    /**
     * This method will perform a GET to get Trip Detail
     *
     * @param trip
     * @return Response
     * @throws Exception
     */
    public Response getDetailTripId(Trip trip) {
        String endpointURL = TravelNextBaseTest.baseUrl + String.format(TripCreationEndpoints.getTripDetail, trip.getTripId());
        RequestSpecification requestSpecification = RestAssured.with().sessionId(GlowBaseTest.sessionId);
        Response response = restUtils.executeGET(requestSpecification, endpointURL);
        TripCreationTest.test.log(Status.INFO, "GET - User: " + TravelNextProperties.loggedInUser);
        TripCreationTest.test.log(Status.INFO, "RequestURL: " + endpointURL);
        return response;
    }

    /**
     * This method will perform a DELETE to delete a Trip
     *
     * @param trip
     * @return Response
     * @throws Exception
     */
    public Response deleteTrip(Trip trip) throws Exception {
        String endpointURL = TravelNextBaseTest.baseUrl + String.format(TripCreationEndpoints.deleteTrip, trip.getTripId());
        RequestSpecification requestSpecification = RestAssured.with().sessionId(GlowBaseTest.sessionId);
        Response response = restUtils.executeDELETE(requestSpecification, endpointURL);
        TripCreationTest.test.log(Status.INFO, "DELETE - User: " + TravelNextProperties.loggedInUser);
        TripCreationTest.test.log(Status.INFO, "RequestURL: " + endpointURL);
        return response;
    }

    /**
     * This method will perform a GET to get Watchers for Trip
     *
     * @param trip
     * @return Response
     * @throws Exception
     */
    public Response getWatchers(Trip trip) throws Exception {
        String endpointURL = TravelNextBaseTest.baseUrl + String.format(TripCreationEndpoints.getWatchers, trip.getTripId());
        RequestSpecification requestSpecification = RestAssured.with().sessionId(GlowBaseTest.sessionId);
        Response response = restUtils.executeGET(requestSpecification, endpointURL);
        TripCreationTest.test.log(Status.INFO, "GET - User: " + TravelNextProperties.loggedInUser);
        TripCreationTest.test.log(Status.INFO, "RequestURL: " + endpointURL);
        return response;
    }

    /**
     * This method will perform a GET to get Projects
     *
     * @param keyWord
     * @return Response
     * @throws Exception
     */
    public Response getProjects(String keyWord) throws Exception {
        String endpointURL = TravelNextBaseTest.baseUrl + String.format(TripCreationEndpoints.getProjects, keyWord);
        RequestSpecification requestSpecification = RestAssured.with().sessionId(GlowBaseTest.sessionId);
        Response response = restUtils.executeGET(requestSpecification, endpointURL);
        TripCreationTest.test.log(Status.INFO, "GET - User: " + TravelNextProperties.loggedInUser);
        TripCreationTest.test.log(Status.INFO, "RequestURL: " + endpointURL);
        return response;
    }

    /**
     * This method will perform a GET to get Travel Reasons
     *
     * @return Response
     * @throws Exception
     */
    public Response getTravelReasonList() throws Exception {
        String endpointURL = TravelNextBaseTest.baseUrl + TripCreationEndpoints.getTravelReasons;
        RequestSpecification requestSpecification = RestAssured.with().sessionId(GlowBaseTest.sessionId);
        Response response = restUtils.executeGET(requestSpecification, endpointURL);
        TripCreationTest.test.log(Status.INFO, "GET - User: " + TravelNextProperties.loggedInUser);
        TripCreationTest.test.log(Status.INFO, "RequestURL: " + endpointURL);
        return response;
    }
}
