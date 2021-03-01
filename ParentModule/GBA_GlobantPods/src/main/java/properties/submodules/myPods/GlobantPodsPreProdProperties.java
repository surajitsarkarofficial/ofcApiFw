package properties.submodules.myPods;

import properties.GlobantPodsProperties;

/**
 * @author ankita.manekar
 *
 */
public class GlobantPodsPreProdProperties extends GlobantPodsProperties {

	public void setProperties() {
		/*
		 * This pod do not require any connection to the database hence the value for
		 * below fields are null
		 */

		database = "";
		dbUser = "";
		dbPassword = "";
		baseURL = "http://ngl0038dxu23.sfo.corp.globant.com:8765";
		firebaseUrl = "https://us-central1-companion-preprod.cloudfunctions.net/api_v2";
		schemaValidationBaseUrl = "https://us-central1-companion-qa.cloudfunctions.net";
	}
}
