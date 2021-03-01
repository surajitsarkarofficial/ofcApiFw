package endpoints.submodules.staffRequest.features;

import endpoints.submodules.staffRequest.StaffRequestEndpoints;

public class GlobalStaffRequestAPIEndPoints extends StaffRequestEndpoints {
		
	public static String checkEditButton = "/proxy/glow/sowservice/sows?projectId=%s";
	
	public static String historyPosition = "/proxy/glow/positionservice/positions/history?rotatedPosition=%s&positionId=%s";
	
	public static String suggestAssignmnet = "/proxy/glow/staffingorchestraservice/positions/suggest-assignments"; 
	
	public static String bookingAssignment = "/proxy/glow/staffingorchestraservice/staffplans/bookings";
	
	
}