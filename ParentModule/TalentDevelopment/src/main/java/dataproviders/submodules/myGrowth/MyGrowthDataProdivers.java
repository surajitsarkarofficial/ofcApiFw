package dataproviders.submodules.myGrowth;

import java.sql.SQLException;
import java.util.Map;

import database.GlowDBHelper;
import org.testng.annotations.DataProvider;

import database.GloberDBHelper;
import database.submodules.myGrowth.FamilyGroupDBHelper;
import database.submodules.myGrowth.MyGrowthDBHelper;
import dataproviders.TalentDevelopmentDataProviders;
import properties.myGrowth.MyGrowthProperties;

/**
 * @author nadia.silva
 */
public class MyGrowthDataProdivers extends TalentDevelopmentDataProviders implements GloberDBHelper {
    MyGrowthDBHelper myGrowthDBHelper = new MyGrowthDBHelper();

    /**
     * This data provider will return users without Talent Model
     *
     * @return Object[][]
     * @throws Exception
     */
    @DataProvider(name = "usersWithoutTalentModel")
    public Object[][] usersWithoutTalentModel() throws Exception {
        String xmlInputStream = new MyGrowthProperties().dataProviderPathMG + "usersWithoutTalentModel.xml";
        String xpathExpression = "/dataObjects/dataObject";
        return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
    }
    /**
     * This data provider will return users with Closed Mission and this user is in Better me
     * @return Object[][]
     * @throws Exception
     * @author christian.chacon
     */
    @DataProvider(name="usersWithClosedMission")
    public Object[][] usersWithClosedMissionAndExistInBME() throws Exception{
        Object[][] data = null;
        String xmlInputStream = new MyGrowthProperties().dataProviderPathMG + "userInBMEWithClosedMission.xml";
        String xpathExpression = "/dataObjects/dataObject";
        data=buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
        return data;
    }

    /**
     * This data provider will return a glober with leader assigned
     * @return Object[][]
     * @throws Exception
     * @author christian.chacon
     */
    @DataProvider(name = "usersWithLeaderInBME")
    public Object[][] usersWithLeaderInBME() throws Exception{
        Object[][] data = null;
        String xmlInputStream = new MyGrowthProperties().dataProviderPathMG + "userInBMEWithLeader.xml";
        String xpathExpression = "/dataObjects/dataObject";
        data=buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
        return data;
    }

    /**
     * This data provider will return a glober without leader assigned
     * @return Object[][]
     * @throws Exception
     * @author christian.chacon
     */
    @DataProvider(name = "usersWithoutLeaderInBME")
    public Object[][] usersWithoutLeaderInBME() throws Exception{
        Object[][] data = null;
        String xmlInputStream = new MyGrowthProperties().dataProviderPathMG + "userInBMEWithoutLeader.xml";
        String xpathExpression = "/dataObjects/dataObject";
        data=buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
        return data;
    }

