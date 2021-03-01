package database.submodules.gatekeepers.features;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.submodules.gatekeepers.GatekeepersDBHelper;
import dto.submodules.gatekeepers.GatekeepersDTO;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */

public class SearchGatekeepersDBHelper extends GatekeepersDBHelper {


	/**
	 * This method will return the Seniority list based on position id and seniority
	 * id
	 * 
	 * @param positionId
	 * @param seniorityId
	 * @return {@link String}
	 * @throws SQLException
	 */
	public String getSeniorityForSpecifiedPostion(String positionId, String seniorityId) throws SQLException {
		String values = "(";
		String query = "SELECT pr.name AS position_name, ps.name AS seniority, ps.id AS seniority_id, ps.value AS seniority_value FROM position_roles pr, position_roles_seniorities prs, position_seniorities ps "
				+ "WHERE pr.id=prs.position_role_fk AND prs.seniority_fk=ps.id AND pr.id=" + positionId
				+ " ORDER BY ps.value";
		ResultSet resultSet = getResultSet(query);
		String seniorityValueForSeniorityId = null;
		boolean flag = false;
		try {
			while (resultSet.next()) {
				String seniorityIdFromDb = resultSet.getString("seniority_id");

				if (flag) {
					String greaterSeniorityValue = resultSet.getString("seniority_value");
					if(Integer.valueOf(greaterSeniorityValue)>Integer.valueOf(seniorityValueForSeniorityId)) {
					values = values + resultSet.getString("seniority_value") + ",";
					}
				}
				if (seniorityIdFromDb.equals(seniorityId)) {
					flag = true;
					seniorityValueForSeniorityId=resultSet.getString("seniority_value");
				}
			}
			values = values.substring(0, values.length() - 1) + ")";
		} finally {
			endConnection();
		}
		return values;
	}

