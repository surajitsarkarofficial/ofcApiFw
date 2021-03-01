package database.submodules.staffRequest.features;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.submodules.staffRequest.StaffRequestDBHelper;

public class EditSRDBHelpers extends StaffRequestDBHelper {

	public EditSRDBHelpers() {
	}

	/**
	 * This method will return SR History id
	 * 
	 * @param positionID
	 * @return {@link String}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String getEditSrHistoryId(String positionID) throws SQLException {
		String query = "SELECT id FROM position_change_history WHERE position_fk = " + positionID
				+ " ORDER BY date DESC";
		String result = null;
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			result = rs.getString("id");
		} finally {
			endConnection();
		}
		return result;
	}

	/**
	 * This method will return SR changed hostory id
	 * 
	 * @param EditSRId
	 * @return {@link ArrayList}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public ArrayList<String> getEditSrHistoryChangesId(String EditSRId) throws SQLException {
		String query = "SELECT id FROM position_changes WHERE position_history_fk = " + EditSRId;
		ArrayList<String> result = new ArrayList<String>();
		String id = null;
		ResultSet rs = getResultSet(query);
		try {
			while (rs.next()) {
				id = rs.getString("id");
				result.add(id);
			}
		} finally {
			endConnection();
		}
		return result;
	}

	/**
	 * This method will return SR history details with id
	 * 
	 * @param id
	 * @return {@link ArrayList}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public ArrayList<String> getEditSrHistoryDetailsWithId(String positionId) throws SQLException {
		String query = "SELECT property FROM position_changes WHERE id = " + positionId;
		String query2 = "SELECT old_value FROM position_changes WHERE id = " + positionId;
		String query3 = "SELECT new_value FROM position_changes WHERE id = " + positionId;

		ResultSet rs = getResultSet(query);
		ResultSet rs2 = getResultSet(query2);
		ResultSet rs3 = getResultSet(query3);
		ArrayList<String> result = new ArrayList<>();

		try {
			rs.next();
			String propertyname = rs.getString("property");
			result.add(propertyname);
			rs2.next();
			String oldValue = rs2.getString("old_value");
			result.add(oldValue);
			rs3.next();
			String newValue = rs3.getString("new_value");
			result.add(newValue);
		} finally {
			endConnection();
		}
		return result;
	}
	
	/**
	 * This method will return location Id except provided location name
	 * 
	 * @param locationName
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public String getLocationIdExceptProvidedLocation(String locationName) throws SQLException {
		String query = "SELECT id FROM location_office WHERE location != '"+locationName+"'\n" + 
						"AND  location IN ('IN/PUNE/Hinjewadi','IN/BGL',\n" + 
						"'AR/MDZ','AR/CABA/NorthPark','AR/CABA/Florida') ORDER BY RANDOM() LIMIT 1  ";
		String result = null;
		ResultSet rs = getResultSet(query);
		try {
			rs.next();
			result = rs.getString("id");
		} finally {
			endConnection();
		}
		return result;
	}
}