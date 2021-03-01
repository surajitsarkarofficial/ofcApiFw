package endpoints.submodules.myPods.features;

import endpoints.submodules.myPods.MyPodsEndpoints;

/**
 * @author ankita.manekar
 *
 */
/**
 *Endpoints for Glow and Firebase PodMember api
 */
public class PodMemberEndpoints extends MyPodsEndpoints{

	public static String glowPodMembersUrl = "podservice/pods/members/v1?podId=%d";
	public static String firebasePodMembersUrl = "/pods/%d/members?member=true";
		
}
