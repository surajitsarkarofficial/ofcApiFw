package tests.testCases.submodules.myGrowth.features.familyGroup;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.submodules.myGrowth.MyGrowthDataProdivers;
import io.restassured.response.Response;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.familyGroup.assignmentPodFamilyGroup.AssignmentPodTestHelper;

/**
 * @author christian.chacon
 */
public class AssignmentPodMembersTest extends MyGrowthBaseTest {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(){
        featureTest=subModuleTest.createNode("AssignmentPodMembersTest");
    }

    /**
     * Check if a glober with pod and project has family group
     * @param mapValue
     * @throws Exception
     * @author christian.chacon
     */
    @Test(dataProvider = "usersWithPodIdandProyectIdAssignmentswithFamilyGroup", dataProviderClass = MyGrowthDataProdivers.class)
    public void checkFamilyGroup(Map<String,String> mapValue) throws Exception {
        AssignmentPodTestHelper assignmentPodTestHelper=new AssignmentPodTestHelper(mapValue.get("username"));
        Response response=assignmentPodTestHelper.getResponsefromEndpointSearchPodMembers(mapValue.get("podId"), mapValue.get("projectId"));
        validateResponseToContinueTest(response, 200, "GET method for assignments pods members failed..", true);
        Assert.assertEquals(assignmentPodTestHelper.getFamilyGroup(response, mapValue.get("username")), mapValue.get("familyGroup"));
    }

    /**
     * Check if a glober with pod and project hasn´t family group
     * @param mapValue
     * @throws Exception
     * @author christian.chacon
     */
    @Test(dataProvider = "usersWithPodIdandProyectIdAssignmentswithoutFamilyGroup", dataProviderClass = MyGrowthDataProdivers.class)
    public void checkFamilyGroupisNotPresent(Map<String,String> mapValue) throws Exception {
        AssignmentPodTestHelper assignmentPodTestHelper=new AssignmentPodTestHelper(mapValue.get("username"));
        Response response=assignmentPodTestHelper.getResponsefromEndpointSearchPodMembers(mapValue.get("podId"), mapValue.get("projectId"));
        validateResponseToContinueTest(response, 200, "GET method for assignments pods members failed..", true);
        Assert.assertEquals(assignmentPodTestHelper.getFamilyGroup(response, mapValue.get("username")), mapValue.get("familyGroup"));
    }

}
