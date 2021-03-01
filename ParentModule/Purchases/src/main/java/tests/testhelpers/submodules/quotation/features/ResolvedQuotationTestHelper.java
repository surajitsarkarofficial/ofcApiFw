package tests.testhelpers.submodules.quotation.features;

import dto.submodules.quotation.post.QuotationResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.quotation.QuotationParameters;
import payloads.submodules.quotation.features.ResolveQuotationPayLoadHelper;
import tests.testhelpers.submodules.quotation.QuotationTestHelper;

/**
 * @author german.massello
 *
 */
public class ResolvedQuotationTestHelper extends QuotationTestHelper {

	public ResolvedQuotationTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will create and resolve a quotation.
	 * @param parameters
	 * @return resolvedQuotation
	 * @throws Exception
	 */
	public QuotationResponseDTO resolveQuotation(QuotationParameters parameters) throws Exception {
		QuotationResponseDTO assignedQuotation = new AssignQuotationTestHelper(parameters.getUserName()).assignQuotationFromScratch(parameters);
		ResolveQuotationPayLoadHelper payload = new ResolveQuotationPayLoadHelper().resolveQuotationPayLoad(assignedQuotation);
		Response response = new PutQuotationTestHelper(parameters.getPurchaseUserName()).putQuotation(payload);
		QuotationResponseDTO resolvedQuotation = response.as(QuotationResponseDTO.class, ObjectMapperType.GSON);
		return resolvedQuotation;
	}	
}
