package properties;

/**
 * @author german.massello
 *
 */
public class PurchasesProperties extends GlowProperties {

	public String dataProviderPath = "../Purchases/src/main/resources/dataproviders/";
	public String jsonSchemaPath = "../Purchases/src/main/resources/jsonSchemas/";
	
	public void configureProperties() throws Exception {

		switch (executionEnvironment.toUpperCase()) {

		case "QA":
			new PurchasesQaProperties().setProperties();
			break;
			
		case "DEV":
			new PurchasesDevProperties().setProperties();
			break;
			
		case "PREPROD":
			new PurchasesPreProdProperties().setProperties();
			break;
			
		case "UAT":
			new PurchasesUatProperties().setProperties();
			break;		

		case "SIMILPROD":
			new PurchasesSimilProdProperties().setProperties();
			break;	
			
		default:
			throw new Exception(executionEnvironment+" Environment is not valid for this POD.");
		}
		
	}

}
