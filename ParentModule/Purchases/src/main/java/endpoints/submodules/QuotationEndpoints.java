package endpoints.submodules;

import endpoints.PurchasesEndpoints;

/**
 * @author german.massello
 *
 */
public class QuotationEndpoints extends PurchasesEndpoints {
	public static String postQuotation = "/proxy/glow/purchasesmanagerservice/quotations";
	public static String getQuotations = "/proxy/glow/purchasesmanagerservice/quotations?pageNum=%s&pageSize=%s&state=%s&status=%s&isGlobalView=%s";
	public static String getQuotationsDetails = "/proxy/glow/purchasesmanagerservice/quotations/%s";
	public static String putQuotation = "/proxy/glow/purchasesmanagerservice/quotations";
}
