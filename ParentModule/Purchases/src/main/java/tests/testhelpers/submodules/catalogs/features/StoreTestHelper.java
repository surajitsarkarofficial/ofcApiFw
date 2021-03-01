package tests.testhelpers.submodules.catalogs.features;

import org.testng.SkipException;

import com.aventstack.extentreports.Status;

import dto.submodules.catalogs.store.Entry;
import dto.submodules.catalogs.store.StoreResponseDTO;
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
public class StoreTestHelper extends CatalogsTestHelper {

	public StoreTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to fetch all stores.
	 * @return response
	 * @author german.massello
	 * @throws Exception 
	 */
	public Response getStores(String countryName) throws Exception {
		String countryId = new CountryTestHelper(userName).getSelectedCountry(countryName).getId();
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(PurchasesBaseTest.sessionId);
		String url = PurchasesBaseTest.baseUrl + String.format(CatalogsEndpoints.stores, countryId, "true", "name");
		Response response = restUtils.executeGET(requestSpecification, url);
		PurchasesBaseTest.test.log(Status.INFO, "GET - User: " + userName);
		PurchasesBaseTest.test.log(Status.INFO, "RequestURL: " + url);
		return response;
	}

	/**
	 * This method will return the StoreResponseDTO list.
	 * @return storeResponseDTO
	 * @author german.massello
	 * @throws Exception 
	 */
	private StoreResponseDTO getStoreDTOList(String countryName) throws Exception {
		Response response = getStores(countryName);
		StoreResponseDTO storeResponseDTO = response.as(StoreResponseDTO.class, ObjectMapperType.GSON);
		return storeResponseDTO;
	}
	
	/**
	 * This method will return the selected store.
	 * @param storeName
	 * @return selectedStore
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public Entry getSelectedStore(String storeName, String countryName) throws Exception {
		Entry selectedStore = null;
		StoreResponseDTO storeResponseDTO = getStoreDTOList(countryName);
		for (Entry store : storeResponseDTO.getContent().getEntries()) {
			if (store.getName().equals(storeName)) {
				selectedStore = store;
				return selectedStore;
			}
		}
		if (selectedStore==null) throw new SkipException("There is not any store with the name: " + storeName);
		return selectedStore;
	}
	
	/**
	 * This method will fetch a random store.
	 * 
	 * @return randomStore
	 * @throws Exception
	 */
	public Entry getRandomStore(String countryName) throws Exception {
		StoreResponseDTO storeResponseDTO = getStoreDTOList(countryName);
		return storeResponseDTO.getContent().getEntries().get(Utilities.getRandomNumberBetween(0, storeResponseDTO.getContent().getEntries().size()));
	}
}
