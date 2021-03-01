package database.submodules.staffRequest.features.globalstaffrequest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.submodules.staffRequest.StaffRequestDBHelper;

public class ManageWatcherDBHelper extends StaffRequestDBHelper {

	public ManageWatcherDBHelper() {
	}
	
	/**
	 * Get glober to add as watcher of staff request
	 * @return {@link ArrayList}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public ArrayList<Integer> getGlober() throws SQLException {
		String result = null;
		ArrayList<Integer> watcherList = new ArrayList<Integer>();
		String query = "SELECT globerid FROM glober_view WHERE type = 'Glober'\r\n" + 
				       "AND position IN('Tech Manager','Tech Director')\r\n" + 
				       "AND internalassignmenttype <> 'OUT_OF_COMPANY'\r\n" + 
				       "ORDER BY RANDOM() LIMIT 1";
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			result = rs.getString("globerid");
			watcherList.add(Integer.parseInt(result));
		} finally {
			endConnection();
		}
		return watcherList;
	}
	
	/**
	 * Get Sr Watchers Id
	 * @return {@link ArrayList}
	 * @param positionId
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public ArrayList<String> getWatchersId(String positionId) throws SQLException {
		String result = null;
		ArrayList<String> watcherList = new ArrayList<String>();
		String query = "SELECT watcher_fk FROM position_watchers WHERE position_fk = "+positionId+" \r\n" + 
				       "AND active = 'true'";
		ResultSet rs = getResultSet(query);
		try {
			while(rs.next()) {
				result = rs.getString("watcher_fk");
				watcherList.add(result);
			}	
		} finally {
			endConnection();
		}
		return watcherList;
	}
	
}
