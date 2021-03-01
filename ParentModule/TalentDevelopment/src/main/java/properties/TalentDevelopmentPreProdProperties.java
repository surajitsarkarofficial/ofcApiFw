package properties;

public class TalentDevelopmentPreProdProperties extends TalentDevelopmentProperties {

        public void setProperties()
        {
            database="jdbc:postgresql://ngl0038dxu22.sfo.corp.globant.com:5432/glowpreprod";
            dbUser="microservices";
            dbPassword="microservices";
            baseURL="https://backendportal-preprod.corp.globant.com/BackendPortal";
        }

}
