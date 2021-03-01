package database.submodules.reception.features;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.testng.SkipException;

import database.submodules.reception.ReceptionDBHelper;

/**
 * @author german.massello
 *
 */
public class GetReceptionDBHelper extends ReceptionDBHelper {

	/**
	 * Get reception
	 * @param receptionStatus
	 * @param role
	 * @return Map<String,String>
	 * @throws SQLException
	 */
	public Map<String,String> getReception(String receptionStatus, String role) throws SQLException  {
		Map<String,String> reception=new HashMap<String,String>();
		try {
			String query = "SELECT g.username, COUNT(*) AS quantity\n" + 
					"FROM available_users_authorities aua \n" + 
					"INNER JOIN users ON aua.user_fk = users.id\n" + 
					"INNER JOIN globers g ON g.id = users.resume_fk \n" + 
					"INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk\n" + 
					"INNER JOIN contracts_data cd ON cd.contract_information_fk = ci.id\n" + 
					"LEFT JOIN receptions r ON g.id=r.receptor_fk\n" + 
					"WHERE aua.authority_fk =\n" + 
					"(SELECT id\n" + 
					"FROM public.authorities\n" + 
					"WHERE name='"+role+"')\n" + 
					"AND (LOWER(SUBSTRING(g.username\n" + 
					"FROM 1\n" + 
					"FOR 3)) != 'old')\n" + 
					"AND (cd.end_date IS NULL)\n" + 
					"AND ((CURRENT_DATE - 0) > g.creation_date)\n" + 
					"AND r.status='"+receptionStatus+"'\n" + 
					"GROUP BY g.username\n" + 
					"ORDER BY quantity DESC\n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			String skipMessage="No available reception for this status: "+receptionStatus+ "and role:"+role;
			if(!rs.next()) throw new SkipException(skipMessage);
			reception.put("username", rs.getString("username"));
		}finally{
			endConnection();
		}	
		return reception;
	}

}
