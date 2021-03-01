package dataproviders.submodules.emergencyContacts;

import java.util.Map;

import org.testng.annotations.DataProvider;

import dataproviders.TravelMobileDataProviders;
import properties.TravelMobileProperties;
import utils.Utilities;

public class EmergencyContactsDataproviders extends TravelMobileDataProviders{
	
	/**
	 * This data provider returns data required for adding emergency contact with valid data
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "addEmergencyContactWithValidData")
	public static Object[][] addEmergencyContactWithValidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/emergencyContacts/AddEmergencyContactsWithValidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] eContactDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		int noOfDataSets = eContactDataObjects.length;
		Object[][] newEContactDataObjects = new Object[noOfDataSets][1];
		
		//append trip name with time stamp
		for(int i=0;i<noOfDataSets;i++)
		{
			@SuppressWarnings("unchecked")
			Map<Object,Object> eContactData = (Map<Object, Object>) eContactDataObjects[i][0];
			String timeStamp = Utilities.getCurrentDateAndTime("MMddyyhhmmss");
			String name =eContactData.get("name")+timeStamp;
			eContactData.put("name",name);
			newEContactDataObjects[i][0]=eContactData;
		} 
		return newEContactDataObjects;
	}
	/**
	 * This data provider returns data required for adding emergency contact with invalid data
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "addEmergencyContactWithInvalidData")
	public static Object[][] addEmergencyContactWithInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/emergencyContacts/AddEmergencyContactsWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] eContactDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		int noOfDataSets = eContactDataObjects.length;
		Object[][] newEContactDataObjects = new Object[noOfDataSets][1];
		
		//append trip name with time stamp
		for(int i=0;i<noOfDataSets;i++)
		{
			@SuppressWarnings("unchecked")
			Map<Object,Object> eContactData = (Map<Object, Object>) eContactDataObjects[i][0];
			String timeStamp = Utilities.getCurrentDateAndTime("MMddyyhhmmss");
			String name =eContactData.get("name")+timeStamp;
			eContactData.put("name",name);
			newEContactDataObjects[i][0]=eContactData;
		} 
		return newEContactDataObjects;
	}
	
	/**
	 * This data provider returns data to validate scenarios for adding emergency contact with missing DTO
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "addEmergencyContactForMissingDTO")
	public static Object[][] addEmergencyContactForMissingDTO() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/emergencyContacts/AddEmergencyContactsForMissingDTO.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] createTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		int noOfDataSets = createTripDataObjects.length;
		Object[][] newEContactDataObjects = new Object[noOfDataSets][1];
		
		//append Emergency Contact name with time stamp
		String eName;
		for(int i=0;i<noOfDataSets;i++)
		{
			@SuppressWarnings("unchecked")
			Map<Object,Object> eContactData = (Map<Object, Object>) createTripDataObjects[i][0];
			eName=(String) eContactData.get("name");
			String timeStamp = Utilities.getCurrentDateAndTime("MMddyyhhmmss");
			eName =eName+timeStamp;
			eContactData.put("name",eName);
			newEContactDataObjects[i][0]=eContactData;
		} 
		return newEContactDataObjects;
	}
	/**
	 * This data provider returns data to validate scenarios for updating emergency contact with valid data
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "updateEmergencyContactWithValidData")
	public static Object[][] updateEmergencyContactWithValidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/emergencyContacts/UpdateEmergencyContactsWithValidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] eContactDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		return eContactDataObjects;
	}
	/**
	 * This data provider returns data required for updating emergency contact with invalid data
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "updateEmergencyContactWithInvalidData")
	public static Object[][] updateEmergencyContactWithInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/emergencyContacts/UpdateEmergencyContactsWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] eContactDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		int noOfDataSets = eContactDataObjects.length;
		Object[][] newEContactDataObjects = new Object[noOfDataSets][1];
		
		//append trip name with time stamp
		for(int i=0;i<noOfDataSets;i++)
		{
			@SuppressWarnings("unchecked")
			Map<Object,Object> eContactData = (Map<Object, Object>) eContactDataObjects[i][0];
			String timeStamp = Utilities.getCurrentDateAndTime("MMddyyhhmmss");
			String name =eContactData.get("name")+timeStamp;
			eContactData.put("name",name);
			newEContactDataObjects[i][0]=eContactData;
		} 
		return newEContactDataObjects;
	}
	/**
	 * This data provider returns data to validate scenarios for updating emergency contact with missing DTO
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "updateEmergencyContactForMissingDTO")
	public static Object[][] updateEmergencyContactForMissingDTO() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/emergencyContacts/UpdateEmergencyContactsForMissingDTO.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] eContactDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
		return eContactDataObjects;
	}
	/**
	 * This data provider returns data required for validating delete emergency contacts negative scenarios
	 * 
	 * @return
	 * @throws Exception
	 */
	@DataProvider(name = "deleteEmergencyContactWithInvalidData")
	public static Object[][] deleteEmergencyContactWithInvalidData() throws Exception {
		String xmlInputStream = TravelMobileProperties.dataProviderPath + 
				"/emergencyContacts/DeleteEmergencyContactsWithInvalidData.xml";
		String xpathExpression = "/dataObjects/dataObject";
		Object[][] getTripDataObjects= buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);		
		return getTripDataObjects;
	}
	
}
