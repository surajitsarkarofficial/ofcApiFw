package properties;

/**
 * @author josealberto.gomez
 *
 */
public class TravelNextPreProdProperties extends TravelNextProperties {

    public void setProperties()
    {
        database = "jdbc:mysql://nglb191uxu07.tat.corp.globant.com:3306/travel_nxt_dev";
        dbUser = "travel_nxt";
        dbPassword = "travel_nxt";
        baseURL = "https://backendportal-in-dev.corp.globant.com";
        loggedInUser = "carlos.iguaran";
        userEmail = "josealberto.gomez@globant.com";
        userEmailAlt = "ana.parra@globant.com";
    }
}
