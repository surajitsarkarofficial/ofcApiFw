package tests.testcases.submodules.tripCreation;

import com.aventstack.extentreports.Status;
import dto.submodules.tripCreation.billability.Billability;
import dto.submodules.tripCreation.billability.GetBillabilitiesResponseDTO;
import dto.submodules.tripCreation.tripDetail.TripCreationResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import properties.TravelNextConstants;
import tests.testcases.TravelNextBaseTest;
import tests.testhelpers.TravelNextBaseHelper;
import tests.testhelpers.submodules.tripCreation.BillabilityTestHelper;
import java.util.Random;

/**
 * @author josealberto.gomez
 *
 */
public class BillabilityTest extends TravelNextBaseTest {
    public static Billability billability = null;

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        featureTest = subModuleTest.createNode("Billability Tests");
    }


    /**
     * This method test the method GET Billabilities List
     *
     * @throws Exception
     */
    //Sprint 01
    @Test(priority = 0)
    public void getBillabilities() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        BillabilityTestHelper billabilityTestHelper = new BillabilityTestHelper();
        Response response = billabilityTestHelper.getBillabilities();
        validateResponseToContinueTest(response, 200, "Unable to Get Billabilities", true);
        GetBillabilitiesResponseDTO getBillabilitiesResponse = response.as(GetBillabilitiesResponseDTO.class, ObjectMapperType.GSON);
        softAssert.assertEquals(getBillabilitiesResponse.getStatus(), TravelNextConstants.successStatus,
                String.format("Expected status was '%s' and actual was '%s'.", TravelNextConstants.successStatus, getBillabilitiesResponse.getStatus()));
        softAssert.assertAll();

        billability = getBillabilitiesResponse.getContent().get(new Random().nextInt(getBillabilitiesResponse.getContent().size()));
        test.log(Status.INFO, "Get Billabilities was successful.");
    }

    /**
     * This method test the method PUT to update Billability
     *
     * @throws Exception
     */
    //Sprint 01
    @Test(priority = 1, dependsOnMethods = "getBillabilities")
    public void putBillability() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        if(billability != null) {
            BillabilityTestHelper billabilityTestHelper = new BillabilityTestHelper();
            Response response = billabilityTestHelper.putBillability(TravelNextBaseHelper.getLastTripByAutomation(), billability);
            validateResponseToContinueTest(response, 200, "Unable to update Billability", true);
            TripCreationResponseDTO tripCreationResponse = response.as(TripCreationResponseDTO.class, ObjectMapperType.GSON);
            softAssert.assertEquals(tripCreationResponse.getStatus(), TravelNextConstants.successStatus,
                    String.format("Expected status was '%s' and actual was '%s'.", TravelNextConstants.successStatus, tripCreationResponse.getStatus()));
            softAssert.assertAll();

            test.log(Status.INFO, "Update Billability was successful, tripId: " + tripCreationResponse.getContent().getTripId());
        }
        else
        {
            softAssert.assertTrue(false, "A valid billability is necessary to test a update");
            softAssert.assertAll();
            test.log(Status.FAIL, "A valid billability is necessary to test a update");
        }
    }
}