	/**
	 * This method will return the Gate keepers list based on positionId,
	 * seniorityId and location
	 * 
	 * @param positionId
	 * @param seniorityId
	 * @param location
	 * @param interviewDay
	 * @return {@link Map}
	 * @throws SQLException
	 */
	public Map<String, GatekeepersDTO> getGatekeepersList(String positionId, String seniorityId, String location, String... interviewDate)
			throws SQLException {
		String interviewDay = "2020-12-21 00:00:00";
		if(interviewDate.length>0) {
			interviewDay=interviewDate[0].split(" ")[0]+" 00:00:00";
		}
		
		
		String query = "WITH vars AS (SELECT * FROM (VALUES( " + seniorityId + ",/* -- applied seniority*/ "
				+ positionId + ",/* --applied position*/ '" + location + "' /* -- applied Site_name*/ )) AS t( "
				+ "seniority_id, position_id, site_name)) SELECT g.first_name, "
				+ "g.last_name, g.id AS glober_id, g.work_email AS work_email, cd.position AS position, cd.seniority AS seniority, studio.NAME "
				+ "AS studio, site.NAME AS location, st.timezone AS timezone, ps.value AS seniority_level FROM globers g, contracts_data cd, "
				+ "sites site, gatekeepers gk, position_roles pr, site_timezones st, position_roles_seniorities prs, position_seniorities ps, "
				+ "studios studio, position_roles pr5 WHERE g.work_email NOT LIKE 'old%' AND gk.glober_fk = g.id AND gk.type = 'gatekeeper' AND "
				+"'"+interviewDay+"' NOT IN (SELECT h.holiday_date As holiday FROM sites s, holidays h WHERE s.country_fk = h.country_fk AND s.NAME = (SELECT site_name FROM vars)) AND "
				+ "gk.status = 'active' AND g.contract_information_fk = cd.contract_information_fk AND cd.end_date IS NULL AND g.studio_fk = "
				+ "studio.id AND cd.site_fk = site.id AND site.id = st.site_fk AND pr.id = prs.position_role_fk AND ps.id = prs.seniority_fk AND "
				+ "Lower(cd.position) = Lower(pr.NAME) AND Lower(cd.seniority) = Lower(ps.NAME) AND ( ( gk.inactive_from IS NULL AND gk.inactive_to "
				+ "IS NULL ) OR ( gk.inactive_from < Now() AND gk.inactive_to < Now() ) OR ( gk.inactive_from > Now() AND gk.inactive_to > Now() "
				+ ") OR ( gk.inactive_from < Now() AND gk.inactive_to IS NOT NULL ) ) AND pr.id IN (SELECT pr1.id FROM position_roles pr1 WHERE "
				+ "( pr1.id IN (SELECT plevel1.position_role_fk FROM position_hierarchy_levels plevel1 WHERE plevel1.value > (SELECT value FROM "
				+ "position_roles pr3, position_hierarchy_levels plevel2 WHERE pr3.id = plevel2.position_role_fk AND pr3.NAME = pr5.NAME)) AND gk "
				+ ".id IN (SELECT gatekeeper_fk FROM gatekeepers_skills skill2 WHERE Lower(skill2.NAME) = Lower(pr5.NAME)) OR pr1.NAME = pr5.NAME "
				+ ") OR ( pr1.NAME = pr5.NAME AND pr1.id NOT IN (SELECT phl.position_role_fk FROM position_hierarchy_levels phl) )) AND site.id "
				+ "IN (SELECT s1.id FROM site_timezones st, sites s1 WHERE st.site_fk = s1.id AND ( st.site_fk IN (SELECT s1.id FROM sites s1, "
				+ "sites s2, site_timezones st WHERE s1.id = st.site_fk AND st.timezone IN (SELECT timezone FROM site_timezones WHERE Cast( "
				+ "Replace(Substring(timezone, 4), ':', '.' ) AS NUMERIC) >= ( SELECT Cast(Replace(Substring(st.timezone, 4 ), ':', '.' ) AS "
				+ "NUMERIC) - 2 FROM site_timezones st, sites s WHERE s.NAME = (SELECT site_name FROM vars) AND s.id = site_fk) AND Cast( "
				+ "Replace(Substring(timezone, 4), ':', '.') AS NUMERIC) <= ( SELECT Cast(Replace(Substring(st.timezone, 4), ':', '.' ) AS "
				+ "NUMERIC) + 2 FROM site_timezones st, sites s WHERE s.NAME = (SELECT site_name FROM vars) AND s.id = site_fk))) OR ( "
				+ "country_fk IN (SELECT country_fk FROM sites WHERE NAME = (SELECT site_name FROM vars)) ) /* -- site.name        */ AND country_fk "
				+ "NOT IN (SELECT id FROM countries WHERE NAME IN ( 'USA', 'Canada' )) )) AND ( ps.value > (SELECT pss.value FROM "
				+ "position_roles_seniorities prs, position_roles pr4, position_seniorities pss WHERE prs.position_role_fk = pr4.id AND prs.seniority_fk "
				+ "= pss.id AND pss.id = (SELECT seniority_id FROM vars) AND pr4.NAME = pr5.NAME) OR pr.NAME != pr5.NAME ) AND pr5.id = (SELECT "
				+ "position_id FROM vars) ORDER BY site.NAME, pr.NAME, ps.value;";
				
		ResultSet resultSet = getResultSet(query);
		Map<String, GatekeepersDTO> gatekeepers = new HashMap<>();
		try {
			while (resultSet.next()) {
				GatekeepersDTO gatekeepersDTO = new GatekeepersDTO();
				gatekeepersDTO
						.setGloberName(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
				gatekeepersDTO.setGloberId(resultSet.getString("glober_id"));
				gatekeepersDTO.setGloberEmail(resultSet.getString("work_email"));
				gatekeepersDTO.setGloberLocation(resultSet.getString("location"));
				gatekeepersDTO.setGloberPosition(resultSet.getString("position"));
				gatekeepersDTO.setGloberSeniority(resultSet.getString("seniority"));
				gatekeepersDTO.setGloberSeniorityLevel(resultSet.getString("seniority_level"));
				gatekeepers.put(gatekeepersDTO.getGloberId(), gatekeepersDTO);
			}
		} finally {
			endConnection();
		}
		return gatekeepers;
	}
	
	/**
	 * Get gatekeepers list
	 * @param totalGatekeepersCount
	 * @return {@link String}
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	public List<String> getGatekeepers(Object totalGatekeepersCount) throws Exception {
		String query = "SELECT * FROM gatekeepers where type = 'gatekeeper' AND status='active' ORDER BY random() LIMIT "+totalGatekeepersCount;
		try {
			ResultSet rs = getResultSet(query);
			boolean isRecordFound=false;
			List<String> gatekeepers=new ArrayList<>();
			while (rs.next()) {
				 gatekeepers.add(rs.getString("glober_fk"));
				 isRecordFound=true;
			} 
			if(!isRecordFound) {
				throw new Exception("Gateekeepers are not found from gatekeepers table");
			}
			return gatekeepers;
		} finally {
			endConnection();
		}
	}

	/**
	 * Get position for project manager ladder
	 * @return {@link List<String>}
	 * @throws Exception
	 * @author deepakkumar.hadiya
	 */
	public List<String> getPMLadderPositions() throws Exception {
		String query = "SELECT * FROM position_hierarchy_levels ORDER BY VALUE";
		try {
			ResultSet rs = getResultSet(query);
			boolean isRecordFound=false;
			List<String> positions=new ArrayList<>();
			while (rs.next()) {
				 positions.add(rs.getString("position_role_fk"));
				 isRecordFound=true;
			} 
			if(!isRecordFound) {
				throw new Exception("Positions are not found from position_hierarchy_levels table for project manager ladder");
			}
			return positions;
		} finally {
			endConnection();
		}
	}
}
