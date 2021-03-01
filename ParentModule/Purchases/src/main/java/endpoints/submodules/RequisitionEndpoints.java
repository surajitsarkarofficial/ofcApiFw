package endpoints.submodules;

import endpoints.PurchasesEndpoints;

/**
 * @author german.massello
 *
 */
public class RequisitionEndpoints extends PurchasesEndpoints {
	public static String getRequisitions = "/proxy/glow/purchasesmanagerservice/requisitions?pageNum=%s&pageSize=%s&isPurchaserView=%s"
			+ "&status=%s&state=%s&isApprovalView=%s&sapApprovalView=%s&isGlobalView=%s&groupId=%s&requesterId=%s";
	public static String postRequisitions = "/proxy/glow/purchasesmanagerservice/requisitions";
	public static String sendToApprovalRequisitions = "/proxy/glow/purchasesmanagerservice/requisitions/%s/approval";
}
