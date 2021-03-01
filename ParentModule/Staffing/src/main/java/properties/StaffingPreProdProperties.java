package properties;

public class StaffingPreProdProperties extends StaffingProperties{
	
	public void setProperties()
	{
		database="jdbc:postgresql://ngl0038dxu22.sfo.corp.globant.com:5432/glowpreprod";
		dbUser="microservices";
		dbPassword="microservices";
		baseURL="https://backendportal-preprod.corp.globant.com/BackendPortal";
		alberthaBaseURL = "https://albertha-in2-dev.corp.globant.com/albertha";
		staffingorchestraBaseURL = "http://10.54.151.33:8629";
		alberthaOpenPositionDB = "jdbc:postgresql://glowdb-in.dev.corp.globant.com:5432/albertha_openpositions_test";
		glowVIQAMs = "http://nist000qxu05.tat.corp.globant.com:8510";
	}
}
