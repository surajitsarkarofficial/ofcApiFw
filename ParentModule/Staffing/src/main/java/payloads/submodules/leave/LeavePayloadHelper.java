package payloads.submodules.leave;

import java.sql.SQLException;
import java.util.Map;

import constants.StaffingConstants;
import utils.Utilities;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */
public class LeavePayloadHelper implements StaffingConstants{
	
	/**
	 * This method is used to create json body for applying leave request
	 * @param data
	 * @return
	 * @throws SQLException
	 */
	public String getCreateInterviewPayload(Map<Object, Object> data) throws SQLException {
			data.remove(HEADER_KEY);
			data.remove(HEADER_VALUE);
			String json = Utilities.createJsonBodyFromMap(data);
		return json;
	}

}
