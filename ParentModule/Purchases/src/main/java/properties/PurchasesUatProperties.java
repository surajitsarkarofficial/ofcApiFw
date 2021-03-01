package properties;

/**
 * @author german.massello
 *
 */
public class PurchasesUatProperties extends PurchasesProperties {

	public void setProperties()
	{
		database="jdbc:postgresql://glowdbqa.dev.corp.globant.com:5432/glowpreprod_latam";
		dbUser="automationrw";
		dbPassword="WfzCha8K";
		baseURL="https://backendportal-dev.corp.globant.com/BackendPortal-ui7";
	}
	
}
