package properties.glow7;

import properties.StaffingProperties;
import properties.StaffingSimilProperties;

/**
 * @author deepakkumar.hadiya
 */

public class InquistorProperties extends StaffingProperties {
	
	public void configureProperties() throws Exception {
		
		switch (executionEnvironment.toUpperCase()) {

		case "DEV":
			new InquistorDevProperties().setProperties();
			break;
			
		case "QA":
			new InquistorQAProperties().setProperties();
			break;
		
		case "UAT":
			new InquistorUATProperties().setProperties();
			break;	
			
		case "PREPROD":
			new InquistorPreProdProperties().setProperties();
			break;
			
		case "SIMILPROD":
			new StaffingSimilProperties().setProperties();
			break;
			
		default:
			throw new Exception(executionEnvironment+" Environment is not valid for this POD.");

		}
	}

}
