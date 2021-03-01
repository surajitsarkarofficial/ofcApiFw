package database.myTeam.features;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.myTeam.MyTeamDBHelper;
import dto.myTeam.features.RotateAndReplaceDTOList;

/**
 * @author imran.khan
 *
 */

public class RotateAndReplaceDBHelper extends MyTeamDBHelper {

	public RotateAndReplaceDBHelper() {
		
	}
	
	/**
	 * This method will return the globers which are having shadow users
	 * 
	 * @return
	 * @throws SQLException
	 */
	
	public List<RotateAndReplaceDTOList> getGloberHavingShadowUsers() throws SQLException {
		List<RotateAndReplaceDTOList> listOfReplaceObject = new ArrayList<RotateAndReplaceDTOList>();
		RotateAndReplaceDTOList replaceDto = new RotateAndReplaceDTOList();
		String query = "select g.id globerid,g.username username,p.id positionid,p.name positionName,pj.id  projectId\n"
				+ "from globers g, assignments a, positions p, position_roles pr,projects pj\n"
				+ "where a.resume_fk = g.id\n" + "and a.position_fk = p.id\n" + "and p.assignment_type <> 'SHADOW'\n"
				+ "and p.position_role_fk = pr.id\n" + "and p.project_fk = pj.id\n"
				+ "and pr.name = 'Project Manager'\n" + "and a.end_date > now()\n"
				+ "and g.username not like 'old.%' \n"
				+ "and p.project_fk in (select pj.id from globers g, assignments a, positions p, position_roles pr, projects pj\n"
				+ "where a.resume_fk = g.id\n" + "and a.position_fk = p.id\n" + "and p.assignment_type = 'SHADOW'\n"
				+ "and pr.name <> 'Project Manager'\n" + "and p.position_role_fk = pr.id\n"
				+ "and p.project_fk = pj.id\n" + "and a.end_date > now()\n"
				+ "and g.username not like 'old.%' group by p.id,pj.id) order by g.username limit 10";
		try {
		ResultSet resultSet = getResultSet(query);
		try {
			while (resultSet.next()) {
				replaceDto = new RotateAndReplaceDTOList();
				replaceDto.setUserName(resultSet.getString("username"));
				replaceDto.setGloberId(resultSet.getString("globerid"));
				replaceDto.setProjectId(resultSet.getString("projectId"));
				replaceDto.setPositionId(resultSet.getString("positionId"));
				listOfReplaceObject.add(replaceDto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}finally {
			endConnection();
		}
		return listOfReplaceObject;
	}

	/**
	 * This method will return the shadow globers under particular project
	 * 
	 * @param projectID
	 * @return
	 * @throws SQLException
	 */
	public List<RotateAndReplaceDTOList> getShadowUserUnderProject(String projectId) throws SQLException {
		List<RotateAndReplaceDTOList> listOfFilterObject = new ArrayList<RotateAndReplaceDTOList>();
		RotateAndReplaceDTOList listOfReplaceObject;
		String query = "select g.username, g.id, p.id positionId from globers g, assignments a, positions p, position_roles pr, projects pj, clients c\n"
				+ "where a.resume_fk = g.id\n" + "and a.position_fk = p.id\n" + "and p.assignment_type = 'SHADOW'\n"
				+ "and pr.name <> 'Project Manager'\n" + "and p.position_role_fk = pr.id\n"
				+ "and p.project_fk = pj.id\n" + "and a.end_date > now()\n" + "and c.id = pj.client_fk\n"
				+ "and c.id = (select pj.client_fk where pj.id = '" + projectId + "')\n"
				+ "and g.username not like 'old.%' group by g.username,p.id,g.id";
		try {
		ResultSet resultSet = getResultSet(query);
		try {
			while (resultSet.next()) {
				listOfReplaceObject = new RotateAndReplaceDTOList();
				listOfReplaceObject.setGloberId(resultSet.getString("id"));
				listOfReplaceObject.setUserName(resultSet.getString("username"));
				listOfReplaceObject.setPositionId(resultSet.getString("positionId"));
				listOfFilterObject.add(listOfReplaceObject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}finally {
			endConnection();
		}
		return listOfFilterObject;
	}

	/**
	 * This method will return all the replacements reasons to replace a glober
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> getAllReplacementReasons() throws SQLException {
		List<RotateAndReplaceDTOList> listOfFilterObject = new ArrayList<RotateAndReplaceDTOList>();
		RotateAndReplaceDTOList filterObject = new RotateAndReplaceDTOList();
		Map<String, Object> replacementReasonsMap = new HashMap<String, Object>();
		String query = " select * from replacement_reasons where is_active='true' order by id;";
		try {
		ResultSet resultSet = getResultSet(query);
		try {
			while (resultSet.next()) {
				filterObject = new RotateAndReplaceDTOList();
				filterObject.setReplacementId(resultSet.getString("id"));
				filterObject.setReplacementName(resultSet.getString("name"));
				replacementReasonsMap.put(resultSet.getString("id"), resultSet.getString("name"));
				listOfFilterObject.add(filterObject);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}finally {
			endConnection();
		}
		return replacementReasonsMap;
	}
}
