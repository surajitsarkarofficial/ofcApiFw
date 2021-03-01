package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.SkipException;

public interface ProjectDBHelper {

	/**
	 * Get projectId
	 * 
	 * @return project id
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getProjectId() throws SQLException {
		try {
			String query = "SELECT id FROM projects WHERE project_state='ON_GOING' ORDER BY RANDOM() LIMIT 1";
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("id");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * This method will return project name from project id
	 * @param projectId
	 * @return String
	 * @author akshata.dongare
	 * @throws SQLException
	 */
	public default String getProjectNameFromProjectId(String projectId) throws SQLException {
		String query = "SELECT name as projectName FROM projects WHERE id = '"+projectId+"'";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("projectName");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * This method will return any project id except the specified project Id
	 * @param projectId
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getProjectIdExceptSpecificId(String projectId) throws SQLException {
		String query = "SELECT id as projectId FROM projects WHERE id != '"+projectId+"' ORDER by RANDOM() limit 1;";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("projectId");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}

	/**
	 * Get the projectid from a user
	 * @param user
	 * @return
	 * @throws SQLException
	 * @author christian.chacon
	 */
	public default String getProjectIDOfUser(String user) throws SQLException {
		String query = "SELECT ass.project_fk FROM assignments ass JOIN positions pos ON ass.poSition_fk = pos.id JOIN position_roles posrol ON pos.position_role_fk = posrol.id JOIN globers g ON ass.resume_fk = g.id INNER JOIN projects p ON ass.project_fk = p.id WHERE current_date BETWEEN ass.starting_date AND ass.end_date AND g.username ='" + user + "' LIMIT 1";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("project_fk");
		}finally {
			new BaseDBHelper().endConnection();
		}
	}

	/**
	 * This method will return projects for a particular ceco.
	 * @param ceco
	 * @return String
	 * @throws SQLException
	 * @author german.massello
	 */
	public default String getProjectIdByCeco(String ceco) throws SQLException {
		String query = "SELECT p.id \r\n" + 
				"FROM cecos c\r\n" + 
				"LEFT JOIN projects p ON p.ceco_fk=c.id\r\n" + 
				"WHERE c.number = '"+ceco+"'\r\n" + 
				"ORDER BY RANDOM()\r\n" + 
				"LIMIT 1";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			if(!rs.next()) throw new SkipException("No project availables for ceco : "+ceco);
			return rs.getString("id");
		}finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * This method will return a productive project.
	 * @return String
	 * @throws SQLException
	 */
	public default String getProductiveProjectId() throws SQLException {
		String query = "SELECT p.id\r\n" + 
				"FROM projects p\r\n" + 
				"WHERE ceco_fk IS NULL\r\n" + 
				"AND project_state='ON_GOING'\r\n" + 
				"ORDER BY RANDOM()\r\n" + 
				"LIMIT 1";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			if(!rs.next()) throw new SkipException("No productive project availables ");
			return rs.getString("id");
		}finally {
			new BaseDBHelper().endConnection();
		}
	}

	/**
	 * Get project id from project name
	 * @param projectName
	 * @return String
	 * @throws SQLException
	 */
	public default String getProjectIdFromProjectName(String projectName) throws SQLException {
		String query = "SELECT id\r\n" + 
				"FROM public.projects\r\n" + 
				"WHERE name = '"+projectName+"'";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			if(!rs.next()) throw new SkipException("No project id available for project name " + projectName);
			return rs.getString("id");
		}finally {
			new BaseDBHelper().endConnection();
		}
	}

}
