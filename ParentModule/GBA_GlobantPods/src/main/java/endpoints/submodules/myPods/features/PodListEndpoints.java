package endpoints.submodules.myPods.features;

import endpoints.submodules.myPods.MyPodsEndpoints;

/**
 * @author ankita.manekar
 *
 */

/**
 *Endpoints for Glow and Firebase PodList api
 */
public class PodListEndpoints extends MyPodsEndpoints{

	public static String glowPodListUrl = "podfeedbackservice/podfeedback/clientcontact-details/";
	public static String glowAssesmentAreasUrl = "areaservice/assessment/areas?podId=";
	public static String glowPodConstitutionAreasUrl = "areaservice/areas?podId=";
	public static String firebasePodListUrl = "/pods";

}
