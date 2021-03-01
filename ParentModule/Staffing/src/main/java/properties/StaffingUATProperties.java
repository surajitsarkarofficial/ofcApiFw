package properties;

public class StaffingUATProperties extends StaffingProperties{
	
	public void setProperties()
	{
		database="jdbc\\:postgresql\\://backendportal-in-dev.corp.globant.com/backendportal_ui13";
		dbUser="microservices";
		dbPassword="microservices";
		baseURL="https://ui13-in-dev.corp.globant.com/BackendPortal";
		alberthaBaseURL = "https://albertha-in2-dev.corp.globant.com/albertha";
		staffingorchestraBaseURL = "http://10.220.150.174:8629";
		alberthaOpenPositionDB = "jdbc:postgresql://glowdb-in.dev.corp.globant.com:5432/albertha_openpositions_test";
		glowVIQAMs = "http://nist000qxu05.tat.corp.globant.com:8510";
	}
}
