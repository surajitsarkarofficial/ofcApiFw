package tests.testCases.submodules.myGrowth.features.myGrowthAccess;

import static utils.GlowEnums.ExecutionSuiteGroups.ExeGroups.Sanity;

import java.util.Map;

import database.submodules.myGrowth.MyGrowthDBHelper;
import dto.submodules.myGrowth.features.accessMyGrowth.BasicInfoDto;
import dto.submodules.myGrowth.features.accessMyGrowth.Role;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import dataproviders.submodules.myGrowth.MyGrowthDataProdivers;
import dto.basicInfo.get.BasicInfoDTO;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelper.basicInfo.BasicInfoTestHelper;
import tests.testHelpers.submodules.myGrowth.myGrowthAccess.MyGrowthAccessTestHelper;

/**
 * @author nadia.silva
 */
public class MyGrowthAccessTest extends MyGrowthBaseTest {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(){
        featureTest=subModuleTest.createNode("MyGrowthAccessTests");
    }

    /** Validates that the role "ACP_MY_GROWTH" does not appear in the roles list for a glober that doesn't have talent model.
     *
     * @param username
     * @throws Exception
     */
    @Test(dataProvider = "usersWithoutTalentModel", dataProviderClass = MyGrowthDataProdivers.class
            ,groups = {Sanity})
    public void validateGloberWithoutTalentModelDoesNotHaveMyGrowthRole(String username) throws Exception {
        BasicInfoTestHelper basicInfoTestHelper = new BasicInfoTestHelper(username);
        BasicInfoDTO basicInfoDTO = basicInfoTestHelper.getBasicInfo(username);
        Assert.assertEquals(basicInfoTestHelper.getRoleExistence(basicInfoDTO, "ACP_MY_GROWTH"), false,  "Role did exist");
    }

    /**
     * Validates that the role "ACP_MY_GROWTH" is present in the roles list, and the Family Group is not empty, for a glober with talent model
     * @param mapValue
     * @throws Exception
     */
    @Test(dataProvider = "usersWithTalentModel", dataProviderClass = MyGrowthDataProdivers.class
            ,groups = {Sanity})
    public void validateGloberWithTalentModelHasMyGrowthRole(Map<String,String> mapValue) throws Exception {
        String username = mapValue.get("username");
        BasicInfoTestHelper basicInfoTestHelper = new BasicInfoTestHelper(username);
        BasicInfoDTO basicInfoDTO = basicInfoTestHelper.getBasicInfo(username);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotEquals(basicInfoDTO.getFamilyGroup(), "", "Family Group was empty");
        softAssert.assertEquals(basicInfoTestHelper.getRoleExistence(basicInfoDTO, "ACP_MY_GROWTH"), true,  "Role did not exist");
        softAssert.assertAll();
    }

    /**
     * Validate if the role ACP_MYGROWTH is present for all Globers
     * @param mapValue
     * @throws Exception
     */
    @Test(dataProvider = "globerWithOrWithoutTalentModel", dataProviderClass = MyGrowthDataProdivers.class
            ,groups = {Sanity})
    public void validateIfAnyGloberHasAccessToMyGrowth(Map<String,String> mapValue) throws Exception {
        MyGrowthDBHelper myGrowthDBHelper=new MyGrowthDBHelper();
        MyGrowthAccessTestHelper myGrowthAccessTestHelper=new MyGrowthAccessTestHelper(mapValue.get("username"));
        Response response=myGrowthAccessTestHelper.getBasicInfoToVerifyTheRoleMyGrowth(mapValue.get("globerId"));
        validateResponseToContinueTest(response, 200, "Actual response status code is "+response.getStatusCode()+" and expected respose status code is 200", true);
        BasicInfoDto basicInfoDto=response.as(BasicInfoDto.class, ObjectMapperType.GSON);
        String roleInDB=myGrowthDBHelper.getRoleACPMyGrowthFromGlober(Integer.parseInt(mapValue.get("globerId")), 27);
        Role role=basicInfoDto.getRoles().get(0);
        Assert.assertEquals(role.getName(), roleInDB);
    }

}