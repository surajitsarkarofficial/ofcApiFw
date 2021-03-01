package database.myTeam.features;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.myTeam.MyTeamDBHelper;
import dto.myTeam.features.UserPreferencesDtoList;

/**
 * @author imran.khan
 *
 */

public class UserPreferencesDbHelper extends MyTeamDBHelper {

	public UserPreferencesDbHelper() {
	}

	/**
	 * This method will return user preferences for a glober
	 * 
	 * @return listOfFilterObject
	 * @throws SQLException
	 */
	public List<UserPreferencesDtoList> getuserPreferencesforUser(String globerId) throws SQLException {
		List<UserPreferencesDtoList> listOfFilterObject = new ArrayList<UserPreferencesDtoList>();
		UserPreferencesDtoList filterObject;
		String query = "SELECT active,\r\n" + 
				"       component_key,\r\n" + 
				"       value\r\n" + 
				"FROM user_preferences\r\n" + 
				"WHERE glober_fk='" + globerId +
				"'AND component_key='viewProjectList';";
		try {
			ResultSet resultSet = getResultSet(query);
			while (resultSet.next()) {
				filterObject = new UserPreferencesDtoList();
				filterObject.setComponentKey(resultSet.getString("component_key"));
				filterObject.setValue(resultSet.getString("value"));
				filterObject.setActive(resultSet.getBoolean("active"));
				listOfFilterObject.add(filterObject);
			}
		} finally {
			endConnection();
		}
		return listOfFilterObject;
	}

	/**
	 * This method will delete user preferences for a glober
	 * 
	 * @throws SQLException
	 */
	public void deleteUserPreferenceForUserIfExist(String globerId) throws SQLException {

		String query1 = "delete from user_preferences where glober_fk=" + globerId;
		String query2 = "commit;";
		try {
			executeUpdateQuery(query1);
			executeUpdateQuery(query2);
		} finally {
			endConnection();
		}
	}

}
