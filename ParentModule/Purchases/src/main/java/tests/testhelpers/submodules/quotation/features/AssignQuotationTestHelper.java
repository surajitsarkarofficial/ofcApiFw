package tests.testhelpers.submodules.quotation.features;

import dto.submodules.quotation.post.QuotationResponseDTO;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import parameters.submodules.quotation.QuotationParameters;
import payloads.submodules.quotation.features.AssignQuotationPayLoadHelper;
import tests.testhelpers.submodules.quotation.QuotationTestHelper;

/**
 * @author german.massello
 *
 */
public class AssignQuotationTestHelper extends QuotationTestHelper {

	public AssignQuotationTestHelper(String userName) throws Exception {
		super(userName);
	}
	
	/**
	 * This method will assign a quotation from scratch.
	 * @param parameters
	 * @return assignedQuotation
	 * @throws Exception
	 * @author german.massello
	 */
	public QuotationResponseDTO assignQuotationFromScratch (QuotationParameters parameters) throws Exception { 
		QuotationResponseDTO quotation = new CreateQuotationTestHelper(parameters.getUserName()).createQuotationFromScratch(parameters);
		AssignQuotationPayLoadHelper payload = new AssignQuotationPayLoadHelper().assignQuotationPayLoad(quotation);
		Response response = new PutQuotationTestHelper(parameters.getPurchaseUserName()).putQuotation(payload);
		QuotationResponseDTO assignedQuotation = response.as(QuotationResponseDTO.class, ObjectMapperType.GSON);
		return assignedQuotation;
	}
	
}
