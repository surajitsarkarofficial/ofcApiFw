package utils.batch;

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import utils.UtilsBase;

/**
 * @author german.massello
 *
 */
public class BatchConnection extends UtilsBase {
	
	/**
	 * Get batch https connection
	 * @param serviceUrl
	 * @param type
	 * @return HttpsURLConnection
	 * @throws IOException
	 */
	public static HttpsURLConnection getBatchHttpsConnection(String serviceUrl, String type) throws IOException {
		String log = "Url Services Request: "+serviceUrl + " Type:"+type;
		log(log);
		UrlRequestManager.disableSslVerification();
		URL url = new URL(serviceUrl);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestMethod(type);
		connection.setConnectTimeout(60000);
		connection.setReadTimeout(60000);
		connection.setSSLSocketFactory(connection.getSSLSocketFactory());
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		return connection;		
	}
	
}
