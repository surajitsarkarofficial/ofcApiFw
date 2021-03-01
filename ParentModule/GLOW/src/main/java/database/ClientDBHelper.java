package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ClientDBHelper {

	/**
	 * Get clientId
	 * 
	 * @param projectId
	 * @return client Id
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getClientId(String projectId) throws SQLException {
		String query = " SELECT client_fk AS id from projects where id='" + projectId + "'";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("id");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}

	/**
	 * This method will return client id from client name
	 * 
	 * @param clientName
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getClientIdFromClientName(String clientName) throws SQLException {
		ResultSet resultSet = null;
		String query = "SELECT id FROM clients WHERE name='" + clientName + "'";
		try {
			resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			return resultSet.getString("id");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * This method will return client name from client id
	 * @param clientId
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getClientNameFromClientId(String clientId) throws SQLException {
		String query = "SELECT name as clientName FROM clients WHERE id = '"+clientId+"'";
		try {
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			return resultSet.getString("clientName");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * This method will return any client id except the specified client Id
	 * @param clientId
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getClientIdExceptSpecificId(String clientId) throws SQLException {
		String query = "SELECT id as clientId FROM clients WHERE id != '"+clientId+"' ORDER by RANDOM() limit 1;";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("clientId");
		} finally {
			new BaseDBHelper().endConnection();
		}
	} 

	/**
	 * Get client name from project id.
	 * 
	 * @param projectId
	 * @return String
	 * @throws SQLException
	 * @author german.massello
	 */
	public default String getClientNameFromProjectId(String projectId) throws SQLException {
		String query = "SELECT c.name AS clientName\r\n" + 
				"FROM projects p\r\n" + 
				"LEFT JOIN clients c ON p.client_fk=c.id\r\n" + 
				"WHERE p.id=" + projectId;
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("clientName");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
}
