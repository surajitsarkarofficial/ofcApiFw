package endpoints.submodules.myPods.features;

import endpoints.submodules.myPods.MyPodsEndpoints;

/**
 * @author ankita.manekar
 *
 */
/**
 *Endpoints for Glow and Firebase PodConstitution api
 */
public class PodConstitutionEndpoints extends MyPodsEndpoints{

	public static String glowPodConstitutionAreasUrl = "areaservice/areas?podId=";
	public static String glowPodRolesUrl = "podservice/pods/roles?podId=%d";
	public static String glowPodValuesUrl = "podservice/pods/%d/principlevalue-purpose";
	public static String firebasePodConstitutiontUrl = "/pods/%d/members?constitution=true";

}
