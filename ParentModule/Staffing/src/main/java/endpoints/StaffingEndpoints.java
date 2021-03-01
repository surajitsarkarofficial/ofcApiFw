package endpoints;

public class StaffingEndpoints extends GlowEndpoints{
	
	public static String getAlberthaSkills = "/proxy/glow/skillservice/skills/average?role=%s&seniority=%s";

	public static String suggestGloberUrl = "/proxy/glow/staffingorchestraservice/staffplans";

	public static String globerUrl = "/proxy/glow/staffingorchestraservice/assignments";

}
