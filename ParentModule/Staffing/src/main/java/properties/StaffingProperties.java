package properties;

public class StaffingProperties extends GlowProperties {
	
	public static String dataProviderPath = "../Staffing/src/main/resources/dataproviders";
	
public void configureProperties() throws Exception {
		
		switch (executionEnvironment.toUpperCase()) {

		case "QA":
			new StaffingQAProperties().setProperties();
			break;
			
		case "PREPROD":
			new StaffingPreProdProperties().setProperties();
			break;
			
		case "UAT":
			new StaffingGlowVIUATProperties().setProperties();
			break;
			
		case "SIMIL":
			new StaffingSimilProperties().setProperties();
			break;
			
		default:
			throw new Exception(executionEnvironment+" Environment is not valid for this POD.");

		}
	}

}
