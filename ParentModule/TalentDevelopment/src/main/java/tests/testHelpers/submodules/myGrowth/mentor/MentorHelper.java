package tests.testHelpers.submodules.myGrowth.mentor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.aventstack.extentreports.Status;

import database.submodules.myGrowth.MyGrowthDBHelper;
import dto.submodules.myGrowth.features.workingEcosystem.workingEcosystemSkills.SubjectDto;
import dto.submodules.myGrowth.features.workingEcosystem.workingEcosystemSkills.WorkingEcosystemSkillsDto;
import endpoints.submodules.myGrowth.features.bmeIntegration.BetterMeIntegrationEndpoints;
import endpoints.submodules.myGrowth.features.mentor.MentorEndpoints;
import endpoints.submodules.myGrowth.features.missions.MissionEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.submodules.myGrowth.BmeIntegration.PostRequestFeedbackMission;
import payloads.submodules.myGrowth.missions.UpdateMissionGloberPayload;
import payloads.submodules.myGrowth.missions.CreateMentoringMissionPayload.CreateMentoringMissionPayload;
import payloads.submodules.myGrowth.missions.EditMentoringMissionsPayload.EditMentorMissionPayload;
import payloads.submodules.myGrowth.missions.EditMentoringMissionsPayload.Subject;
import tests.testCases.submodules.myGrowth.MyGrowthBaseTest;
import tests.testHelpers.submodules.myGrowth.MyGrowthTestHelper;
import tests.testHelpers.submodules.myGrowth.missions.MissionsTestHelper;
import utils.RestUtils;
import utils.Utilities;

public class MentorHelper extends MyGrowthTestHelper {
    /**
     * @author nadia.silva
     */
    public MentorHelper(String userName) throws Exception {
        super(userName);
    }
    /**
     * This method will perform a POST for the mentoring mission service
     * @param createMentoringMissionPayload
     * @param userName
     * @return
     * @throws Exception
     */
    public Response createMentoringMission(CreateMentoringMissionPayload createMentoringMissionPayload, String userName) throws Exception {
        RestUtils restUtils = new RestUtils();
        String requestURL = MyGrowthBaseTest.baseUrl + MissionEndpoints.mentoringMissionUrl;
        RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
                .sessionId(MyGrowthBaseTest.sessionId).contentType(ContentType.JSON).body(createMentoringMissionPayload);
        Response response = restUtils.executePOST(requestSpecification, requestURL);

        MyGrowthBaseTest.test.log(Status.INFO, "User: " + userName);
        MyGrowthBaseTest.test.log(Status.INFO, "RequestURL: " + requestURL);
        MyGrowthBaseTest.test.log(Status.INFO, "Payload: " + Utilities.convertJavaObjectToJson(createMentoringMissionPayload));
        MyGrowthBaseTest.test.log(Status.INFO, "Creation of mentoring mission was created successfully.");
        return response;
    }
    /**
     * This method will perform a PUT for service missions/mission/glober
     * @param updateMissionGloberPayload
     * @return Response
     * @author christian.chacon
     */
    public Response updateMentoringMission(UpdateMissionGloberPayload updateMissionGloberPayload){
        RestUtils restUtils = new RestUtils();
        String requestURL = MyGrowthBaseTest.baseUrl + MissionEndpoints.updateStatusMision;
        RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
                .sessionId(MyGrowthBaseTest.sessionId).contentType(ContentType.JSON).body(updateMissionGloberPayload);
        Response response = restUtils.executePUT(requestSpecification, requestURL);
        return response;
    }

    /**
     * This method will perform a PUT for service /mentor/mission
     * @param editMentorMissionPayload
     * @return Response
     */
    public Response editMentoringMission(EditMentorMissionPayload editMentorMissionPayload){
        RestUtils restUtils = new RestUtils();
        String requestUrl = MyGrowthBaseTest.baseUrl + MissionEndpoints.editMentorinMissionsUrl;
        RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
                .sessionId(MyGrowthBaseTest.sessionId).contentType(ContentType.JSON).body(editMentorMissionPayload);
        Response response = restUtils.executePUT(requestSpecification, requestUrl);
        return response;
    }

    /**
     * This method will create a feedback request to the mentor after a mentee closes mentoring mission
     * @param postRequestFeedbackMission
     * @return Response
     * @author christian.chacon
     */
    public Response postSendFeedbackRequest(PostRequestFeedbackMission postRequestFeedbackMission){
        RestUtils restUtils = new RestUtils();
        String requestURL = MyGrowthBaseTest.baseUrl + BetterMeIntegrationEndpoints.sendFeedbackRequestURL;
        RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
                .sessionId(MyGrowthBaseTest.sessionId).contentType(ContentType.JSON).body(postRequestFeedbackMission);
        Response response = restUtils.executePOST(requestSpecification, requestURL);
        return response;
    }
    /**
     * This method will return the response and whether he is a mentor or/and has a high role assigned
     * @param globerId
     * @return Response
     * @author christian.chacon
     */
    public Response getIsMentor(String globerId){
        String requestUrl= MyGrowthBaseTest.baseUrl+String.format(MentorEndpoints.isMentorUrl, globerId);
        return getMethod(requestUrl, globerId);
    }

    /**
     * Obtain the list of subjects to edit in the mentoring mission
     * @param username
     * @param globerIdMentee
     * @return List<Subject>
     * @throws Exception
     */
    public List<Subject> getListofSubjects(String username, String globerIdMentee) throws Exception {
        Random random=new Random();
        List<Subject> subjectstoPut=new ArrayList<Subject>();
        MyGrowthDBHelper myGrowthDBHelper=new MyGrowthDBHelper();
        MissionsTestHelper missionsTestHelper=new MissionsTestHelper(username);
        Response response=missionsTestHelper.getSkillsbyWorkingEcosystemandGloberid(myGrowthDBHelper.getPrimaryWorkingEcosystemOfGlober(globerIdMentee), globerIdMentee);
        WorkingEcosystemSkillsDto workingEcosystemSkillsDto=response.as(WorkingEcosystemSkillsDto.class, ObjectMapperType.GSON);
        int maxSkills=random.nextInt(6)+1;
        for (int i = 0; i < maxSkills; i++) {
            SubjectDto subjectDto=workingEcosystemSkillsDto.getDetails().getSubjects().get(random.nextInt(31));
            Subject subject=new Subject();
            subject.setBlocked(true);
            subject.setId(subjectDto.getId());
            subject.setName(subjectDto.getName());
            subjectstoPut.add(subject);
        }
        return subjectstoPut;
    }



}
