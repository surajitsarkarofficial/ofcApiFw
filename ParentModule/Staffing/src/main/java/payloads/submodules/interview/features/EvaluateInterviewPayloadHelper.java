package payloads.submodules.interview.features;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.Utilities;

/**
 * 
 * @author deepakkumar.hadiya
 *
 */
public class EvaluateInterviewPayloadHelper {

	/**
	 * This method is used to create json body for evaluating interview request
	 * 
	 * @param interviewId
	 * @return {@link String}
	 * @throws SQLException
	 */
	public String evaluateInterviewPayload(String interviewId) throws SQLException {
		Map<Object, Object> dataMap = new HashMap<>();
		dataMap.put("interviewId", interviewId);
		dataMap.put("status", 3030);
		dataMap.put("yearsOfExperience", 5429899);
		dataMap.put("yearsOfExperienceComments", null);
		dataMap.put("strengthSkillsComments", null);
		dataMap.put("weakSkillsComments", null);
		dataMap.put("technicalEnglishLevel", 65);
		dataMap.put("technicalEnglishLevelComments", null);
		dataMap.put("evaluationResult", true);
		dataMap.put("fitPosition", true);
		dataMap.put("fitCompanyValue", true);
		dataMap.put("suggestedStudioId", 101886290);
		dataMap.put("evaluatedSeniority", 6);
		dataMap.put("evaluationResultComments", null);
		dataMap.put("interviewLength", 5429907);
		dataMap.put("interviewLengthComments", null);
		dataMap.put("overallComments", null);
		dataMap.put("otherPositionSuggested", null);
		dataMap.put("lastEditedField", null);
		Map<Object, Object> skill1 = new HashMap<Object, Object>();
		skill1.put("id", 2682);
		skill1.put("name", "reactjs");
		skill1.put("weight", 0);
		Map<Object, Object> skill2 = new HashMap<Object, Object>();
		skill2.put("id", 4015);
		skill2.put("name", "focused");
		skill2.put("weight", 100);
		skill2.put("area", "generic");
		List<Map<Object, Object>> skills = new ArrayList<>();
		skills.add(skill1);
		skills.add(skill2);
		dataMap.put("skills", skills);
		String json = Utilities.createJsonBodyFromMap(dataMap);
		return json;

	}

}
