package dataproviders.submodules.gatekeepers.features;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.testng.annotations.DataProvider;

import constants.submodules.gatekeepers.GatekeepersConstants;
import database.submodules.gatekeepers.features.SearchGatekeepersDBHelper;
import dataproviders.submodules.gatekeepers.GatekeepersDataProviders;
import utils.Utilities;

/**
 * @author deepakkumar.hadiya
 */

public class SearchGatekeepersDataProviders extends GatekeepersDataProviders implements GatekeepersConstants{

	private SearchGatekeepersDBHelper dbHelper=new SearchGatekeepersDBHelper();
	/**
	 * This data provider can be used in positive scenario for searching gatekeepers
	 * @apiNote Here we have combined data from xml and database
	 * @return Object
	 * @throws Exception
	 */
	@DataProvider(name = "validTestData")
	public Object[][] getTestDataForPositiveScenario() throws Exception {
		String xmlFilePath = gatekeepersDataProviderPath + "/SearchGatekeepersData.xml";
		String xpathExpression = ("//dataObject[@type='positive']");
		Object[][] dataFromXml = buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
		
		List<String> locations = dbHelper.getLocationForEachCountry();
		List<String> oneLocationForEachCountry = new ArrayList<String>(locations);
		int totalLocations=locations.size();
		locations.addAll(oneLocationForEachCountry);
		List<String> positions = dbHelper.getPositionIds("ALL");
		List<Map<Object, Object>> dataList=new ArrayList<Map<Object,Object>>();
		// This block is to add 1 site from each country with random position and 1 site from each country with static position 
		for (int i=0;i<locations.size();i++) {
			Map<Object, Object> dataMap = new LinkedHashMap<Object, Object>();
			if(i<totalLocations) {
				String positionId=positions.get(Utilities.getRandomNumberBetween(0, positions.size()-1));
				dataMap.put(POSITION, positionId);
				dataMap.put(SENIORITY, dbHelper.getSeniorityId(positionId));
			} else {
				dataMap.put(POSITION, 1);
				dataMap.put(SENIORITY, 1);
			}	
				dataMap.put(LOCATION, locations.get(i));
				dataMap.put(CANDIDATE_SKILLS, "1");
				dataMap.put(DTO_TYPE, "FAT");
				dataMap.put(HEADER_KEY, TOKEN);
				dataMap.put(HEADER_VALUE, "shital.devalkar");
				dataMap.put(INTERVIEW_DATE, 0);
				dataList.add(dataMap);
		}
		
		Object[][] dataFromDB = new Object[dataList.size()][1];
		for(int j=0;j<dataFromDB.length;j++) {
			dataFromDB[j][0]=dataList.get(j);
		}
		return combineDataOfXmlAndDataBase(dataFromXml, dataFromDB);
	}

	/**
	 * This data provider can be used in positive scenario for searching gatekeepers from project manager ladder
	 * @return {@link Objects}}
	 * @throws Exception
	 */
	@DataProvider(name = "testData_For_PM_Ladder_From_Each_Country")
	public Object[][] getTestDataForPMLadder() throws Exception {
		List<String> locations = dbHelper.getLocationForEachCountry();
		List<Map<Object, Object>> dataList=new ArrayList<Map<Object,Object>>();
		
		for(String location:locations) {
		//This block is to add each pm ladder position with random location
				List<String> pmLadderPositions = dbHelper.getPMLadderPositions();
				int totalPMLadderPositions=pmLadderPositions.size();
				for (int i=0;i<totalPMLadderPositions;i++) {
						Map<Object, Object> dataMap = new LinkedHashMap<Object, Object>();
						String positionId = pmLadderPositions.get(i);
						dataMap.put(POSITION, positionId);
						dataMap.put(SENIORITY, dbHelper.getSeniorityId(positionId));	
						dataMap.put(LOCATION, location);
						dataMap.put(CANDIDATE_SKILLS, "1");
						dataMap.put(DTO_TYPE, "FAT");
						dataMap.put(HEADER_KEY, TOKEN);
						dataMap.put(HEADER_VALUE, "shital.devalkar");
						dataMap.put(INTERVIEW_DATE, 0);
						dataList.add(dataMap);
				}
		}
				Object[][] dataFromDB = new Object[dataList.size()][1];
				for(int j=0;j<dataFromDB.length;j++) {
					dataFromDB[j][0]=dataList.get(j);
				}
				return dataFromDB;
	}
	
	/**
	 * This data provider can be used in negative scenario for searching gate
	 * keepers
	 * 
	 * @return Object
	 * @throws Exception
	 */
	@DataProvider(name = "validTestdataForNegativeScenario")
	public Object[][] getValidTestDataFor_NegativeScenario() throws Exception {
		Object[][] data = null;
		String xmlFilePath = gatekeepersDataProviderPath + "/SearchGatekeepersData.xml";
		String xpathExpression = ("//dataObject[@type='positive' and @data='valid']");
		data = buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
		return data;
	}

	/**
	 * This data provider can be used in negative scenario with invalid header key
	 * for searching gatekeepers
	 * 
	 * @return Object
	 * @throws Exception
	 */

	@DataProvider(name = "invalidHeaderKey")
	public Object[][] getTestDataFor_invalidHeaderKey() throws Exception {
		Object[][] data = null;
		String xmlFilePath = gatekeepersDataProviderPath + "/SearchGatekeepersData.xml";
		String xpathExpression = ("//dataObject[@type='negative' and @invalidProperty='HeaderKey']");
		data = buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
		return data;
	}

