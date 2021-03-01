package properties;
/**
 * @author rachana.jadhav
 */
import properties.submodules.homepage.PodsViewPreProdProperties;
import properties.submodules.homepage.PodsViewQAProperties;
import properties.submodules.homepage.PodsViewUATProperties;

public class PodsViewBaseProperties extends GlowProperties {
	/**
	 * This method will configure the properties based on environment set
	 * @throws Exception
	 */
	public static String firebaseUrl,schemaValidationBaseUrl;
	public String dataProviderPath="../GBA_PodsView/src/main/resources/dataproviders/";
	
	public void configureProperties() throws Exception {
		switch (executionEnvironment.toUpperCase()) {

		case "QA":
			new PodsViewQAProperties().setProperties();
			break;

		case "UAT":
			new PodsViewUATProperties().setProperties();
			break;

		case "PreProd":
			new PodsViewPreProdProperties().setProperties();
			break;

		default:
			throw new Exception(executionEnvironment+" Environment is not valid for this POD.");

		}
	}
}
