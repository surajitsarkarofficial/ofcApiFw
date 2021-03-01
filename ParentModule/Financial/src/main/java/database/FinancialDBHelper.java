package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.SkipException;

/**
 * @author german.massello
 *
 */
public class FinancialDBHelper extends GlowDBHelper {

	public FinancialDBHelper() {
	}

	/**
	 * This method will return a random glober.
	 * This glober has an active bank account. 
	 * @return String
	 * @throws SQLException
	 * @author german.massello
	 */
	public String getGloberThatHaveABankAccount() throws SQLException  {
		try {
			String query = "SELECT g.username AS username\n" + 
					"FROM available_users_authorities aua \n" + 
					"INNER JOIN users ON aua.user_fk = users.id \n" + 
					"INNER JOIN globers g ON g.id = users.resume_fk \n" + 
					"INNER JOIN personal_information pi ON g.personal_information_fk=pi.id \n" + 
					"INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk \n" + 
					"INNER JOIN contracts_data cd ON cd.contract_information_fk = ci.id \n" + 
					"WHERE pi.bank_account_fk IS NOT NULL AND\n" + 
					"	aua.authority_fk = (\n" + 
					"	SELECT id FROM public.authorities WHERE name='Glober') \n" + 
					"	AND (LOWER(SUBSTRING(g.username FROM 1 FOR 3)) != 'old') \n" + 
					"	AND (cd.end_date IS NULL) AND ((CURRENT_DATE - 0) > g.creation_date) \n" + 
					"   AND pi.country_fk=(SELECT id FROM countries WHERE name='Mexico') \n"+
					"	AND g.username NOT LIKE '%ñ%' " +
					"   AND (aua.expiration_date > CURRENT_DATE) \n" +
					"   ORDER BY random() LIMIT 1";
			ResultSet rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No glober that have a bank account available.");
			return rs.getString("username");
		}finally{
			endConnection();
		}	
	}
	
	/**
	 * This method will return a random contable code.
	 * @return String
	 * @throws SQLException
	 * @author german.massello
	 */
	public String getRandomContableCode() throws SQLException  {
		try {
			String query = "SELECT code\n" + 
					"FROM public.contable_code\n" + 
					"WHERE active=true\n" + 
					"ORDER BY RANDOM()\n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No contable code available.");
			return  rs.getString("code");
		}finally{
			endConnection();
		}	
	}

	/**
	 * This method will return a random currency code.
	 * @return String
	 * @throws SQLException
	 * @author german.massello
	 */
	public String getRandomCurrencyCode() throws SQLException  {
		try {
			String query = "SELECT code\n" + 
					"FROM public.currency\n" + 
					"WHERE is_common=true\n" + 
					"ORDER BY RANDOM()\n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No currency code available.");
			return  rs.getString("code");
		}finally{
			endConnection();
		}	
	}

	/**
	 * This method will return a currency code.
	 * @param name
	 * @return String
	 * @throws SQLException
	 */
	public String getContableCodeByName(String name) throws SQLException  {
		try {
			String query = "SELECT code\n" + 
					"FROM public.contable_code\n" + 
					"WHERE name='"+name+"'";
			ResultSet rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No contable code available.");
			return  rs.getString("code");
		}finally{
			endConnection();
		}	
	}

}
