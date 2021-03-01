package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.SkipException;

public interface GloberDBHelper {

	/**
	 * Get globerId
	 * 
	 * @param username
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getGloberId(String username) throws SQLException {
		ResultSet rs;
		String globerId = null;
		try {
			String query = "SELECT id AS id FROM globers WHERE username='" + username.toLowerCase() + "'";
			rs = new BaseDBHelper().getResultSet(query);
			if (!rs.next()) {
				throw new SkipException("No Glober Id available for this test.. Skipped...");
			} else {
				globerId = rs.getString("id");
			}
		} finally {
		}
		return globerId;
	}

	/**
	 * This method is used to get glober id from global id
	 * 
	 * @param globalId
	 * @return globerId
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getGloberIdFromGlobalId(String globalId) throws SQLException {
		ResultSet resultSet = null;
		String query = "SELECT globerid AS globerId FROM glober_view WHERE globalid='" + globalId + "'";
		try {
			resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
		} finally {
			new BaseDBHelper().endConnection();
		}
		return resultSet.getString("globerId");
	}

	/**
	 * Get Global Id FROM Glober Id
	 * 
	 * @param globerId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getGlobalIdFromGloberId(String globerId) throws SQLException {
		String result = null;
		String query = " SELECT globalid AS globalId FROM glober_view WHERE globerid='" + globerId
				+ "' AND globalid IS NOT NULL ORDER BY RANDOM()";
		ResultSet rs = new BaseDBHelper().getResultSet(query);
		try {
			rs.next();
			result = rs.getString("globalid");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}

	/**
	 * Get globerName
	 * 
	 * @param globerId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getGloberFullName(String globerId) throws SQLException {
		try {
			String query = "select Concat(first_name, ' ',last_name) as globerFullName from globers where id='"
					+ globerId + "'";
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("globerFullName");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}

	/**
	 * Get list of Glober Ids
	 * 
	 * @param totalGloberIds
	 * @return {@link List}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default List<String> getGloberIds(int totalGloberIds) throws SQLException {
		try {
			String query = "SELECT g.id AS id FROM globers g LEFT JOIN contracts_information ci ON ci.id = g.contract_information_fk LEFT JOIN contracts_data cd ON cd.contract_information_fk = ci.id WHERE g.username NOT LIKE 'old%' AND (cd.end_date IS NULL) AND ((CURRENT_DATE - 0) > g.creation_date) ORDER BY random() LIMIT "
					+ totalGloberIds;
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			List<String> globerIds = new ArrayList<String>();
			while (resultSet.next()) {
				globerIds.add(resultSet.getString("id"));
			}
			return globerIds;
		} finally {
			new BaseDBHelper().endConnection();
		}
	}

	/**
	 * Get glober username.
	 *
	 * @param globerId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getGloberUsername(String globerId) throws SQLException {
		try {
			String query = "SELECT username FROM globers WHERE id=" + globerId;
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			return resultSet.getString("username");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}

	/**
	 * This method will return a random glober with a particular rol. For example
	 * the rol could be: PM, TL, Staffing, Finance, etc.
	 * 
	 * @param rol
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getRandomGloberByRol(String rol) throws SQLException {
		String glober = null;
		try {
			String query = "SELECT g.username\r\n" + "FROM available_users_authorities aua\r\n"
					+ "INNER JOIN users ON aua.user_fk = users.id\r\n"
					+ "INNER JOIN globers g ON g.id = users.resume_fk\r\n"
					+ "INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk\r\n"
					+ "INNER JOIN contracts_data cd ON cd.contract_information_fk = ci.id\r\n"
					+ "WHERE aua.authority_fk =\r\n" + "(SELECT id\r\n" + "FROM public.authorities\r\n" + "WHERE name='"
					+ rol + "')\r\n" + "AND (LOWER(SUBSTRING(g.username\r\n" + "FROM 1\r\n" + "FOR 3)) != 'old')\r\n"
					+ "AND (cd.end_date IS NULL)\r\n" + "AND ((CURRENT_DATE - 0) > g.creation_date)\r\n"
					+ "ORDER BY random()";
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			glober = rs.getString("username");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return glober;
	}

	/**
	 * Get globerId from Globers Schema
	 * 
	 * @param id
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public default String getGloberIdFromGlobersSchema(String id) throws SQLException {
		String result = null;
		String query = "SELECT id FROM globers WHERE first_name = \r\n"
				+ "(SELECT firstname FROM glober_view WHERE globerid = '" + id + "' LIMIT 1) AND\r\n"
				+ "last_name = (SELECT lastname FROM glober_view WHERE globerid = '" + id + "' LIMIT 1)";
		ResultSet resultSet = new BaseDBHelper().getResultSet(query);
		try {
			resultSet.next();
			result = resultSet.getString("id");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}

	/**
	 * Get globerId,STGs candidateName AND working email
	 * 
	 * @param role
	 * @return {@link String[]}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String[] getSoonToBeGloberDetails(String role) throws SQLException {
		String result[] = new String[3];
		String query = "SELECT first_name,last_name,work_email FROM globers WHERE work_email LIKE '%" + role + "%'";

		ResultSet rs = new BaseDBHelper().getResultSet(query);
		try {
			rs.next();
			result[0] = rs.getString("first_name");
			result[1] = rs.getString("last_name");
			result[2] = rs.getString("work_email");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}

	/**
	 * This method will return random glober id
	 * 
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getRandomGloberId() throws SQLException {
		String globerId = null;
		String query = "SELECT globerid as globerId FROM glober_view WHERE username not like 'old%' order by RANDOM() limit 1";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			globerId = rs.getString("globerId");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return globerId;
	}

	
	
	/**
	 * Get Glober id based on Catagory
	 * 
	 * @param type
	 * @return String
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public default String getGloberIdwithCatagory(String type) throws SQLException {
		try {
			String query = " SELECT globerid AS globerId FROM glober_View WHERE type='" + type
					+ "' AND assignedforsr='false' ORDER BY RANDOM() IS NOT NULL";
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("globerId");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}

	/**
	 * Get glober username by glober
	 * @param firstname
	 * @param lastname
	 * @return String
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public default String getGloberUsernameByGloberFullName(String firstname,String lastname) throws SQLException {
		try {
			String query = "SELECT username FROM glober_view WHERE firstname = '"+firstname+"' AND lastname = '"+lastname+"' LIMIT 1";
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			return resultSet.getString("username");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}

	/**
	 * Get Cats Status for In Pipe STG
	 * @param globerId
	 * @return String
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public default String getCatsStatusForSoonToBeGlober(String globerId) throws SQLException {
		try {
			String query = "SELECT catsstatus AS catsStatus FROM glober_view WHERE globerid='" + globerId + "'";
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("catsStatus");
		} finally {
			new BaseDBHelper().endConnection();
		}
	 }

	/**
	 * Get glober position role by global Id
	 * 
	 * @param globalId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getGloberPositionRoleById(String globalId) throws SQLException {
		try {
			String query = "SELECT position FROM glober_view WHERE globalid = " + globalId + " LIMIT 1";
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			return resultSet.getString("position");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * Get Global Id of a glober who is in TP
	 * @param type
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getGlobalIdOfTPGlober(String type) throws SQLException {
		String query = "SELECT globalid AS globalId FROM glober_view "
				+ "WHERE "	
				+ "((((assignmentstartdate BETWEEN CURRENT_DATE and (CURRENT_DATE+ INTEGER '30')) "
				+ "OR (assignmentstartdate < (CURRENT_DATE+ INTEGER '30') AND assignmentenddate IS NULL) "
				+ "OR (assignmentstartdate<(CURRENT_DATE+ INTEGER '30') AND assignmentenddate>(CURRENT_DATE+ INTEGER '30'))) "
				+ "AND type='"+type+"' "
				+ "AND availability BETWEEN 0 AND 100  "
				+ "AND internalassignmenttype='BENCH' "
				+ "AND lastdate IS NULL "
				+ "AND enddate IS NULL ))ORDER BY RANDOM() limit 1";
		try {
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			return resultSet.getString("globalId");
		}finally {
			new BaseDBHelper().endConnection();
		}
	}

	/**
	 * Get the User by Position
	 * @param position
	 * @return String
	 * @throws SQLException
	 * @author christian.chacon
	 */
	public default String getUserByPosition(String position) throws SQLException {
		String query = "SELECT g.username FROM available_users_authorities aua INNER JOIN authorities a ON aua.authority_fk = a.id INNER JOIN users ON aua.user_fk = users.id INNER JOIN globers g ON g.id = users.resume_fk INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk INNER JOIN contracts_data cd ON cd.contract_information_fk = ci.id WHERE a.name='Glober' AND (LOWER(SUBSTRING(g.username FROM 1 FOR 3)) != 'old') AND (cd.end_date IS NULL) AND ((CURRENT_DATE - 0 ) > g.creation_date) AND g.username in (SELECT g.username FROM assignments ass JOIN positions pos ON ass.poSition_fk = pos.id JOIN position_roles posrol ON pos.position_role_fk = posrol.id JOIN globers g ON ass.resume_fk = g.id INNER JOIN projects p ON ass.project_fk = p.id WHERE cd.position = '"+position+"' AND current_date BETWEEN ass.starting_date AND ass.end_date) ORDER BY RANDOM() LIMIT 1";
		try {
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			return resultSet.getString("username");
		}finally {
			new BaseDBHelper().endConnection();
		}

	}

