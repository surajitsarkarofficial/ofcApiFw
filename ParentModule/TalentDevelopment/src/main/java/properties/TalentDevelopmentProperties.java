package properties;

public class TalentDevelopmentProperties extends GlowProperties {

    public static String dataProviderPath = "../TalentDevelopment/src/main/resources/dataproviders/";
    public String jsonSchemaPath = "../TalentDevelopment/src/main/resources/jsonSchema/";

    public void configureProperties() throws Exception {

        switch (executionEnvironment.toUpperCase()) {

            case "QA":
                new TalentDevelopmentQAProperties().setProperties();
                break;
            case "UAT":
                new TalentDevelopmentUATProperties().setProperties();
                break;
            case "PREPROD":
                new TalentDevelopmentPreProdProperties().setProperties();
                break;
            default:
                throw new Exception(executionEnvironment+" Environment is not valid for this POD.");

        }
    }
}
