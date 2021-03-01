package tests.testCases.submodules.myGrowth.features.familyGroup;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import database.submodules.myGrowth.MyGrowthDBHelper;
import dataproviders.submodules.myGrowth.MyGrowthDataProdivers;
import dto.basicInfo.get.BasicInfoDTO;
import dto.submodules.myGrowth.features.globerSearchFamilyGroup.GloberSearchResponse;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelper.basicInfo.BasicInfoTestHelper;
import tests.testHelpers.submodules.myGrowth.familyGroup.globerSearchFamilyGroup.GloberSearchTestHelper;

/**
 * @author christian.chacon
 */
public class GloberSearchTest extends MyGrowthBaseTest {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(){
        featureTest=subModuleTest.createNode("GloberSearchTest");
    }

    /**
     * Check the glober's family group by position is present in the response against DB
     * @param userName
     * @throws Exception
     * @author christian.chacon
     */
    @Test(dataProvider = "usersWithTalentModel", dataProviderClass = MyGrowthDataProdivers.class)
    public void checkFamilyGroup(String userName) throws Exception {
        MyGrowthDBHelper myGrowthDBHelper = new MyGrowthDBHelper();
        GloberSearchTestHelper globerSearchTestHelper=new GloberSearchTestHelper(userName);
        Response response=globerSearchTestHelper.getGloberSearch(userName, "Glober");
        validateResponseToContinueTest(response, 200, "GET method for glober search failed..", true);
        GloberSearchResponse globerSearchResponse=response.as(GloberSearchResponse.class, ObjectMapperType.GSON);
        Assert.assertEquals(globerSearchResponse.getGlobers().get(0).getFamilyGroup(), myGrowthDBHelper.getActualFamilyGroupOfGlober(globerSearchResponse.getGlobers().get(0).getGloberId()));
    }

    /**
     * Check the family group is not present in the response against DB
     * @param userName
     * @throws Exception
     * @author christian.chacon
     */
    @Test(dataProvider = "usersWithoutTalentModel", dataProviderClass = MyGrowthDataProdivers.class)
    public void checkFamilyGroupNotPresent(String userName) throws Exception {
        BasicInfoTestHelper basicInfoTestHelper=new BasicInfoTestHelper(userName);
        BasicInfoDTO basicInfoDTO = basicInfoTestHelper.getBasicInfo(userName);
        GloberSearchTestHelper globerSearchTestHelper=new GloberSearchTestHelper(userName);
        Response response=globerSearchTestHelper.getGloberSearch(basicInfoDTO.getUsername(), "Glober");
        validateResponseToContinueTest(response, 200, "GET method for glober search failed..", true);
        GloberSearchResponse globerSearchResponse=response.as(GloberSearchResponse.class, ObjectMapperType.GSON);
        Assert.assertEquals(globerSearchResponse.getGlobers().get(0).getFamilyGroup(), "");
    }

}