	/**
	 * Get a random globerid and username
	 * @return Map<String, String>
	 * @throws SQLException
	 * @author christian.chacon
	 */
	public default Map<String, String> getRandomGloberIdAndUsername() throws SQLException {
		String query="SELECT glo.id AS globerId, glo.username FROM contracts_data cd JOIN contracts_information ci ON ci.id = cd.contract_information_fk JOIN globers glo ON glo.contract_information_fk = ci.id WHERE glo.work_email NOT like 'old.%' AND glo.username NOT like 'old.%' AND cd.end_date IS NULL GROUP BY glo.username, glo.id ORDER BY RANDOM() LIMIT 1;";
		Map<String, String> m;
		try {
			m= new HashMap<String, String>();
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			m.put("globerId", resultSet.getString("globerId"));
			m.put("username", resultSet.getString("username"));
			return m;
		}finally {
			new BaseDBHelper().endConnection();
		}
	}

	/**
	 * Get the actual family Group for globers with birthdays
	 * @throws SQLException
	 * @return Map<Integer, String>
	 * @author christian.chacon
	 */
	public default Map<Integer, String> getActualFamilyGroupBirthdaysGlobers() throws SQLException {
		String query="SELECT glo.id, cd.family_group FROM contracts_data cd JOIN contracts_information ci ON ci.id=cd.contract_information_fk JOIN globers glo ON glo.contract_information_fk=ci.id JOIN personal_information pinfo ON pinfo.id=glo.personal_information_fk WHERE glo.work_email NOT like 'old.%' AND glo.username NOT like 'old.%' AND cd.end_date IS NULL AND EXTRACT(DAY FROM birth_date)=DATE_PART('day', CURRENT_DATE) AND EXTRACT(MONTH FROM birth_date)=DATE_PART('month', CURRENT_DATE) ORDER BY glo.id DESC;";
		Map<Integer, String> allBirthdayFamilyGroupList=new HashMap<>();
		try{
			allBirthdayFamilyGroupList=new HashMap<>();
			ResultSet resultSet=new BaseDBHelper().getResultSet(query);

			while(resultSet.next()){
				allBirthdayFamilyGroupList.put(Integer.parseInt(resultSet.getString("id")), resultSet.getString("family_group"));
			}

		}finally {
			new BaseDBHelper().endConnection();
		}
		return allBirthdayFamilyGroupList;
	}

