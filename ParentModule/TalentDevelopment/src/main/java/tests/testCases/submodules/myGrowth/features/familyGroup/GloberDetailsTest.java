package tests.testCases.submodules.myGrowth.features.familyGroup;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import dataproviders.submodules.myGrowth.MyGrowthDataProdivers;
import dto.globerDetailsFamilyGroup.GloberDetailsFamilyDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.familyGroup.globerDetailsFamilyGroup.GloberDetailsFamilyGroupTestHelper;

/**
 * @author christian.chacon
 */
public class GloberDetailsTest extends MyGrowthBaseTest {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(){
        featureTest=subModuleTest.createNode("GloberDetailsTest");
    }

    /**
     * Check the family group is present in the response against DB
     * @param mapValue
     * @throws Exception
     * @author christian.chacon
     */
    @Test(dataProvider = "getGloberRandomWithFamilyGroup", dataProviderClass = MyGrowthDataProdivers.class)
    public void checkFamilyGroup(Map<String,String> mapValue) throws Exception {
        GloberDetailsFamilyGroupTestHelper globerDetailsFamilyGroupTestHelper=new GloberDetailsFamilyGroupTestHelper(mapValue.get("username"));
        Response response=globerDetailsFamilyGroupTestHelper.getGloberFamilyGroupResponse(mapValue.get("globerId"));
        validateResponseToContinueTest(response, 200, "GET method for glober details failed..", true);
        GloberDetailsFamilyDTO globerDetailsFamilyDTO=response.as(GloberDetailsFamilyDTO.class, ObjectMapperType.GSON);
        Assert.assertEquals(globerDetailsFamilyDTO.getFamilyGroup(), mapValue.get("familyGroup"));
    }

    /**
     * check if the family group is not present
     * @param mapValue
     * @throws Exception
     * @author christian.chacon
     */
    @Test(dataProvider = "getGloberRandomWithoutFamilyGroup", dataProviderClass = MyGrowthDataProdivers.class)
    public void checkFamilyGroupIsNotPresent(Map<String,String> mapValue) throws Exception {
        GloberDetailsFamilyGroupTestHelper globerDetailsFamilyGroupTestHelper=new GloberDetailsFamilyGroupTestHelper(mapValue.get("username"));
        Response response=globerDetailsFamilyGroupTestHelper.getGloberFamilyGroupResponse(mapValue.get("globerId"));
        validateResponseToContinueTest(response, 200, "GET method for glober details failed..", true);
        GloberDetailsFamilyDTO globerDetailsFamilyDTO=response.as(GloberDetailsFamilyDTO.class, ObjectMapperType.GSON);
        globerDetailsFamilyDTO.setFamilyGroup(null);
        Assert.assertEquals(globerDetailsFamilyDTO.getFamilyGroup(), mapValue.get("familyGroup"));
    }

}
