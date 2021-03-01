package database.submodules.reports.features.handlerandhandlerteam;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.submodules.reports.ReportsDBHelper;

public class HandlerAndHandlerTeamDBHelper extends ReportsDBHelper {

	public HandlerAndHandlerTeamDBHelper() {
	}

	/**
	 * Get handler name
	 * 
	 * @param SrNumber
	 * @return {@link String[]}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public String[] getHandlerNameWithSRnumber(String srNumber) throws SQLException {

		String result[] = new String[2];
		String query = "SELECT handlerfirstname,handlerlastname FROM glober_view WHERE handlerId IS NOT NULL "
				+ "AND handlerId = (SELECT handler_fk FROM positions WHERE issue_tracker_number = '" + srNumber
				+ "') LIMIT 1";

		ResultSet resultSet = getResultSet(query);
		try {
			resultSet.next();
			result[0] = resultSet.getString("handlerfirstname");
			result[1] = resultSet.getString("handlerlastname");
		} finally {
			endConnection();
		}
		return result;
	}

	/**
	 * Get handler team name
	 * 
	 * @param SrNumber
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public String getHandlerTeamName(String srNumber) throws SQLException {
		String result = "";
		String query = "SELECT name FROM handler_team WHERE id = (SELECT handler_team_fk FROM positions WHERE issue_tracker_number = '"
				+ srNumber + "')";

		ResultSet resultSet = getResultSet(query);
		try {
			resultSet.next();
			result = resultSet.getString("name");
		} finally {
			endConnection();
		}
		return result;
	}

	/**
	 * Get handlerId AND handler e-Mail
	 * 
	 * @param type
	 * @return {@link String[]}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public String[] getHandlerIdWithEmail(String type) throws SQLException {
		String result[] = new String[2];
		String query = "SELECT handlerId,handleremail FROM glober_view WHERE type='" + type
				+ "' AND status = 'New' ORDER BY RANDOM() LIMIT 1";

		ResultSet resultSet = getResultSet(query);
		try {
			resultSet.next();
			result[0] = resultSet.getString("handlerId");
			result[1] = resultSet.getString("handleremail");
		} finally {
			endConnection();
		}
		return result;
	}

	/**
	 * Get handler Id except provided handler Id
	 * 
	 * @param handlerId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public String getHandlerIdExceptProvidedId(String handlerId) throws SQLException {
		String result = "";
		String query = "SELECT handlerId FROM glober_view WHERE handlerId IS NOT NULL AND handlerId != '"
				+ handlerId + "' LIMIT 1";
		ResultSet resultSet = getResultSet(query);
		try {
			resultSet.next();
			result = resultSet.getString("handlerId");
		} finally {
			endConnection();
		}
		return result;
	}

	/**
	 * Get handlerName from handlerId
	 * 
	 * @param handlerId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public String[] getHandlerNameFromId(String handlerId) throws SQLException {
		String result[] = new String[2];
		String query = "SELECT handlerfirstname,handlerlastname FROM glober_view WHERE handlerId IS NOT NULL "
				+ "AND handlerId = '" + handlerId + "' LIMIT 1";
		ResultSet resultSet = getResultSet(query);
		try {
			resultSet.next();
			result[0] = resultSet.getString("handlerfirstname");
			result[1] = resultSet.getString("handlerlastname");
		} finally {
			endConnection();
		}
		return result;
	}

	/**
	 * Get handler action history
	 * 
	 * @param SrNumber
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public String getHandlerActionHistory(String srNumber) throws SQLException {
		String result = "";
		String query = "SELECT position_change_history.action FROM "
				+ "position_changes,position_change_history,positions WHERE "
				+ "positions.id = position_change_history.position_fk AND "
				+ "position_change_history.id = position_changes.position_history_fk AND "
				+ "position_changes.property = 'handler' AND positions.issue_tracker_number = '" + srNumber
				+ "'" + " ORDER BY position_change_history.date DESC LIMIT 1";
		ResultSet resultSet = getResultSet(query);
		try {
			while(resultSet.next()) {
			 result = resultSet.getString("action");}
		} finally {
			endConnection();
		}
		return result;
	}

	/**
	 * Get handler history
	 * 
	 * @param SrNumber
	 * @return {@link String[]}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public String[] gethandlerHistoryWithOldAndNewName(String srNumber) throws SQLException {
		String result[] = new String[2];
		String query = "SELECT position_changes.old_value,position_changes.new_value FROM "
				+ "position_changes,position_change_history ,positions WHERE "
				+ "positions.id = position_change_history.position_fk AND "
				+ "position_change_history.id = position_changes.position_history_fk AND "
				+ "position_changes.property = 'handler' AND positions.issue_tracker_number = '" + srNumber
				+ "' " + "ORDER BY position_change_history.date DESC LIMIT 1";
		ResultSet resultSet = getResultSet(query);
		try {
			resultSet.next();
			result[0] = resultSet.getString("old_value");
			result[1] = resultSet.getString("new_value");
		} finally {
			endConnection();
		}
		return result;
	}

	/**
	 * Get handlerteam history
	 * 
	 * @param SrNumber
	 * @return {@link String[]}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public String[] getHandlerTeamHistoryWithOldAndNewName(String srNumber) throws SQLException {
		String result[] = new String[2];
		String query = "SELECT position_changes.old_value,position_changes.new_value FROM \r\n" + 
						"position_changes,position_change_history ,positions WHERE \r\n" + 
						"positions.id = position_change_history.position_fk AND \r\n" + 
						"position_change_history.id = position_changes.position_history_fk AND \r\n" + 
						"position_changes.property = 'handler Team' AND positions.issue_tracker_number = '"+srNumber+"'\r\n" + 
						"ORDER BY position_change_history.date DESC LIMIT 1";
		ResultSet resultSet = getResultSet(query);
		try {
			while(resultSet.next()) {
			result[0] = resultSet.getString("old_value");
			result[1] = resultSet.getString("new_value");}
		} finally {
			endConnection();
		}
		return result;
	}

	/**
	 * Get handler team id
	 * 
	 * @param handlerId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public String getHandlerTeamId(String handlerId) throws SQLException {
		String result = null;
		String query = "SELECT id FROM handler_team WHERE id in (13,17,15,18,19,22,14,16,20,21) AND "
				+ "id != (SELECT handler_team_fk FROM handler_team_handlers WHERE handler_fk = '" + handlerId+"'"
				+ " LIMIT 1) ORDER BY RANDOM() LIMIT 1";
		ResultSet resultSet = getResultSet(query);
		try {
			while(resultSet.next()) {
			result = resultSet.getString("id");}
		} finally {
			endConnection();
		}
		return result;
	}

	/**
	 * Get handler team name
	 * 
	 * @param handlerTeamId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public String getHandlerTeamNameById(String handlerTeamId) throws SQLException {
		String result = null;
		String query = "SELECT name FROM handler_team WHERE id =" + handlerTeamId;
		ResultSet resultSet = getResultSet(query);
		try {
			resultSet.next();
			result = resultSet.getString("name");
		} finally {
			endConnection();
		}
		return result;
	}

	/**
	 * Get glober handler action by globerId
	 * 
	 * @param id
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public String getGloberHandlerAction(String id) throws SQLException {
		String result = null;
		String query = "SELECT action FROM glober_change_history WHERE glober_fk = '" + id
				+ "' ORDER BY created_date DESC LIMIT 1";
		ResultSet resultSet = getResultSet(query);
		try {
			resultSet.next();
			result = resultSet.getString("action");
		} finally {
			endConnection();
		}
		return result;
	}

	/**
	 * Get glober handler history by globerId
	 * 
	 * @param id
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public String[] getGloberHandlerHistoryById(String id) throws SQLException {
		String result[] = new String[2];
		String query = "SELECT old_value,new_value FROM glober_changes WHERE glober_history_fk = "
				+ "(SELECT id FROM glober_change_history WHERE glober_fk = '" + id + "' "
				+ "ORDER BY created_date DESC LIMIT 1)";
		ResultSet resultSet = getResultSet(query);
		try {
			resultSet.next();
			result[0] = resultSet.getString("old_value");
			result[1] = resultSet.getString("new_value");
		} finally {
			endConnection();
		}
		return result;
	}
}
