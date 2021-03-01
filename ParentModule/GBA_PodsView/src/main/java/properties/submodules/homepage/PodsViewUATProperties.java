package properties.submodules.homepage;
/**
 * @author rachana.jadhav
 */
import properties.PodsViewBaseProperties;

public class PodsViewUATProperties extends PodsViewBaseProperties{
	public  void setProperties()
	{
		//This pod do not require any connection to the database hence the value for below fields are null
		database="";
		dbUser="";
		dbPassword="";
		baseURL="http://nglb088qxu02.tat.corp.globant.com:8765";
		firebaseUrl="https://us-central1-companion-uat.cloudfunctions.net/api_uat_v3_1_0";
		schemaValidationBaseUrl="https://us-central1-companion-qa.cloudfunctions.net";
		
	}

	
}
