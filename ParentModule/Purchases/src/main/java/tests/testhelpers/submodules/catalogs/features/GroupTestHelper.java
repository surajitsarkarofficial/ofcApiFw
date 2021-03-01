package tests.testhelpers.submodules.catalogs.features;

import org.testng.SkipException;

import com.aventstack.extentreports.Status;

import dto.submodules.catalogs.group.Entry;
import dto.submodules.catalogs.group.GroupResponseDTO;
import endpoints.submodules.CatalogsEndpoints;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.PurchasesBaseTest;
import tests.testhelpers.submodules.catalogs.CatalogsTestHelper;

/**
 * @author german.massello
 *
 */
public class GroupTestHelper extends CatalogsTestHelper {

	public GroupTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to fetch all groups.
	 * @return response
	 * @author german.massello
	 */
	public Response getGroups() {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(PurchasesBaseTest.sessionId);
		String url = PurchasesBaseTest.baseUrl + String.format(CatalogsEndpoints.groups, "true", "description", "Good");
		Response response = restUtils.executeGET(requestSpecification, url);
		PurchasesBaseTest.test.log(Status.INFO, "GET - User: " + userName);
		PurchasesBaseTest.test.log(Status.INFO, "RequestURL: " + url);
		return response;
	}

	/**
	 * This method will return the GroupResponseDTO list.
	 * @return groupResponseDTO
	 * @author german.massello
	 * @throws Exception 
	 */
	public GroupResponseDTO getGroupDTOList() throws Exception {
		Response response = getGroups();
		GroupResponseDTO groupResponseDTO = response.as(GroupResponseDTO.class, ObjectMapperType.GSON);
		return groupResponseDTO;
	}
	
	/**
	 * This method will return the selected Group.
	 * @param groupName
	 * @return selectedGroup
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public Entry getSelectedGroup(String groupName) throws Exception {
		Entry selectedGroup = null;
		GroupResponseDTO groupResponseDTO = getGroupDTOList();
		if (groupResponseDTO==null) throw new SkipException("There is not any group available");
		for (Entry group : groupResponseDTO.getContent().getEntries()) {
			if (group.getDescription().equals(groupName)) {
				selectedGroup = group;
				return selectedGroup;
			}
		}
		if (selectedGroup==null) throw new SkipException("There is not any group with the name: " + groupName);
		return selectedGroup;
	}
	
}
