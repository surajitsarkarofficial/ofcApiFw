package properties;

/**
 * @author imran.khan
 *
 */

public class DeliveryDevProperties extends DeliveryProperties{
	
	public  void setProperties()
	{
		database="jdbc:postgresql://glowdb-in.dev.corp.globant.com:5432/glow_ui6";
		dbUser="glow_ms";
		dbPassword="glow_ms";
		baseURL="https://backendportal-in-dev.corp.globant.com/BackendPortal-ui6";
	}
}
