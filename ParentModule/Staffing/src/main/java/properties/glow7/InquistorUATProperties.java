package properties.glow7;

import properties.StaffingQAProperties;

/**
 * @author deepakkumar.hadiya
 */

public class InquistorUATProperties extends StaffingQAProperties {

	@Override
	public void setProperties() {

		baseURL = "http://nglo502dxu06.tat.corp.globant.com:8765";
		database="jdbc:postgresql://glowdb-in.dev.corp.globant.com:5432/glow_ui29";
		dbUser="glow_ms";
		dbPassword="glow_ms";
	}
}
