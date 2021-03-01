package properties.submodules.homepage;
/**
 * @author rachana.jadhav
 */
import properties.PodsViewBaseProperties;

public class PodsViewQAProperties extends PodsViewBaseProperties{
	public  void setProperties()
	{
		//This pod do not require any connection to the database hence the value for below fields are null
		database="";
		dbUser="";
		dbPassword="";
		baseURL="http://34.125.225.37:8080";
		firebaseUrl="https://us-central1-globantview-qa.cloudfunctions.net/api_v2_2_0";
		schemaValidationBaseUrl="https://us-central1-companion-qa.cloudfunctions.net";
					
	}
}
