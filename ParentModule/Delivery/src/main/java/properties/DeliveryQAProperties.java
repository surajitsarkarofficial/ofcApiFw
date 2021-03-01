package properties;

/**
 * @author imran.khan
 *
 */

public class DeliveryQAProperties extends DeliveryProperties{
	
	public  void setProperties()
	{
		database="jdbc:postgresql://glowdb-in.dev.corp.globant.com:5432/glow_ui7";
		dbUser="glow_ms";
		dbPassword="glow_ms";
		baseURL="https://backendportal-in-dev.corp.globant.com/BackendPortal-ui7";
	}
}
