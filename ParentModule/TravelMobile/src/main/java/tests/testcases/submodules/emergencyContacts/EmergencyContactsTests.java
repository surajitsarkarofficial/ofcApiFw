package tests.testcases.submodules.emergencyContacts;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import dataproviders.submodules.emergencyContacts.EmergencyContactsDataproviders;
import io.restassured.response.Response;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.submodules.emergencyContacts.EmergencyContactsTestHelper;
import tests.testhelpers.submodules.myTrips.features.PassengerTestsHelper;
import utils.RestUtils;
import utils.Utilities;

public class EmergencyContactsTests extends TravelMobileBaseTest {

	public static String passengerId, emergencyContactId, passengerEmail, econtactName, eContactRelationship,
			eContactPhoneNumber, eContactAddress;

	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		subModuleTest = moduleTest.createNode("EmergencyContactsTests");
	}

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		featureTest = subModuleTest.createNode("EmergencyContactsTests");
	}

	/**
	 * This method will test the add emergency contact valid scenario
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 0, dataProvider = "addEmergencyContactWithValidData", dataProviderClass = EmergencyContactsDataproviders.class)
	public void verifyAddEmergencyContact(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EmergencyContactsTestHelper testHelper = new EmergencyContactsTestHelper();
		RestUtils restUtils = new RestUtils();
		passengerEmail = (String) dtoMap.get("passengerEmail");
		dtoMap.remove("passengerEmail");
		// First delete all the emergency contacts if present
		testHelper.deleteAllEmergencyContacts(passengerEmail);
		String phoneNumber = Utilities.generateRandomPhoneNumber();
		dtoMap.put("phoneNumber", phoneNumber);
		// Fetch the passenger info to verify in the response of add Passenger
		Response passengerInfoResponse = new PassengerTestsHelper().getUserInfo(passengerEmail);
		validateResponseToContinueTest(passengerInfoResponse, 200,
				"Unable to fetch passenger info for user with email id - " + passengerEmail, true);
		passengerId = String.valueOf(restUtils.getValueFromResponse(passengerInfoResponse, "content.passengerId"));
		Response response = testHelper.addEmergencyContact(dtoMap, passengerId);
		validateResponseToContinueTest(response, 200, "Unable to add EmergencyContact", true);
		dtoMap.put("passengerEmail", passengerEmail);
		dtoMap.put("passengerId", passengerId);
		String expectedStatus = "SUCCESS";
		String actualStatus = String.valueOf(restUtils.getValueFromResponse(response, "status"));
		softAssert.assertEquals(actualStatus, expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'.", expectedStatus, actualStatus));

		String expectedMessage = "Execution Successful";
		String actualMessage = String.valueOf(restUtils.getValueFromResponse(response, "message"));
		softAssert.assertEquals(actualMessage, expectedMessage,
				String.format("Expected message was '%s' and actual was '%s'.", expectedMessage, actualMessage));

		emergencyContactId = String.valueOf(restUtils.getValueFromResponse(response, "content.id"));
		softAssert.assertFalse(emergencyContactId == null, "Emergency Contact id was NULL");

		econtactName = (String) dtoMap.get("name");
		String actualName = String.valueOf(restUtils.getValueFromResponse(response, "content.name"));
		softAssert.assertEquals(actualName, econtactName,
				String.format("Expected Name was '%s' and actual was '%s'.", econtactName, actualName));

		eContactRelationship = (String) dtoMap.get("relationship");
		String actualRelationship = String.valueOf(restUtils.getValueFromResponse(response, "content.relationship"));
		softAssert.assertEquals(actualRelationship, eContactRelationship, String.format(
				"Expected Relationship was '%s' and actual was '%s'.", eContactRelationship, actualRelationship));

		eContactPhoneNumber = String.valueOf(dtoMap.get("phoneNumber"));
		String actualPhoneNumber = String.valueOf(restUtils.getValueFromResponse(response, "content.phoneNumber"));
		softAssert.assertEquals(actualPhoneNumber, eContactPhoneNumber, String
				.format("Expected Phone Number was '%s' and actual was '%s'.", eContactPhoneNumber, actualPhoneNumber));

		eContactAddress = (String) dtoMap.get("address");
		String actualAddress = String.valueOf(restUtils.getValueFromResponse(response, "content.address"));
		softAssert.assertEquals(actualAddress, eContactAddress,
				String.format("Expected Address was '%s' and actual was '%s'.", eContactAddress, actualAddress));

		softAssert.assertAll();

		test.log(Status.PASS, "Add Emergency Contact verification was successful.");

	}

	/**
	 * This test method will test the update emergency contact valid scenario
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 1, dependsOnMethods = "verifyAddEmergencyContact", dataProvider = "updateEmergencyContactWithValidData", dataProviderClass = EmergencyContactsDataproviders.class)
	public void verifyUpdateEmergencyContact(Map<Object, Object> dtoMap) throws Exception {
		String testScenario = dtoMap.get("dataScenario").toString();
		test.log(Status.INFO,
				String.format("Validating Add Emergency Contact for Incorrect DTO by providing '%s'", testScenario));
		dtoMap.remove("dataScenario");
		SoftAssert softAssert = new SoftAssert();
		EmergencyContactsTestHelper testHelper = new EmergencyContactsTestHelper();
		RestUtils restUtils = new RestUtils();
		passengerEmail = (String) dtoMap.get("passengerEmail");
		dtoMap.remove("passengerEmail");
		dtoMap.put("name", econtactName);
		dtoMap.put("relationship", eContactRelationship);
		dtoMap.put("phoneNumber", eContactPhoneNumber);
		dtoMap.put("address", eContactAddress);
		dtoMap = testHelper.constructDTOForUpdateEmergencyContactTests(dtoMap, testScenario);
		dtoMap.put("id", emergencyContactId);
		Response response = testHelper.updateEmergencyContact(dtoMap, emergencyContactId, passengerId);
		validateResponseToContinueTest(response, 200, "Unable to update EmergencyContact", true);
		dtoMap.put("passengerEmail", passengerEmail);
		dtoMap.put("passengerId", passengerId);
		dtoMap.put("dataScenario", testScenario);
		System.out.println(dtoMap);
		String expectedStatus = "SUCCESS";
		String actualStatus = String.valueOf(restUtils.getValueFromResponse(response, "status"));
		softAssert.assertEquals(actualStatus, expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'.", expectedStatus, actualStatus));

		String expectedMessage = "Execution Successful";
		String actualMessage = String.valueOf(restUtils.getValueFromResponse(response, "message"));
		softAssert.assertEquals(actualMessage, expectedMessage,
				String.format("Expected message was '%s' and actual was '%s'.", expectedMessage, actualMessage));

		String actualEmergencyContactId = String.valueOf(restUtils.getValueFromResponse(response, "content.id"));
		softAssert.assertEquals(actualEmergencyContactId, emergencyContactId, String.format(
				"Expected Contact Id was '%s' and actual was '%s'.", emergencyContactId, actualEmergencyContactId));

		econtactName = (String) dtoMap.get("name");
		String actualName = String.valueOf(restUtils.getValueFromResponse(response, "content.name"));
		softAssert.assertEquals(actualName, econtactName,
				String.format("Expected Name was '%s' and actual was '%s'.", econtactName, actualName));

		eContactRelationship = (String) dtoMap.get("relationship");
		String actualRelationship = String.valueOf(restUtils.getValueFromResponse(response, "content.relationship"));
		softAssert.assertEquals(actualRelationship, eContactRelationship, String.format(
				"Expected Relationship was '%s' and actual was '%s'.", eContactRelationship, actualRelationship));

		eContactPhoneNumber = String.valueOf(dtoMap.get("phoneNumber"));
		String actualPhoneNumber = String.valueOf(restUtils.getValueFromResponse(response, "content.phoneNumber"));
		softAssert.assertEquals(actualPhoneNumber, eContactPhoneNumber, String
				.format("Expected Phone Number was '%s' and actual was '%s'.", eContactPhoneNumber, actualPhoneNumber));

		eContactAddress = (String) dtoMap.get("address");
		String actualAddress = String.valueOf(restUtils.getValueFromResponse(response, "content.address"));
		softAssert.assertEquals(actualAddress, eContactAddress,
				String.format("Expected Address was '%s' and actual was '%s'.", eContactAddress, actualAddress));

		softAssert.assertAll();

		test.log(Status.PASS, "Update Emergency Contact verification was successful.");

	}

	/**
	 * This test method will test update emergency contact api for invalid data
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 2, dependsOnMethods = "verifyAddEmergencyContact", dataProvider = "updateEmergencyContactWithInvalidData", dataProviderClass = EmergencyContactsDataproviders.class)
	public void verifyUpdateEmergencyContactWithInvalidData(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		EmergencyContactsTestHelper testHelper = new EmergencyContactsTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = dtoMap.get("dataScenario").toString();
		String expectedMessage = dtoMap.get("expectedMessage").toString();
		String expectedStatus = dtoMap.get("expectedStatus").toString();
		dtoMap.remove("dataScenario");
		dtoMap.remove("expectedMessage");
		dtoMap.remove("expectedStatus");
		test.log(Status.INFO,
				String.format("Validating Add Emergency Contact for Incorrect DTO by providing '%s'", testScenario));
		dtoMap = testHelper.constructDTOForInvalidDataAddAndUpdateEmergencyContactTests(dtoMap, testScenario);
		String testemergencyContactId, testPassengerId;
		if (testScenario.contains("Invalid Emergency Contact Id")) {
			testemergencyContactId = "999999";
		} else {
			testemergencyContactId = emergencyContactId;
		}
		if (testScenario.contains("Invalid Passenger Id")) {
			testPassengerId = "999999";
		} else {
			testPassengerId = passengerId;
		}
		if (!testScenario.contains("Empty Id")) {
			dtoMap.put("id", testemergencyContactId);
		} else {
			dtoMap.put("id", "");
		}
		Response response = testHelper.updateEmergencyContact(dtoMap, testemergencyContactId, testPassengerId);
		validateResponseToContinueTest(response, 200,
				"Unable to verify update Emergency Contact for negative scenarios", true);
		dtoMap.put("dataScenario", testScenario);
		String actualStatus = String.valueOf(restUtils.getValueFromResponse(response, "status"));
		softAssert.assertEquals(actualStatus, expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'.", expectedStatus, actualStatus));

		String actualMessage = String.valueOf(restUtils.getValueFromResponse(response, "message"));
		softAssert.assertEquals(actualMessage, expectedMessage,
				String.format("Expected message was '%s' and actual was '%s'.", expectedMessage, actualMessage));

		softAssert.assertAll();

		test.log(Status.PASS, "Add Emergency Contact verification for Invalid data was successful.");

	}

	/**
	 * This test method will test update emergency contact for incorrect dtos
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 3, dependsOnMethods = "verifyAddEmergencyContact", dataProvider = "updateEmergencyContactForMissingDTO", dataProviderClass = EmergencyContactsDataproviders.class)
	public void verifyUpdateEmergencyContactForIncorrectDTOs(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		EmergencyContactsTestHelper testHelper = new EmergencyContactsTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = dtoMap.get("dataScenario").toString();
		String expectedMessage = dtoMap.get("expectedMessage").toString();
		String expectedStatus = dtoMap.get("expectedStatus").toString();
		dtoMap.remove("dataScenario");
		dtoMap.remove("expectedMessage");
		dtoMap.remove("expectedStatus");
		test.log(Status.INFO,
				String.format("Validating Add Emergency Contact for Incorrect DTO by providing '%s'", testScenario));
		dtoMap.put("id", emergencyContactId);
		dtoMap = testHelper.constructDTOForMissingDTOEmergencyContactTests(dtoMap, testScenario);
		Response response = testHelper.updateEmergencyContact(dtoMap, emergencyContactId, passengerId);
		;
		validateResponseToContinueTest(response, 200, "Unable to verify Update EmergencyContact", true);
		dtoMap.put("dataScenario", testScenario);
		String actualStatus = String.valueOf(restUtils.getValueFromResponse(response, "status"));
		softAssert.assertEquals(actualStatus, expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'.", expectedStatus, actualStatus));

		String actualMessage = String.valueOf(restUtils.getValueFromResponse(response, "message"));
		softAssert.assertEquals(actualMessage, expectedMessage,
				String.format("Expected message was '%s' and actual was '%s'.", expectedMessage, actualMessage));

		softAssert.assertAll();

		test.log(Status.PASS, "Update Emergency Contact verification for Incorrect DTOs was successful.");

	}

	/**
	 * This test method will test delete emergency contact for valid data
	 * 
	 * @throws Exception
	 */
	@Test(priority = 4, dependsOnMethods = "verifyUpdateEmergencyContact")
	public void verifyDeleteEmergencyContactForValidData() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EmergencyContactsTestHelper testHelper = new EmergencyContactsTestHelper();
		RestUtils restUtils = new RestUtils();

		Response response = testHelper.deleteEmergencyContact(emergencyContactId);
		validateResponseToContinueTest(response, 200, "Unable to Delete Emergency Contact id " + emergencyContactId,
				true);

		String expectedStatus = "SUCCESS";
		String actualStatus = String.valueOf(restUtils.getValueFromResponse(response, "status"));
		softAssert.assertEquals(actualStatus, expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'.", expectedStatus, actualStatus));

		String expectedMessage = "Execution Successful";
		String actualMessage = String.valueOf(restUtils.getValueFromResponse(response, "message"));
		softAssert.assertEquals(actualMessage, expectedMessage,
				String.format("Expected message was '%s' and actual was '%s'.", expectedMessage, actualMessage));

		List<Integer> eIdsList = testHelper.getListOfEmergencyContactIds(passengerEmail);
		softAssert.assertFalse(eIdsList.contains(Integer.parseInt(emergencyContactId)),
				String.format("Emergency contact id %s was not deleted succesfully.", emergencyContactId));

		softAssert.assertAll();

		test.log(Status.PASS, "Delete Emergency Contact verification was successful.");
	}

	/**
	 * This test method will test delete emergency contacts for invalid data
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 5, dependsOnMethods = "verifyDeleteEmergencyContactForValidData", dataProvider = "deleteEmergencyContactWithInvalidData", dataProviderClass = EmergencyContactsDataproviders.class)
	public void verifyDeleteEmergencyContactForInvalidData(Map<Object, Object> dtoMap) throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EmergencyContactsTestHelper testHelper = new EmergencyContactsTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = dtoMap.get("dataScenario").toString();
		String expectedMessage = dtoMap.get("expectedMessage").toString();
		String expectedStatus = dtoMap.get("expectedStatus").toString();
		test.log(Status.INFO, String
				.format("Validating Delete Emergency Contact for negative scenarios by providing '%s'", testScenario));
		String testEContId;
		if (testScenario.equals("Already Deleted Emergency Contact id")) {
			testEContId = emergencyContactId;
		} else {
			testEContId = dtoMap.get("emergencyContactId").toString();
		}
		Response response = testHelper.deleteEmergencyContact(testEContId);
		validateResponseToContinueTest(response, 200, "Unable to Delete Emergency Contact id " + emergencyContactId,
				true);

		String actualStatus = String.valueOf(restUtils.getValueFromResponse(response, "status"));
		softAssert.assertEquals(actualStatus, expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'.", expectedStatus, actualStatus));

		String actualMessage = String.valueOf(restUtils.getValueFromResponse(response, "message"));
		softAssert.assertEquals(actualMessage, expectedMessage,
				String.format("Expected message was '%s' and actual was '%s'.", expectedMessage, actualMessage));

		softAssert.assertAll();

		test.log(Status.PASS, "Delete Emergency Contact negative verification was successful.");
	}

	/**
	 * This test method will test add emergency contact api for invalid data
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */

	@Test(priority = 6, dependsOnMethods = "verifyAddEmergencyContact", dataProvider = "addEmergencyContactWithInvalidData", dataProviderClass = EmergencyContactsDataproviders.class)
	public void verifyAddEmergencyContactWithInvalidData(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		EmergencyContactsTestHelper testHelper = new EmergencyContactsTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = dtoMap.get("dataScenario").toString();
		String expectedMessage = dtoMap.get("expectedMessage").toString();
		String expectedStatus = dtoMap.get("expectedStatus").toString();
		dtoMap.remove("dataScenario");
		dtoMap.remove("expectedMessage");
		dtoMap.remove("expectedStatus");
		// First delete all the emergency contacts if present
		testHelper.deleteAllEmergencyContacts(passengerEmail);
		test.log(Status.INFO,
				String.format("Validating Add Emergency Contact for Incorrect DTO by providing '%s'", testScenario));
		dtoMap = testHelper.constructDTOForInvalidDataAddAndUpdateEmergencyContactTests(dtoMap, testScenario);
		String testPassengerId;
		if (testScenario.contains("Invalid Passenger Id")) {
			testPassengerId = "999999";
		} else {
			testPassengerId = passengerId;
		}
		if (!(testScenario.contains("Alphanumerics in the PhoneNumber")
				|| testScenario.contains("Special Characters in the PhoneNumber")
				|| testScenario.contains("Empty PhoneNumber"))) {
			String phoneNumber = Utilities.generateRandomPhoneNumber();
			dtoMap.put("phoneNumber", phoneNumber);
		}
		Response response = testHelper.addEmergencyContact(dtoMap, testPassengerId);
		validateResponseToContinueTest(response, 200, "Unable to verify add Emergency Contact for negative scenarios",
				true);
		dtoMap.put("dataScenario", testScenario);
		String actualStatus = String.valueOf(restUtils.getValueFromResponse(response, "status"));
		softAssert.assertEquals(actualStatus, expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'.", expectedStatus, actualStatus));

		String actualMessage = String.valueOf(restUtils.getValueFromResponse(response, "message"));
		softAssert.assertEquals(actualMessage, expectedMessage,
				String.format("Expected message was '%s' and actual was '%s'.", expectedMessage, actualMessage));

		softAssert.assertAll();

		test.log(Status.PASS, "Add Emergency Contact verification for Invalid data was successful.");

	}

	/**
	 * This test method will test add emergency contact for incorrect dtos
	 * 
	 * @param dtoMap
	 * @throws Exception
	 */
	@Test(priority = 7, dependsOnMethods = "verifyAddEmergencyContact", dataProvider = "addEmergencyContactForMissingDTO", dataProviderClass = EmergencyContactsDataproviders.class)
	public void verifyAddEmergencyContactForIncorrectDTOs(Map<Object, Object> dtoMap) throws Exception {

		SoftAssert softAssert = new SoftAssert();
		EmergencyContactsTestHelper testHelper = new EmergencyContactsTestHelper();
		RestUtils restUtils = new RestUtils();
		String testScenario = dtoMap.get("dataScenario").toString();
		String expectedMessage = dtoMap.get("expectedMessage").toString();
		String expectedStatus = dtoMap.get("expectedStatus").toString();
		dtoMap.remove("dataScenario");
		dtoMap.remove("expectedMessage");
		dtoMap.remove("expectedStatus");
		String phoneNumber = Utilities.generateRandomPhoneNumber();
		dtoMap.put("phoneNumber", phoneNumber);
		// First delete all the emergency contacts if present
		testHelper.deleteAllEmergencyContacts(passengerEmail);
		test.log(Status.INFO,
				String.format("Validating Add Emergency Contact for Incorrect DTO by providing '%s'", testScenario));
		dtoMap = testHelper.constructDTOForMissingDTOEmergencyContactTests(dtoMap, testScenario);
		Response response = testHelper.addEmergencyContact(dtoMap, passengerId);
		validateResponseToContinueTest(response, 200, "Unable to add EmergencyContact", true);
		dtoMap.put("dataScenario", testScenario);
		String actualStatus = String.valueOf(restUtils.getValueFromResponse(response, "status"));
		softAssert.assertEquals(actualStatus, expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'.", expectedStatus, actualStatus));

		String actualMessage = String.valueOf(restUtils.getValueFromResponse(response, "message"));
		softAssert.assertEquals(actualMessage, expectedMessage,
				String.format("Expected message was '%s' and actual was '%s'.", expectedMessage, actualMessage));

		softAssert.assertAll();

		test.log(Status.PASS, "Add Emergency Contact verification for Incorrect DTOs was successful.");

	}
	/**
	 * This method will verify validation for addin emergency contact with same number
	 * @throws Exception
	 */
	@Test(priority = 8, dependsOnMethods = "verifyAddEmergencyContact")
	public void verifyValidationForAddEmergencyContactWithSamePhoneNumber() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EmergencyContactsTestHelper testHelper = new EmergencyContactsTestHelper();
		RestUtils restUtils = new RestUtils();
		// First delete all the emergency contacts if present
		testHelper.deleteAllEmergencyContacts(passengerEmail);

		// Add 1 emergency contacts
		Map<Object, Object> dtoMap = new LinkedHashMap<Object, Object>();
		dtoMap.put("name", "Auto " + Utilities.getCurrentDateAndTime("ddMMYYhhmmss"));
		dtoMap.put("relationship", "Colleague");
		String phoneNumber = Utilities.generateRandomPhoneNumber();
		dtoMap.put("phoneNumber", phoneNumber);
		dtoMap.put("isoCountryCode", "IN");
		dtoMap.put("address", "Pune India");
		Response response = testHelper.addEmergencyContact(dtoMap, passengerId);
		validateResponseToContinueTest(response, 200, "Unable to add EmergencyContact", true);
		String expectedStatus = "SUCCESS";
		String actualStatus = String.valueOf(restUtils.getValueFromResponse(response, "status"));
		softAssert.assertEquals(actualStatus, expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'.", expectedStatus, actualStatus));

		String expectedMessage = "Execution Successful";
		String actualMessage = String.valueOf(restUtils.getValueFromResponse(response, "message"));
		softAssert.assertEquals(actualMessage, expectedMessage,
				String.format("Expected message was '%s' and actual was '%s'.", expectedMessage, actualMessage));

		// Add Second contact with same phone number used above
		dtoMap = new LinkedHashMap<Object, Object>();
		dtoMap.put("name", "Auto " + Utilities.getCurrentDateAndTime("ddMMYYhhmmss"));
		dtoMap.put("relationship", "Colleague");
		dtoMap.put("phoneNumber", phoneNumber);
		dtoMap.put("isoCountryCode", "IN");
		dtoMap.put("address", "Pune India");
		response = testHelper.addEmergencyContact(dtoMap, passengerId);
		validateResponseToContinueTest(response, 200, "Unable to add EmergencyContact", true);
		expectedStatus = "FAILURE";
		actualStatus = String.valueOf(restUtils.getValueFromResponse(response, "status"));
		softAssert.assertEquals(actualStatus, expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'.", expectedStatus, actualStatus));

		expectedMessage = "Emergency contact with this phone number already exists.";
		actualMessage = String.valueOf(restUtils.getValueFromResponse(response, "message"));
		softAssert.assertEquals(actualMessage, expectedMessage,
				String.format("Expected message was '%s' and actual was '%s'.", expectedMessage, actualMessage));

		softAssert.assertAll();
		testHelper.deleteAllEmergencyContacts(passengerEmail);

		test.log(Status.PASS, "Validation for Max limit allowed for emergency contact was successful.");

	}
	/**
	 * This test will verify max number of emergency contacts to be added.
	 * @throws Exception
	 */
	@Test(priority = 9, dependsOnMethods = "verifyAddEmergencyContact")
	public void verifyMaxLimitForEmergencyContact() throws Exception {
		SoftAssert softAssert = new SoftAssert();
		EmergencyContactsTestHelper testHelper = new EmergencyContactsTestHelper();
		RestUtils restUtils = new RestUtils();
		// First delete all the emergency contacts if present
		testHelper.deleteAllEmergencyContacts(passengerEmail);

		// Add 3 emergency contacts
		for (int i = 0; i < 3; i++) {
			Map<Object, Object> dtoMap = new LinkedHashMap<Object, Object>();
			dtoMap.put("name", "Auto " + Utilities.getCurrentDateAndTime("ddMMYYhhmmss"));
			dtoMap.put("relationship", "Colleague");
			String phoneNumber = Utilities.generateRandomPhoneNumber();
			dtoMap.put("phoneNumber", phoneNumber);
			dtoMap.put("isoCountryCode", "IN");
			dtoMap.put("address", "Pune India");
			Response response = testHelper.addEmergencyContact(dtoMap, passengerId);
			validateResponseToContinueTest(response, 200, "Unable to add EmergencyContact", true);
			String expectedStatus = "SUCCESS";
			String actualStatus = String.valueOf(restUtils.getValueFromResponse(response, "status"));
			softAssert.assertEquals(actualStatus, expectedStatus,
					String.format("Expected status was '%s' and actual was '%s'.", expectedStatus, actualStatus));

			String expectedMessage = "Execution Successful";
			String actualMessage = String.valueOf(restUtils.getValueFromResponse(response, "message"));
			softAssert.assertEquals(actualMessage, expectedMessage,
					String.format("Expected message was '%s' and actual was '%s'.", expectedMessage, actualMessage));
		}

		// Verify for the 4th contact
		Map<Object, Object> dtoMap = new LinkedHashMap<Object, Object>();
		dtoMap.put("name", "Auto " + Utilities.getCurrentDateAndTime("ddMMYYhhmmss"));
		dtoMap.put("relationship", "Colleague");
		String phoneNumber = Utilities.generateRandomPhoneNumber();
		dtoMap.put("phoneNumber", phoneNumber);
		dtoMap.put("isoCountryCode", "IN");
		dtoMap.put("address", "Pune India");
		Response response = testHelper.addEmergencyContact(dtoMap, passengerId);
		validateResponseToContinueTest(response, 200, "Unable to add EmergencyContact", true);
		String expectedStatus = "FAILURE";
		String actualStatus = String.valueOf(restUtils.getValueFromResponse(response, "status"));
		softAssert.assertEquals(actualStatus, expectedStatus,
				String.format("Expected status was '%s' and actual was '%s'.", expectedStatus, actualStatus));

		String expectedMessage = "Max 3 emergency contacts can be added for a Glober.";
		String actualMessage = String.valueOf(restUtils.getValueFromResponse(response, "message"));
		softAssert.assertEquals(actualMessage, expectedMessage,
				String.format("Expected message was '%s' and actual was '%s'.", expectedMessage, actualMessage));

		softAssert.assertAll();
		testHelper.deleteAllEmergencyContacts(passengerEmail);

		test.log(Status.PASS, "Validation for Max limit allowed for emergency contact was successful.");

	}

}
