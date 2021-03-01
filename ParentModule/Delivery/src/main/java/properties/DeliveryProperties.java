package properties;


/**
 * @author imran.khan
 *
 */

public class DeliveryProperties extends GlowProperties {
	
	public static String dataProviderPath = "../Delivery/src/main/resources/dataproviders/";
	public static String jsonSchemaPath = "../Delivery/src/main/resources/jsonSchema/";
	
	public void configureProperties() throws Exception {
		
		switch (executionEnvironment.toUpperCase()) {

		case "QA":
			new DeliveryQAProperties().setProperties();
			break;
			
		case "DEV":
			new DeliveryDevProperties().setProperties();
			break;
		case "PREPROD":
			new DeliveryPreProdProperties().setProperties();
			break;
			
		default:
			throw new Exception(executionEnvironment+" Environment is not valid for this POD.");

		}
	}

}
