package properties;

/**
 * @author german.massello
 *
 */
public class PurchasesSimilProdProperties extends PurchasesProperties {

	public void setProperties()
	{
		database="jdbc:postgresql://glowdbqa.dev.corp.globant.com:5432/glowsimilprod";
		dbUser="automationrw";
		dbPassword="WfzCha8K";
		baseURL="https://backendportal-dev.corp.globant.com/BackendPortal-ui1";
	}
	
}
