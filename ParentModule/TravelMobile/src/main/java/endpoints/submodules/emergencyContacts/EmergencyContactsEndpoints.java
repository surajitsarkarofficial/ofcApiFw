package endpoints.submodules.emergencyContacts;

import endpoints.TravelMobileEndpoints;

public class EmergencyContactsEndpoints extends TravelMobileEndpoints{
	
	public static String addEmergencyContactEndpoint = "/travel/orchestra/emergencyContact?passengerId=%s";
	public static String updateEmergencyContactEndpoint = "/travel/orchestra/emergencyContact/%s?passengerId=%s";
	public static String deleteEmergencyContactEndpoint = "/travel/orchestra/emergencyContact/%s";
	

}
