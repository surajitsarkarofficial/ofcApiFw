package database.submodules.manage;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.FinancialDBHelper;

/**
 * @author german.massello
 *
 */
public class ManageDBHelper extends FinancialDBHelper {

	public ManageDBHelper() {
	}

	/**
	 * This method will return a random ceco number.
	 * @return randomCeco
	 * @throws SQLException
	 * @author german.massello
	 */
	public String getRandomCeco() throws SQLException  {
		try {
			String query = "SELECT number\n" + 
					"FROM public.cecos\n" + 
					"WHERE deleted=false\n" + 
					"ORDER BY RANDOM()\n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("number");
		}finally{
			endConnection();
		}	
	}
	
	/**
	 * This method will return a non existent ceco number.
	 * @return String
	 * @throws SQLException
	 * @author german.massello
	 */
	public String getNonExistentCecoNumber() throws SQLException  {
		try {
			String query = "SELECT MAX(number)+1 AS number\n" + 
					"FROM public.cecos\n";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("number");
		}finally{
			endConnection();
		}	
	}
}
