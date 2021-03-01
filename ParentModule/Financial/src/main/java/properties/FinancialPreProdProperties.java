package properties;

/**
 * @author german.massello
 *
 */
public class FinancialPreProdProperties extends FinancialProperties {
	
	public void setProperties()
	{
		database="jdbc:postgresql://ngl0038dxu22.sfo.corp.globant.com:5432/glowpreprod";
		dbUser="microservices";
		dbPassword="microservices";
		baseURL="https://backendportal-preprod.corp.globant.com/BackendPortal";
		microserviceBaseURL="http://10.54.151.60";
		batchServer = "";
	}
	
}
