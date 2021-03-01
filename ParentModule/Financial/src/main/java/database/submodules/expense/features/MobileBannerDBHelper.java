package database.submodules.expense.features;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.submodules.expense.ExpenseDBHelper;

/**
 * @author german.massello
 *
 */
public class MobileBannerDBHelper extends ExpenseDBHelper {

	/**
	 * This method will return a glober without mobile expenses.
	 * @param rol
	 * @return username
	 * @throws SQLException
	 * @author german.massello
	 */
	public String getGloberWithoutMobileExpenses(String rol) throws SQLException  {
		try {
			String query = "SELECT g.username\n" + 
					"FROM globers g\n" + 
					"LEFT JOIN ticket_details td ON g.id = td.submitter_fk\n" + 
					"WHERE td.expense_origin=1\n" + 
					"  AND\n" + 
					"    (SELECT count(*)\n" + 
					"     FROM ticket_details td2\n" + 
					"     WHERE td2.expense_origin=2\n" + 
					"       AND td2.submitter_fk=g.id )=0\n" + 
					"  AND\n" + 
					"    (SELECT count(*)\n" + 
					"     FROM tickets t\n" + 
					"     WHERE t.report_origin=2\n" + 
					"       AND t.submitter_fk=g.id)=0\n" + 
					"  AND g.id NOT IN\n" + 
					"    (SELECT glober_fk\n" + 
					"     FROM public.glober_banners)\n" + 
					"  AND (LOWER(SUBSTRING(g.username\n" + 
					"                       FROM 1\n" + 
					"                       FOR 3)) != 'old')\n" + 
					"ORDER BY td.id DESC\n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("username");
		}finally{
			endConnection();
		}	
	}

}
