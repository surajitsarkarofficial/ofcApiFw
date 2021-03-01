package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDBHelper {
	public static Connection con = null;
	
	public static String dbName; 
	public static String userName;
	public static String password;

	/***
	 * This method will establish the connection with the specified database
	 * 
	 * @return Connection
	 * @throws SQLException
	 */
	private Connection establishConnection() throws SQLException {
		con = DriverManager.getConnection(dbName, userName, password);
		return con;
	}

	/**
	 * This method will end the database connection established.
	 * 
	 * @throws SQLException
	 */
	protected void endConnection() throws SQLException {
		if(con!=null)
		con.close();
	}

	/**
	 * This method will execute the query and return the result set.
	 * 
	 * @param query
	 * @return ResultSet
	 * @throws SQLException 
	 */
	protected ResultSet getResultSet(String query) throws SQLException {
		establishConnection();
		Statement stmt = con.createStatement();
		ResultSet res = stmt.executeQuery(query);
		return res;
	}

	/**
	 * This method will execute Update Queries and return no. of rows affected
	 * @param query
	 * @return int
	 * @throws SQLException
	 */
	protected int executeUpdateQuery(String query) throws SQLException  {
		int noOfRowsAffected;
		establishConnection();
		Statement stmt = con.createStatement();
		noOfRowsAffected = stmt.executeUpdate(query);
		return noOfRowsAffected;
	}

}
