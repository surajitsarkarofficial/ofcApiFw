package payloads.submodules.homepage.features;
/**
 * @author rachana.jadhav
 */
import com.google.gson.Gson;

import payloads.submodules.homepage.HomePageFeaturesBasePayload;
import tests.testhelper.submodules.homepage.features.SearchTestHelper;
/**
 *Podsview Post Search api payloadHelper
 */
public class RealSearchPayloadHelper extends HomePageFeaturesBasePayload{
	/**
	 * This method will return the jsonBody for post recent search
	 * @return jsonBody
	 * @param name
	 * @param id
	 */
	public String postSearchPayloadHelper(String name, String id) throws Exception 
	{
		Gson gson = new Gson();
		SearchTestHelper SearchTestHelper = new SearchTestHelper();
		Object responseData=SearchTestHelper.postSearchHelper(name, id);
		String json = gson.toJson(responseData);
		String jsonBody = "{\"searchObject\" :"+json+"}";
		return jsonBody;
	}
}
