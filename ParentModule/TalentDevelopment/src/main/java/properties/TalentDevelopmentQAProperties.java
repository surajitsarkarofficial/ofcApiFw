package properties;

public class TalentDevelopmentQAProperties extends TalentDevelopmentProperties {

    public void setProperties()
    {
        database="jdbc:postgresql://glowdbqa.dev.corp.globant.com:5432/scp_20190929";
        dbUser="microservices";
        dbPassword="microservices";
        baseURL="https://backendportal-dev.corp.globant.com/BackendPortal-ui17";
    }
}
