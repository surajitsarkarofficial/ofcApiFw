package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.SkipException;

/**
 * @author german.massello
 *
 */
public interface SafeApproverDBHelper {

	
	/**
	 * Get safe approver.
	 * @param notificationId
	 * @return String
	 * @throws SQLException
	 */
	public default String getSafeApprover(String notificationId) throws SQLException  {
		ResultSet rs;
		String safeApprover = null;
		try {
			String query = "SELECT g.username AS safeApprover\n" + 
					"FROM approval_chain_notification acn\n" + 
					"LEFT JOIN globers g ON g.id=acn.owner_fk\n" + 
					"WHERE acn.id="+notificationId;
			rs = new BaseDBHelper().getResultSet(query);
			if(!rs.next()) throw new SkipException("No safe approver available for this notification id.");
			else safeApprover=rs.getString("safeApprover");
		}finally{
			new BaseDBHelper().endConnection();
		}	
		return safeApprover;
	}

	/**
	 * Get notification id
	 * @param reportId
	 * @return String
	 * @throws SQLException
	 */
	public default String getNotificationId(String reportId) throws SQLException  {
		ResultSet rs;
		String notificationId = null;
		try {
			String query = "SELECT acn.id AS notificationId\n" + 
					"FROM approval_chain_header ach\n" + 
					"LEFT JOIN approval_chain_notification acn ON ach.id=acn.header_fk\n" + 
					"WHERE acn.delegated_fk ISNULL\n" + 
					"AND ach.object_id="+reportId+"\n" + 
					"AND acn.status='PENDING'\n" + 
					"ORDER BY RANDOM()\n" + 
					"LIMIT 1";
			rs = new BaseDBHelper().getResultSet(query);
			if(!rs.next()) throw new SkipException("No notification id available for this report.");
			else notificationId=rs.getString("notificationId");
		}finally{
			new BaseDBHelper().endConnection();
		}	
		return notificationId;
	}

}
