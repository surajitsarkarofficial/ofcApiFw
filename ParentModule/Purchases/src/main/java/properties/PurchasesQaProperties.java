package properties;

/**
 * @author german.massello
 *
 */
public class PurchasesQaProperties extends PurchasesProperties {

	public void setProperties()
	{
		database="jdbc:postgresql://glowdbqa.dev.corp.globant.com:5432/glow_bc";
		dbUser="automationrw";
		dbPassword="WfzCha8K";
		baseURL="https://backendportal-dev.corp.globant.com/BackendPortal-4";
	}
	
}
