package database.submodules.manage.features.approverManagement;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.SkipException;

import database.submodules.manage.ManageDBHelper;

/**
 * @author german.massello
 *
 */
public class ApproverDBHelper extends ManageDBHelper {

	/**
	 * This method will return a random approver full name.
	 * @return String
	 * @throws SQLException
	 */
	public String getApproverFullName() throws SQLException  {
		try {
			String query = "SELECT (Concat(first_name, ' ',last_name)) as approverFullName\n" + 
					"FROM public.approvers a\n" + 
					"LEFT JOIN globers g	ON a.approver_fk=g.id\n" + 
					"LEFT JOIN cecos c ON a.ceco_fk=c.id AND c.deleted=false\n" + 
					"WHERE (LOWER(SUBSTRING(g.username\n" + 
					"FROM 1\n" + 
					"FOR 3)) != 'old')\n" + 
					"AND g.username!='martin.migoya'\n" + 
					"ORDER BY RANDOM()\n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No approver available.");
			return rs.getString("approverFullName");
		}finally{
			endConnection();
		}	
	}

}