    /**
     * This data provider will return users with Closed Mission and this user is not in Better me
     * @return Object[][]
     * @throws Exception
     * @author christian.chacon
     */
    @DataProvider(name="usersWithClosedMissionAndNoExistInBME")
    public Object[][] usersWithClosedMissionAndNoExistInBME() throws Exception{
        Object[][] data = null;
        String xmlInputStream = new MyGrowthProperties().dataProviderPathMG + "userIsNotPresentInBMEWithClosedMission.xml";
        String xpathExpression = "/dataObjects/dataObject";
        data=buildDataProviderObjectFromXmlAsMap(xmlInputStream, xpathExpression);
        return data;
    }
    /**
     * This data provider will return users with talent model and that are also in the BME database with a valid leader assigned (not every glober is in BME data base and/or with a leader assigned)
     *
     * @return Object[][]
     * @throws Exception
     */
    @DataProvider(name = "usersInBetterMe")
    public Object[][] usersInBetterMe() throws Exception {
        String xmlInputStream = new MyGrowthProperties().dataProviderPathMG + "bmeIntegration/usersInBetterMeDataBase";
        String xpathExpression = "/dataObjects/dataObject";
        return buildDataProviderObjectFromXML(xmlInputStream, xpathExpression);
    }
    /**
     * This data provider will return users without self endorsement but in Albertha database so an evaluation can be created.
     * @return Object[][]
     * @throws Exception
     */
    @DataProvider(name = "usersWithTalentModel")
    public Object[][] getUsersWithTalentModel() throws Exception {
        MyGrowthDBHelper myGrowthDBHelper=new MyGrowthDBHelper();
        Map<String, String> data=myGrowthDBHelper.getRandomGloberGivenAWorkingEcosystem(1);
        Object [][] hashMapObj = new Object [2][1];
        hashMapObj[0][0]=data;
        data=myGrowthDBHelper.getRandomGloberGivenAWorkingEcosystem(2);
        hashMapObj[1][0]=data;
        return hashMapObj;
    }
    /**
     * This data provider will return users with the self Endorsement created
     *
     * @return
     * @throws Exception
     */
    @DataProvider(name = "getGlobersRandomWithSelfEndorsement")
    public Object[] getGlobersWithSelfEdnorsement() throws Exception {
        Object[] dataObject = new Object[2];
        dataObject[0] = myGrowthDBHelper.getGloberWithSelfEndorsement("IOS Mobile Developer");
        dataObject[1] = myGrowthDBHelper.getGloberWithSelfEndorsement("Android Mobile Developer");
        return dataObject;
    }
    /**
     * Returns a mentor and its mentees
     * @return Object[][]
     * @throws Exception
     */
    @DataProvider(name = "getMentorMocked")
    public Object[][] getMockedMentor() throws Exception {
        Object[][] data = null;
        String xmlFilePath = MyGrowthProperties.dataProviderPath + "myGrowth/mentorMockedDataProvider.xml";
        String xpathExpression = ("//dataObject[@type='isAMentor']");
        data = buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
        return data;
    }

    /**
     * This data provider will return the username, podId and projectId from a random Glober with family group
     * @return Object[][]
     * @throws Exception
     * @author christian.chacon
     */
    @DataProvider(name = "usersWithPodIdandProyectIdAssignmentswithFamilyGroup")
    public Object[][] usersWithPodIdandProyectIdAssignmentswithFamilyGroup() throws Exception {
        FamilyGroupDBHelper familyGroupDBHelper=new FamilyGroupDBHelper();
        Map<String, String> user=familyGroupDBHelper.getRandomUserWithPodidProjectIdandFamilyGroupNOTNULL();
        Object [][] hashMapObj = new Object [1][1];
        hashMapObj[0][0]=user;
        return hashMapObj;
    }

    /**
     * This data provider will return the username, podId and projectId from a random Glober without family group
     * @return Object[][]
     * @throws Exception
     * @author christian.chacon
     */
    @DataProvider(name = "usersWithPodIdandProyectIdAssignmentswithoutFamilyGroup")
    public Object[][] usersWithPodIdandProyectIdAssignmentswithoutFamilyGroup() throws Exception {
        FamilyGroupDBHelper familyGroupDBHelper=new FamilyGroupDBHelper();
        Map<String, String> user=familyGroupDBHelper.getRandomUserWithPodidProjectIdandFamilyGroupISNULL();
        Object [][] hashMapObj = new Object [1][1];
        hashMapObj[0][0]=user;
        return hashMapObj;
    }

    /**
     * This data provider will return the username and globerId with family group from the database
     * @return Object[][]
     * @throws Exception
     * @author christian.chacon
     */
    @DataProvider(name = "getGloberRandomWithFamilyGroup")
    public Object[][] getGloberRandomWithFamilyGroup() throws Exception {
        FamilyGroupDBHelper familyGroupDBHelper=new FamilyGroupDBHelper();
        Map<String, String> user=familyGroupDBHelper.getRandomGloberIdAndUsernameWithFamilyGroup();
        Object [][] hashMapObj = new Object [1][1];
        hashMapObj[0][0]=user;
        return hashMapObj;
    }

