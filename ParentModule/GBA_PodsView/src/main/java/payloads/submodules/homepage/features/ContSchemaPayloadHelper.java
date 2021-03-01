package payloads.submodules.homepage.features;

/**
 * @author rachana.jadhav
 */

import java.io.FileReader;

import io.restassured.response.Response;
import net.minidev.json.parser.JSONParser;
import payloads.submodules.homepage.HomePageFeaturesBasePayload;
import utils.Utilities;
/**
 *Podsview Contact screen payloadHelper
 */
public class ContSchemaPayloadHelper extends HomePageFeaturesBasePayload{
	/**
	 * This method will return the jsonBody for contact schema
	 * @return jsonBody
	 * @param response
	 */
	public String getContactSchemaValidation(Response response) throws Exception 
	{
		JSONParser jsonparser= new JSONParser(JSONParser.MODE_JSON_SIMPLE);
		FileReader reader= new FileReader("../GBA_PodsView/src/main/resources/jsonSchema/Contacts.json");
		Object javaobject= jsonparser.parse(reader);
		String jsonObject = Utilities.convertJavaObjectToJson(javaobject);
		String jsonBody =
				"{\"notification\" : true,\"emails\" : [\"rachana.jadhav@globant.com\"],\"data\":"+response.asString()+","+
						"\"schema\": "+jsonObject+"}";
		return jsonBody;
	}
}