	/**
	 * Get the actual family Group for a specific glober
	 * @param globerId
	 * @return String
	 * @throws SQLException
	 * @author christian.chacon
	 */
	public default String getActualFamilyGroupOfGlober(String globerId) throws SQLException {
		String query = "SELECT cd.family_group FROM contracts_data cd JOIN contracts_information ci ON ci.id = cd.contract_information_fk JOIN globers glo ON glo.contract_information_fk = ci.id WHERE glo.id = "+globerId+" AND cd.family_group IS NOT null GROUP BY cd.position, family_group, cd.start_date, cd.end_date ORDER BY end_date DESC LIMIT 1;";
		String familygroup;
		try {
			ResultSet rs=new BaseDBHelper().getResultSet(query);
			if (!rs.next()) {
				return "";
			} else {
				familygroup = rs.getString("family_group");
			}
		} finally {
			new BaseDBHelper().endConnection();
		}
		return familygroup;
	}

	/**
	 * Get the actual family Group for a specific glober by username
	 * @param username
	 * @return String
	 * @throws SQLException
	 * @author christian.chacon
	 */
	public default String getActualFamilyGroupOfGloberbyUsername(String username) throws SQLException {
		String query = "SELECT cd.family_group FROM contracts_data cd JOIN contracts_information ci ON ci.id = cd.contract_information_fk JOIN globers glo ON glo.contract_information_fk = ci.id WHERE glo.username = '"+username+"' AND cd.family_group IS NOT null GROUP BY cd.position, family_group, cd.start_date, cd.end_date ORDER BY end_date DESC LIMIT 1;";
		String familygroup;
		try {
			ResultSet rs=new BaseDBHelper().getResultSet(query);
			if (!rs.next()) {
				return "";
			} else {
				familygroup = rs.getString("family_group");
			}
		} finally {
			new BaseDBHelper().endConnection();
		}
		return familygroup;
	}

