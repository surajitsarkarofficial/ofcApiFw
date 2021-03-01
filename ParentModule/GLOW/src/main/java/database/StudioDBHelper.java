package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface StudioDBHelper {

	/**
	 * Get Studio Id
	 * 
	 * @param projectId
	 * @return {@link String}
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getStudioId(String projectId) throws SQLException {
		String query = "SELECT s.id AS id FROM studios s JOIN projects p ON p.studio_fk=s.id WHERE p.id='" + projectId
				+ "'";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("id");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}

	/**
	 * Get Project Studio Name
	 * 
	 * @param studio Id
	 * @return {@link String}
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getProjectStudioName(String studioId) throws SQLException {
		String query = "SELECT name FROM studios WHERE id='" + studioId + "'";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("name");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * This method will return studio id
	 * 
	 * @param SrNumber
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getStudioIDFromPosition(String SrNumber) throws SQLException {
		String query = "SELECT studio_fk FROM positions WHERE issue_tracker_number =" + SrNumber;
		String result = null;
		ResultSet rs = new BaseDBHelper().getResultSet(query);
		try {
			rs.next();
			result = rs.getString("studio_fk");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}
	
	/**
	 * This method will return random studio id
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getRandomStudioId() throws SQLException {
		String query = "SELECT id as studioId FROM studios ORDER BY RANDOM() limit 1;";
		String result = null;
		ResultSet rs = new BaseDBHelper().getResultSet(query);
		try {
			rs.next();
			result = rs.getString("studioId");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}

	/**
	 * Get studioName from studio Id
	 * 
	 * @param studioId
	 * @return {@link String}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public default String getStudioName(String studioId) throws SQLException {
		try {
			String query = " SELECT DISTINCT studioname AS studioName FROM glober_view WHERE studioid='" + studioId
					+ "'";
			String result = "";
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			while (rs.next()) {
				result = rs.getString("studioName");
			}
			return result;
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
}
