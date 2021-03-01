package properties.glow7;

import properties.StaffingQAProperties;

/**
 * @author deepakkumar.hadiya
 */

public class InquistorQAProperties extends StaffingQAProperties {

	@Override
	public void setProperties() {

		baseURL = "http://nglo502dxu03.tat.corp.globant.com:8765";
		database="jdbc:postgresql://glowdb-in.dev.corp.globant.com:5432/glow_ui26";
		dbUser="glow_ms";
		dbPassword="glow_ms";
	}
}
