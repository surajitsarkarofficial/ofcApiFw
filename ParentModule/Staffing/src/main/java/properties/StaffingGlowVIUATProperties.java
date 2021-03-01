package properties;

public class StaffingGlowVIUATProperties extends StaffingProperties{
	
	public void setProperties()
	{
		database="jdbc:postgresql://glowdb-in.dev.corp.globant.com:5432/glow_ui13";
		dbUser="glow_ms";
		dbPassword="glow_ms";
		baseURL="https://backendportal-in-dev.corp.globant.com/BackendPortal-ui13";
		alberthaBaseURL = "https://albertha-in2-dev.corp.globant.com/albertha";
	}
}
