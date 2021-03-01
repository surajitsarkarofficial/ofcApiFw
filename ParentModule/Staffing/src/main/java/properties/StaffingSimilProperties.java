package properties;

public class StaffingSimilProperties extends StaffingProperties{
	
	public  void setProperties()
	{
		database="jdbc:postgresql://glowdbqa.dev.corp.globant.com:5432/glowsimilprod";
		dbUser="microservices";
		dbPassword="microservices";
		baseURL="https://backendportal-dev.corp.globant.com/BackendPortal-ui1";
		alberthaBaseURL = "https://albertha-in2-dev.corp.globant.com/albertha";
		staffingorchestraBaseURL = "http://10.220.150.174:8629";
		alberthaOpenPositionDB = "jdbc:postgresql://glowdb-in.dev.corp.globant.com:5432/albertha_openpositions_test";
	}

}
