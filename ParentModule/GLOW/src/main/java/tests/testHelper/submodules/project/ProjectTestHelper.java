package tests.testHelper.submodules.project;

import org.testng.SkipException;

import com.aventstack.extentreports.Status;

import dto.submodules.project.Detail;
import dto.submodules.project.ProjectDTOList;
import endpoints.submodules.project.ProjectEndpoints;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tests.GlowBaseTest;
import tests.GlowBaseTestHelper;
import utils.RestUtils;

/**
 * @author german.massello
 *
 */
public class ProjectTestHelper extends GlowBaseTestHelper {

	private String userName;
	
	public ProjectTestHelper(String userName) throws Exception {
        new GlowBaseTest().createSession(userName);
        this.userName = userName;
	}
	
	/**
	 * This method will fetch all available projects.
	 * 
	 * @param url
	 * @return projectDTOList
	 * @throws Exception
	 * @author german.massello
	 */
	public ProjectDTOList getProjectsDTOList() throws Exception {
		Response response = getProjects();
		ProjectDTOList projectDTOList = response.as(ProjectDTOList.class, ObjectMapperType.GSON);
		if (projectDTOList.getDetails().size() == 0) throw new SkipException("There is not any project available");
		return projectDTOList;
	}
	
	/**
	 * This method will perform a GET in order to obtain all projects.
	 * 
	 * @param url
	 * @return response
	 * @throws Exception
	 * @author german.massello
	 */
	public Response getProjects() throws Exception {
		String url = GlowBaseTest.baseUrl + String.format(ProjectEndpoints.getProjectsFlags,"ON_GOING","true","glow"); 
		RequestSpecification requestSpecification = RestAssured.with().urlEncodingEnabled(false)
				.sessionId(GlowBaseTest.sessionId);
		Response response = new RestUtils().executeGET(requestSpecification, url);
		if (response.getStatusCode()!=200) throw new SkipException("The project services is not working. Actual status:"+response.getStatusCode());
		GlowBaseTest.test.log(Status.INFO, "GET - User: " + userName);
		GlowBaseTest.test.log(Status.INFO, "RequestURL: " + url);
		return response;
	}
	
	/**
	 * This method will fetch a random project.
	 * 
	 * @param url
	 * @return randomProject
	 * @throws Exception
	 * @author german.massello
	 */
	public Detail getRandomProject() throws Exception {
		ProjectDTOList projectDTOList = getProjectsDTOList();
		return projectDTOList.getDetails().get(0);
	}
	
	/**
	 * This method will return the selected Project.
	 * @param projectName
	 * @return selectedProject
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public Detail getSelectedProject(String projectName) throws Exception {
		Detail selectedProject = null;
		ProjectDTOList projectDTOList = getProjectsDTOList();
		for (Detail project : projectDTOList.getDetails()) {
			if (project.getProjectName().equals(projectName)) {
				selectedProject = project;
				return selectedProject;
			}
		}
		if (selectedProject==null) throw new SkipException("There is not any project with the name: " + projectName);
		return selectedProject;
	}
	
}
