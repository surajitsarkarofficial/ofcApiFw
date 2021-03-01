package database.submodules.globers.features;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.submodules.globers.GlobersDBHelper;
import dto.submodules.globers.AgeingFilterObjects;
import dto.submodules.globers.CommentsSoonToBeGlobersObjects;
import dto.submodules.globers.GloberAvailabilityObjects;
import dto.submodules.globers.HandlersObjects;
import dto.submodules.globers.LeadersObjects;
import dto.submodules.globers.LocationsObjects;
import dto.submodules.globers.PositionsObjects;
import dto.submodules.globers.SeniorityFilterSoonToBeGloberObjects;
import dto.submodules.globers.SoonToBeGloberDetailsObjects;
import dto.submodules.globers.StatusGridSoonToBeGloberObjects;
import dto.submodules.globers.Statuses;
import dto.submodules.globers.StudiosObjects;
import dto.submodules.globers.soonTobeGlobers;
import dto.submodules.staffRequest.features.PositionNeedDTOList;
import io.restassured.response.Response;
import utils.Utilities;

public class SoonTobeGloberDBHelper extends GlobersDBHelper {
	/**
	 * Get glober type of glober
	 * 
	 * @return {@link List}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public List<soonTobeGlobers> getSoonToBeGloberType() throws SQLException {
		try {
			List<soonTobeGlobers> listOfSoonToBeGloberTypeObject = new ArrayList<soonTobeGlobers>();
			String query = " SELECT DISTINCT type AS name FROM glober_view " + " WHERE " + " (( "
					+ " ((assignmentstartdate BETWEEN CURRENT_DATE and (CURRENT_DATE+ INTEGER '30')) "
					+ " OR (assignmentstartdate < (CURRENT_DATE+ INTEGER '30') AND assignmentenddate IS NULL) "
					+ " OR (assignmentstartdate<(CURRENT_DATE+ INTEGER '30') AND assignmentenddate>(CURRENT_DATE+ INTEGER '30'))) "
					+ " AND type='Glober' " + " AND availability BETWEEN 0 AND 100  "
					+ " AND internalassignmenttype='BENCH' " + " AND lastdate IS NULL " + " AND enddate IS NULL "
					+ " ) " + " OR (type<>'Glober' AND assignedforsr='false') " + " )";
			ResultSet resultSet = getResultSet(query);
			while (resultSet.next()) {
				soonTobeGlobers soonToBeGloberTypeObject = new soonTobeGlobers(resultSet.getString("name"));
				listOfSoonToBeGloberTypeObject.add(soonToBeGloberTypeObject);
			}
			return listOfSoonToBeGloberTypeObject;
		} finally {
			endConnection();
		}
	}

	/**
	 * Get soon to be glober for applied positions filter
	 * 
	 * @param positionName
	 * @return {@link List}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public List<PositionsObjects> getSoonToBeGlobersForPositionFilter(String positionName) throws SQLException {
		try {
			String query = "   SELECT DISTINCT candidateid AS candidateId FROM glober_view " + " WHERE "
					+ " ((type<>'Glober' AND assignedforsr='false')) " + "  AND LOWER(position)='" + positionName + "'";
			ResultSet resultset = getResultSet(query);
			List<PositionsObjects> listOfPositionFilters = new ArrayList<PositionsObjects>();

			while (resultset.next()) {
				PositionsObjects positionObjects = new PositionsObjects(resultset.getString("candidateId"));
				listOfPositionFilters.add(positionObjects);
			}
			return listOfPositionFilters;
		} finally {
			endConnection();
		}
	}

	/**
	 * Get soon to be glober for applied location filter
	 * 
	 * @param locationId
	 * @return {@link List}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public List<LocationsObjects> getSoonToBeGlobersForLocationFilter(String locationId) throws SQLException {
		try {
			String query = "   SELECT DISTINCT candidateid AS candidateId FROM glober_view " + " WHERE "
					+ " ((type<>'Glober' AND assignedforsr='false')) " + "  AND siteid='" + locationId + "'";
			ResultSet resultset = getResultSet(query);
			List<LocationsObjects> listOfGlobersLocationFilters = new ArrayList<LocationsObjects>();

			while (resultset.next()) {
				LocationsObjects globersForLocationFilterObjects = new LocationsObjects(
						resultset.getString("candidateId"));
				listOfGlobersLocationFilters.add(globersForLocationFilterObjects);
			}
			return listOfGlobersLocationFilters;
		} finally {
			endConnection();
		}
	}

	/**
	 * Get soon to be glober for applied seniority filter
	 * 
	 * @param seniorityName
	 * @return {@link List}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public List<SeniorityFilterSoonToBeGloberObjects> getSoonToBeGlobersForSeniorityFilter(String seniorityName)
			throws SQLException {
		try {
			String query = "  SELECT DISTINCT candidateid AS candidateId FROM glober_view " + " WHERE "
					+ " ((type<>'Glober' AND assignedforsr='false')) " + "  AND lower(seniority)='" + seniorityName
					+ "'";
			ResultSet resultset = getResultSet(query);
			List<SeniorityFilterSoonToBeGloberObjects> listOfGlobersSeniorityFilters = new ArrayList<SeniorityFilterSoonToBeGloberObjects>();

			while (resultset.next()) {
				SeniorityFilterSoonToBeGloberObjects globersForSeniorityFilterObjects = new SeniorityFilterSoonToBeGloberObjects(
						resultset.getString("candidateId"));
				listOfGlobersSeniorityFilters.add(globersForSeniorityFilterObjects);
			}
			return listOfGlobersSeniorityFilters;
		} finally {
			endConnection();
		}
	}

	/**
	 * Get soon to be glober for applied handler filter
	 * 
	 * @param handlerId
	 * @return {@link List}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public List<HandlersObjects> getSoonToBeGlobersForHandlerFilter(String handlerId) throws SQLException {
		try {
			List<HandlersObjects> listOfGlobersHandlersFilters = new ArrayList<HandlersObjects>();
			if (!handlerId.equals("0")) {
				String query = "  SELECT DISTINCT candidateid AS candidateId FROM glober_view " + " WHERE "
						+ " ((type<>'Glober' AND assignedforsr='false')) " + "  AND handlerid='" + handlerId + "'";
				ResultSet resultset = getResultSet(query);

				while (resultset.next()) {
					HandlersObjects globersHandlersFilterObjects = new HandlersObjects(
							resultset.getString("candidateId"));
					listOfGlobersHandlersFilters.add(globersHandlersFilterObjects);
				}
			} else {
				String query = "  SELECT DISTINCT candidateid AS candidateId FROM glober_view " + " WHERE "
						+ " ((type<>'Glober' AND assignedforsr='false')) " + "  AND handlerid IS NULL ";
				ResultSet resultset = getResultSet(query);
				while (resultset.next()) {
					HandlersObjects globersForHandlersFilterObjects = new HandlersObjects(
							resultset.getString("candidateId"));
					listOfGlobersHandlersFilters.add(globersForHandlersFilterObjects);
				}
			}
			return listOfGlobersHandlersFilters;
		} finally {
			endConnection();
		}
	}

	/**
	 * Get leaders
	 * 
	 * @return {@link List}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public List<LeadersObjects> getAvailableLeaderForSoonToBeGlobers() throws SQLException {
		try {
			String query = "  SELECT DISTINCT leaderid AS id, CONCAT(leaderfirstname,' ',leaderlastname) AS name FROM glober_view "
					+ " WHERE " + " ((type<>'Glober' AND assignedforsr='false')) AND leaderid IS NOT NULL ";
			ResultSet resultset = getResultSet(query);
			List<LeadersObjects> listOfLeaders = new ArrayList<LeadersObjects>();

			while (resultset.next()) {
				LeadersObjects leadersObjects = new LeadersObjects(resultset.getString("id"),
						resultset.getString("name"));
				listOfLeaders.add(leadersObjects);
			}
			return listOfLeaders;
		} finally {
			endConnection();
		}
	}

	/**
	 * Get soon to be globers when availability filter is applied
	 * 
	 * @return {@link List}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public List<GloberAvailabilityObjects> getSoonToBeGlobersForAvailabilityFilter() throws SQLException {
		try {
			String query = "   SELECT DISTINCT candidateid AS candidateId FROM glober_view " + "WHERE "
					+ " ((type<>'Glober' AND assignedforsr='false')) " + " AND availability BETWEEN 0 AND 100";
			ResultSet resultset = getResultSet(query);
			List<GloberAvailabilityObjects> listOfGlobersAvailabilityFilters = new ArrayList<GloberAvailabilityObjects>();

			while (resultset.next()) {
				GloberAvailabilityObjects globersForAvailabilityFilterObjects = new GloberAvailabilityObjects(
						resultset.getString("candidateId"));
				listOfGlobersAvailabilityFilters.add(globersForAvailabilityFilterObjects);
			}
			return listOfGlobersAvailabilityFilters;
		} finally {
			endConnection();
		}
	}

	/**
	 * Get details of globers for ageing filter
	 * 
	 * @return {@link List}
	 * @throws ParseException
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public List<AgeingFilterObjects> getFilterAgeing() throws ParseException, SQLException {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date startDate = new Date();
			AgeingFilterObjects ageingFilterObjects;
			List<AgeingFilterObjects> listOfAgeingFilterObjects = new ArrayList<AgeingFilterObjects>();
			String queryGetAgeing = " SELECT DISTINCT candidateid AS candidateId ,benchstartdate AS benchStartDate FROM glober_view "
					+ " WHERE " + " ((type<>'Glober' AND assignedforsr='false')) ";
			ResultSet resultSet = getResultSet(queryGetAgeing);
			while (resultSet.next()) {
				ageingFilterObjects = new AgeingFilterObjects(resultSet.getString("candidateId"));
				String localDate = Utilities.getTodayDate("dd-MM-yyyy");
				Date todayDate = formatter.parse(localDate);
				if (resultSet.getString("benchStartDate") != null) {
					startDate = formatter.parse(resultSet.getString("benchStartDate"));
					if (!resultSet.getString("benchStartDate").equalsIgnoreCase(Utilities.getTodayDate("yyyy-MM-dd"))) {
						long ageing = (Long) ((todayDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
						if (ageing >= 0 && ageing <= 100) {
							ageingFilterObjects.setAgeing(ageing);
							listOfAgeingFilterObjects.add(ageingFilterObjects);
						}
					}
				}
			}
			return listOfAgeingFilterObjects;
		} finally {
			endConnection();
		}
	}

	/**
	 * Get soon to be globers list when glober studio filter is applied
	 * 
	 * @param studioId
	 * @return {@link List}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public List<StudiosObjects> getSoonToBeGlobersForGloberStudioFilter(String studioId) throws SQLException {
		try {
			String query = "  SELECT DISTINCT candidateid AS candidateId FROM glober_view " + " WHERE "
					+ " ((type<>'Glober' AND assignedforsr='false')) " + "  AND studioid='" + studioId + "'";
			ResultSet resultset = getResultSet(query);
			List<StudiosObjects> listOfGlobersGloberStudioFilters = new ArrayList<StudiosObjects>();

			while (resultset.next()) {
				StudiosObjects globersForGloberStudioFilterObjects = new StudiosObjects(
						resultset.getString("candidateId"));
				listOfGlobersGloberStudioFilters.add(globersForGloberStudioFilterObjects);
			}
			return listOfGlobersGloberStudioFilters;
		} finally {
			endConnection();
		}
	}

	/**
	 * Get status Name
	 * 
	 * @param statusId
	 * @return String
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String getStatusName(String statusId) throws SQLException {
		try {
			String result = null;
			if (statusId.equals("0")) {
				result = "Assigned";
			} else if (statusId.equals("-1")) {
				result = "Assignment Requested";
			} else if (statusId.equals("-3")) {
				result = "Available";
			} else if (statusId.equals("4")) {
				result = "TBD";
			} else if (statusId.equals("5")) {
				result = "Academy";
			} else if (statusId.equals("6")) {
				result = "Exit";
			}
			return result;
		} finally {
			endConnection();
		}
	}

	/**
	 * Get List Of Status after applying status filters
	 * 
	 * @param planTypeStatus
	 * @return {@link List}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public List<StatusGridSoonToBeGloberObjects> getGloberStatusGridDataSoonToBeGlobers(String planTypeStatus)
			throws SQLException {
		Statuses status = new Statuses();
		ArrayList<Statuses> listOfStatuses = new ArrayList<Statuses>();
		StatusGridSoonToBeGloberObjects globerStatusGridObjects;
		List<StatusGridSoonToBeGloberObjects> listOfGloberManualStatus = new ArrayList<StatusGridSoonToBeGloberObjects>();
		List<StatusGridSoonToBeGloberObjects> listOfGloberStatusGridObjects = new ArrayList<StatusGridSoonToBeGloberObjects>();
		try {
			String queryGetStatus = "SELECT DISTINCT g.candidateid ,spt.id AS statusTypeId,spt.plan_type AS statusType FROM staffing_plantype spt JOIN glober_statuses gs ON spt.id=gs.staffing_plantype_fk  "
					+ " JOIN glober_view g ON gs.glober_fk=g.globerid " + " WHERE "
					+ " (gs.end_date>=CURRENT_DATE or spt.plan_type='TBD') AND "
					+ " ((g.type<>'Glober' AND g.assignedforsr='false')) " + " AND spt.plan_type='" + planTypeStatus
					+ "' GROUP BY spt.plan_type,spt.id,g.globerid,g.candidateid";
			ResultSet resultSet = getResultSet(queryGetStatus);
			while (resultSet.next()) {
				globerStatusGridObjects = new StatusGridSoonToBeGloberObjects(resultSet.getString("candidateId"));
				listOfGloberManualStatus.add(globerStatusGridObjects);
			}
			String queryGetAgeing = " SELECT DISTINCT candidateid AS candidateId FROM glober_view  WHERE  ((type<>'Glober' AND assignedforsr='false')) AND status='"
					+ planTypeStatus + "'";
			ResultSet resultSetAgeing = getResultSet(queryGetAgeing);
			while (resultSetAgeing.next()) {
				globerStatusGridObjects = new StatusGridSoonToBeGloberObjects(resultSetAgeing.getString("candidateId"));
				listOfGloberStatusGridObjects.add(globerStatusGridObjects);
			}
			if (planTypeStatus.equalsIgnoreCase("Assignment Requested")) {
				String queryGetBookedGlober = "SELECT DISTINCT candidateid AS candidateId,status AS status FROM glober_view "
						+ "  WHERE " + " ((type<>'Glober' AND assignedforsr='false')) "
						+ " AND status='Assignment Requested'";
				ResultSet resultSetBookedGlober = getResultSet(queryGetBookedGlober);
				while (resultSetBookedGlober.next()) {
					String planType = resultSetBookedGlober.getString("status");
					if (planType == "Assignment Requested") {
						status.setStatusTypeId("-1");
						status.setStatusType("Assignment Requested");
						listOfStatuses.add(status);
						globerStatusGridObjects = new StatusGridSoonToBeGloberObjects(
								resultSet.getString("candidateId"), listOfStatuses);
						listOfGloberStatusGridObjects.add(globerStatusGridObjects);
					}
				}
			}
			listOfGloberStatusGridObjects.addAll(listOfGloberManualStatus);
			return listOfGloberStatusGridObjects;
		} finally {
			endConnection();
		}
	}

	/**
	 * Get Cats Location for STG
	 * 
	 * @param globerId
	 * @return {@link String}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String getCatsLocationForSoonToBeGlober(String globerId) throws SQLException {
		try {
			String query = "SELECT (CASE WHEN catslocationname is NULL THEN 'NA' ELSE sitename END) AS location FROM glober_view WHERE globerid='"
					+ globerId + "'";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("location");
		} finally {
			endConnection();
		}
	}

	/**
	 * Get Soon To Be Glober Basic Information
	 * 
	 * @param globerId
	 * @return {@link List}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public List<SoonToBeGloberDetailsObjects> getSoonToBeGloberBasicInformation(String globerId) throws SQLException {
		try {
			String query = "  SELECT DISTINCT (CASE WHEN gv.catsactivity IS NULL THEN 'null' ELSE gv.catsactivity END) AS catsActivity,sv.meaning AS englishLevel,(CASE WHEN gv.catslocationname IS NULL THEN 'N/A' ELSE gv.sitename END) AS location, gv.catsstatus AS catsStatus,"
					+ " (CASE WHEN cp.start_date IS NULL THEN 'null' ELSE to_char(cp.start_date,'dd-MM-yyyy') END) AS entryDate, "
					+ " CONCAT(recruiter.firstname,' ',recruiter.lastname) AS completeNameRecruiter, "
					+ " CONCAT(gatekeeper.firstname,' ',gatekeeper.lastname) AS completeNameGatekeeper "
					+ " FROM technical_interviews tv "
					+ " JOIN glober_view recruiter ON tv.recruiter_id=recruiter.globerid  "
					+ " JOIN glober_view gatekeeper ON tv.interviewer_id=gatekeeper.globerid "
					+ " JOIN candidates_profile cp ON cp.candidate_id=tv.candidate_id "
					+ " JOIN glober_view gv ON gv.globerid=cp.id "
					+ " JOIN scale_values sv ON sv.id=cp.english_level_fk " + " WHERE cp.id='" + globerId
					+ "' AND tv.results_fk IS NOT NULL";
			ResultSet resultset = getResultSet(query);
			List<SoonToBeGloberDetailsObjects> listOfSoonToBeGloberBasicInfoObject = new ArrayList<SoonToBeGloberDetailsObjects>();
			while (resultset.next()) {
				SoonToBeGloberDetailsObjects soonToBeGloberBasicInfoObject = new SoonToBeGloberDetailsObjects(
						resultset.getString("englishLevel"), resultset.getString("catsStatus"),
						resultset.getString("entryDate"), resultset.getString("completeNameRecruiter"),
						resultset.getString("completeNameGatekeeper"), resultset.getString("location"),
						resultset.getString("catsActivity"));
				listOfSoonToBeGloberBasicInfoObject.add(soonToBeGloberBasicInfoObject);
			}
			return listOfSoonToBeGloberBasicInfoObject;
		} finally {
			endConnection();
		}
	}

	/**
	 * Get Technical interview skill
	 * 
	 * @param candidateId
	 * @return ArrayList
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public ArrayList<String> getTechnicalInterviewSkills(String candidateId) throws SQLException {
		try {
			String sId = null;
			ArrayList<String> arrlst = new ArrayList<String>();
			String query = "Select results_fk from technical_interviews where candidate_id ='" + candidateId
					+ "' order by last_modified desc LIMIT 1";
			ResultSet rs = getResultSet(query);
			while (rs.next()) {
				// rs.next();
				sId = rs.getString("results_fk");
			}
			String query1 = "Select * from technical_interviews_results  where id ='" + sId
					+ "' ORDER BY RANDOM() LIMIT 1";
			ResultSet rs1 = getResultSet(query1);
			while (rs1.next()) {
				arrlst.add(rs1.getString("technical_english_level"));
				arrlst.add(rs1.getString("years_of_experience"));
				arrlst.add(rs1.getString("evaluated_seniority"));
			}
			return arrlst;
		} finally {
			endConnection();
		}
	}

	/**
	 * This method will get the skills for a position
	 * 
	 * @param Response
	 * @return List<PositionNeedDTOList>
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public List<PositionNeedDTOList> getPositionDTOList(Response response) throws SQLException {
		try {
			PositionNeedDTOList positionDtoList;
			List<PositionNeedDTOList> listOfSkills = new ArrayList<PositionNeedDTOList>();

			for (int i = 0; i < 8; i++) {
				int alberthaId = response.jsonPath().get("data[" + i + "].skill.id");
				String competencyId = null;
				String competencyName = response.jsonPath().get("data[" + i + "].skill.name");
				String importance = "2";
				String importanceName = "Required";
				positionDtoList = new PositionNeedDTOList(alberthaId, competencyId, competencyName, importance,
						importanceName);
				listOfSkills.add(positionDtoList);
			}
			return listOfSkills;
		} finally {
			endConnection();
		}
	}

	/**
	 * Get position seniority
	 * 
	 * @param String
	 * @return {@link String}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String getPositionSeniority() throws SQLException {
		String seniority = "SSr";
		return seniority;
	}

	/**
	 * Get assignment start date
	 * 
	 * @param positionID
	 * @return String
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String assignmntStrtDt(String positionID) throws SQLException {
		try {
			String query = "SELECT start_date::Date FROM positions WHERE id = '" + positionID + "'";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("start_date");
		} finally {
			endConnection();
		}
	}

	/**
	 * Get assignment end date
	 * 
	 * @param positionID
	 * @return String
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String assignmentEndDt(String positionID) throws SQLException {
		try {
			String query = "SELECT assignment_end_date::Date FROM positions WHERE id = '" + positionID + "'";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("assignment_end_date");
		} finally {
			endConnection();
		}
	}

	/**
	 * Get soon to be glober name
	 * 
	 * @param globerID
	 * @return String
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String getStgName(String globerId) throws SQLException {
		try {
			String query = " SELECT firstname FROM glober_view WHERE globerid='" + globerId + "'";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("firstname");
		} finally {
			endConnection();
		}
	}

	/**
	 * Get getSoonToBeGloberFeedbackComments
	 * 
	 * @param globerId
	 * @return {@link List}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public List<CommentsSoonToBeGlobersObjects> getSoonToBeGloberFeedbackComments(String globalId) throws SQLException {
		List<CommentsSoonToBeGlobersObjects> listOfFeedbackObjects = new ArrayList<CommentsSoonToBeGlobersObjects>();
		String query = "SELECT comment FROM feedbacks WHERE global_id='" + globalId + "' ORDER BY id DESC";
		ResultSet resultSet = getResultSet(query);
		try {
			while (resultSet.next()) {
				CommentsSoonToBeGlobersObjects feedbackObject = new CommentsSoonToBeGlobersObjects(
						resultSet.getString("comment"));
				listOfFeedbackObjects.add(feedbackObject);
			}
			return listOfFeedbackObjects;
		} finally {
			endConnection();
		}
	}
	
	/**
	 * Get firstname for Candidate STG
	 * 
	 * @param type
	 * @return {@link String}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String getFirstNameForStgwithType(String type) throws SQLException {
		try {
			String query = "SELECT firstname AS globername FROM glober_view WHERE type='"+type+"' AND assignedforsr='false' ORDER BY RANDOM() LIMIT 1";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("globername");
		} finally {
			endConnection();
		}
	}
	
	/**
	 * Get role for new position
	 * 
	 * @return {@link String}
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String getRoleForPosition() throws SQLException {
		String role = "Java%20Developer";
		return role;
	}
	
	/**
	 * Get STG details
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String> getStgDetails() throws SQLException {
		try {
			ArrayList<String> alist = new ArrayList<String>();
			String query = "SELECT firstname FROM glober_view WHERE type!='Glober'";
			ResultSet rs = getResultSet(query);
			while (rs.next()) {
				String str = rs.getString("firstname");
				alist.add(str);
			}
			return alist;
		} finally {
			endConnection();
		}
	}
	
	/**
	 * Get soon to be glober name without process status
	 * @return ArrayList
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public ArrayList<String> getStgNameWithoutProcessStatus() throws SQLException {
		try {
			String sCode = getCATSCode();
			ArrayList<String> alist = new ArrayList<String>();
			String query = "SELECT candidate_name FROM candidates_profile WHERE cats_status_fk=" + sCode;
			ResultSet rs = getResultSet(query);
			while (rs.next()) {
				String str = rs.getString("candidate_name");
				alist.add(str);
			}
			return alist;
		} finally {
			endConnection();
		}
	}
	
	/**
	 * Get catagory status
	 * @return String
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String getCATSCode() throws SQLException {
		try {
			String query = "SELECT id FROM cats_statuses WHERE status ='Out Of Process'";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("id");
		} finally {
			endConnection();
		}
	}
	
	/**
	 * Get seniority of soon to be glober
	 * @param candidateFullName
	 * @return String
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String getSeniorityofStg(String candidateFullName) throws SQLException {
		try {
			String query = "SELECT seniority FROM glober_view WHERE candidatefullname='" + candidateFullName
					+ "' AND assignedforsr='false' ORDER BY RANDOM() LIMIT 1";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("seniority");
		} finally {
			endConnection();
		}
	}
	
	/**
	 * Get position of soon to be glober
	 * @param globerId
	 * @return String
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String getPositionofStg(String globerId) throws SQLException {
		try {
			String query = "SELECT position FROM glober_view WHERE globerId='" + globerId
					+ "' AND assignedforsr='false' ORDER BY RANDOM() IS NOT NULL";
			ResultSet rs = getResultSet(query);
			rs.next();
			return rs.getString("position");
		} finally {
			endConnection();
		}
	}
	
	/**
	 * Get handler name of soon to be glober
	 * @param globerId
	 * @return String
	 * @throws SQLException
	 * @author pornima.chakurkar
	 */
	public String getHandlernameofStg(String globerId) throws SQLException {
		try {
			String query1 = "SELECT handlerfirstname FROM glober_view WHERE globerId='" + globerId
					+ "' AND assignedforsr='false' ORDER BY RANDOM() LIMIT 1";
			String query2 = "SELECT handlerlastname FROM glober_view WHERE globerId='" + globerId
					+ "' AND assignedforsr='false' ORDER BY RANDOM() LIMIT 1";
			ResultSet rs1 = getResultSet(query1);
			ResultSet rs2 = getResultSet(query2);
			rs1.next();
			rs2.next();
			String sHandlrFstNme = rs1.getString("handlerfirstname");
			String sHandlrLstNme = rs2.getString("handlerlastname");
			String sHandlerFullName = sHandlrFstNme + " " + sHandlrLstNme;
			return sHandlerFullName;
		} finally {
			endConnection();
		}
	}
	
	/**
	 * Get technical skills for candidate 
	 * 
	 * @param candidateId
	 * @return {@link String}
	 * @throws SQLException
	 */
	public String getTechnicalSkillsForCandidate(String candidateId) throws SQLException {
		String query = "SELECT tis.tech_skills AS techSkills FROM technical_interviews ti JOIN technical_interviews_results tis ON tis.id=ti.results_fk WHERE ti.candidate_id='"+candidateId+"'";
		ResultSet rs = getResultSet(query);
		rs.next();
		return rs.getString("techSkills");
	}
	
	/**
	 * Get name of STG with new hire status
	 * @return ArrayList
	 * @throws SQLException
	 */
	public ArrayList<String> getNameOfSTGWithNewHireStatus() throws SQLException {
		try {
			ArrayList<String> lst = new ArrayList<String>();
			String str = null;
			String query = "SELECT DISTINCT firstname FROM glober_view WHERE type In ('New Hire') AND status <> 'Assigned' AND enddate IS NULL";
			ResultSet rs = getResultSet(query);

			while (rs.next()) {
				str = rs.getString("firstname");
				lst.add(str);
			}
			return lst;
		} finally {
			endConnection();
		}
	}
}
