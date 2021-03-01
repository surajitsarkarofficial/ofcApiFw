package properties;

public class TravelMobileQAProperties extends TravelMobileProperties{
	public  void setProperties()
	{
		/*database="jdbc:mysql://nglb191dxu01.tat.corp.globant.com:3306/travel_portal";
		dbUser="travel_portal";
		dbPassword="travel_portal";*/
		database="jdbc:postgresql://nglo742dxu08.tat.corp.globant.com:5432/travel_mob_qa";
		dbUser="travel_user";
		dbPassword="travel";
		baseURL="https://nglb191qou00.dev.globant.com";
		loggedInUser="surajit.sarkar";
	}
}
