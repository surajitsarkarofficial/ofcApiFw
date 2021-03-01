package payloads.submodules.interview.features;

import java.sql.SQLException;
import java.util.Map;

import utils.Utilities;

public class CreateInterviewPayloadHelper {
	
	/**
	 * This method is used to create json body for creating interview request
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public String getCreateInterviewPayload(Map<Object, Object> data) throws SQLException {
			data.remove("header1Key");
			data.remove("header1Value");
			String json = Utilities.createJsonBodyFromMap(data);
		return json;
	}

}
