package properties.glow7;

import properties.StaffingProperties;

public class InquistorPreProdProperties extends StaffingProperties{
	
	public void setProperties()
	{
		database="jdbc:postgresql://ngl0038dxu22.sfo.corp.globant.com:5432/glowpreprod";
		dbUser="microservices";
		dbPassword="microservices";
		baseURL="http://ngl0038dxu23.sfo.corp.globant.com:8765";
		alberthaBaseURL = "https://albertha-in2-dev.corp.globant.com/albertha";
	}
}
