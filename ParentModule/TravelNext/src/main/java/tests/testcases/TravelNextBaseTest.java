package tests.testcases;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;
import properties.GlowProperties;
import tests.GlowBaseTest;
import properties.TravelNextProperties;

/**
 * @author josealberto.gomez
 *
 */
public class TravelNextBaseTest extends GlowBaseTest{
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite(ITestContext context) throws Exception {
        moduleTest = extent.createTest("TravelNext Suite");
        RestAssured.useRelaxedHTTPSValidation();
        new TravelNextProperties().configureProperties();
        baseUrl = GlowProperties.baseURL;
    }

    @BeforeSuite(alwaysRun = true)
    public void fakeLogin() throws Exception
    {
        fakeLogin(TravelNextProperties.loggedInUser);
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest() {
        subModuleTest = moduleTest.createNode("TravelNext Tests");
    }

    /**
     * This method will perform a fake login
     *
     * @param username
     * @throws Exception
     */
    public void fakeLogin(String username) throws Exception
    {
        new GlowBaseTest().createSession(username);
    }


    /**
     * This method will fetch the value of the specified key from the response
     * object
     *
     * @param jsonString
     * @param key
     * @return Object
     * @throws Exception
     */
    public static Object getArrayFromJSONString(String jsonString, String key) {
        Object value = null;

        if (jsonString == null || jsonString.equals("")) {
            return null;
        }
        try {
            ReadContext ctx = JsonPath.parse(jsonString);
            value = ctx.read(key);
        } catch (Exception e) {
            return null;
        }
        return value;
    }
}