	/**
	 * Get out of company glober id
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getOutOfCompanyGloberId() throws SQLException {
		String query = "SELECT resume_fk as globerId FROM assignments WHERE internal_assignment_type like '%OUT%' ORDER by RANDOM() limit 1";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("globerId");
		}finally {
			new BaseDBHelper().endConnection();
		}
	}

	/**
	 * Get glober username by position and only glober that has a POD
	 * @param position
	 * @return
	 * @throws SQLException
	 * @author christian.chacon
	 */
	public default String getUserByPositionWithPodAssigned(String position) throws SQLException {
		String query = "SELECT g.username FROM available_users_authorities aua INNER JOIN authorities a ON aua.authority_fk = a.id INNER JOIN users ON aua.user_fk = users.id INNER JOIN globers g ON g.id = users.resume_fk INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk INNER JOIN contracts_data cd ON cd.contract_information_fk = ci.id INNER JOIN pod_core_members mem ON mem.glober_fk = g.id INNER JOIN pods pod ON pod.id = mem.pod_fk WHERE a.name='Glober' AND (LOWER(SUBSTRING(g.username FROM 1 FOR 3)) != 'old') AND (cd.end_date IS NULL) AND ((CURRENT_DATE - 0 ) > g.creation_date) AND g.username in (SELECT g.username FROM assignments ass JOIN positions pos ON ass.poSition_fk = pos.id JOIN position_roles posrol ON pos.position_role_fk = posrol.id JOIN globers g ON ass.resume_fk = g.id INNER JOIN projects p ON ass.project_fk = p.id WHERE cd.position = '" + position + "' AND current_date BETWEEN ass.starting_date AND ass.end_date) ORDER BY RANDOM() LIMIT 1";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("username");
		}finally {
			new BaseDBHelper().endConnection();
		}
	}

	/**
	 * Get a user by rol not expired
	 * @param rol
	 * @return String
	 * @throws SQLException
	 * @author christian.chacon
	 */
	public default String getUserByRolNotExpired(String rol) throws SQLException {
		String query = "SELECT g.username FROM available_users_authorities aua INNER JOIN users ON aua.user_fk = users.id INNER JOIN globers g ON g.id = users.resume_fk INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk INNER JOIN contracts_data cd ON cd.contract_information_fk = ci.id WHERE aua.authority_fk = (SELECT id FROM public.authorities WHERE name='"+rol+"') AND (LOWER(SUBSTRING(g.username FROM 1 FOR 3)) != 'old') AND (cd.end_date IS NULL) AND ((CURRENT_DATE - 0) > g.creation_date) AND g.username NOT LIKE '%ñ%' AND (aua.expiration_date > CURRENT_DATE) ORDER BY random() LIMIT 1";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("username");
		}finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * Get glober id who has completed trainings
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getTrainingCompletedGloberId() throws SQLException {
		String query = "SELECT resume_fk as globerId FROM training_course_attendances ORDER BY RANDOM() limit 1;";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("globerId");
		}finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * Get glober id for globant academy training
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getGlobantAcademyTrainingGloberId() throws SQLException {
		String query = "SELECT glober_fk as globerId FROM glober_statuses WHERE start_date IS NOT NULL ORDER by RANDOM() limit 1;";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("globerId");
		}finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * Get glober id according to the action provided
	 * @param action
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getGloberIdAccordingToAction(String action) throws SQLException {
		String query = "SELECT resume_fk as globerId FROM performance_assessment WHERE performance_assessment_state='"+action+"' "
				+ "ORDER by RANDOM() limit 1;";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			return rs.getString("globerId");
		}finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * Get glober id from project feedback
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getProjectFeedbackGloberId() throws SQLException {
		String globerId = null;
		String query = "SELECT resume_fk as globerId FROM project_evaluation_glober WHERE creation_date IS NOT NULL ORDER by RANDOM() limit 1";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			globerId = rs.getString("globerId");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return globerId;
	}
	
	/**
	 * Get glober id from performance assessment
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getPerformanceAssessmentGloberId() throws SQLException {
		String globerId = null;
		String query = "SELECT resume_fk as globerId FROM performance_assessment ORDER by RANDOM() limit 1";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			globerId = rs.getString("globerId");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return globerId;
	}
	/**
	 * Get a glober without self endorsement
	 * @author nadia.silva
	 * @param position
	 * @return
	 * @throws SQLException
	 */
	public default String getGloberWithoutSelfEndorsement(String position) throws SQLException {
		ResultSet rs;
		String globerId = null;
		try {
			String query = "SELECT g.username FROM available_users_authorities aua INNER JOIN authorities a ON aua.authority_fk = a.id INNER JOIN users ON aua.user_fk = users.id INNER JOIN globers g ON g.id = users.resume_fk INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk INNER JOIN contracts_data cd ON cd.contract_information_fk = ci.id WHERE a.name='Glober' AND (LOWER(SUBSTRING(g.username FROM 1 FOR 3)) != 'old') AND (cd.end_date IS NULL) AND ((CURRENT_DATE - 0 ) > g.creation_date) AND g.id not in (select glober_fk from acp_area_glober_level) AND g.username in (SELECT g.username FROM assignments ass JOIN positions pos ON ass.poSition_fk = pos.id JOIN position_roles posrol ON pos.position_role_fk = posrol.id JOIN globers g ON ass.resume_fk = g.id INNER JOIN projects p ON ass.project_fk = p.id WHERE cd.position = '" + position + "' AND current_date BETWEEN ass.starting_date AND ass.end_date) ORDER BY RANDOM() LIMIT 1";
			rs = new BaseDBHelper().getResultSet(query);
			if (!rs.next()) {
				throw new SkipException("No Glober without Self Endorsement available...");
			} else {
				globerId = rs.getString("username");
			}
		} finally {
			new BaseDBHelper().endConnection();
		}
		return globerId;
	}
	/**
	 * Get a glober with self endorsement
	 * @author nadia.silva
	 * @param position
	 * @return
	 * @throws SQLException
	 */
	public default String getGloberWithSelfEndorsement(String position) throws SQLException {
		ResultSet rs;
		String globerId = null;
		try {
			String query = "SELECT g.username FROM available_users_authorities aua INNER JOIN authorities a ON aua.authority_fk = a.id INNER JOIN users ON aua.user_fk = users.id INNER JOIN globers g ON g.id = users.resume_fk INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk INNER JOIN contracts_data cd ON cd.contract_information_fk = ci.id WHERE a.name='Glober' AND (LOWER(SUBSTRING(g.username FROM 1 FOR 3)) != 'old') AND (cd.end_date IS NULL) AND ((CURRENT_DATE - 0 ) > g.creation_date) AND g.id in (select glober_fk from acp_area_glober_level) AND g.username in (SELECT g.username FROM assignments ass JOIN positions pos ON ass.poSition_fk = pos.id JOIN position_roles posrol ON pos.position_role_fk = posrol.id JOIN globers g ON ass.resume_fk = g.id INNER JOIN projects p ON ass.project_fk = p.id WHERE cd.position = '" + position + "' AND current_date BETWEEN ass.starting_date AND ass.end_date) ORDER BY RANDOM() LIMIT 1";
			rs = new BaseDBHelper().getResultSet(query);
			if (!rs.next()) {
				throw new SkipException("No Glober without Self Endorsement available for this test..");
			} else {
				globerId = rs.getString("username");
			}
		} finally {
			new BaseDBHelper().endConnection();
		}
		return globerId;
	}

	/**
	 * Get global id from position id
	 * @param positionId
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getSuggestedGlobalId(String positionId) throws SQLException {
		String globalId = null;
		String query = "SELECT global_Id as globalId FROM staffing_plan WHERE position_fk = "+positionId+" order by RANDOM() limit 1";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			globalId = rs.getString("globalId");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return globalId;
	}
	 
	/**
	 * Get glober first name from globerid
	 * @param globerId
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getGloberFirstNameFromId(String globerId) throws SQLException {
		String firstName = null;
		String query = "SELECT firstname as firstName FROM glober_view WHERE globerid ="+globerId;
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			firstName = rs.getString("firstName");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return firstName;
	}

	/**
	 * Get globerType by globerId
	 * @param globerId
	 * @return String
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getGloberTypeByGloberId(String globerId) throws SQLException {
		String firstName = null;
		String query = "SELECT type FROM glober_view WHERE globerid = "+globerId+" ORDER BY benchstartdate DESC";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			firstName = rs.getString("type");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return firstName;
	}

	/**
	 * Get user by specified rol
	 *
	 * @param rol
	 * @return HashMap<String, String>
	 * @throws SQLException
	 * @author nadia.silva
	 */
	public static HashMap<String, String> getUserByRol(String rol) throws SQLException {
		HashMap<String, String> glober = new HashMap<>();
		String query = "SELECT glo.id, glo.username from globers glo JOIN contracts_information ci ON glo.contract_information_fk = ci.id JOIN contracts_data cd ON ci.id = cd.contract_information_fk WHERE cd.position = '" + rol + "' AND cd.end_date is null AND glo.username NOT like 'old.%' order by Random() limit 1";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			glober.put("id", rs.getString("id"));
			glober.put("username", rs.getString("username"));

		} finally {
			new BaseDBHelper().endConnection();
		}
		return glober;

	}
	
	/**
	 * This method will return a random global id by rol.
	 * @param rol
	 * @return String
	 * @throws SQLException
	 * @author german.massello
	 */
	public default String getRandomGlobalIdByRol(String rol) throws SQLException {
		try {
			String query = "SELECT c.global_id AS globalId\r\n" + 
					"FROM available_users_authorities aua\r\n" + 
					"INNER JOIN users ON aua.user_fk = users.id\r\n" + 
					"INNER JOIN globers g ON g.id = users.resume_fk\r\n" + 
					"INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk\r\n" + 
					"INNER JOIN contracts_data cd ON cd.contract_information_fk = ci.id\r\n" + 
					"LEFT JOIN candidates c ON c.glober_fk=g.id\r\n" + 
					"WHERE aua.authority_fk =\r\n" + 
					"(SELECT id\r\n" + 
					"FROM public.authorities\r\n" + 
					"WHERE name='"+rol+"')\r\n" + 
					"AND (LOWER(SUBSTRING(g.username\r\n" + 
					"FROM 1\r\n" + 
					"FOR 3)) != 'old')\r\n" + 
					"AND (cd.end_date IS NULL)\r\n" + 
					"AND ((CURRENT_DATE - 0) > g.creation_date)\r\n" + 
					"ORDER BY random()\r\n" + 
					"LIMIT 1";
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			if(!rs.next()) throw new SkipException("No global id available.");
			return rs.getString("globalId");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * This method will return the global id by username.
	 * @param username
	 * @return String
	 * @throws SQLException
	 * @author german.massello
	 */
	public default String getGlobalIdByUsername(String username) throws SQLException {
		try {
			String query = "SELECT g.info_type_32 AS globalId\r\n" + 
					"FROM globers g \r\n" + 
					"WHERE g.username='"+username+"'";
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			if(!rs.next()) throw new SkipException("No global id available.");
			return rs.getString("globalId");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}

	/**
	 * This method will return the glober currency.
	 * @param username
	 * @return String
	 * @throws SQLException
	 * @author german.massello
	 */
	public default String getGloberCurrency(String username) throws SQLException {
		try {
			String query = "SELECT ci.currency\r\n" + 
					"FROM globers g\r\n" + 
					"INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk\r\n" + 
					"INNER JOIN contracts_data cd ON cd.contract_information_fk = ci.id\r\n" + 
					"WHERE (cd.end_date IS NULL)\r\n" + 
					"AND g.username='"+username+"'";
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			if(!rs.next()) throw new SkipException("No currency available for : "+username);
			if (rs.getString("currency")==null) return "USD";
			return rs.getString("currency");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}

	/**
	 * Get glober id not having mentor
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getGloberIdWithNoMentor() throws SQLException {
		String globerId = null;
		String query = "SELECT globerid FROM glober_view WHERE username not like '%old%' AND mentorfirstname "
				+ "is null ORDER by RANDOM() limit 1";
		try {
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			globerId = resultSet.getString("globerid");
			return globerId;
		}finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * Get glober id having mentor
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getGloberIdHavingMentor() throws SQLException {
		String globerId = null;
		String query = "SELECT globerid FROM glober_view WHERE username not like '%old%' AND mentorfirstname "
				+ "is not null ORDER by RANDOM() limit 1";
		try {
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			globerId = resultSet.getString("globerid");
			return globerId;
		}finally {
			new BaseDBHelper().endConnection();
		}
	}

	/**
	 * Get mentor glober id from glober id from mentor
	 * @param globerId
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getMentorIdFromGlober(String globerId) throws SQLException {
		String query = "SELECT mentor_glober_fk as mentorId FROM mentor WHERE glober_fk ="+globerId;
		try {
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			return resultSet.getString("mentorId");
		}finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * Get mentor glober id from glober id from mentor_leader
	 * @param globerId
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getMentorIdFromGloberId(String globerId) throws SQLException {
		String query = "SELECT update_by_resume_fk as mentorId FROM mentor_leader WHERE glober_fk ="+globerId;
		try {
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			return resultSet.getString("mentorId");
		}finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * Get mentor id from glober id
	 * @param globerId
	 * @return String
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getMentorIdFromGloberView(String globerId) throws SQLException {
		String query = "SELECT mentorid FROM glober_view WHERE globerid ="+globerId;
		try {
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			return resultSet.getString("mentorid");
		}finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * Get glober society.
	 * @param username
	 * @return String
	 * @throws SQLException
	 * @author german.massello
	 */
	public default String getGloberSociety(String username) throws SQLException {
		String query = "SELECT s.ceco AS society\r\n" + 
				"FROM globers g\r\n" + 
				"LEFT JOIN contracts_information ci ON ci.id = g.contract_information_fk\r\n" + 
				"LEFT JOIN contracts_data cd ON cd.contract_information_fk = ci.id\r\n" + 
				"LEFT JOIN societies s ON s.id=cd.society_fk\r\n" + 
				"WHERE \r\n" + 
				"(cd.end_date IS NULL)\r\n" + 
				"AND ((CURRENT_DATE - 0) > g.creation_date)\r\n" + 
				"AND g.username='"+username+"'";
		try {
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			return resultSet.getString("society");
		}finally {
			new BaseDBHelper().endConnection();
		}
	}
}