package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SoonTobeGloberDbHelper {

	/**
	 * Get STG Full Name
	 * 
	 * @param soonToBeGloberId
	 * @return {@link String}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public default String getSoonToBeGloberFullName(String soonToBeGloberId) throws SQLException {
		try {
			String query = " SELECT candidatefullname AS givenName FROM glober_view WHERE globerid='" + soonToBeGloberId
					+ "";
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("givenName");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * Get Full Name from Glober Full Name
	 * 
	 * @param firstName
	 * @return String
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public default String getFullNamefromGloberFName(String firstName) throws SQLException {
		try {
			String query = "SELECT candidatefullname AS globername FROM glober_view WHERE firstname = " + firstName
					+ "";
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("globername");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * Get STGs globalId by firstname
	 * 
	 * @param firstName
	 * @return String
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public default String getSTGsGlobalIdByFirstName(String firstName) throws SQLException {
		try {
			String query = "SELECT globalid FROM glober_view WHERE firstname = '"+firstName+"' LIMIT 1";
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("globalid");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * Get CandidateId from globerId
	 * 
	 * @param globerId
	 * @return {@link String}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public default String getCandidateIdFromGloberId(String globerId) throws SQLException {
		try {
			String query = " SELECT candidateid AS candidateId FROM glober_View WHERE globerid='" + globerId
					+ "' AND candidateid!=0 IS NOT NULL";
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("candidateId");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * Get random CandidateId who's type is Soon to be Glober Candidate
	 * 
	 * @param stgType
	 * @return {@link String}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public default String getCandidateIdFromType(String stgType) throws SQLException {
		try {
			String query = " SELECT candidateid AS candidateId FROM glober_View WHERE type='" + stgType
					+ "' AND assignedforsr='false' ORDER BY RANDOM() LIMIT 1";
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("candidateId");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
}
