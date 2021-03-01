package tests.testcases.submodules.tripCreation;

import com.aventstack.extentreports.Status;
import dto.submodules.tripCreation.costAllocation.CostAllocation;
import dto.submodules.tripCreation.costAllocation.CostAllocationResponseDTO;
import dto.submodules.tripCreation.costAllocation.CostAllocationResponseGetDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import properties.TravelNextConstants;
import tests.testcases.TravelNextBaseTest;
import tests.testhelpers.TravelNextBaseHelper;
import tests.testhelpers.submodules.tripCreation.CostAllocationTestHelper;
import utils.TravelNextEnums;

/**
 * @author josealberto.gomez
 *
 */
public class CostAllocationTest extends TravelNextBaseTest {
    public static CostAllocation savedCostAllocation = null;

    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        featureTest = subModuleTest.createNode("Cost Allocation Tests");
    }

    /**
     * This method test POST to add new Cost Allocation to Trip
     *
     * @throws Exception
     */
    @Test(priority = 0, groups = {TravelNextEnums.ExecutionSuiteGroups.ExeGroups.SprintOne})
    public void postCostAllocation() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        CostAllocationTestHelper costAllocationTestHelper = new CostAllocationTestHelper();
        Response response = costAllocationTestHelper.postCostAllocation(TravelNextBaseHelper.getLastTripByAutomation());
        validateResponseToContinueTest(response, 200, "Unable to Create Cost Allocation", true);
        CostAllocationResponseDTO costAllocationResponse = response.as(CostAllocationResponseDTO.class, ObjectMapperType.GSON);
        softAssert.assertEquals(costAllocationResponse.getStatus(), TravelNextConstants.successStatus,
                String.format("Expected status was '%s' and actual was '%s'.", TravelNextConstants.successStatus, costAllocationResponse.getStatus()));
        softAssert.assertAll();

        savedCostAllocation = costAllocationResponse.getContent();
        test.log(Status.INFO, "Post Cost Allocation was successful, costAllocationId: " + savedCostAllocation.getCostAllocationId());
    }

    /**
     * This method test PUT to update Cost Allocation
     *
     * @throws Exception
     */
    @Test(priority = 1, dependsOnMethods = "postCostAllocation", groups = {TravelNextEnums.ExecutionSuiteGroups.ExeGroups.SprintOne})
    public void putCostAllocation() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        if(savedCostAllocation != null) {
            CostAllocationTestHelper costAllocationTestHelper = new CostAllocationTestHelper();
            Response response = costAllocationTestHelper.putCostAllocation(savedCostAllocation);
            validateResponseToContinueTest(response, 200, "Unable to update Cost Allocation", true);
            CostAllocationResponseDTO costAllocationResponse = response.as(CostAllocationResponseDTO.class, ObjectMapperType.GSON);
            softAssert.assertEquals(costAllocationResponse.getStatus(), TravelNextConstants.successStatus,
                    String.format("Expected status was '%s' and actual was '%s'.", TravelNextConstants.successStatus, costAllocationResponse.getStatus()));
            softAssert.assertAll();

            savedCostAllocation = costAllocationResponse.getContent();
            test.log(Status.INFO, "Update Cost Allocation was successful, costAllocationId: " + savedCostAllocation.getCostAllocationId());
        }
        else
        {
            softAssert.assertTrue(false, "A valid costAllocationId is necessary to test a update");
            softAssert.assertAll();
            test.log(Status.FAIL, "A valid costAllocationId is necessary to test a update");
        }

    }

    /**
     * This method test GET Cost Allocation from Trip
     *
     * @throws Exception
     */
    @Test(priority = 2, dependsOnMethods = "postCostAllocation", groups = {TravelNextEnums.ExecutionSuiteGroups.ExeGroups.SprintOne})
    public void getCostAllocation() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        CostAllocationTestHelper costAllocationTestHelper = new CostAllocationTestHelper();
        Response response = costAllocationTestHelper.getCostAllocations(savedCostAllocation);
        validateResponseToContinueTest(response, 200, "Unable to get Cost Allocations", true);
        CostAllocationResponseGetDTO costAllocationResponse = response.as(CostAllocationResponseGetDTO.class, ObjectMapperType.GSON);
        softAssert.assertEquals(costAllocationResponse.getStatus(), TravelNextConstants.successStatus,
                String.format("Expected status was '%s' and actual was '%s'.", TravelNextConstants.successStatus, costAllocationResponse.getStatus()));
        softAssert.assertAll();

        test.log(Status.INFO, "Get Cost Allocations was successful");
    }

    /**
     * This method test DELETE Cost Allocation
     *
     * @throws Exception
     */
    @Test(priority = 3, dependsOnMethods = "postCostAllocation", groups = {TravelNextEnums.ExecutionSuiteGroups.ExeGroups.SprintOne})
    public void deleteCostAllocation() throws Exception {
        SoftAssert softAssert = new SoftAssert();

        if(savedCostAllocation != null) {
            CostAllocationTestHelper costAllocationTestHelper = new CostAllocationTestHelper();
            Response response = costAllocationTestHelper.deleteCostAllocation(savedCostAllocation);
            validateResponseToContinueTest(response, 200, "Unable to delete Cost Allocation", true);
            CostAllocationResponseDTO costAllocationResponse = response.as(CostAllocationResponseDTO.class, ObjectMapperType.GSON);
            softAssert.assertEquals(costAllocationResponse.getStatus(), TravelNextConstants.successStatus,
                    String.format("Expected status was '%s' and actual was '%s'.", TravelNextConstants.successStatus, costAllocationResponse.getStatus()));
            softAssert.assertAll();

            test.log(Status.INFO, "Delete Cost Allocation was successful, costAllocationId: " + savedCostAllocation.getCostAllocationId());
        }
        else
        {
            softAssert.assertTrue(false, "A valid costAllocationId is necessary to test a delete");
            softAssert.assertAll();
            test.log(Status.FAIL, "A valid costAllocationId is necessary to test a delete");
        }

    }
}
