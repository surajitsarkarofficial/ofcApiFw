package properties;

/**
 * @author german.massello
 *
 */
public class FinancialQaProperties extends FinancialProperties {
	
	public void setProperties()
	{
		database="jdbc:postgresql://glowdbqa.dev.corp.globant.com:5432/glow_bc";
		dbUser="automationrw";
		dbPassword="WfzCha8K";
		baseURL="https://backendportal-dev.corp.globant.com/BackendPortal-4";
		microserviceBaseURL="http://ms4.dev.corp.globant.com";
		batchServer = "glow-bc.cs.globant.com";
	}
	
}
