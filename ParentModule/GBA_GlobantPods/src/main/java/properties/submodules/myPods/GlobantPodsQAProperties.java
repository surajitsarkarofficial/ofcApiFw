package properties.submodules.myPods;

import properties.GlobantPodsProperties;

/**
 * @author ankita.manekar
 *
 */
public class GlobantPodsQAProperties extends GlobantPodsProperties {

	public void setProperties() {
		/*
		 * This pod do not require any connection to the database hence the value for
		 * below fields are null
		 */
		database = "";
		dbUser = "";
		dbPassword = "";
		baseURL = "http://34.125.225.37:8080/glow/";
		firebaseUrl = "https://us-central1-companion-qa.cloudfunctions.net/api_qa_v4_3_0";
		schemaValidationBaseUrl = "https://us-central1-companion-qa.cloudfunctions.net";
		generateTokenBaseUrl= "https://us-central1-companion-dev-235618.cloudfunctions.net";

	}
}
