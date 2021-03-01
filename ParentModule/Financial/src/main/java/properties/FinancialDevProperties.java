package properties;

/**
 * @author german.massello
 *
 */
public class FinancialDevProperties extends FinancialProperties {
	
	public void setProperties()
	{
		database="jdbc:postgresql://glowdbqa.dev.corp.globant.com:5432/glow_bc5";
		dbUser="automationrw";
		dbPassword="WfzCha8K";
		baseURL="https://backendportal-dev.corp.globant.com/BackendPortal-1";
		microserviceBaseURL="http://ms2.dev.corp.globant.com";
		batchServer = "";
	}
	
}
