package tests.testhelpers.submodules.project;

import endpoints.submodules.project.ProjectEndpoints;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.testcases.TravelMobileBaseTest;
import tests.testhelpers.TravelMobileBaseHelper;
import utils.RestUtils;

/**
 * 
 * @author surajit.sarkar
 *
 */
public class TNProjectTestHelper extends TravelMobileBaseHelper{
	
	/**
	 * This will return he response of get project search api
	 * @param query
	 * @return Response
	 */
	public Response getProjectSearch(String query)
	{
		String endpointURL = TravelMobileBaseTest.baseUrl+
				String.format(ProjectEndpoints.projectSearchEndpoint,query);
		RequestSpecification requestSpec = RestAssured.with().header("token",TravelMobileBaseTest.token).
				header("content-type","application/json");		
		Response response =new RestUtils().executeGET(requestSpec, endpointURL);
		return response;		
	}
	
	/**
	 * This method will check if the query string is matching the project name or the client name
	 * @param projectName
	 * @param clientName
	 * @param query
	 * @return boolean
	 */
	public boolean isQueryMatchingProjectOrClientNameFound(String projectName, String clientName,String query)
	{
		String[] queryString = query.split(" ");
		
		//verify if the query string matches either the project name or the client name
		int prevIndex =0;
		boolean isMatched = true;
		for(String str : queryString)
		{
			int currIndex= projectName.indexOf(str, prevIndex);
			if(!(currIndex>=prevIndex))
			{
				isMatched = false;
				break;
			}
			prevIndex=currIndex;
			
		}
		if(!isMatched)//if the query string did not match the project name then verify for client name
		{
			isMatched=true;
			prevIndex=0;
			for(String str : queryString)
			{
				int currIndex= projectName.indexOf(str, prevIndex);
				if(!(currIndex>=prevIndex))
				{
					isMatched = false;
					break;
				}
				prevIndex=currIndex;
				
			}
		}
		
		return isMatched;
	}

}
