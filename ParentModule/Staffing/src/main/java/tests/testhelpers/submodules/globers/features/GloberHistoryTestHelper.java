package tests.testhelpers.submodules.globers.features;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import database.submodules.globers.features.GloberHistoryDBHelper;
import endpoints.submodules.globers.features.GloberHistoryEndPoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.submodules.globers.GlobersBaseTest;
import tests.testhelpers.submodules.globers.GlobersTestHelper;
import utils.RestUtils;
import utils.Utilities;

/**
 * 
 * @author akshata.dongare
 *
 */
public class GloberHistoryTestHelper extends GlobersTestHelper{

	RestUtils restUtils = new RestUtils();
	Response reqExtensionResponse;
	GloberHistoryDBHelper globerHistoryDBHelper;
	
	public GloberHistoryTestHelper(String userName) throws Exception {
		super(userName);
		globerHistoryDBHelper = new GloberHistoryDBHelper();
	}
	

	/**
	 * This method will return glober history response
	 * @param type
	 * @param globerId
	 * @param globalId
	 * @return response
	 */
	public Response globerHistoryResponse(String type, String globerId, String globalId) {
		String globerHistoryUrl = GlobersBaseTest.baseUrl + String.format(GloberHistoryEndPoints.getGloberHistory,type,globerId,globalId,"ALL");
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(GlobersBaseTest.sessionId);

		Response globerHistoryResponse = restUtils.executeGET(requestSpecification, globerHistoryUrl);
		return globerHistoryResponse;
	}
	
	/**
	 * This method will return bench start date response
	 * @param type
	 * @return response
	 * @throws SQLException
	 */
	public Response getHistoryResponseFromGlobalId(String type, String globalId) throws SQLException {
		String globerId = globerHistoryDBHelper.getGloberIdFromGlobalId(globalId);
		return globerHistoryResponse(type,globerId,globalId);
	}
	
	/**
	 * This method will return out of company response
	 * @param type
	 * @param globerId
	 * @return respone
	 * @throws SQLException
	 */
	public Response getHistoryResponseFromGloberId(String type, String globerId) throws SQLException {
		String globalId = globerHistoryDBHelper.getGlobalIdFromGloberId(globerId);
		return globerHistoryResponse(type,globerId,globalId);
	}
	
	/**
	 * This method will return db dates after conversion
	 * @param listOfDbDates
	 * @return list
	 * @throws ParseException
	 */
	public List<String> getFinalDbDatesAfterConversion(List<String> listOfDbDates) throws ParseException{
		List<String> listOfDbDatesHrMin = new ArrayList<String>();
		
		for(int i=0; i<listOfDbDates.size(); i++) {
			String dbDatesConvertHrMin = Utilities.getDateWithTime(listOfDbDates.get(i));
			listOfDbDatesHrMin.add(dbDatesConvertHrMin);
		}
		return listOfDbDatesHrMin;
	}
	/**
	 * This method will return bench start dates from db
	 * @param globalId
	 * @return list
	 * @throws Exception
	 */
	public List<String> getBenchStartDateDb(String globalId) throws Exception{
		List<String> listOfBenchStartDatesDb = globerHistoryDBHelper.getBenchStarDateFromGlobalId(globalId);
		return getFinalDbDatesAfterConversion(listOfBenchStartDatesDb);
	}
	
	/**
	 * This method will return out of company dates from db
	 * @param globerId
	 * @return list
	 * @throws Exception
	 */
	public List<String> getOutOfCompanyDateDB(String globerId) throws Exception{
		List<String> listOfOutOfCompanyDatesDb = globerHistoryDBHelper.getOutOfCompanyDates(globerId);
		return getFinalDbDatesAfterConversion(listOfOutOfCompanyDatesDb);
	}	
	
	/**
	 * This method will return glober training dates from db
	 * @param globerId
	 * @return list
	 * @throws Exception
	 */
	public List<String> getGloberTrainingDatesDB(String globerId) throws Exception{
		List<String> listOfTrainingDatesDb = globerHistoryDBHelper.getGloberTrainingDates(globerId);
		return getFinalDbDatesAfterConversion(listOfTrainingDatesDb);
	}
	
	/**
	 * This method will return globant academy training from db
	 * @param globerId
	 * @return list
	 * @throws Exception
	 */
	public List<String> getGloberAcademyTrainingDatesDb(String globerId) throws Exception{
		List<String> listOfGlobantAcademyTrainingDatesDb = globerHistoryDBHelper.getGlobantAcademyTrainingDateDb(globerId);
		listOfGlobantAcademyTrainingDatesDb.remove(null);
		return getFinalDbDatesAfterConversion(listOfGlobantAcademyTrainingDatesDb);
	}
	
	/**
	 * Get db according to the action
	 * @param globerId
	 * @param action
	 * @return list
	 * @throws Exception
	 */
	public List<String> getDbDates(String globerId, String action) throws Exception{
		List<String> listOfDbDates = globerHistoryDBHelper.getActionDbDates(globerId, action);
		return getFinalDbDatesAfterConversion(listOfDbDates);
	}
	
	/**
	 * Get glober joining date from db
	 * @param globerId
	 * @return list
	 * @throws Exception
	 */
	public List<String> getGloberJoiningDatesDb(String globerId) throws Exception{
		List<String> listOfGloberJoiningDatesDeb = globerHistoryDBHelper.getGloberJoinDate(globerId);
		return getFinalDbDatesAfterConversion(listOfGloberJoiningDatesDeb);
	}
	
	/**
	 * Get glober project feedback dates from db
	 * @param globerId
	 * @return list
	 * @throws SQLException
	 */
	public List<String> getGloberProjectFeedbackDb(String globerId) throws Exception{
		List<String> listOfGloberProjectFeedback = globerHistoryDBHelper.getProjectFeedbackDatesDb(globerId);
		return getFinalDbDatesAfterConversion(listOfGloberProjectFeedback);
	}
	
	/**
	 * Get glober performance assessment dates
	 * @param globerId
	 * @return list
	 * @throws Exception
	 */
	public List<String> getPerformanceAssessmentDbDates(String globerId) throws Exception{
		List<String> listOfPerformanceAssessmentDatesDb = globerHistoryDBHelper.getPerformanceAssessmentDatesDb(globerId);
		return getFinalDbDatesAfterConversion(listOfPerformanceAssessmentDatesDb);
	}
	
	/**
	 * Get recategorization  dates
	 * @param recategorizationId
	 * @return list
	 * @throws Exception
	 */
	public List<String> getRecategorizationDatesDb(String recategorizationId) throws Exception{
		List<String> listOfRecategorizationDatesDb = globerHistoryDBHelper.getRecategorizationDatesDb(recategorizationId);
		return getFinalDbDatesAfterConversion(listOfRecategorizationDatesDb);
	}
	
}
