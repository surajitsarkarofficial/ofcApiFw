package endpoints.submodules.manage.features;

import endpoints.submodules.manage.ManageEndpoints;

/**
 * @author german.massello
 *
 */
public class CrossApproversEndpoints extends ManageEndpoints {
	public static String getAvailableCrossApprovers = "/proxy/glow/approverschainservice/crossapprovers/available?limit=%s&userName=%s";
	public static String postCrossApprover = "/proxy/glow/approverschainservice/crossapprovers";
	public static String getCrossApprovers = "/proxy/glow/approverschainservice/crossapprovers?pageSize=%s&pageNum=%s";
	public static String deleteCrossApprover = "/proxy/glow/approverschainservice/crossapprovers/%s";
}
