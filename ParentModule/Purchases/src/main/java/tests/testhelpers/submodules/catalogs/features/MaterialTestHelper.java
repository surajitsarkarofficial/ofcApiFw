package tests.testhelpers.submodules.catalogs.features;

import com.aventstack.extentreports.Status;

import dto.submodules.catalogs.material.Entry;
import dto.submodules.catalogs.material.MaterialResponseDTO;
import endpoints.submodules.CatalogsEndpoints;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.PurchasesBaseTest;
import tests.testhelpers.submodules.catalogs.CatalogsTestHelper;
import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class MaterialTestHelper extends CatalogsTestHelper {

	public MaterialTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to fetch a materials list.
	 * @param groupName
	 * @param storeName
	 * @return response
	 * @author german.massello
	 * @throws Exception 
	 */
	public Response getMaterials(String groupName, String countryName) throws Exception {
		String groupId = new GroupTestHelper(userName).getSelectedGroup(groupName).getId();
		String storeName = new StoreTestHelper(userName).getRandomStore(countryName).getName();
		String society = new StoreTestHelper(userName).getSelectedStore(storeName, countryName).getSociety();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(PurchasesBaseTest.sessionId);
		String url = PurchasesBaseTest.baseUrl + String.format(CatalogsEndpoints.materials, "true", "description", groupId, society);
		Response response = restUtils.executeGET(requestSpecification, url);
		PurchasesBaseTest.test.log(Status.INFO, "GET - User: " + userName);
		PurchasesBaseTest.test.log(Status.INFO, "RequestURL: " + url);
		return response;
	}
	
	/**
	 * This method will return the MaterialResponseDTO list.
	 * @return materialResponseDTO
	 * @author german.massello
	 * @throws Exception 
	 */
	public MaterialResponseDTO getMaterialsDTO(String groupName, String countryName) throws Exception {
		Response response = getMaterials(groupName, countryName);
		MaterialResponseDTO materialResponseDTO = response.as(MaterialResponseDTO.class, ObjectMapperType.GSON);
		return materialResponseDTO;
	}
	
	/**
	 * This method will fetch a random material.
	 * 
	 * @return randomMaterial
	 * @throws Exception
	 */
	public Entry getRandomMaterial(String groupName, String countryName) throws Exception {
		MaterialResponseDTO materialResponseDTO = getMaterialsDTO(groupName, countryName);
		return materialResponseDTO.getContent().getEntries().get(Utilities.
				getRandomNumberBetween(0, materialResponseDTO.getContent().getEntries().size()));
	}
}
