package database.submodules.manage.features.approverManagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.testng.SkipException;

import database.submodules.manage.ManageDBHelper;

/**
 * @author german.massello
 *
 */
public class CecoDBHelper extends ManageDBHelper {

	/**
	 * This method will return a new ceco number.
	 * @return String
	 * @throws SQLException
	 */
	public String getNewCecoNumber() throws SQLException  {
		try {
			String query = "SELECT MAX(\"number\")+1 AS cecoNumber\n" + 
					"FROM public.cecos";
			ResultSet rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No ceco available.");
			return rs.getString("cecoNumber");
		}finally{
			endConnection();
		}	
	}

	/**
	 * This method will return a existing ceco number.
	 * @return String
	 * @throws SQLException
	 */
	public String getExistingCecoNumber() throws SQLException  {
		try {
			String query = "SELECT \"number\" AS cecoNumber\n" + 
					"FROM cecos\n" + 
					"WHERE deleted=false\n" + 
					"ORDER BY RANDOM()\n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No ceco available.");
			return rs.getString("cecoNumber");
		}finally{
			endConnection();
		}	
	}

	/**
	 * This method will return a existing ceco number, that have related projects.
	 * @return String
	 * @throws SQLException
	 */
	public String getRandomCecoNumberWithRelatedProjects() throws SQLException  {
		try {
			String query = "SELECT c.number AS cecoNumber\n" + 
					"FROM cecos c\n" + 
					"LEFT JOIN projects p on p.ceco_fk=c.id\n" + 
					"WHERE  p.ceco_fk IS NOT NULL\n" + 
					"AND c.deleted=false\n" + 
					"ORDER BY RANDOM()\n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No ceco with related projects available.");
			return rs.getString("cecoNumber");
		}finally{
			endConnection();
		}	
	}
	
	/**
	 * This method will return a existing ceco number, that do not have related projects.
	 * @return String
	 * @throws SQLException
	 */
	public String getRandomCecoNumberWithoutRelatedProjects() throws SQLException  {
		try {
			String query = "SELECT c.number AS cecoNumber\n" + 
					"FROM cecos c\n" + 
					"LEFT JOIN projects p on p.ceco_fk=c.id\n" + 
					"WHERE  p.ceco_fk IS NULL\n" + 
					"AND c.deleted=false\n" + 
					"ORDER BY RANDOM()\n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No ceco without related projects available.");
			return rs.getString("cecoNumber");
		}finally{
			endConnection();
		}	
	}
	
	/**
	 * This method will return a random ceco number and the required all levels status.
	 * @return Map<String,String>
	 * @throws SQLException
	 */
	public Map<String,String> getRandomCecoNumber() throws SQLException  {
		Map<String,String> ceco = new HashMap<>();
		try {
			String query = "SELECT number AS cecoNumber, required_all_levels AS requiredAllLevels\n" + 
					"FROM public.cecos\n" + 
					"WHERE deleted=false\n" + 
					"ORDER BY RANDOM()\n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No report id available.");
			else {
				ceco.put("cecoNumber", rs.getString("cecoNumber"));
				ceco.put("requiredAllLevels", rs.getString("requiredAllLevels"));
			}
			return ceco;
		}finally{
			endConnection();
		}	
	}

}
