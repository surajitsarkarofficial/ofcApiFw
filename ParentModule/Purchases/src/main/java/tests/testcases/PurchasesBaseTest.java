package tests.testcases;

import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;

import database.submodules.catalog.features.GroupCatalogDBHelper;
import io.restassured.RestAssured;
import properties.PurchasesProperties;
import tests.GlowBaseTest;

/**
 * @author german.massello
 *
 */
public class PurchasesBaseTest extends GlowBaseTest {

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite(ITestContext context) throws Exception {
		moduleTest=extent.createTest("Purchases");
		RestAssured.useRelaxedHTTPSValidation();
		new PurchasesProperties().configureProperties();
		if (new GroupCatalogDBHelper().getActiveGroupsQuantity()<1) new GroupCatalogDBHelper().activeGroupCatalog();
	}
	
}
