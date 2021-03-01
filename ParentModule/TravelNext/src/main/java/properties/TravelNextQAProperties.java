package properties;

/**
 * @author josealberto.gomez
 *
 */
public class TravelNextQAProperties extends TravelNextProperties {

    public void setProperties()
    {
        database = "jdbc:postgresql://nglo742dxu08.tat.corp.globant.com:5432/travel_ui17";
        dbUser = "travel_user";
        dbPassword = "travel";
        baseURL = "https://backendportal-in-dev.corp.globant.com/BackendPortal-ui17";
        loggedInUser = "carlos.iguaran";
        userEmail = "josealberto.gomez@globant.com";
        userEmailAlt = "ana.parra@globant.com";
    }
}
