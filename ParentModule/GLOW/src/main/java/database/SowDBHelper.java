package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SowDBHelper {

	/**
	 * This method will return the SOW Id of the specified project id.
	 * 
	 * @param projectID
	 * @return sowId
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getSowId(String projectID) throws SQLException {
		try {
			String query = "SELECT id FROM legal_documents WHERE project_fk =" + projectID;
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("id");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * This method will return SOW name
	 * 
	 * @param projectID
	 * @return {@link ArrayList}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default ArrayList<String> getSowName(String projectID) throws SQLException {
		ArrayList<String> arr = new ArrayList<String>();
		String query = "SELECT name FROM legal_documents WHERE project_fk =" + projectID;

		ResultSet rs = new BaseDBHelper().getResultSet(query);
		try {
			while (rs.next())
				arr.add(rs.getString("name").replaceAll("\\[|\\]", ""));
		} finally {
			new BaseDBHelper().endConnection();
		}
		return arr;
	}	
}
