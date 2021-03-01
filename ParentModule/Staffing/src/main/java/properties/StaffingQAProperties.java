package properties;

public class StaffingQAProperties extends StaffingProperties{
	
	public  void setProperties()
	{
		database="jdbc:postgresql://glowdb-in.dev.corp.globant.com:5432/glow_ui14";
		dbUser="glow_ms";
		dbPassword="glow_ms";
		baseURL="https://backendportal-in-dev.corp.globant.com/BackendPortal-ui14";
		alberthaBaseURL = "https://albertha-in2-dev.corp.globant.com/albertha";
		staffingorchestraBaseURL = "http://10.220.150.174:8629";
		alberthaOpenPositionDB = "jdbc:postgresql://glowdb-in.dev.corp.globant.com:5432/albertha_openpositions_test";
		glowVIQAMs = "http://nist000qxu05.tat.corp.globant.com:8510";
	}
}
