package endpoints.submodules.manage.features;

import endpoints.submodules.manage.ManageEndpoints;

/**
 * @author german.massello
 *
 */
public class ConfigureAmountEndpoints extends ManageEndpoints {
	public static String getSafeMatrixRoles = "/proxy/glow/approverschainservice/safeMatrix/role";
	public static String getSafeMatrixRolDetail = "/proxy/glow/approverschainservice/safeMatrix/modalInfo?roleId=%s";
	public static String getPosition = "/proxy/glow/approverschainservice/safeMatrix/positionroleavailable?search=%s";
	public static String postPosition = "/proxy/glow/approverschainservice/safeMatrix";
	public static String deletePosition = "/proxy/glow/approverschainservice/safeMatrix/%s";
	public static String postRol = "/proxy/glow/approverschainservice/safeMatrix/role";
	public static String putRol = "/proxy/glow/approverschainservice/safeMatrix/role";
	public static String deleteRol = "/proxy/glow/approverschainservice/safeMatrix/role/%s";
}