	/**
	 * This data provider can be used in negative scenario with invalid header value
	 * for searching gatekeepers
	 * 
	 * @return Object
	 * @throws Exception
	 */

	@DataProvider(name = "invalidHeaderValue")
	public Object[][] getTestDataFor_invalidHeaderValue() throws Exception {
		Object[][] data = null;
		String xmlFilePath = gatekeepersDataProviderPath + "/SearchGatekeepersData.xml";
		String xpathExpression = ("//dataObject[@type='negative' and @invalidProperty='HeaderValue']");
		data = buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
		return data;
	}

	/**
	 * This data provider can be used in negative scenario for searching gatekeepers
	 * with invalid url and request method
	 * 
	 * @return Object
	 * @throws Exception
	 */
	@DataProvider(name = "invalid_Url_and_requestMethod", indices = { 0 })
	public Object[][] getTestDataFor_invalid_Url_and_requestMethod() throws Exception {
		Object[][] data = null;
		String xmlFilePath = gatekeepersDataProviderPath + "/SearchGatekeepersData.xml";
		String xpathExpression = ("//dataObject[@type='positive']");
		data = buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
		return data;
	}
	
	/**
	 * This data provider can be used in negative scenario with invalid parameter value
	 * for searching gatekeepers
	 * 
	 * @return Object
	 * @throws Exception
	 */

	@SuppressWarnings("unchecked")
	@DataProvider(name = "invalidParameterValue")
	public Object[][] getTestDataFor_invalidParameterValue() throws Exception {
	String xmlFilePath = gatekeepersDataProviderPath + "/SearchGatekeepersData.xml";
	String xpathExpression = ("//dataObject[@type='negative' and @data='invalidParameterValue']");
	Object[][] NegativeData = buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
	Object[][] positiveData=getValidTestDataFor_NegativeScenario();
	Object[][] updatedData=new Object[NegativeData.length][1];
	for(int i=0;i<updatedData.length;i++) {
		Map<Object,Object> map=new HashedMap<Object, Object>();
		map.putAll((Map<Object,Object>) positiveData[0][0]);
		map.putAll((Map<Object,Object>) NegativeData[i][0]);
		updatedData[i][0]=map;
	}
	return updatedData;
	}
	
	/**
	 * This data provider can be used to verify searched gatekeepers
	 * are in random order for specific rank
	 * 
	 * @return Object
	 * @throws Exception
	 */
	@DataProvider(name = "validTestData_for_random_ordering")
	public Object[][] getTestDataForRandomOrdering() throws Exception {
		Object[][] data = null;
		String xmlFilePath = gatekeepersDataProviderPath + "/SearchGatekeepersData.xml";
		String xpathExpression = ("//dataObject[@type='positive' and @randomOrder='true']");
		data = buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
		return data;
	}
	
	/**
	 * This data provider can be used in positive scenario for searching gate
	 * keepers for all locations
	 * @return Object
	 * @throws Exception
	 */
	@DataProvider(name = "validTestDataForAllLocations")
	public Object[][] getTestDataForAllLocations() throws Exception {
		
		List<String> locations = dbHelper.getLocationNames("ALL");
		Object[][] dataFromDB = new Object[locations.size()][1];
		int i=0;
		for (String location : locations) {
			Map<Object, Object> dataMap = new LinkedHashMap<Object, Object>();
				dataMap.put(POSITION, 1);
				dataMap.put(SENIORITY, 3);
				dataMap.put(LOCATION, location);
				dataMap.put(CANDIDATE_SKILLS, "1");
				dataMap.put(DTO_TYPE, "FAT");
				dataMap.put(HEADER_KEY, TOKEN);
				dataMap.put(HEADER_VALUE, "shital.devalkar");
				dataMap.put(INTERVIEW_DATE, 0);
				dataFromDB[i++][0]=dataMap;
			}
		return dataFromDB;
	}
	
	/**
	 * This data provider can be used in positive scenario for searching gate
	 * keepers for all positions
	 * @return Object
	 * @throws Exception
	 */
	@DataProvider(name = "validTestDataForAllPositions")
	public Object[][] getTestDataForAllPositions() throws Exception {
		
		int i=1;
		List<String> locations = dbHelper.getLocationNames("ALL");
		List<String> positionIds = dbHelper.getPositionIds("ALL");
		List<Map<Object, Object>> dataList=new ArrayList<Map<Object,Object>>();
		for (String positionId : positionIds) {
			String seniorityId = dbHelper.getSeniorityId(positionId);
			if (!seniorityId.contains("not available")) {
				Map<Object, Object> dataMap = new LinkedHashMap<Object, Object>();
				dataMap.put(POSITION, positionId);
				dataMap.put(SENIORITY, dbHelper.getSeniorityId(positionId));
				dataMap.put(LOCATION, locations.get(Utilities.getRandomNumberBetween(0, locations.size()-1)));
				dataMap.put(CANDIDATE_SKILLS, "1");
				dataMap.put(DTO_TYPE, "FAT");
				dataMap.put(HEADER_KEY, TOKEN);
				dataMap.put(HEADER_VALUE, "shital.devalkar");
				dataMap.put(INTERVIEW_DATE, 0);
				dataList.add(dataMap);
				System.out.println(i++);
			}
		}
		Object[][] dataFromDB = new Object[dataList.size()][1];
		for(int j=0;j<dataFromDB.length;j++) {
			dataFromDB[j][0]=dataList.get(j);
		}
		return dataFromDB;
	}
}
