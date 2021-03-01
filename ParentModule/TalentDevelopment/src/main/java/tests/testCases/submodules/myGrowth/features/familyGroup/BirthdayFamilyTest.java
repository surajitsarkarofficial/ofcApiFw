package tests.testCases.submodules.myGrowth.features.familyGroup;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import database.submodules.myGrowth.MyGrowthDBHelper;
import dataproviders.submodules.myGrowth.MyGrowthDataProdivers;
import dto.submodules.myGrowth.features.todayBirthdays.GlobersTodayDTO;
import io.restassured.response.Response;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.familyGroup.birthdayFamilyGroup.BirthdayTestHelper;

/**
 * @author christian.chacon
 */
public class BirthdayFamilyTest extends MyGrowthBaseTest {

    @BeforeClass(alwaysRun = true)
    public void beforeClass(){
        featureTest=subModuleTest.createNode("BirthdayFamilyGroupTest");
    }


    /**
     * Check the endpoint status
     * @param userName
     * @throws Exception
     */
    @Test(dataProvider = "usersWithTalentModel", dataProviderClass = MyGrowthDataProdivers.class)
    public void checkStatus(String userName) throws Exception {
        BirthdayTestHelper birthdayTestHelper=new BirthdayTestHelper(userName);
        Response response=birthdayTestHelper.getTodayGlobersBirthday();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    /**
     * Checks Family Group value is accurate against DB.
     * @param userName
     * @throws Exception
     */
    @Test(dataProvider = "usersWithTalentModel", dataProviderClass = MyGrowthDataProdivers.class)
    public void checkFamilyGroup(String userName) throws Exception {
        MyGrowthDBHelper myGrowthDBHelper = new MyGrowthDBHelper();
        BirthdayTestHelper birthdayTestHelper=new BirthdayTestHelper(userName);
        Response response=birthdayTestHelper.getTodayGlobersBirthday();
        List<GlobersTodayDTO> todayBirthdaysResponse=birthdayTestHelper.convertEmptyValuestoNull(Arrays.asList(response.body().as(GlobersTodayDTO[].class)));
        Map<Integer, String> allBirthdayJsonList=birthdayTestHelper.getGloberandFamilyGroup(todayBirthdaysResponse);
        Map<Integer, String> allBirthdayDBList=myGrowthDBHelper.getActualFamilyGroupBirthdaysGlobers();
        SoftAssert softAssert = new SoftAssert();
        for(Map.Entry<Integer, String> glober:allBirthdayJsonList.entrySet()){
            String familyGroup=allBirthdayDBList.get(glober.getKey());
            softAssert.assertEquals(familyGroup, glober.getValue());
        }
        softAssert.assertAll();
    }
}