    /**
     * This data provider will return the username and globerId without family group
     * @return Object[][]
     * @throws Exception
     * @author christian.chacon
     */
    @DataProvider(name = "getGloberRandomWithoutFamilyGroup")
    public Object[][] getGloberRandomWithoutFamilyGroup() throws Exception {
        FamilyGroupDBHelper familyGroupDBHelper=new FamilyGroupDBHelper();
        Map<String, String> user=familyGroupDBHelper.getRandomGloberIdAndUsernameWithoutFamilyGroup();
        Object [][] hashMapObj = new Object [1][1];
        hashMapObj[0][0]=user;
        return hashMapObj;
    }

    /**
     * This data provider will return the globerid and mission name with status available and mission type Mentor:Mission
     * @return Object[][]
     * @throws SQLException
     * @author christian.chacon
     */
    @DataProvider(name = "getCurrentMentoringMissionWithStatusAvailable")
    public Object[][] getCurrentMentoringMissionWithStatusAvailable() throws SQLException {
        MyGrowthDBHelper myGrowthDBHelper=new MyGrowthDBHelper();
        Map<String, String> data=myGrowthDBHelper.getCurrentStatusforMentorinMissions("AVAILABLE", "MENTOR_MISSION");
        Object [][] hashMapObj = new Object [1][1];
        hashMapObj[0][0]=data;
        return hashMapObj;
    }

    /**
     * This data provider will return the globerid and mission name with status "STARTED" and mission type Mentor "Mission"
     * @return Object[][]
     * @throws SQLException
     * @author christian.chacon
     */
    @DataProvider(name = "mentoringMissionWithStatusStarted")
    public Object[][] getMentoringMissionWithStatusStarted() throws SQLException {
        MyGrowthDBHelper myGrowthDBHelper=new MyGrowthDBHelper();
        Map<String, String> data=myGrowthDBHelper.getCurrentStatusforMentorinMissions("STARTED", "MENTOR_MISSION");
        Object [][] hashMapObj = new Object [1][1];
        hashMapObj[0][0]=data;
        return hashMapObj;
    }

    /**
     * This data provider will return the globerid and mission name with status "STARTED" and mission type Mentor "Mission"
     * @return Object[][]
     * @throws SQLException
     */
    @DataProvider(name = "mentoringMissionWithStatusAvailable")
    public Object[][] getMentoringMissionWithStatusAvailable() throws SQLException {
        MyGrowthDBHelper myGrowthDBHelper=new MyGrowthDBHelper();
        Map<String, String> data=myGrowthDBHelper.getCurrentStatusforMentorinMissions("AVAILABLE", "MENTOR_MISSION");
        Object [][] hashMapObj = new Object [1][1];
        hashMapObj[0][0]=data;
        return hashMapObj;
    }

    /**
     * This data provider will return the globerid and mission name with status "CLOSED" and mission type Mentor "Mission"
     * @return Object[][]
     * @throws SQLException
     * @author christian.chacon
     */
    @DataProvider(name = "mentoringMissionWithStatusClosed")
    public Object[][] getMentoringMissionWithStatusClosed() throws SQLException {
        MyGrowthDBHelper myGrowthDBHelper=new MyGrowthDBHelper();
        Map<String, String> data=myGrowthDBHelper.getCurrentStatusforMentorinMissions("CLOSED", "MENTOR_MISSION");
        Object [][] hashMapObj = new Object [1][1];
        hashMapObj[0][0]=data;
        return hashMapObj;
    }

    /**
     * Returns glober id and username of a random mentor for given working ecosystem
     * @return Object[][]
     * @throws SQLException
     */
    @DataProvider(name = "mentorForGivenWorkingEcosystem")
    public Object[][] getMentorForGivenWorkingEcosystem() throws SQLException {
        MyGrowthDBHelper myGrowthDBHelper=new MyGrowthDBHelper();
        Map<String, String> data=myGrowthDBHelper.getMentorForAGivenPrimaryWorkingEcosystem(1);
        Object [][] hashMapObj = new Object [1][1];
        hashMapObj[0][0]=data;

        return hashMapObj;
    }

