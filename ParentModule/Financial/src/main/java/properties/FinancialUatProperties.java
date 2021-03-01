package properties;

/**
 * @author german.massello
 *
 */
public class FinancialUatProperties extends FinancialProperties {
	
	public void setProperties()
	{
		database="jdbc:postgresql://glowdbqa.dev.corp.globant.com:5432/glowpreprod_latam";
		dbUser="automationrw";
		dbPassword="WfzCha8K";
		baseURL="https://backendportal-dev.corp.globant.com/BackendPortal-ui7";
		microserviceBaseURL="http://ms6.dev.corp.globant.com";
		batchServer = "";
	}

}
