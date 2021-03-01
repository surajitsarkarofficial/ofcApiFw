package database.submodules.manage.features.approverManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.testng.SkipException;
import database.submodules.manage.ManageDBHelper;

/**
 * @author german.massello
 *
 */
public class CrossApproverDBHelper extends ManageDBHelper {

	/**
	 * This method will return a new cross approver id.
	 * @return String
	 * @throws SQLException
	 */
	public String getNewCrossApproverId() throws SQLException  {
		try {
			String query = "SELECT g.id \n" + 
					"FROM available_users_authorities aua \n" + 
					"INNER JOIN users ON aua.user_fk = users.id\n" + 
					"INNER JOIN globers g ON g.id = users.resume_fk INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk\n" + 
					"INNER JOIN contracts_data cd ON cd.contract_information_fk = ci.id\n" + 
					"WHERE aua.authority_fk =\n" + 
					"(SELECT id\n" + 
					"FROM public.authorities\n" + 
					"WHERE name='PM')\n" + 
					"AND (LOWER(SUBSTRING(g.username\n" + 
					"FROM 1\n" + 
					"FOR 3)) != 'old')\n" + 
					"AND (cd.end_date IS NULL)\n" + 
					"AND ((CURRENT_DATE - 0) > g.creation_date)\n" + 
					"AND g.id NOT IN (\n" + 
					"	SELECT id\n" + 
					"	FROM public.cross_approvers \n" + 
					"	WHERE active=true\n" + 
					")\n" + 
					"ORDER BY random() \n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No new cross approver available.");
			return rs.getString("id");
		}finally{
			endConnection();
		}	
	}

	/**
	 * This method will return a existing cross approver id.
	 * @return String
	 * @throws SQLException
	 */
	public String getExistingCrossApproverId() throws SQLException  {
		try {
			String query = "SELECT ca.glober_fk AS id\n" + 
					"FROM public.cross_approvers ca\n" + 
					"LEFT JOIN globers g ON g.id=ca.glober_fk\n" + 
					"LEFT JOIN contracts_information ci ON ci.id = g.contract_information_fk\n" + 
					"LEFT JOIN contracts_data cd ON cd.contract_information_fk = ci.id\n" + 
					"WHERE (LOWER(SUBSTRING(g.username\n" + 
					"FROM 1\n" + 
					"FOR 3)) != 'old')\n" + 
					"AND (cd.end_date IS NULL)\n" + 
					"AND ((CURRENT_DATE - 0) > g.creation_date)\n" + 
					"AND ca.active=true \n" + 
					"ORDER BY random() \n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No existing cross approver available.");
			return rs.getString("id");
		}finally{
			endConnection();
		}	
	}

	/**
	 * This method will delete a cross approver
	 * @param crossApproverId
	 * @return int
	 * @throws SQLException
	 */
	public int deleteCrossApprover (String crossApproverId) throws SQLException {
		String query="DELETE \n" + 
				"FROM public.cross_approvers\n" + 
				"WHERE glober_fk="+crossApproverId;
		try {
			return executeUpdateQuery(query);
		}finally{
			endConnection();
		}	
	}

}
