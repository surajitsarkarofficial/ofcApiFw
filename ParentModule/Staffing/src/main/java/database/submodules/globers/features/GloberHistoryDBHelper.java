package database.submodules.globers.features;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.submodules.globers.GlobersDBHelper;

/**
 * 
 * @author akshata.dongare
 *
 */
public class GloberHistoryDBHelper extends GlobersDBHelper {

	public GloberHistoryDBHelper() {
		
	}
	
	/**
	 * Get benchstartdate from globalId
	 * @param globalId
	 * @return List
	 * @throws SQLException
	 */
	public ArrayList<String> getBenchStarDateFromGlobalId(String globalId) throws SQLException {
		ArrayList<String> listOfBenchStartDate = new ArrayList<String>();
		String query = "SELECT benchstartdate as benchStartDate FROM glober_view  WHERE globalid = " + globalId + " "
				+ "and internalassignmenttype = 'BENCH' " + "and benchstartdate < now() ORDER BY benchstartdate DESC;";
		try {
			ResultSet rs = getResultSet(query);
			while (rs.next()) {
				listOfBenchStartDate.add(rs.getString("benchStartDate"));
			}
			return listOfBenchStartDate;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * Get out of company dates of a glober
	 * @param globerId
	 * @return list
	 * @throws SQLException
	 */
	public List<String> getOutOfCompanyDates(String globerId) throws SQLException{
		List<String> listOfOutOfCompanyDate = new ArrayList<String>();
		String query = "SELECT starting_date as outOfCompanyDate FROM assignments WHERE internal_assignment_type LIKE '%OUT%' AND resume_fk = "+globerId+
				"ORDER BY starting_date DESC";
		try {
			ResultSet rs = getResultSet(query);
			while (rs.next()) {
				listOfOutOfCompanyDate.add(rs.getString("outOfCompanyDate"));
			}
			return listOfOutOfCompanyDate;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * Get training dates of a glober
	 * @param globerId
	 * @return list
	 * @throws SQLException
	 */
	public List<String> getGloberTrainingDates(String globerId) throws SQLException{
		List<String> listOfTrainingDates = new ArrayList<String>();
		String query = "SELECT registration_date as trainingDate FROM training_course_attendances WHERE resume_fk = "+globerId+" "
				+ "and registration_date is NOT NULL ORDER BY registration_date DESC ;";
		try {
			ResultSet rs = getResultSet(query);
			while (rs.next()) {
				listOfTrainingDates.add(rs.getString("trainingDate"));
			}
			return listOfTrainingDates;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * Get globant academy training dates
	 * @param globerId
	 * @return list
	 * @throws SQLException
	 */
	public List<String> getGlobantAcademyTrainingDateDb(String globerId) throws SQLException{
		List<String> listOfTrainingDates = new ArrayList<String>();
		String query = "SELECT start_date as globantAcademyDate FROM glober_statuses WHERE glober_fk = "+globerId+"";
		try {
			ResultSet rs = getResultSet(query);
			while (rs.next()) {
				listOfTrainingDates.add(rs.getString("globantAcademyDate"));
			}
			return listOfTrainingDates;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * Get db dates of glober according to action
	 * @param globerId
	 * @param action
	 * @return list
	 * @throws SQLException
	 */
	public List<String> getActionDbDates(String globerId,String action) throws SQLException{
		List<String> listOfDbDates = new ArrayList<String>();
		String query = "SELECT end_evaluation_period as dbDates FROM performance_assessment p, performance_assessment_period pp"
				+ " WHERE p.performance_assessment_state='"+action+"' "
				+ "and p.period_fk = pp.id and p.resume_fk = "+globerId+" ORDER BY end_evaluation_period DESC";
		try {
			ResultSet resultSet = getResultSet(query);
			while (resultSet.next()) {
				listOfDbDates.add(resultSet.getString("dbDates"));
			}
			return listOfDbDates;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * Get db date of glober joining
	 * @param globerId
	 * @return list
	 * @throws SQLException
	 */
	public List<String> getGloberJoinDate(String globerId) throws SQLException{
		List<String> globerJoinDbDates = new ArrayList<String>();
		String query = "SELECT ci.entry_date as globerJoinDate FROM globers g, contracts_information ci WHERE g.contract_information_fk=ci.id and g.id="+globerId;
		try {
			ResultSet resultSet = getResultSet(query);
			while (resultSet.next()) {
				globerJoinDbDates.add(resultSet.getString("globerJoinDate"));
			}
			return globerJoinDbDates;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * Get project feedback dates of a glober
	 * @param globerId
	 * @return list
	 * @throws SQLException
	 */
	public List<String> getProjectFeedbackDatesDb(String globerId) throws SQLException{
		List<String> listOfProjectFeedbackDates = new ArrayList<String>();
		String query = "SELECT distinct e.evaluation_period_to as date FROM evaluations e " + "LEFT JOIN assignments a "
				+ "on e.project_fk = a.project_fk WHERE e.evaluated_fk = '" + globerId + "' and a.resume_fk = '"
				+ globerId + "' ORDER by e.evaluation_period_to DESC";
		try {
			ResultSet resultSet = getResultSet(query);
			while (resultSet.next()) {
				listOfProjectFeedbackDates.add(resultSet.getString("date"));
			}
			return listOfProjectFeedbackDates;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * Get performance assessment dates of a glober
	 * @param globerId
	 * @return list
	 * @throws SQLException
	 */
	public List<String> getPerformanceAssessmentDatesDb(String globerId) throws SQLException{
		List<String> listOfPerformanceAssessment = new ArrayList<String>();
		String query = "SELECT p.end_evaluation_period as performanceAssessmentDate FROM performance_assessment a "
				+ "LEFT JOIN performance_assessment_period p ON a.period_fk = p.id WHERE a.resume_fk = '"
				+ globerId+ "' and a.performance_assessment_state = 'ASSESSMENT_CLOSED' ORDER by p.end_evaluation_period DESC";
		
		try {
			ResultSet resultSet = getResultSet(query);
			while (resultSet.next()) {
				listOfPerformanceAssessment.add(resultSet.getString("performanceAssessmentDate"));
			}
			return listOfPerformanceAssessment;
		}finally {
			endConnection();
		}

	}
	
	/**
	 * Get Recategorization Dates Db
	 * @param recategorizationId
	 * @return list
	 * @throws SQLException
	 */
	public List<String> getRecategorizationDatesDb(String recategorizationId) throws SQLException{
		List<String> listOfRecategorizationDates = new ArrayList<String>();
		String query = "select start_date as recategorizationDate from contracts_data where contract_information_fk = "
				+ recategorizationId +";";
		try {
			ResultSet resultSet = getResultSet(query);
			while (resultSet.next()) {
				listOfRecategorizationDates.add(resultSet.getString("recategorizationDate"));
			}
			return listOfRecategorizationDates;
		}finally {
			endConnection();
		}
	}
	
	/**
	 * Get recategorization id of a glober
	 * @param globerId
	 * @return String
	 * @throws SQLException
	 */
	public String getRecategorizationId(String globerId) throws SQLException {
		String query = "SELECT contract_information_fk as recategorizationId FROM globers WHERE id ="+globerId;
		try {
			ResultSet resultSet = getResultSet(query);
			resultSet.next();
			return resultSet.getString("recategorizationId");
		}finally {
			endConnection();
		}
	}
}
