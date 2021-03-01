package tests.testhelpers.submodules.manage.features.approverManagement;

import org.testng.SkipException;

import dto.submodules.manage.approverManagement.getAvailableApprovers.AvailableApprover;
import dto.submodules.manage.approverManagement.getAvailableApprovers.GetAvailableApproversResponseDTO;
import endpoints.submodules.manage.features.ApproverManagementEndpoints;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import parameters.submodules.manage.features.approverManagement.GetAvailableApproversParameters;
import tests.testcases.submodules.manage.ManageBaseTest;
import tests.testhelpers.submodules.manage.ManageTestHelper;

/**
 * @author german.massello
 *
 */
public class GetAvailableApproversTestHelper extends ManageTestHelper {

	public GetAvailableApproversTestHelper(String userName) throws Exception {
		super(userName);
	}

	/**
	 * This method will perform a GET in order to fetch available approvers.
	 * @param parameters
	 * @return Response
	 * @throws Exception 
	 */
	public Response getAvailableApprovers(GetAvailableApproversParameters parameters) throws Exception {
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false).sessionId(ManageBaseTest.sessionId);
		String url = ManageBaseTest.baseUrl + String.format(ApproverManagementEndpoints.getAvailableApprovers, parameters.getCecoNumber(), parameters.getUserCriteria(),
				parameters.getLimit());
		Response response = restUtils.executeGET(requestSpecification, url);
		validateResponseToContinueTest(response, 200, "Get available approvers api call was not successful.", true);
		return response;
	}

	
	/**
	 * This method will return the GetAvailableApproversResponseDTO  
	 * @param parameters
	 * @return GetAvailableApproversResponseDTO
	 * @throws Exception 
	 */
	public GetAvailableApproversResponseDTO getAvailableApproversResponseDTO(GetAvailableApproversParameters parameters) throws Exception {
		Response response = getAvailableApprovers(parameters);
		return response.as(GetAvailableApproversResponseDTO.class, ObjectMapperType.GSON);
	}
	
   /**
    * This method will return a random available approver.
    * @param cecoNumber
    * @return AvailableApprover
    * @throws Exception 
    */
   public AvailableApprover getAvailableApprover(String cecoNumber) throws Exception {
	   GetAvailableApproversResponseDTO approvers = getAvailableApproversResponseDTO(new GetAvailableApproversParameters(cecoNumber));
	   AvailableApprover availableApprover = null;
	   for (AvailableApprover approver : approvers.getContent().getAvailableApprovers()) {
		   if (!approver.getMaxAmount().equals("0")) availableApprover=approver;
	   }
	   if (availableApprover==null) throw new SkipException("not approver available");
	   return availableApprover;
   }

	/**
	* This method will return a available approver without assigned rol.
	* @param cecoNumber
	* @return AvailableApprover
	* @throws Exception 
	*/
	public AvailableApprover getAvailableApproverWithoutAssignedRol(String cecoNumber) throws Exception {
		GetAvailableApproversResponseDTO approvers = getAvailableApproversResponseDTO(new GetAvailableApproversParameters(cecoNumber));
		for (AvailableApprover availableApprover:approvers.getContent().getAvailableApprovers()) if (availableApprover.getRole().equals("Non Role Assigned")) return availableApprover;
		return new AvailableApprover();
	}
	
	   /**
	    * This method will return a random available approver for all cecos.
	    * @return AvailableApprover
	    * @throws Exception 
	    */
	   public AvailableApprover getAvailableApproverForAllCecos() throws Exception {
		   GetAvailableApproversParameters parameter = new GetAvailableApproversParameters();
		   parameter.setCecoNumber("");
		   GetAvailableApproversResponseDTO approvers = getAvailableApproversResponseDTO(parameter);
		   AvailableApprover availableApprover = null;
		   for (AvailableApprover approver : approvers.getContent().getAvailableApprovers()) {
			   if (!approver.getMaxAmount().equals("0")) availableApprover=approver;
		   }
		   if (availableApprover==null) throw new SkipException("not approver available");
		   return availableApprover;
	   }
 
}
