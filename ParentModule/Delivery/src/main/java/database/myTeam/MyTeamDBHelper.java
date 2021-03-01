package database.myTeam;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.DeliveryDBHelper;
import dto.myTeam.features.FilterObject;

/**
 * @author dnyaneshwar.waghmare
 *
 */

public class MyTeamDBHelper extends DeliveryDBHelper {

	public MyTeamDBHelper() {
		// TODO Auto-generated constructor stub
	}

	/***
	 * This method will return the all Client Details For Glober.
	 * 
	 * @param userId
	 * @return Map<String, List<String>>
	 */
	public Map<String, List<String>> getClientDetailsForGlober(String userId) throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();
		Map<String, List<String>> clientListForDD = new HashMap<String, List<String>>();

		String query = "SELECT id, name FROM clients WHERE id in (SELECT DISTINCT on (p.client_fk) p.client_fk FROM assignments a INNER JOIN projects p on a.project_fk = p.id "
				+ "WHERE a.resume_fk = '" + userId
				+ "' AND internal_assignment_type !='BENCH' AND a.end_date > Now());";

		ResultSet resultSet = getResultSet(query);

		while (resultSet.next()) {
			filterObject = new FilterObject();
			filterObject.setClientId(resultSet.getString("id"));
			filterObject.setClientName(resultSet.getString("name"));
			listOfFilterObject.add(filterObject);
			clientListForDD.put(resultSet.getString("id"), Arrays.asList(resultSet.getString("name")));
			listOfFilterObject.add(filterObject);

		}