    /**
     * Returns glober id and username for a glober who is not a mentor.
     * @return Object[][]
     * @throws SQLException
     */
    @DataProvider(name = "globerNotBeingMentor")
    public Object[][] getGloberWhoIsNotAMentor() throws SQLException {
        MyGrowthDBHelper myGrowthDBHelper=new MyGrowthDBHelper();
        Map<String, String> data=myGrowthDBHelper.getAGloberWhoIsNotAMentor(1);
        Object [][] hashMapObj = new Object [1][1];
        hashMapObj[0][0]=data;

        return hashMapObj;
    }
    /**
     * Get glober with high rol (TD-TM-SME)
     * @return Object[][]
     * @throws SQLException
     */
    @DataProvider(name = "globerWithHighRole")
    public Object[][] getGloberByRol() throws SQLException {
        Map<String, String> data= GloberDBHelper.getUserByRol("Subject Matter Expert");
        Object [][] hashMapObj = new Object [3][1];
        hashMapObj[0][0]=data;
        data= GloberDBHelper.getUserByRol("Tech Director");
        hashMapObj[1][0]=data;
        data= GloberDBHelper.getUserByRol("Tech Manager");
        hashMapObj[2][0]=data;

        return hashMapObj;
    }

    /**
     * Get HighRole
     * @return Object[][]
     * @throws Exception
     */
    @DataProvider(name = "mockedHighRole")
    public Object[][] getMockedHighRole() throws Exception {
        Object[][] data = null;
        String xmlFilePath = MyGrowthProperties.dataProviderPath + "myGrowth/mentor/HighRoleDataProvider";
        String xpathExpression = ("//dataObject[@type='SME']");
        data = buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
        return data;
    }
    /**
     * Get glober not having HighRole
     * @return Object[][]
     * @throws Exception
     */
    @DataProvider(name = "mockedNotHighRole")
    public Object[][] getMockedNotHighRole() throws Exception {
        Object[][] data = null;
        String xmlFilePath = MyGrowthProperties.dataProviderPath + "myGrowth/mentor/HighRoleDataProvider";
        String xpathExpression = ("//dataObject[@type='isNotHighRole']");
        data = buildDataProviderObjectFromXmlAsMap(xmlFilePath, xpathExpression);
        return data;
    }

    /**
     * Get a mentoring mission available created by the same mentor
     * @return Object[][]
     * @throws SQLException
     */
    @DataProvider(name = "mentorMissionAvailabletoEdit")
    public Object[][] mentorMissionAvailabletoEdit() throws SQLException {
        MyGrowthDBHelper myGrowthDBHelper=new MyGrowthDBHelper();
        Map<String, String> data=myGrowthDBHelper.getMentoringMissionCreatedbyAMentortoMentees("AVAILABLE","MENTOR_MISSION");
        Object [][] hashMapObj = new Object [1][1];
        hashMapObj[0][0]=data;
        return hashMapObj;
    }

    /**
     * Get a mentoring mission started created by the same mentor
     * @return Object[][]
     * @throws SQLException
     */
    @DataProvider(name = "mentorMissionStartedtoEdit")
    public Object[][] mentorMissionStartedtoEdit() throws SQLException {
        MyGrowthDBHelper myGrowthDBHelper=new MyGrowthDBHelper();
        Map<String, String> data=myGrowthDBHelper.getMentoringMissionCreatedbyAMentortoMentees("STARTED","MENTOR_MISSION");
        Object [][] hashMapObj = new Object [1][1];
        hashMapObj[0][0]=data;
        return hashMapObj;
    }

    /**
     * Get a Random glober with or without talent model
     * @return Object[][]
     * @throws SQLException
     */
    @DataProvider(name = "globerWithOrWithoutTalentModel")
    public Object[][] globerWithOrWithoutTalentModel() throws SQLException {
        GlowDBHelper glowDBHelper=new GlowDBHelper();
        Map<String, String> data=glowDBHelper.getRandomGloberIdAndUsername();
        Object [][] hashMapObj = new Object [1][1];
        hashMapObj[0][0]=data;
        return hashMapObj;
    }

}
