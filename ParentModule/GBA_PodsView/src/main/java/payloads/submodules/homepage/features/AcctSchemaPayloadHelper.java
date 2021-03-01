package payloads.submodules.homepage.features;
import java.io.FileReader;
import java.io.IOException;

import io.restassured.response.Response;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
/**
 * @author rachana.jadhav
 */
import payloads.submodules.homepage.HomePageFeaturesBasePayload;
import utils.Utilities;

/**
 *Podsview Account screen payloadHelper
 */
public class AcctSchemaPayloadHelper extends HomePageFeaturesBasePayload{
	/**
	 * This method will return the jsonBody for account schema 
	 * @return jsonBody
	 * @param response
	 */
	public String getAccountSchemaValidation(Response response) throws IOException, ParseException  
	{
		JSONParser jsonparser= new JSONParser(JSONParser.MODE_JSON_SIMPLE);
		FileReader reader= new FileReader("../GBA_PodsView/src/main/resources/jsonSchema/Accounts.json");
		Object object= jsonparser.parse(reader);
		String jsonObject = Utilities.convertJavaObjectToJson(object);
		String jsonBody =
				"{\"notification\" : true,\"emails\" : [\"rachana.jadhav@globant.com\"],\"data\":"+response.asString()+","+
						"\"schema\": "+jsonObject+"}";
		return jsonBody;
	}
}
