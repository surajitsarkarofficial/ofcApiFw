package properties;

/**
 * @author german.massello
 *
 */
public class FinancialProperties extends GlowProperties {

	public String dataProviderPath = "../Financial/src/main/resources/dataproviders/";
	public String recieptPath = "../Financial/src/main/resources/files/expense/";
	
	public void configureProperties() throws Exception {
		
		coorporativeUsername="";
		coorporativePassword="";

		switch (executionEnvironment.toUpperCase()) {

		case "QA":
			new FinancialQaProperties().setProperties();
			break;
			
		case "DEV":
			new FinancialDevProperties().setProperties();
			break;
			
		case "PREPROD":
			new FinancialPreProdProperties().setProperties();
			break;

		case "UAT":
			new FinancialUatProperties().setProperties();
			break;
			
		default:
			throw new Exception(executionEnvironment+" Environment is not valid for this POD.");

		}
	}
}
