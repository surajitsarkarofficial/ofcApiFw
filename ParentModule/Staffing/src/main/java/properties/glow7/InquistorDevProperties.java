package properties.glow7;

import properties.StaffingQAProperties;

/**
 * @author deepakkumar.hadiya
 */

public class InquistorDevProperties extends StaffingQAProperties {

	@Override
	public void setProperties() {

		baseURL = "http://nglo502dxu00.tat.corp.globant.com:8765";
		database="jdbc:postgresql://glowdb-in.dev.corp.globant.com:5432/glow_ui24";
		dbUser="glow_ms";
		dbPassword="glow_ms";
	}
}
