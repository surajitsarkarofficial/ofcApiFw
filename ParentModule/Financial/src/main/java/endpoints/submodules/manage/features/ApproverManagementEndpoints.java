package endpoints.submodules.manage.features;

import endpoints.submodules.manage.ManageEndpoints;

/**
 * @author german.massello
 *
 */
public class ApproverManagementEndpoints extends ManageEndpoints {
	public static String getApprovers = "/proxy/glow/approverschainservice/approvers/%s?projectId=%s&amount=%s";
	public static String postApprover = "/proxy/glow/approverschainservice/approvers";
	public static String getAvailableApprovers = "/proxy/glow/approverschainservice/approvers/ceco?cecoNumber=%s&userName=%s&limit=%s";
	public static String getCecoDetails = "/proxy/glow/approverschainservice/approvers/safe/ceco?cecoNumber=%s";
	public static String getCecos = "/proxy/glow/approverschainservice/approvers/activeApproversCeco?pageSize=%s&pageNum=%s&sortAscending=%s&searchValue=%s";
	public static String deleteApprover = "/proxy/glow/approverschainservice/approvers?idApprover=%s&cecoNumber=%s";
	public static String postCeco = "/proxy/glow/approverschainservice/safeMatrix/ceco";
	public static String deleteCeco = "/proxy/glow/approverschainservice/safeMatrix/ceco/%s";
	public static String getProjectsByCeco = "/proxy/glow/approverschainservice/safeMatrix/projects?cecoNumber=%s";
	public static String putCecoRequiredAllLevels = "/proxy/glow/approverschainservice/safeMatrix/ceco/%s?requiredAllLevels=%s";
	public static String getActiveApproversAvailable = "/proxy/glow/approverschainservice/approvers/activeApproversAvailable?pageSize=%s&pageNum=%s&searchValue=%s";
	public static String getCecoByApprover = "/proxy/glow/approverschainservice/approvers/cecosAvailableApprover?pageSize=%s&pageNum=%s&globerId=%s";
	public static String putSwapApprover = "/proxy/glow/approverschainservice/approvers/swap";
	public static String postMassiveApproverCeco = "/proxy/glow/approverschainservice/approvers/actions/massiveApproverCeco";
	public static String deleteMassiveApproverCeco = "/proxy/glow/approverschainservice/approvers/actions/massiveDeleteApproverCeco";
}
