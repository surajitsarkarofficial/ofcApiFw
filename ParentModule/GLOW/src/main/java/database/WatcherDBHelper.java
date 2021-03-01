package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface WatcherDBHelper {

	/**
	 * Get Sr Watcher Name by SrNumber
	 * 
	 * @param SrNumber
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public default String getWatcherNameByPosition(String srNumber) throws SQLException {
		String watcher_name = null;
		String result[] = new String[2];
		String query = "SELECT first_name,last_name FROM globers WHERE id = (SELECT watcher_fk FROM position_watchers WHERE position_fk = "
				+ "(SELECT id FROM positions WHERE issue_tracker_number = '" + srNumber + "') LIMIT 1)";
		ResultSet resultSet = new BaseDBHelper().getResultSet(query);
		try {
			resultSet.next();
			result[0] = resultSet.getString("first_name");
			result[1] = resultSet.getString("last_name");
		} finally {
			new BaseDBHelper().endConnection();
		}
		watcher_name = result[0] + " " + result[1];
		return watcher_name;
	}
	
	/**
	 * Get glober internal assignment type
	 * 
	 * @param watcherName
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public default String getGloberInternalAssignTypeByWatcherName(String[] watcherName) throws SQLException {
		String result = null;
		String query = "SELECT internalassignmenttype FROM glober_view" + "WHERE firstname = '" + watcherName[0]
				+ "' AND lastname = '" + watcherName[1] + "' ORDER BY benchstartdate DESC LIMIT 1";
		ResultSet resultSet = new BaseDBHelper().getResultSet(query);
		try {
			resultSet.next();
			result = resultSet.getString("internalassignmenttype");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}
}
