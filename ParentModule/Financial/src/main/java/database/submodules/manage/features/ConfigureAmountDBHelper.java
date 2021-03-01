package database.submodules.manage.features;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.submodules.manage.ManageDBHelper;

/**
 * @author german.massello
 *
 */
public class ConfigureAmountDBHelper extends ManageDBHelper {

	/**
	 * This method will return an existing rol name.
	 * @param excludedName
	 * @return name
	 * @throws SQLException
	 */
	public String getExistingRolName(String excludedName) throws SQLException  {
		try {
			String query = "SELECT name\n" + 
					"FROM public.approvers_safe_matrix_roles\n" + 
					"WHERE is_ceco=true \n" + 
					"AND status='active'\n" + 
					"AND name !='"+excludedName+"'\n" + 
					"ORDER BY RANDOM()\n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("name");
		}finally{
			endConnection();
		}	
	}
	
	/**
	 * This method will return an existing rol amount.
	 * @param excludedAmount
	 * @return max_amount
	 * @throws SQLException
	 */
	public String getExistingRolAmount(long excludedAmount) throws SQLException  {
		try {
			String query = "SELECT max_amount\n" + 
					"FROM public.approvers_safe_matrix_roles\n" + 
					"WHERE is_ceco=true \n" + 
					"AND status='active'\n" + 
					"AND max_amount != "+excludedAmount+"\n" + 
					"AND max_amount != "+-1+"\n" + 
					"ORDER BY RANDOM()\n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("max_amount");
		}finally{
			endConnection();
		}	
	}
	
	/**
	 * This method will return an non-existing rol id.
	 * @return id
	 * @throws SQLException
	 */
	public String getAnNonExistingRolId() throws SQLException  {
		try {
			String query = "SELECT max(id)+1 as id\n" + 
					"FROM public.approvers_safe_matrix_roles\n";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("id");
		}finally{
			endConnection();
		}	
	}

	/**
	 * This method will check if a position has a role.
	 * @param positionName
	 * @return String
	 * @throws SQLException
	 */
	public String positionHasRole(String positionName) throws SQLException  {
		try {
			String query = "SELECT apr.id\n" + 
					"FROM public.approvers_position_role apr\n" + 
					"LEFT JOIN public.approvers_safe_matrix_roles asmr ON asmr.id=apr.role_fk\n" + 
					"WHERE apr.position='"+positionName+"'\n" + 
					"AND asmr.status='active'";
			ResultSet rs = getResultSet(query);
			if (rs.next()) return rs.getString("id");
			else return "0";
		}finally{
			endConnection();
		}	
	}
	
}
