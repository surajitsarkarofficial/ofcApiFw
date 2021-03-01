package database.myTeam.features;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.myTeam.MyTeamDBHelper;
import dto.myTeam.features.FilterObject;

/**
 * @author dnyaneshwar.waghmare
 *
 */

public class MyTeamViewTabforDDDBHelper extends MyTeamDBHelper {

	public MyTeamViewTabforDDDBHelper() {
	}

	/***
	 * This will get the Active Clients UNder DD
	 * 
	 * @param userId
	 * @return Map<String, List<String>>
	 */
	public Map<String, List<String>> getActiveClientsUnderDD(String userId) throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();
		Map<String, List<String>> clientListForDD = new HashMap<String, List<String>>();

		String query = " SELECT c.id,c.name,c.tag,c.business_unit_fk from clients c, projects p, assignments a, positions pos\n"
				+ "       WHERE a.project_fk = p.id\n" + "       AND pos.project_fk = a.project_fk\n"
				+ "       AND c.id = p.client_fk\n" + "       AND a.resume_fk = '" + userId + "' \n"
				+ "       AND p.project_state='ON_GOING'\n"
				+ "	   AND (a.end_date is NULL or (a.end_date>now()::date or a.end_date=now()::date))\n"
				+ "	   GROUP BY c.id ";

		ResultSet resultSet = getResultSet(query);

		while (resultSet.next()) {
			filterObject = new FilterObject();
			filterObject.setClientId(resultSet.getString("id"));
			filterObject.setClientName(resultSet.getString("name"));
			filterObject.setTag(resultSet.getString("tag"));
			filterObject.setBusinessUnitId(resultSet.getString("business_unit_fk"));
			clientListForDD.put(resultSet.getString("id"), Arrays.asList(resultSet.getString("name"),
					resultSet.getString("tag"), resultSet.getString("business_unit_fk")));
			listOfFilterObject.add(filterObject);

		}

