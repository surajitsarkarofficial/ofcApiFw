package endpoints.submodules;

import endpoints.PurchasesEndpoints;

/**
 * @author german.massello
 *
 */
public class ReceptionEndpoints extends PurchasesEndpoints {
	public static String getPurchaseOrders = "/proxy/glow/purchasesmanagerservice/requisition/item?status=%s&isGlobalView=%s&pageSize=%s&pageNum=%s";
	public static String postReception = "/proxy/glow/purchasesmanagerservice/receptions";
	public static String putReception = "/proxy/glow/purchasesmanagerservice/receptions";
	public static String getReception = "/proxy/glow/purchasesmanagerservice/receptions?pageNum=%s&pageSize=%s&status=%s&isGlobalView=%s&purchasesView=%s";
	public static String getReceptionGlobalView = "/proxy/glow/purchasesmanagerservice/receptions?pageNum=%s&pageSize=%s&isGlobalView=%s&purchasesView=%s";
}
