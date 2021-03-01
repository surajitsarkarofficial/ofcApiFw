package payloads.submodules.myPods.features;

import java.io.FileReader;
import java.io.IOException;

import io.restassured.response.Response;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import payloads.submodules.myPods.MyPodsPayload;
import utils.Utilities;

/**
 * @author ankita.manekar
 *
 */
/**
 * GlobantPods PodList payloadHelper
 */
public class PodListPayloadHelper extends MyPodsPayload {
	/**
	 * This method will return the jsonBody for PodList schema
	 * 
	 * @return jsonBody
	 * @param response
	 */
	public String getPodListSchemaValidation(Response response) throws IOException, ParseException {
		JSONParser jsonparser = new JSONParser(JSONParser.MODE_JSON_SIMPLE);
		FileReader reader = new FileReader("../GBA_GlobantPods/src/main/resources/jsonSchema/podList.json");
		Object object = jsonparser.parse(reader);
		String jsonObject = Utilities.convertJavaObjectToJson(object);
		String jsonBody = "{\"notification\" : true,\"emails\" : [\"rachana.jadhav@globant.com\",\"ankita.manekar@globant.com\"],\"data\":"
				+ response.asString() + "," + "\"schema\": " + jsonObject + "}";
		return jsonBody;
	}
}