		return clientListForDD;
	}

	/***
	 * This will get the Active Projects Under DD
	 * 
	 * @param userId
	 * @return Map<String, List<String>>
	 */
	public Map<String, List<String>> getActiveProjectsUnderDD(String userId) throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();
		Map<String, List<String>> projectListForDD = new HashMap<String, List<String>>();

		String query = "SELECT pr.id                                  AS project_id,\r\n"
				+ "       pr.NAME                                AS Project_Name   \r\n" + "FROM   projects pr\r\n"
				+ "       INNER JOIN clients c\r\n" + "		 ON pr.client_fk = c.id\r\n"
				+ "       INNER JOIN studios s\r\n" + "         ON c.business_unit_fk = s.id\r\n"
				+ "       INNER JOIN projects_view pv\r\n" + "         ON pr.id = pv.project_id\r\n"
				+ "       INNER JOIN positions pos\r\n" + "         ON pos.project_fk = pr.id\r\n"
				+ "       INNER JOIN assignments a\r\n" + "         ON a.position_fk = pos.id\r\n"
				+ "WHERE  a.resume_fk = '" + userId + "'\r\n" + "       AND pr.project_state = 'ON_GOING'\r\n"
				+ "       AND a.starting_date < Now()\r\n"
				+ "       AND (a.end_date > Now() OR a.end_date IS NULL)         \r\n" + "ORDER BY pr.NAME ASC";

		ResultSet resultSet = getResultSet(query);

		while (resultSet.next()) {
			filterObject = new FilterObject();
			filterObject.setClientId(resultSet.getString("project_id"));
			filterObject.setClientName(resultSet.getString("Project_Name"));
			projectListForDD.put(resultSet.getString("project_id"), Arrays.asList(resultSet.getString("Project_Name")));
			listOfFilterObject.add(filterObject);

		}

		return projectListForDD;
	}

	/***
	 * This will get the Active Client Members UNder DD
	 * 
	 * @param userId
	 * @param clientId
	 * @return Map<String, List<String>>
	 */
	public Map<String, List<String>> getActiveClientsMembersUnderDD(String userId, int clientId) throws SQLException {
		List<FilterObject> listOfFilterObject = new ArrayList<FilterObject>();
		FilterObject filterObject = new FilterObject();
		Map<String, List<String>> clientListForDD = new HashMap<String, List<String>>();

		String query = " SELECT g.id                                                             AS globerid,\r\n"
				+ "       	g.username                                                       AS username,\r\n"
				+ "                   COALESCE ((SELECT COUNT(ptl.id) > 0\r\n"
				+ "                              FROM pods pods\r\n"
				+ "                       INNER JOIN project_tech_leads ptl\r\n"
				+ "                                           ON pods.tech_lead_fk = ptl.id\r\n"
				+ "                  WHERE (ptl.end_date IS NULL OR ptl.end_date > Now())\r\n"
				+ "                                     AND pods.project_fk = pr.id\r\n"
				+ "                                     AND ptl.tech_lead_resume_fk= g.id), false)     AS istl,\r\n"
				+ "                   CASE\r\n" + "                WHEN posrol.NAME='Project Manager' THEN true\r\n"
				+ "                ELSE false\r\n"
				+ "       END                                                              AS ispm\r\n"
				+ "FROM   assignments a\r\n" + "       INNER JOIN positions pos\r\n"
				+ "               ON a.position_fk = pos.id\r\n" + "       INNER JOIN position_roles posrol\r\n"
				+ "               ON pos.position_role_fk = posrol.id\r\n" + "       INNER JOIN projects pr\r\n"
				+ "               ON pos.project_fk = pr.id\r\n" + "       INNER JOIN clients c\r\n"
				+ "               ON pr.client_fk = c.id\r\n" + "       INNER JOIN globers g\r\n"
				+ "               ON a.resume_fk = g.id\r\n"
				+ "       LEFT OUTER JOIN (SELECT a.id     AS assign_id,\r\n"
				+ "                               pro.NAME AS role_name\r\n"
				+ "                        FROM   project_roles_mapping prm\r\n"
				+ "                               INNER JOIN projects pr\r\n"
				+ "                                       ON prm.project_fk = pr.id\r\n"
				+ "                               INNER JOIN clients c\r\n"
				+ "                                       ON pr.client_fk = c.id\r\n"
				+ "                               INNER JOIN globers g\r\n"
				+ "                                       ON prm.glober_fk = g.id\r\n"
				+ "                               INNER JOIN project_roles pro\r\n"
				+ "                                       ON prm.project_role_fk = pro.id\r\n"
				+ "                               LEFT OUTER JOIN assignments a\r\n"
				+ "                                        ON ( a.resume_fk = g.id\r\n"
				+ "                                              AND a.project_fk = pr.id )\r\n"
				+ "                        WHERE  pr.id IN (SELECT pr.id                                 \r\n"
				+ "                                         FROM   projects pr\r\n"
				+ "                                                 INNER JOIN clients c\r\n"
				+ "                                                        ON pr.client_fk = c.id\r\n"
				+ "                                                             INNER JOIN positions pos\r\n"
				+ "                                                                                ON pos.project_fk = pr.id\r\n"
				+ "                                                             INNER JOIN assignments a\r\n"
				+ "                                                                                            ON a.position_fk = pos.id\r\n"
				+ "                                          		 WHERE  a.resume_fk = '" + userId + "'\r\n"
				+ "                                                             AND c.id = '" + clientId + "'\r\n"
				+ "                                                 AND a.starting_date < Now()\r\n"
				+ "                                                 AND (a.end_date > Now() OR a.end_date IS NULL)\r\n"
				+ "                                                 AND pr.project_state = 'ON_GOING' \r\n"
				+ "                                          ORDER  BY pr.id)\r\n"
				+ "                        AND prm.role_start_date < Now()\r\n"
				+ "                        AND prm.role_end_date > Now()\r\n"
				+ "                        AND a.end_date > Now()) prorol\r\n"
				+ "                    ON a.id = prorol.assign_id\r\n"
				+ "       LEFT OUTER JOIN personal_information pi\r\n"
				+ "                    ON g.personal_information_fk = pi.id\r\n"
				+ "       LEFT OUTER JOIN contracts_information ci\r\n"
				+ "                    ON g.contract_information_fk = ci.id\r\n"
				+ "       LEFT OUTER JOIN contracts_data cd\r\n"
				+ "                    ON ci.id = cd.contract_information_fk\r\n" + "       LEFT OUTER JOIN sites s\r\n"
				+ "                    ON s.id = cd.site_fk\r\n" + "       LEFT OUTER JOIN sites_details sd\r\n"
				+ "                    ON s.site_details_fk = sd.id\r\n" + "       LEFT OUTER JOIN countries co\r\n"
				+ "                    ON co.id = s.country_fk\r\n"
				+ "WHERE  pr.id IN (SELECT pr.id                                 \r\n"
				+ "                 FROM   projects pr\r\n" + "                        INNER JOIN clients c\r\n"
				+ "                                ON pr.client_fk = c.id\r\n"
				+ "                                    INNER JOIN positions pos\r\n"
				+ "                                                        ON pos.project_fk = pr.id\r\n"
				+ "                                    INNER JOIN assignments a\r\n"
				+ "                                                                    ON a.position_fk = pos.id\r\n"
				+ "                 WHERE  a.resume_fk = '" + userId + "'\r\n" + "                        AND c.id = '"
				+ clientId + "'\r\n" + "                        AND a.starting_date < Now()\r\n"
				+ "                        AND (a.end_date > Now() OR a.end_date IS NULL)\r\n"
				+ "                                    AND pr.project_state = 'ON_GOING' \r\n"
				+ "                 ORDER  BY pr.id)\r\n" + "       AND a.starting_date < Now()\r\n"
				+ "       AND (a.end_date > Now() OR a.end_date IS NULL)\r\n" + "       AND cd.start_date < Now()\r\n"
				+ "       AND (cd.end_date > Now() OR cd.end_date IS NULL)\r\n" + "       AND ci.last_date IS NULL\r\n"
				+ "GROUP  BY a.id,g.id,pr.id,pos.id,posrol.id, pi.id, cd.id, ci.id, s.id, co.id, sd.id, prorol.role_name\r\n"
				+ "ORDER  BY a.end_date ASC";

		ResultSet resultSet = getResultSet(query);

		while (resultSet.next()) {
			filterObject = new FilterObject();
			filterObject.setClientId(resultSet.getString("globerid"));
			filterObject.setClientName(resultSet.getString("username"));
			clientListForDD.put(resultSet.getString("globerid"), Arrays.asList(resultSet.getString("username")));
			listOfFilterObject.add(filterObject);
		}

		return clientListForDD;
	}
}