		return clientListForDD;
	}

	/***
	 * This method will return the List of Studios For Glober.
	 * 
	 * @param userId
	 * @return Map<String, List<String>>
	 */
	public Map<String, List<String>> getStudiosListForGlober(String userId) throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();
		Map<String, List<String>> studioListForDD = new HashMap<String, List<String>>();

		String query = "SELECT s.id as studioId, s.name as businessUnit FROM assignments a INNER JOIN projects p on a.project_fk = p.id INNER JOIN clients c on c.id = p.client_fk INNER JOIN studios s on s.id = c.business_unit_fk INNER JOIN positions pos on pos.id = a.position_fk WHERE resume_fk = (SELECT id FROM globers WHERE id = '"
				+ userId
				+ "') AND internal_assignment_type = 'ASSIGNED_TO_PROJECT'AND end_date >now() GROUP BY studioId ORDER BY studioId;";

		ResultSet resultSet = getResultSet(query);
		while (resultSet.next()) {
			filterObject = new FilterObject();
			filterObject.setClientId(resultSet.getString("studioId"));
			filterObject.setClientName(resultSet.getString("businessUnit"));
			listOfFilterObject.add(filterObject);
			studioListForDD.put(resultSet.getString("studioId"), Arrays.asList(resultSet.getString("businessUnit")));
			listOfFilterObject.add(filterObject);

		}

		return studioListForDD;
	}

	/***
	 * This method will return the List of Project States For Glober.
	 * 
	 * @param userId
	 * @return Map<String, List<String>>
	 */
	public Map<String, List<String>> getProjectStatesListForGlober(String userId) throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();
		Map<String, List<String>> studioListForDD = new HashMap<String, List<String>>();

		String query = "SELECT project_state FROM projects GROUP BY project_state ORDER BY project_state ;";

		ResultSet resultSet = getResultSet(query);
		while (resultSet.next()) {
			filterObject = new FilterObject();
			filterObject.setClientId(resultSet.getString("project_state"));
			listOfFilterObject.add(filterObject);
			studioListForDD.put(resultSet.getString("project_state"),
					Arrays.asList(resultSet.getString("project_state")));
			listOfFilterObject.add(filterObject);
		}

		return studioListForDD;
	}

	/***
	 * This method will return Project List for Glober
	 * 
	 * @param projectId
	 * @return List<FilterObject>
	 */
	public List<FilterObject> getProjectStatesListForGlober(int projectId) throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();
		String query = "SELECT p.id as projectId, p.client_fk as clientId, p.name as projectName, c.name as clientName, NULL as endDate, sum(a.percentage) as assignmentPercentage, pd.name as podName , pd.id as podId FROM projects  p INNER JOIN assignments a on a.project_fk = p.id INNER JOIN positions pos on a.position_fk = pos.id INNER JOIN pod_positions podpos on podpos.position = pos.id INNER JOIN clients c on c.id = p.client_fk INNER JOIN pods pd on pd.id = podpos.pod WHERE p.id = '"
				+ projectId
				+ "' AND (a.end_date is NULL OR (a.end_date>'03-19-2018' OR a.end_date='03-19-2018')AND a.starting_date<'03-19-2018') AND (p.name NOT LIKE '%Talent Pool%') AND (p.name NOT LIKE '%OUT OF COMPANY%') AND (p.name NOT LIKE '%Maternity Leave%') AND (p.name NOT LIKE '%Vacaciones%') AND (p.name NOT LIKE '%Otros Ingresos%') AND (p.name NOT LIKE '%Licencia Sin Sueldo%') GROUP BY p.id , pd.id, p.name, pd.name, p.client_fk, c.name ORDER BY pd.name;";

		ResultSet resultSet = getResultSet(query);
		while (resultSet.next()) {
			filterObject = new FilterObject();
			filterObject.setProjectId(resultSet.getString("projectId"));
			filterObject.setClientId(resultSet.getString("clientId"));
			filterObject.setProjectName(resultSet.getString("projectName"));
			filterObject.setClientName(resultSet.getString("clientName"));
			filterObject.setPodName(resultSet.getString("podName"));
			filterObject.setPodId(Integer.parseInt(resultSet.getString("podId")));
			listOfFilterObject.add(filterObject);

		}

		return listOfFilterObject;
	}

	/***
	 * This methd will return Project Member Details
	 * 
	 * @param projectId
	 * @return List<FilterObject>
	 */
	public List<FilterObject> getProjectMemberDetails(int projectId) throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();

		String query = "SELECT coalesce((SELECT count(pl.id)>0 \r\n" + "	FROM pods pd \r\n"
				+ "		INNER JOIN projects proj on pd.project_fk=proj.id \r\n"
				+ "		INNER JOIN project_tech_leads pl on pd.tech_lead_fk=pl.id \r\n"
				+ "		INNER JOIN globers gl on pl.tech_lead_resume_fk=gl.id \r\n"
				+ "	WHERE (pl.end_date is NULL OR pl.end_date>now()::date) \r\n" + "		AND gl.id=g.id \r\n"
				+ "		AND proj.id='" + projectId + "'), false) as isTl, \r\n"
				+ "	case when pr.name='Project Manager' then true else false end as isPm, \r\n"
				+ "	g.id as globerId, pi.gender as gender, g.first_name as firstName, g.last_name as lastName, \r\n"
				+ "	g.username as userName, cd.seniority as seniority, \r\n"
				+ "	cd.position as position, g.work_email as email, ci.entry_date as tempEntryDate, \r\n"
				+ "	a.starting_date as tempAssignmentStartDate, a.end_date as tempAssignmentEndDate, si.id as location, sd.phone as phone, pr.name as projectPosition, \r\n"
				+ "	sum(a.percentage) as assignmentPercentage, si.city_name as city, con.name as country\r\n"
				+ "FROM assignments a \r\n" + "	INNER JOIN projects p on a.project_fk=p.id \r\n"
				+ "	INNER JOIN globers g on a.resume_fk=g.id \r\n"
				+ "	INNER JOIN personal_information pi on g.personal_information_fk=pi.id \r\n"
				+ "	INNER JOIN contracts_information ci on g.contract_information_fk=ci.id \r\n"
				+ "	INNER JOIN contracts_data cd on ci.id=cd.contract_information_fk AND (cd.start_date<now()::date AND (cd.end_date is NULL OR cd.end_date>now()::date)) \r\n"
				+ "	INNER JOIN sites si on cd.site_fk=si.id \r\n"
				+ "	LEFT OUTER JOIN sites_details sd on si.site_details_fk=sd.id \r\n"
				+ "	LEFT OUTER JOIN countries con on si.country_fk=con.id \r\n"
				+ "	LEFT OUTER JOIN positions pos on a.position_fk=pos.id \r\n"
				+ "	LEFT OUTER JOIN position_roles pr on pos.position_role_fk=pr.id \r\n" + "WHERE p.id= '" + projectId
				+ "' AND (g.username NOT LIKE 'old.%') \r\n"
				+ "	AND (a.end_date is NULL OR (a.end_date>now()::date OR a.end_date=now()::date) \r\n"
				+ "	AND a.starting_date<'03-14-2018') AND (p.name NOT LIKE '%Talent Pool%') \r\n"
				+ "	AND (p.name NOT LIKE '%OUT OF COMPANY%') \r\n" + "	AND (p.name NOT LIKE '%Maternity Leave%') \r\n"
				+ "	AND (p.name NOT LIKE '%Vacaciones%') \r\n" + "	AND (p.name NOT LIKE '%Otros Ingresos%') \r\n"
				+ "	AND (p.name NOT LIKE '%Licencia Sin Sueldo%') \r\n"
				+ "GROUP BY pr.name , g.id , g.first_name , g.last_name , cd.seniority , ci.entry_date , cd.position , a.end_date , si.id , sd.phone , si.city_name , con.name , pi.gender, a.starting_date\r\n"
				+ "\r\n" + "";

		ResultSet resultSet = getResultSet(query);
		while (resultSet.next()) {
			filterObject = new FilterObject();
			filterObject.setFirstName(resultSet.getString("firstname"));
			filterObject.setLastName(resultSet.getString("lastname"));
			filterObject.setEmail(resultSet.getString("email"));
			filterObject.setGender(resultSet.getString("gender"));
			filterObject.setGloberId(resultSet.getString("globerid"));
			filterObject.setPosition(resultSet.getString("position"));
			filterObject.setStartDate(resultSet.getString("tempassignmentstartdate"));
			filterObject.setEndDate(resultSet.getString("tempassignmentenddate"));
			filterObject.setProjectPosition(resultSet.getString("projectposition"));
			filterObject.setAssignmentPercentage(resultSet.getString("assignmentpercentage"));
			filterObject.setCity(resultSet.getString("city"));
			filterObject.setCountry(resultSet.getString("country"));
			listOfFilterObject.add(filterObject);

		}
		return listOfFilterObject;
	}

	/***
	 * This method will return Project Member Details For POD
	 * 
	 * @param projectId
	 * @return List<FilterObject>
	 */
	public List<FilterObject> getProjectMembersDetailsForPOD(int projectId) throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();
		String query = "SELECT (SELECT count(gl.id)>0 \r\n" + "		FROM pods pd \r\n"
				+ "			INNER JOIN projects prj on pd.project_fk=prj.id \r\n"
				+ "			INNER JOIN project_tech_leads pl on pd.tech_lead_fk=pl.id \r\n"
				+ "			INNER JOIN globers gl on pl.tech_lead_resume_fk=gl.id \r\n"
				+ "		WHERE (pl.end_date is NULL OR pl.end_date>now()::date) \r\n"
				+ "			AND gl.id=g.id AND (pd.name NOT LIKE 'Default') \r\n" + "			AND prj.id='"
				+ projectId + "') as isTl, \r\n" + "			1=0 as isPm, \r\n" + "	pod.id,\r\n"
				+ "	g.id as globerId, pi.gender as gender, \r\n"
				+ "	g.first_name as firstName, g.last_name as lastName, \r\n"
				+ "	g.username as userName, cd.seniority as seniority, \r\n"
				+ "	pr.name as projectPosition, g.work_email as email, \r\n"
				+ "	ci.entry_date as tempEntryDate, \r\n"
				+ "	a.starting_date as tempAssignmentStartDate, a.end_date as tempAssignmentEndDate, si.id as location, sd.phone as phone, pr.name as projectPosition, \r\n"
				+ "	sum(a.percentage) as assignmentPercentage, si.city_name as city, con.name as country\r\n"
				+ "FROM assignments a \r\n" + "	INNER JOIN positions pos on a.position_fk=pos.id \r\n"
				+ "	LEFT OUTER JOIN position_roles pr on pos.position_role_fk=pr.id \r\n"
				+ "	LEFT OUTER JOIN pod_positions podpos on pos.id=podpos.position \r\n"
				+ "	LEFT OUTER JOIN pods pod on podpos.pod=pod.id \r\n"
				+ "	INNER JOIN projects p on a.project_fk=p.id \r\n"
				+ "	INNER JOIN globers g on a.resume_fk=g.id \r\n"
				+ "	INNER JOIN personal_information pi on g.personal_information_fk=pi.id \r\n"
				+ "	INNER JOIN contracts_information ci on g.contract_information_fk=ci.id \r\n"
				+ "	INNER JOIN contracts_data cd on ci.id=cd.contract_information_fk \r\n"
				+ "		AND (cd.start_date<now()::date AND (cd.end_date is NULL OR cd.end_date>now()::date))\r\n"
				+ "	INNER JOIN sites si on cd.site_fk=si.id \r\n"
				+ "	LEFT OUTER JOIN sites_details sd on si.site_details_fk=sd.id \r\n"
				+ "	INNER JOIN countries con on si.country_fk=con.id 	\r\n" + "WHERE p.id = '" + projectId + "' \r\n"
				+ "AND (g.username NOT LIKE 'old.%') \r\n"
				+ "AND (a.end_date is NULL OR (a.end_date>now()::date OR a.end_date=now()::date) AND a.starting_date<now()::date)\r\n"
				+ "AND (p.name NOT LIKE '%Talent Pool%') \r\n" + "AND (p.name NOT LIKE '%OUT OF COMPANY%') \r\n"
				+ "AND (p.name NOT LIKE '%Maternity Leave%') \r\n" + "AND (p.name NOT LIKE '%Vacaciones%') \r\n"
				+ "AND (p.name NOT LIKE '%Otros Ingresos%') \r\n" + "AND (p.name NOT LIKE '%Licencia Sin Sueldo%') \r\n"
				+ "AND (pod.id is NOT NULL) \r\n" + "GROUP BY pr.name , g.id , g.first_name , g.last_name , \r\n"
				+ "	cd.seniority , ci.entry_date , cd.position , \r\n"
				+ "	a.end_date , si.id , sd.phone , si.city_name , con.name , pi.gender, pod.id, a.starting_date";

		ResultSet resultSet = getResultSet(query);
		while (resultSet.next()) {

			filterObject = new FilterObject();
			filterObject.setFirstName(resultSet.getString("firstname"));
			filterObject.setLastName(resultSet.getString("lastname"));
			filterObject.setEmail(resultSet.getString("email"));
			filterObject.setGender(resultSet.getString("gender"));
			filterObject.setGloberId(resultSet.getString("globerid"));
			filterObject.setPosition(resultSet.getString("projectposition"));
			filterObject.setStartDate(resultSet.getString("tempassignmentstartdate"));
			filterObject.setEndDate(resultSet.getString("tempassignmentenddate"));
			filterObject.setProjectPosition(resultSet.getString("projectposition"));
			filterObject.setAssignmentPercentage(resultSet.getString("assignmentpercentage"));
			filterObject.setCity(resultSet.getString("city"));
			filterObject.setCountry(resultSet.getString("country"));
			listOfFilterObject.add(filterObject);

		}

		return listOfFilterObject;
	}

	/***
	 * This method will return All TYpes of PODs
	 * 
	 * @return List<FilterObject>
	 */
	public List<FilterObject> getAllTypesOfPODS() throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();
		String query = "SELECT id, name FROM pod_types ;";

		ResultSet resultSet = getResultSet(query);
		while (resultSet.next()) {
			filterObject = new FilterObject();
			filterObject.setPodId(Integer.parseInt(resultSet.getString("id")));
			filterObject.setPodName(resultSet.getString("name"));
			listOfFilterObject.add(filterObject);
		}

		return listOfFilterObject;
	}

	/***
	 * This method will return All Empty PODs Under Project
	 * 
	 * @param projectId
	 * @return List<FilterObject>
	 */
	public List<FilterObject> getAllEmptyPODsUnderProject(int projectId) throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();
		String query = "SELECT name FROM pods WHERE project_fk = " + projectId
				+ " AND tech_lead_fk IS NOT NULL ORDER BY name;";

		ResultSet resultSet = getResultSet(query);
		while (resultSet.next()) {
			filterObject = new FilterObject();
			filterObject.setPodName(resultSet.getString("name"));
			listOfFilterObject.add(filterObject);
		}

		return listOfFilterObject;
	}

	/***
	 * This method will return Project List For Glober
	 * 
	 * @param projectId
	 * @param podName
	 * @return List<FilterObject>
	 */
	public List<FilterObject> getProjectStatesListForGlober(int projectId, String podName) throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();

		String query = "SELECT p.pod_id,p.name as podName,p.pod_type_fk,pt.name as podTypeName, p.maturity_fk as podMaturityId, pm.value as podMaturityValue FROM pods p INNER JOIN pod_types pt on p.pod_type_fk=pt.id INNER JOIN pod_maturities pm on pt.id=pm.id WHERE project_fk = '"
				+ projectId + "' AND p.name LIKE '" + podName + "'; ";

		ResultSet resultSet = getResultSet(query);
		while (resultSet.next()) {
			filterObject = new FilterObject();
			filterObject.setPodId(resultSet.getInt("pod_id"));
			filterObject.setPodName(resultSet.getString("podName"));
			filterObject.setPodType(resultSet.getString("pod_type_fk"));
			filterObject.setPodTypeName(resultSet.getString("podTypeName"));
			filterObject.setPodMaturityId(resultSet.getString("podMaturityId"));
			filterObject.setPodMaturityValue(resultSet.getString("podMaturityValue"));
			listOfFilterObject.add(filterObject);

		}

		return listOfFilterObject;
	}

	/***
	 * This method will return POD Details For Glober
	 * 
	 * @param podId
	 * @return List<FilterObject>
	 */
	public List<FilterObject> getPODDetailsForGlober(int podId) throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();
		String query = "SELECT id,name,pod_type_fk,maturity_fk FROM pods WHERE id in(" + podId + ")";

		ResultSet resultSet = getResultSet(query);
		while (resultSet.next()) {
			filterObject = new FilterObject();
			filterObject.setPodId(resultSet.getInt("id"));
			filterObject.setPodName(resultSet.getString("name"));
			filterObject.setPodType(resultSet.getString("pod_type_fk"));
			filterObject.setPodMaturityId(resultSet.getString("maturity_fk"));
			listOfFilterObject.add(filterObject);

		}

		return listOfFilterObject;
	}

	/***
	 * This method will return Project Member Details
	 * 
	 * @param projectId
	 * @return List<FilterObject>
	 */
	public List<FilterObject> getProjectMembersDetails(int projectId) throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();
		String query = "SELECT coalesce((SELECT count(pl.id)>0 \r\n" + "	FROM pods pd \r\n"
				+ "		INNER JOIN projects proj on pd.project_fk=proj.id \r\n"
				+ "		INNER JOIN project_tech_leads pl on pd.tech_lead_fk=pl.id \r\n"
				+ "		INNER JOIN globers gl on pl.tech_lead_resume_fk=gl.id \r\n"
				+ "	WHERE (pl.end_date is NULL OR pl.end_date>now()::date) \r\n" + "		AND gl.id=g.id \r\n"
				+ "		AND proj.id='" + projectId + "'), false) as isTl, \r\n"
				+ "	case when pr.name='Project Manager' then true else false end as isPm, \r\n"
				+ "	g.id as globerId, pi.gender as gender, g.first_name as firstName, g.last_name as lastName, \r\n"
				+ "	g.username as userName, cd.seniority as seniority, \r\n"
				+ "	cd.position as position, g.work_email as email, ci.entry_date as tempEntryDate, \r\n"
				+ "	a.starting_date as tempAssignmentStartDate, a.end_date as tempAssignmentEndDate, si.id as location, sd.phone as phone, pr.name as projectPosition, \r\n"
				+ "	sum(a.percentage) as assignmentPercentage, si.city_name as city, con.name as country\r\n"
				+ "FROM assignments a \r\n" + "	INNER JOIN projects p on a.project_fk=p.id \r\n"
				+ "	INNER JOIN globers g on a.resume_fk=g.id \r\n"
				+ "	INNER JOIN personal_information pi on g.personal_information_fk=pi.id \r\n"
				+ "	INNER JOIN contracts_information ci on g.contract_information_fk=ci.id \r\n"
				+ "	INNER JOIN contracts_data cd on ci.id=cd.contract_information_fk AND (cd.start_date<now()::date AND (cd.end_date is NULL OR cd.end_date>now()::date)) \r\n"
				+ "	INNER JOIN sites si on cd.site_fk=si.id \r\n"
				+ "	LEFT OUTER JOIN sites_details sd on si.site_details_fk=sd.id \r\n"
				+ "	LEFT OUTER JOIN countries con on si.country_fk=con.id \r\n"
				+ "	LEFT OUTER JOIN positions pos on a.position_fk=pos.id \r\n"
				+ "	LEFT OUTER JOIN position_roles pr on pos.position_role_fk=pr.id \r\n" + "WHERE p.id= '" + projectId
				+ "' AND (g.username NOT LIKE 'old.%') \r\n"
				+ "	AND (a.end_date is NULL OR (a.end_date>now()::date OR a.end_date=now()::date) \r\n"
				+ "	AND a.starting_date<'03-14-2018') AND (p.name NOT LIKE '%Talent Pool%') \r\n"
				+ "	AND (p.name NOT LIKE '%OUT OF COMPANY%') \r\n" + "	AND (p.name NOT LIKE '%Maternity Leave%') \r\n"
				+ "	AND (p.name NOT LIKE '%Vacaciones%') \r\n" + "	AND (p.name NOT LIKE '%Otros Ingresos%') \r\n"
				+ "	AND (p.name NOT LIKE '%Licencia Sin Sueldo%') \r\n"
				+ "GROUP BY pr.name , g.id , g.first_name , g.last_name , cd.seniority , ci.entry_date , cd.position , a.end_date , si.id , sd.phone , si.city_name , con.name , pi.gender, a.starting_date\r\n"
				+ "\r\n" + "";

		ResultSet resultSet = getResultSet(query);
		while (resultSet.next()) {
			filterObject = new FilterObject();
			filterObject.setFirstName(resultSet.getString("firstname"));
			filterObject.setLastName(resultSet.getString("lastname"));
			filterObject.setEmail(resultSet.getString("email"));
			filterObject.setGender(resultSet.getString("gender"));
			filterObject.setGloberId(resultSet.getString("globerid"));
			filterObject.setPosition(resultSet.getString("position"));
			filterObject.setStartDate(resultSet.getString("tempassignmentstartdate"));
			filterObject.setEndDate(resultSet.getString("tempassignmentenddate"));
			filterObject.setProjectPosition(resultSet.getString("projectposition"));
			filterObject.setAssignmentPercentage(resultSet.getString("assignmentpercentage"));
			filterObject.setCity(resultSet.getString("city"));
			filterObject.setCountry(resultSet.getString("country"));
			listOfFilterObject.add(filterObject);
		}
		return listOfFilterObject;
	}

	/***
	 * This method will return POD Date FROM DB
	 * 
	 * @param podId
	 * @return List<FilterObject>
	 */
	public List<FilterObject> getPODDateFromDB(String podId) throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();
		String query = "SELECT * FROM pods WHERE pod_id='" + podId + "';";

		ResultSet resultSet = getResultSet(query);
		while (resultSet.next()) {
			filterObject = new FilterObject();
			filterObject.setPodName(resultSet.getString("name"));
			filterObject.setPodId(resultSet.getInt("pod_id"));
			listOfFilterObject.add(filterObject);
		}
		return listOfFilterObject;
	}

	/***
	 * This method will return All PODs Under Glober
	 * 
	 * @param userName
	 * @return List<FilterObject>
	 */
	public List<FilterObject> getAllPODsUnderGlober(String userName) throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();
		String query = "SELECT DISTINCT ON (tb.\"PodId\") * \r\n"
				+ "FROM   (SELECT pods.id                                    AS \"PodId\", \r\n"
				+ "              pods.NAME                                  AS \"PodName\", \r\n"
				+ "			pods.pod_type_fk as \"podTypeId\",\r\n" + "			pods.purpose as \"podPupose\",\r\n"
				+ "              prj.id                                     AS \"ProjectId\", \r\n"
				+ "              prj.NAME                                   AS \"ProjectName\", \r\n"
				+ "              asg.percentage                             AS \r\n"
				+ "              \"AssignmentPercentage\"\r\n" + "       FROM   pods \r\n"
				+ "              INNER JOIN pod_positions pod_pos \r\n"
				+ "                      ON pod_pos.pod = pods.id \r\n" + "              INNER JOIN positions pos \r\n"
				+ "                      ON pod_pos.position = pos.id \r\n"
				+ "              INNER JOIN projects prj \r\n" + "                      ON prj.id = pos.project_fk \r\n"
				+ "              INNER JOIN assignments asg \r\n"
				+ "                      ON asg.project_fk = prj.id \r\n" + "              INNER JOIN globers glb \r\n"
				+ "                      ON glb.id = asg.resume_fk \r\n" + "       WHERE  asg.position_fk = pos.id \r\n"
				+ "              AND glb.username = '" + userName + "' \r\n"
				+ "              AND asg.assignment_status_type NOT IN ( \r\n"
				+ "                      'POSITION_CLOSED_REASIGNED', 'POSITION_CLOSED_REASIGNED', \r\n"
				+ "                      'PROJECT_ENDED', 'STRATEGIC_CLIENT_DECISION', \r\n"
				+ "  'REPLACEMENT', 'BUDGET_ISSUES' ) \r\n" + "              AND prj.ceco_fk IS NOT NULL) tb;";

		ResultSet resultSet = getResultSet(query);

		while (resultSet.next()) {
			filterObject = new FilterObject();
			filterObject.setPodName(resultSet.getString("PodName"));
			filterObject.setPodId(resultSet.getInt("PodId"));
			filterObject.setProjectId(resultSet.getString("projectId"));
			filterObject.setProjectName(resultSet.getString("projectName"));
			filterObject.setAssignmentPercentage(resultSet.getString("assignmentPercentage"));
			listOfFilterObject.add(filterObject);
		}
		return listOfFilterObject;
	}

	/***
	 * This method will return Project Data As Per Filter
	 * 
	 * @param globerName
	 * @return List<FilterObject>
	 */
	public List<FilterObject> getProjectDataAsPerFilter(String globerName) throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();
		String query = "with assigns as\r\n" + "(\r\n" + "SELECT \r\n" + "a.end_date, a.starting_date,\r\n"
				+ "p.id as projectId , p.name, p.project_state , c.id as clientId, c.name as clientName,\r\n"
				+ "s.id as businessUnitId, ps.id, ps.name,  s.name as businessUnit, \r\n"
				+ "a.internal_assignment_type, pos.name, pr.name as glober_role, g.id as projectManagerGloberId, \r\n"
				+ "row_number()over \r\n" + "(partition by (p.id)\r\n" + "ORDER BY a.end_date desc\r\n"
				+ ") as row_num\r\n" + "FROM assignments a\r\n" + "INNER JOIN projects p on a.project_fk = p.id\r\n"
				+ "INNER JOIN clients c on c.id = p.client_fk\r\n"
				+ "INNER JOIN studios s on s.id = c.business_unit_fk\r\n"
				+ "INNER JOIN studios ps on ps.id = p.studio_fk\r\n"
				+ "INNER JOIN positions pos on pos.id = a.position_fk\r\n"
				+ "INNER JOIN position_roles pr on pos.position_role_fk = pr.id\r\n"
				+ "INNER JOIN globers g on g.id = a.resume_fk\r\n"
				+ "WHERE resume_fk = (SELECT id FROM globers WHERE username = '" + globerName + "')\r\n"
				+ "AND a.internal_assignment_type = 'ASSIGNED_TO_PROJECT'\r\n" + "AND p.project_state='ON_GOING'\r\n"
				+ "AND (a.end_date is NULL OR (a.end_date>now()::date OR a.end_date=now()::date)) AND a.starting_date <= now()\r\n"
				+ "GROUP BY g.id,p.id, p.name, p.project_state, c.id,  c.name, s.id, ps.id, ps.name,  s.name, pos.name, a.internal_assignment_type, a.end_date, pr.name, a.starting_date\r\n"
				+ "ORDER BY c.name, p.name, a.end_date desc\r\n" + ") \r\n" + "SELECT * \r\n" + "FROM assigns \r\n"
				+ "WHERE row_num = 1";

		ResultSet resultSet = getResultSet(query);
		while (resultSet.next()) {
			filterObject = new FilterObject();
			filterObject.setProjectId(resultSet.getString("projectId"));
			filterObject.setProjectName(resultSet.getString("name"));
			listOfFilterObject.add(filterObject);
		}

		return listOfFilterObject;
	}

	/***
	 * This method will return Project and Client For Glober
	 * 
	 * @param userId
	 * @return List<FilterObject>
	 */
	public List<FilterObject> getProjectAndClientForGlober(String userId) throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();
		String query = "SELECT pr.id AS project_id,\r\n"
				+ " pr.NAME AS Project_Name,\r\n"
				+ "	c.name As Client_Name,\r\n"
				+ "	c.id	As Client_Id\r\n" + "FROM projects pr\r\n"
				+ " INNER JOIN clients c\r\n" + " ON pr.client_fk = c.id\r\n"
				+ " INNER JOIN studios s\r\n" + " ON c.business_unit_fk = s.id\r\n"
				+ " INNER JOIN projects_view pv\r\n" + " ON pr.id = pv.project_id\r\n"
				+ " INNER JOIN positions pos\r\n" + " ON pos.project_fk = pr.id\r\n"
				+ " INNER JOIN assignments a\r\n" + " ON a.position_fk = pos.id\r\n"
				+ "WHERE  a.resume_fk = '" + userId + "'\r\n" + " AND pr.project_state = 'ON_GOING'\r\n"
				+ " AND a.starting_date < Now()\r\n"
				+ " AND (a.end_date > Now() OR a.end_date IS NULL) \r\n" + "ORDER BY pr.NAME ASC";

		ResultSet resultSet = getResultSet(query);
		while (resultSet.next()) {
			filterObject = new FilterObject();
			filterObject.setProjectId(resultSet.getString("project_id"));
			filterObject.setProjectName(resultSet.getString("project_name"));
			filterObject.setClientId(resultSet.getString("client_id"));
			filterObject.setClientName(resultSet.getString("client_name"));
			listOfFilterObject.add(filterObject);

		}

		return listOfFilterObject;
	}

	/***
	 * This method will return the List of Project States.
	 * 
	 * @return Map<String, List<String>>
	 * @throws SQLException
	 */
	public Map<String, List<String>> getProjectStates() throws SQLException {
		Map<String, List<String>> projectStates = new HashMap<String, List<String>>();
		List<String> stateValues = new ArrayList<String>();
		String query = "Select DISTINCT(project_state) from projects";

		ResultSet resultSet = getResultSet(query);
		while (resultSet.next()) {
			stateValues.add(resultSet.getString("project_state"));
		}
		projectStates.put("project_state", stateValues);
		return projectStates;
	}

	/***
	 * This method will return POD Details For Glober
	 * 
	 * @param userId
	 * @return List<FilterObject>
	 * @throws SQLException
	 */
	public List<FilterObject> getPODsForGlober(String userId) throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();
		String query = "Select a.resume_fk as glober_id, pod.id as pod_id, pod.name as pod_name\r\n"
				+ "from assignments a inner join positions pos on a.position_fk = pos.id\r\n"
				+ "inner join pod_positions podpos on pos.id = podpos.position\r\n"
				+ "inner join pods pod on podpos.pod = pod.id\r\n"
				+ "where a.end_date > now() and a.starting_date < Now() and a.resume_fk = " + userId + "\r\n"
				+ "order by a.resume_fk";

		ResultSet resultSet = getResultSet(query);
		while (resultSet.next()) {
			filterObject = new FilterObject();
			filterObject.setPodId(resultSet.getInt("pod_id"));
			filterObject.setPodName(resultSet.getString("pod_name"));
			listOfFilterObject.add(filterObject);
		}

		return listOfFilterObject;
	}

	/***
	 * This method will return existing assignment end date
	 * 
	 * @param assignmentId
	 * @return String
	 * @throws SQLException
	 */
	public String getExistingAssignmentDate(int assignmentId) throws SQLException {
		String assignmentEndDate = "";
		String query = "Select end_date from assignments where id = " + assignmentId;

		ResultSet resultSet = getResultSet(query);
		while (resultSet.next()) {
			assignmentEndDate = resultSet.getString("end_date");
		}

		return assignmentEndDate;
	}
}
