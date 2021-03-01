package endpoints.submodules.globers.features;

import endpoints.submodules.globers.GlobersEndpoints;

public class SoonTobeGlobersEndPoints  extends GlobersEndpoints {

	public static String filtersoonToBeGloberTalenPoolGrids = "/v1/globerfilters/types?viewId=%s&userId=%s&benchDateRange=%s,%s";

	public static String applySoonToBeGlobersTypeUrl = "/v1/globers?viewId=%d&userId=%s&parentViewId=%d&sorting=%s&type=%s,%s,%s&benchDateRange=%s,%s";

	public static String filterPositionsUrl = "/v1/globerfilters/positions?viewId=%d&userId=%s&type=%s,%s,%s&benchDateRange=%s,%s";

	public static String applyPositionFilterUrl = "/v1/globers?viewId=%d&userId=%s&parentViewId=%d&sorting=%s&type=%s,%s,%s&position=%s&benchDateRange=%s,%s";

	public static String locationFilterGlobantTalentPoolGrid = "/v1/globerfilters/locations?viewId=%d&userId=%s&parentViewId=%d&sorting=%s&type=%s,%s,%s&benchDateRange=%s,%s";

	public static String applyLocationFilterStgFilterUrl = "/v1/globers?viewId=%d&userId=%s&parentViewId=%d&sorting=%s&type=%s,%s,%s&location=%s&benchDateRange=%s,%s";

	public static String seniorityFilterGlobantTalentPoolGrid = "/v1/globerfilters/seniorities?viewId=%d&userId=%s&parentViewId=%d&sorting=%s&type=%s,%s,%s&benchDateRange=%s,%s";

	public static String applySenioriyFilterStgFilterUrl = "/v1/globers?viewId=%d&userId=%s&parentViewId=%d&sorting=%s&type=%s,%s,%s&seniority=%s&benchDateRange=%s,%s";

	public static String handlerFilterGlobantTalentPoolGrid = "/v1/globerfilters/handlers?viewId=%d&userId=%s&parentViewId=%d&sorting=%s&type=%s,%s,%s&benchDateRange=%s,%s";

	public static String applyHandlerFilterStgFilterUrl = "/v1/globers?viewId=%d&userId=%s&parentViewId=%d&sorting=%s&type=%s,%s,%s&handler=%s&benchDateRange=%s,%s";

	public static String leaderFilterGlobantTalentPoolGrid = "/v1/globerfilters/leaders?viewId=%d&userId=%s&parentViewId=%d&sorting=%s&type=%s,%s,%s&benchDateRange=%s,%s";

	public static String applyAvailabilityFilterGlobantTalentPoolGrid = "/v1/globers?viewId=%d&userId=%s&parentViewId=%d&sorting=%s&type=%s,%s,%s&availability=%d,%d&benchDateRange=%s,%s";

	public static String applyGloberStudioFilterGlobantTalentPoolGrid = "/v1/globers?viewId=%d&userId=%s&parentViewId=%d&sorting=%s&type=%s,%s,%s&studio=%s&benchDateRange=%s,%s";

	public static String globerStudioFilterGlobantTalentPoolGrid = "/v1/globerfilters/studios?viewId=%d&userId=%s&parentViewId=%d&sorting=%s&type=%s,%s,%s&benchDateRange=%s,%s";

	public static String skillsFilterGlobantTalentPoolGrid = "/v1/globerfilters/skills?viewId=%d&userId=%s&type=%s,%s,%s&benchDateRange=%s,%s";

	public static String statusFilterGlobantTalentPoolGrid = "/v1/globerfilters/statuses?viewId=%d&userId=%s&type=%s,%s,%s&benchDateRange=%s,%s";

	public static String applyStatusFilterGlobantTalentPoolGrid = "/v1/globers?viewId=%d&userId=%s&parentViewId=%s&sorting=%s&type=%s,%s,%s&statuses=%s&benchDateRange=%s,%s";

	public static String getCatsStatusSoonToBeGlobers = "/proxy/glow/globerservice/globers?viewId=%d&globerId=%s&type=%s";

	public static String getCatsLocationForSoonToBeGlober = "/proxy/glow/globerservice/globers?viewId=%d&globerId=%s&type=%s";

	public static String showRecruitmentFeedback = "/proxy/glow/interviewservice/technicalInterviews/result?interviewId=%s";

	public static String globerFeedbackApiUrl = "/proxy/glow/globerservice/globers/feedbacks";
	
	public static String getStgDetailsPageUrl = "/v1/globers?viewId=%s&globerId=%s&type=%s&userId=%s&isSTGFullNameRequired=%s";

	public static String searchGloberUrl = "/v1/globerfilters/globernames?viewId=%d&userId=%s&benchDateRange=%s,%s&key=%s&roles=%s";

	public static String searchGloberSuggestdirectlyUrl = "/v1/globerfilters/globernames?viewId=%d&userId=%s&type=%s&key=%s&offset=%d&limit=%d&roles=%s";

	public static String gateKeeperRecruiterDetailUrl = "/proxy/glow/interviewservice/technicalInterviews/technicalInterviewResult/candidate/%s?evaluationResult=%s";

	public static String getPartialNameWithTypeForStgUrl = "/v1/globers?viewId=%d&userId=%s&parentViewId=%d&offset=%d&sorting=%s&type=%s,%s,%s&benchDateRange=%s,%s";

	public static String getPartialNameForStgAllGloberPageUrl = "/v1/globers?viewId=%d&%s&parentViewId=%d&offset=%d&sorting=%s&type=%s,%s,%s";

	public static String getPartialNameForStgUrl = "/v1/globers?viewId=%d&userID=%s&parentViewId=%d&offset=%d&sorting=%s&benchDateRange=%s,%s";

	public static String getFullNameForStgAllGloberPageUrl = "/v1/globers?viewId=%d&globerId=%s&type=%s&userId=%s&isSTGFullNameRequired=%s";

	public static String globerUrl = "/proxy/glow/staffingorchestraservice/globers/handlers";

	public static String showSkillsForSoonToBeGloberUrl = "/proxy/glow/technicalinterviewservice/technicalInterviews/technicalInterviewResult/candidate/%s";

}
