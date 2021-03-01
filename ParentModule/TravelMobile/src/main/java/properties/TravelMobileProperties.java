package properties;

public class TravelMobileProperties extends GlowProperties {
	
	public static String dataProviderPath = "../TravelMobile/src/main/resources/dataproviders/";
	public static String loggedInUser;
	
	/**
	 * This method will configure the properties based on environment set
	 * @throws Exception
	 */
	public void configureProperties() throws Exception {
		switch (executionEnvironment.toUpperCase()) {

		case "QA":
			new TravelMobileQAProperties().setProperties();
			break;
			
		case "DEV":
			new TravelMobileDEVProperties().setProperties();
			break;
			
		case "UI21":
			new TravelMobileUI21Properties().setProperties();
			break;

		default:
			throw new Exception(executionEnvironment+" Environment is not valid for this POD.");

		}
	}
}
