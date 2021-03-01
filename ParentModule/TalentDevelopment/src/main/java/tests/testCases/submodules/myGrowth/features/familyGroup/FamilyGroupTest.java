package tests.testCases.submodules.myGrowth.features.familyGroup;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import database.submodules.myGrowth.MyGrowthDBHelper;
import dataproviders.submodules.myGrowth.MyGrowthDataProdivers;
import dto.basicInfo.get.BasicInfoDTO;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelper.basicInfo.BasicInfoTestHelper;

/**
 * @author christian.chacon
 */
public class FamilyGroupTest extends MyGrowthBaseTest {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(){
        featureTest=subModuleTest.createNode("FamilyGroupTest");
    }

    /**
     * Checks familiy group from Database against service response, for users with specific positions.
     * @param username
     * @throws Exception
     */
    @Test(dataProvider = "usersWithTalentModel", dataProviderClass = MyGrowthDataProdivers.class)
    public void checkFamilyGroup (String username) throws Exception {
        MyGrowthDBHelper myGrowthDBHelper = new MyGrowthDBHelper();
        BasicInfoTestHelper basicInfoTestHelper=new BasicInfoTestHelper(username);
        BasicInfoDTO basicInfoDTO = basicInfoTestHelper.getBasicInfo(username);
        String familyGroup=myGrowthDBHelper.getActualFamilyGroupOfGlober(basicInfoDTO.getGloberId());
        Assert.assertEquals(basicInfoDTO.getFamilyGroup(),familyGroup);
    }

    /**
     * Validates that Family Group atribute is not present for users that don't have one yet.
     * @param username
     * @throws Exception
     */
    @Test(dataProvider = "usersWithoutTalentModel", dataProviderClass = MyGrowthDataProdivers.class)
    public void checkFamilyGroupIsNotPresent (String username) throws Exception {
        BasicInfoTestHelper basicInfoTestHelper=new BasicInfoTestHelper(username);
        BasicInfoDTO basicInfoDTO = basicInfoTestHelper.getBasicInfo(username);
        Assert.assertEquals(basicInfoDTO.getFamilyGroup(),"");
    }


}
