package tests.testcases.submodules.tripCreation;

import com.aventstack.extentreports.Status;
import dto.submodules.tripCreation.tripDetail.*;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import properties.TravelNextConstants;
import tests.testcases.TravelNextBaseTest;
import tests.testhelpers.TravelNextBaseHelper;
import tests.testhelpers.submodules.tripCreation.TripCreationTestHelper;
import utils.TravelNextEnums;

/**
 * @author josealberto.gomez
 *
 */
public class TripCreationTest extends TravelNextBaseTest {
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        featureTest = subModuleTest.createNode("Trip Creation Tests");
    }

    /**
     * This method test POST to create new Trip
     *
     * @throws Exception
     */
    @Test(priority = 0, groups = {TravelNextEnums.ExecutionSuiteGroups.ExeGroups.SprintOne})
    public void postTrip() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        TripCreationTestHelper tripCreationTestHelper = new TripCreationTestHelper();
        Response response = tripCreationTestHelper.postTrip();
        validateResponseToContinueTest(response, 200, "Unable to Create Trip", true);
        TripCreationResponseDTO tripDetailResponse = response.as(TripCreationResponseDTO.class, ObjectMapperType.GSON);
        softAssert.assertEquals(tripDetailResponse.getStatus(), TravelNextConstants.successStatus,
                String.format("Expected status was '%s' and actual was '%s'.", TravelNextConstants.successStatus, tripDetailResponse.getStatus()));
        softAssert.assertAll();

        test.log(Status.INFO, "Create Trip was successful, tripId: " + tripDetailResponse.getContent().getTripId());
    }

    /**
     * This method test PUT to update a Trip
     *
     * @throws Exception
     */
    @Test(priority = 1, dependsOnMethods = "postTrip", groups = {TravelNextEnums.ExecutionSuiteGroups.ExeGroups.SprintOne})
    public void putTrip() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        TripCreationTestHelper tripCreationTestHelper = new TripCreationTestHelper();
        Response response = tripCreationTestHelper.putTrip(TravelNextBaseHelper.getLastTripByAutomation());
        validateResponseToContinueTest(response, 200, "Unable to update Trip", true);
        TripCreationResponseDTO tripDetailResponse = response.as(TripCreationResponseDTO.class, ObjectMapperType.GSON);
        softAssert.assertEquals(tripDetailResponse.getStatus(), TravelNextConstants.successStatus,
                String.format("Expected status was '%s' and actual was '%s'.", TravelNextConstants.successStatus, tripDetailResponse.getStatus()));
        softAssert.assertAll();

        test.log(Status.INFO, "Update Trip was successful, tripId: " + tripDetailResponse.getContent().getTripId());
    }

    /**
     * This method test GET Trip detail Service
     *
     * @throws Exception
     */
    @Test(priority = 2, dependsOnMethods = "postTrip", groups = {TravelNextEnums.ExecutionSuiteGroups.ExeGroups.SprintOne})
    public void getTripDetail() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        TripCreationTestHelper tripCreationTestHelper = new TripCreationTestHelper();
        Response response = tripCreationTestHelper.getDetailTripId(TravelNextBaseHelper.getLastTripByAutomation());
        validateResponseToContinueTest(response, 200, "Unable to Get Trip Detail", true);
        TripCreationResponseDTO tripDetailResponse = response.as(TripCreationResponseDTO.class, ObjectMapperType.GSON);
        softAssert.assertEquals(tripDetailResponse.getStatus(), TravelNextConstants.successStatus,
                String.format("Expected status was '%s' and actual was '%s'.", TravelNextConstants.successStatus, tripDetailResponse.getStatus()));
        softAssert.assertAll();

        test.log(Status.INFO, "Get Trip Detail was successful, tripId: " + tripDetailResponse.getContent().getTripId());
    }

    /**
     * This method test GET Watchers for Trip
     *
     * @throws Exception
     */
    @Test(priority = 3, groups = {TravelNextEnums.ExecutionSuiteGroups.ExeGroups.SprintOne})
    public void getWatchers() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        TripCreationTestHelper tripCreationTestHelper = new TripCreationTestHelper();
        Response response = tripCreationTestHelper.getWatchers(TravelNextBaseHelper.getLastTripByAutomation());
        validateResponseToContinueTest(response, 200, "Unable to Get Watchers", true);
        GetWatchersResponseDTO getWatchersResponse = response.as(GetWatchersResponseDTO.class, ObjectMapperType.GSON);
        softAssert.assertEquals(getWatchersResponse.getStatus(), TravelNextConstants.successStatus,
                String.format("Expected status was '%s' and actual was '%s'.", TravelNextConstants.successStatus, getWatchersResponse.getStatus()));
        softAssert.assertAll();

        test.log(Status.INFO, "Get Watchers was successful.");
    }

    /**
     * This method test GET Projects
     *
     * @throws Exception
     */
    @Test(priority = 4, groups = {TravelNextEnums.ExecutionSuiteGroups.ExeGroups.SprintOne})
    public void getProjects() throws Exception {
        String keyWord = "Globant";
        SoftAssert softAssert = new SoftAssert();
        TripCreationTestHelper tripCreationTestHelper = new TripCreationTestHelper();
        Response response = tripCreationTestHelper.getProjects(keyWord);
        validateResponseToContinueTest(response, 200, "Unable to Get Projects", true);
        GetProjectsResponseDTO getProjectsResponse = response.as(GetProjectsResponseDTO.class, ObjectMapperType.GSON);
        softAssert.assertEquals(getProjectsResponse.getStatus(), TravelNextConstants.successStatus,
                String.format("Expected status was '%s' and actual was '%s'.", TravelNextConstants.successStatus, getProjectsResponse.getStatus()));
        softAssert.assertAll();

        test.log(Status.INFO, "Get Projects was successful, keyword: " + keyWord);
    }

    /**
     * This method test GET Travel Reasons
     *
     * @throws Exception
     */
    @Test(priority = 5, groups = {TravelNextEnums.ExecutionSuiteGroups.ExeGroups.SprintOne})
    public void getTravelReasons() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        TripCreationTestHelper tripCreationTestHelper = new TripCreationTestHelper();
        Response response = tripCreationTestHelper.getTravelReasonList();
        validateResponseToContinueTest(response, 200, "Unable to Get Travel Reasons", true);
        GetTravelReasonsResponseDTO travelReasonsResponse = response.as(GetTravelReasonsResponseDTO.class, ObjectMapperType.GSON);
        softAssert.assertEquals(travelReasonsResponse.getStatus(), TravelNextConstants.successStatus,
                String.format("Expected status was '%s' and actual was '%s'.", TravelNextConstants.successStatus, travelReasonsResponse.getStatus()));
        softAssert.assertAll();

        test.log(Status.INFO, "Get Travel Reasons was successful.");
    }

    /**
     * This method test DELETE Trip Service
     *
     * @throws Exception
     */
    @Test(priority = 6, dependsOnMethods = "postTrip", groups = {TravelNextEnums.ExecutionSuiteGroups.ExeGroups.DontDeleteTrip, TravelNextEnums.ExecutionSuiteGroups.ExeGroups.SprintTen})
    public void deleteTrip() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        TripCreationTestHelper tripCreationTestHelper = new TripCreationTestHelper();
        Response response = tripCreationTestHelper.deleteTrip(TravelNextBaseHelper.getLastTripByAutomation());
        validateResponseToContinueTest(response, 200, "Unable to Delete Trip", true);
        TripDeleteResponseDTO tripDeleteResponse = response.as(TripDeleteResponseDTO.class, ObjectMapperType.GSON);
        softAssert.assertEquals(tripDeleteResponse.getStatus(), TravelNextConstants.successStatus,
                String.format("Expected status was '%s' and actual was '%s'.", TravelNextConstants.successStatus, tripDeleteResponse.getStatus()));
        softAssert.assertAll();

        test.log(Status.INFO, "Delete Trip was successful, tripId: " + tripDeleteResponse.getContent());
    }
}
