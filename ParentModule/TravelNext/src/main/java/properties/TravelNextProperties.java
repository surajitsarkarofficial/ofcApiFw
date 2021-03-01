package properties;

/**
 * @author josealberto.gomez
 *
 */
public class TravelNextProperties extends GlowProperties {

    public static String dataProviderPath = "../TravelNext/src/main/resources/dataproviders";
    public static String schemasPath = "Schemas/";
    public static String loggedInUser;
    public static String userEmail;
    public static String userEmailAlt;

    public void configureProperties() throws Exception {

        switch (executionEnvironment.toUpperCase()) {

            case "QA":
                new TravelNextQAProperties().setProperties();
                break;

            case "DEV":
                new TravelNextDevProperties().setProperties();
                break;

            case "PREPROD":
                new TravelNextPreProdProperties().setProperties();
                break;

            default:
                throw new Exception(executionEnvironment + " Environment is not valid for this POD.");
        }

    }

}
