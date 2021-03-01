package tests.testHelpers.submodules.myGrowth.familyGroup.assignmentPodFamilyGroup;

import java.util.Arrays;
import java.util.List;

import database.GlowDBHelper;
import dto.assignmentsFamilyGroup.AssignmentPodResponse;
import endpoints.submodules.project.assignments.AssignmentPodEndpoint;
import io.restassured.response.Response;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.MyGrowthTestHelper;

/**
 * @author christian.chacon
 */
public class AssignmentPodTestHelper extends MyGrowthTestHelper {

    private String globerId;

    public AssignmentPodTestHelper(String userName) throws Exception {
        super(userName);
        this.globerId= new GlowDBHelper().getGloberId(userName);
    }

    /**
     * Get the response from the endpoint Search Pod Members
     * @param podId
     * @param projectId
     * @return Response
     * @author christian.chacon
     */
    public Response getResponsefromEndpointSearchPodMembers(String podId, String projectId){
        String requestUrl= MyGrowthBaseTest.baseUrl+String.format(AssignmentPodEndpoint.assignmentpodmemberurl, projectId, podId);
        return getMethod(requestUrl, globerId);
    }

    /**
     * Get the family group for a Glober from the response
     * @param response
     * @param username
     * @return String
     * @author christian.chacon
     */
    public String getFamilyGroup(Response response, String username){
        List<AssignmentPodResponse> getListGlobersAssignmentPod= Arrays.asList(response.getBody().as(AssignmentPodResponse[].class));
        AssignmentPodResponse assignmentPodResponse=new AssignmentPodResponse();
        for (AssignmentPodResponse assignmentPodResponse1:getListGlobersAssignmentPod){
            if(assignmentPodResponse1.getUsername().equals(username)){
                assignmentPodResponse=assignmentPodResponse1;
            }
        }
        return assignmentPodResponse.getFamilyGroup();
    }

}
