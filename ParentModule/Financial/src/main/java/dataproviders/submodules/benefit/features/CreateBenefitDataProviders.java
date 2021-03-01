package dataproviders.submodules.benefit.features;

import org.testng.annotations.DataProvider;

import database.submodules.benefit.BenefitDBHelper;
import dataproviders.submodules.benefit.BenefitDataProviders;

/**
 * @author german.massello
 *
 */
public class CreateBenefitDataProviders extends BenefitDataProviders {

	
	/**
	 * This data provider will return a list with all globers.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "allGlobers")
	public Object[][] allGlobers() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new BenefitDBHelper().getAllGlobers("100000");
		return dataObject;
	}
	
	/**
	 * This data provider will return one glober.
	 * @return dataObject
	 * @throws Exception
	 */
	@DataProvider(name = "oneGlober")
	public Object[][] oneGlober() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new BenefitDBHelper().getOneGlober("german.massello");
		return dataObject;
	}
	
	/**
	 * This data provider will return a talent pool glober.
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "talentPoolGlober")
	public Object[][] talentPoolGlober() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new BenefitDBHelper().getTalentPoolGlober();
		return dataObject;
	}

	/**
	 * Get random glober.
	 * @return Object[][]
	 * @throws Exception
	 */
	@DataProvider(name = "randomGlober")
	public Object[][] randomGlober() throws Exception {
		Object[][] dataObject = new Object[1][1];
		dataObject [0][0] = new BenefitDBHelper().getRandomGlober();
		return dataObject;
	}

}
