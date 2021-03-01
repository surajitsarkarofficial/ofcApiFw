package tests.testhelpers.submodules.catalogs.features;

import org.testng.SkipException;

import com.aventstack.extentreports.Status;

import dto.submodules.catalogs.country.CountryResponseDTO;
import dto.submodules.catalogs.country.Entry;
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
public class CountryTestHelper extends CatalogsTestHelper {

	public CountryTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to fetch all countries.
	 * @return response
	 * @author german.massello
	 */
	public Response getCountry() {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(PurchasesBaseTest.sessionId);
		String url = PurchasesBaseTest.baseUrl + String.format(CatalogsEndpoints.countries, "true", "description");
		Response response = restUtils.executeGET(requestSpecification, url);
		PurchasesBaseTest.test.log(Status.INFO, "GET - User: " + userName);
		PurchasesBaseTest.test.log(Status.INFO, "RequestURL: " + url);
		return response;
	}
	
	/**
	 * This method will return the CountryResponseDTO list.
	 * @return countryResponseDTO
	 * @author german.massello
	 */
	public CountryResponseDTO getCountryDTOList() {
		Response response = getCountry();
		CountryResponseDTO countryResponseDTO = response.as(CountryResponseDTO.class, ObjectMapperType.GSON);
		return countryResponseDTO;
	}

	/**
	 * This method will return the selected Country.
	 * @param countryName
	 * @return selectedCountry
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public Entry getSelectedCountry(String countryName) throws Exception {
		Entry selectedCountry = null;
		CountryResponseDTO countryResponseDTO = getCountryDTOList();
		for (Entry country : countryResponseDTO.getContent().getEntries()) {
			if (country.getDescription().equals(countryName)) {
				selectedCountry = country;
				return selectedCountry;
			}
		}
		if (selectedCountry==null) throw new SkipException("There is not any country with the name: " + countryName);
		return selectedCountry;
	}
}
