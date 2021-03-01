package properties;

import properties.submodules.myPods.GlobantPodsPreProdProperties;
import properties.submodules.myPods.GlobantPodsQAProperties;
import properties.submodules.myPods.GlobantPodsUATProperties;

/**
 * @author ankita.manekar
 *
 */
public class GlobantPodsProperties extends GlowProperties {
	/**
	 * This method will configure the properties based on environment set
	 * 
	 * @throws Exception
	 */
	public static String firebaseUrl, schemaValidationBaseUrl,generateTokenBaseUrl;
	public static String dataProviderPath = "../GBA_GlobantPods/src/main/resources/dataproviders/";

	public void configureProperties() throws Exception {
		switch (executionEnvironment.toUpperCase()) {

		case "QA":
			new GlobantPodsQAProperties().setProperties();
			break;

		case "UAT":
			new GlobantPodsUATProperties().setProperties();
			break;

		case "PreProd":
			new GlobantPodsPreProdProperties().setProperties();
			break;

		default:
			throw new Exception(executionEnvironment + " Environment is not valid for this POD.");

		}
	}
}
