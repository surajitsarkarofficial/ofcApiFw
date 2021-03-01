package database.submodules.myGrowth;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.SkipException;

import database.TalentDevelopmentDBHelper;

/**
 * @author nadia.silva
 */
public class MyGrowthDBHelper extends TalentDevelopmentDBHelper {

    /**
     * Gets all the glober missions regarding that working ecosystem
     * @author nadia.silva
     * @param glober
     * @param workingEcosystem
     * @return string list
     * @throws SQLException
     */
    public List<String> getGloberMissionsForWorkingEcosystem(String glober, String workingEcosystem) throws SQLException {
        ArrayList<String> missionsList = new ArrayList<>();
        String query = "SELECT  mis.name FROM acp_missions mis INNER JOIN acp_missions_globers misglo ON mis.id = misglo.acp_missions_fk INNER JOIN acp_mission_origins ori ON ori.id = mis.acp_origin_fk INNER JOIN acp_missions_priority_catalog mpc ON mpc.id = mis.acp_missions_prioritty_catalog_fk INNER JOIN acp_missions_dependency dep ON mis.id = dep.acp_mission INNER JOIN acp_working_ecosystem_by_glober we ON we.glober_fk = misglo.globers_fk INNER JOIN acp_working_ecosystem_by_mission wemis ON wemis.acp_mission_fk = mis.id LEFT JOIN acp_missions_history mh ON mh.acp_missions_fk = misglo.id and end_date is nulL WHERE misglo.globers_fk = "+glober+" AND we.acp_working_ecosystem_fk = "+workingEcosystem+" AND wemis.acp_working_ecosystems_fk = "+workingEcosystem+" group by mis.name";
            try {
                ResultSet rs = getResultSet(query);

                while (rs.next()) {
                    String str = rs.getString("name");
                    missionsList.add(str);
                }
            } finally {
            	endConnection();
            }
            return missionsList;
    }

    /**
     * Returns all working ecosystems the glober has assigned
     * @author nadia.silva
     * @param globerId
     * @return string list
     * @throws SQLException
     */
    public List<String> getWorkingEcosystemsOfGlober(String globerId) throws SQLException {
        ArrayList<String> workingEcosystems = new ArrayList<>();
        String query = "Select acp_working_ecosystem_fk from acp_working_ecosystem_by_glober where glober_fk = '"+globerId+"';";
        try {
            ResultSet rs = getResultSet(query);
            while (rs.next()) {
                String str = rs.getString("acp_working_ecosystem_fk");
                workingEcosystems.add(str);
            }
        } finally {
        	endConnection();
        }

        return workingEcosystems;
    }

    /**
     * Returns ID of Working ecosystem
     * @param workingEcosystemName
     * @author nadia.silva
     * @return String
     * @throws SQLException
     */
    public String getIdofWorkingEcosystem(String workingEcosystemName) throws SQLException {
        ResultSet rs;
        String globerId = null;
        try {
            String query = "Select id from acp_working_ecosystems where name = '"+workingEcosystemName+"';";
            rs = getResultSet(query);
            if (!rs.next()) {
                throw new SkipException("Working Ecosystem name incorrect!");
            } else {
                globerId = rs.getString("id");
            }
        } finally {
        	endConnection();
        }
        return globerId;
    }

    /**
     * returns primary working ecosystem of glober
     * @author nadia.silva
     * @param globerId
     * @return string
     * @throws SQLException
     */
    public String getPrimaryWorkingEcosystemOfGlober(String globerId) throws SQLException {
        ResultSet rs;
        String we = "";
        try {
            String query = "Select acp_working_ecosystem_fk from acp_working_ecosystem_by_glober where glober_fk = '"+globerId+"' and primary_we = true";
            rs = getResultSet(query);
            if (!rs.next()) {
                throw new SkipException("Glober does not have working ecosystems assigned in data base yet.");
            } else {
                we = rs.getString("acp_working_ecosystem_fk");
            }
        } finally {
        	endConnection();
        }
        return we;
    }

    /**
     * Get glober level for the working ecosystem provided.
     * @param globerId
     * @param workingEcosystem
     * @return String
     * @throws SQLException
     */
    public String getLevelOfGloberInWorkingEcosystem(String globerId, String workingEcosystem) throws SQLException {
        ResultSet rs;
        String we = "";
        try {
            String query = "select level from acp_working_ecosystem_by_glober where glober_fk = "+globerId+" and acp_working_ecosystem_fk = "+workingEcosystem+";";
            rs = getResultSet(query);
            if (!rs.next()) {
                throw new SkipException("This glober doesnt exist in this working ecosystem.");
            } else {
                we = rs.getString("level");
            }
        } finally {
        	endConnection();
        }
        return we;
    }

    /**
     * get the current area level and id for glober depending on seniority
     * @param level
     * @return Strings Hashmap
     * @throws SQLException
     */
    public HashMap<String, String> getCurrentAreasLevelBySeniorityLevel(String level) throws SQLException {
        HashMap<String, String> levelAreas = new HashMap<>();
        String query = "select * from acp_areas_talent_model where level = "+level+";";
        try {
            ResultSet rs = getResultSet(query);
            while (rs.next()) {
                levelAreas.put(rs.getString("acp_areas_fk"), rs.getString("acp_level_catalog_fk"));
            }
        } finally {
        	endConnection();
        }

        return levelAreas;
    }

    /**
     * get the current area level and id for glober depending on seniority
     * @param level
     * @return Strings Hashmap
     * @throws SQLException
     */
    public HashMap<String, String> getCurrentLevelAndAreaIdBySeniority(String level) throws SQLException {
        HashMap<String, String> areaAndLevel = new HashMap<>();

        String query = "select acp_areas_fk, acp_level_catalog_fk from acp_areas_talent_model where level = "+level+";";
        try {
            ResultSet rs = getResultSet(query);
            while (rs.next()) {
                areaAndLevel.put(rs.getString("acp_areas_fk"), rs.getString("acp_level_catalog_fk"));
            }
        } finally {
        	endConnection();
        }
        return areaAndLevel;
    }
    /**
     * get the current subject level and id for glober depending on seniority and area
     * @param level, area
     * @return Strings Hashmap
     * @throws SQLException
     */
    public HashMap<String, String> getCurrentLevelAndSubjectIdByAreaAndSeniority(String level, String area) throws SQLException {
        HashMap<String, String> subjectAndLevel = new HashMap<>();

        String query = "select acp_subjects_fk, subTM.acp_level_catalog_fk, sub.acp_areas_fk as id_area, sub.name as subject_name from acp_subjects_talent_model as subTM\n" +
                "inner join acp_subjects as sub ON subTM.acp_subjects_fk = sub.id\n" +
                "where level = "+level+" and sub.acp_areas_fk = "+area+"\n" +
                "order by acp_subjects_fk;";
        try {
            ResultSet rs = getResultSet(query);
            while (rs.next()) {
                subjectAndLevel.put(rs.getString("acp_subjects_fk"), rs.getString("acp_level_catalog_fk"));

            }
        } finally {
        	endConnection();
        }
        return subjectAndLevel;
    }
    /**
     * get the current subject level for glober depending on seniority and area
     * @param level
     * @return String list, area
     * @throws SQLException
     */
    public List<String> getCurrentLevelSubjectsByAreaAndSeniority(String level, String area) throws SQLException {
        ArrayList<String> levelSubject = new ArrayList<>();
        String query = "select subTM.acp_, sub.acp_areas_fk as id_area, sub.name as subject_name  from acp_subjects_talent_model as subTM\n" +
                "inner join acp_subjects as sub ON subTM.acp_subjects_fk = sub.id\n" +
                "where level = "+level+" and sub.acp_areas_fk = "+area+"\n" +
                "order by acp_subjects_fk;";
        try {
            ResultSet rs = getResultSet(query);
            while (rs.next()) {
                String str = rs.getString("acp_level_catalog_fk");
                levelSubject.add(str);
            }
        } finally {
        	endConnection();
        }

        return levelSubject;
    }
    /**
     * Gets a random working ecosystem that has not been added to the glober already
     * @param globerId
     * @return List<String>
     * @throws SQLException
     */
    public List<String> getWorkingEcosystemThatGlobersDoesNotHaveAlready(String globerId) throws SQLException {
        ResultSet rs;
        List<String> idAndName =  new ArrayList<String>();
        try {
            String query = "Select * from acp_working_ecosystems where id not in (Select acp_working_ecosystem_fk from acp_working_ecosystem_by_glober where glober_fk = '" + globerId + "') order by RANDOM () limit 1";
            rs = getResultSet(query);
            if (!rs.next()) {
                throw new SkipException("Glober does not have any other working ecosystem to be added.");
            } else {

                idAndName.add(rs.getString("id"));
                idAndName.add(rs.getString("name"));
            }
        } finally {
            endConnection();
        }
        return idAndName;
    }

    /**
     * Obtain a random glober with mission status and mission type
     * @param status
     * @param missionType
     * @return HashMap
     * @throws SQLException
     * @author christian.chacon
     */
    public HashMap<String, String> getCurrentStatusforMentorinMissions(String status, String missionType) throws SQLException {
        HashMap<String, String> result = new HashMap<>();
        String query="SELECT amg.globers_fk AS globerId, am.id AS missionId, glo.username AS username FROM acp_missions_history amh\n" +
                "INNER JOIN acp_missions_globers amg ON amg.id=amh.acp_missions_fk\n" +
                "INNER JOIN acp_missions am ON am.id=amg.acp_missions_fk\n" +
                "INNER JOIN acp_mission_origins amo ON am.acp_origin_fk=amo.id\n" +
                "INNER JOIN globers AS glo ON amg.globers_fk=glo.id\n" +
                "WHERE amh.status='"+status+"' AND amo.name='"+missionType+"' AND glo.username NOT like 'old.%' AND amh.end_date IS NULL ORDER BY RANDOM() DESC LIMIT 1;";
        try {
            ResultSet resultSet = getResultSet(query);
            if(!resultSet.next()) throw new SkipException("There is no mentoring mission with status "+status+" in the database");
            else
            result.put("globerId", resultSet.getString("globerId"));
            result.put("missionId", resultSet.getString("missionId"));
            result.put("username", resultSet.getString("username"));
        } finally {
            endConnection();
        }
        return result;
    }

    /**
     * This query will return the current status for a Mentoring Mission by missionId
     * @param missionId
     * @return String
     * @throws SQLException
     * @author christian.chacon
     */
    public String getCurrentMentoringMissionStatus(Long missionId) throws SQLException {
        String status="";
        String query = "SELECT amh.status as status FROM acp_missions_history amh\n" +
                "INNER JOIN acp_missions_globers amg ON amg.id=amh.acp_missions_fk\n" +
                "INNER JOIN acp_missions am ON am.id=amg.acp_missions_fk\n" +
                "INNER JOIN acp_mission_origins amo ON am.acp_origin_fk=amo.id\n" +
                "INNER JOIN globers AS glo ON amg.globers_fk=glo.id\n" +
                "WHERE amo.name='MENTOR_MISSION' AND am.id = "+missionId+" AND amh.end_date IS NULL;";
        try {
            ResultSet resultSet = getResultSet(query);
            if (!resultSet.next()) {
                throw new SkipException("This mission hasn't been closed");
            } else {
                status = resultSet.getString("status");
            }
        } finally {
            endConnection();
        }
        return status;
    }

    /**
     * This query will Return a random glober for specific working ecosystem
     * @param workingecosystemId
     * @return HashMap<String, String>
     * @throws SQLException
     * @author christian.chacon
     */
    public HashMap<String, String> getRandomGloberGivenAWorkingEcosystem(int workingecosystemId) throws SQLException {
        HashMap<String, String> result = new HashMap<>();
        String query="SELECT aweg.glober_fk as globerId, glo.username as username FROM acp_working_ecosystem_by_glober aweg INNER JOIN globers glo ON aweg.glober_fk=glo.id WHERE aweg.acp_working_ecosystem_fk="+workingecosystemId+" AND glo.username NOT like 'old.%' AND aweg.primary_we=TRUE ORDER BY RANDOM() LIMIT 1;";
        try{
            ResultSet resultSet=getResultSet(query);
            if (!resultSet.next()) {
                throw new SkipException("anyone glober was found to create self endorsment");
            } else {
                result.put("globerId", resultSet.getString("globerId"));
                result.put("username", resultSet.getString("username"));
            }
        }finally {
            endConnection();
        }
        return result;
    }

    /**
     * Return the authority name given the glober id and the authority id
     * @param globerid
     * @param authorityid
     * @return String
     * @throws SQLException
     */
    public String getRoleACPMyGrowthFromGlober(int globerid, int authorityid) throws SQLException {
        String role="";
        String query="select aut.name as role from users u INNER JOIN globers g ON g.id = u.resume_fk\n" +
                "INNER JOIN available_users_authorities aua ON aua.user_fk=u.id\n" +
                "INNER JOIN authorities aut ON aut.id=aua.authority_fk where g.id="+globerid+" and aut.id="+authorityid+";";
        try{
            ResultSet resultSet=getResultSet(query);
            if (!resultSet.next()) {
                throw new SkipException("The role was not found in DB");
            } else {
                role = resultSet.getString("role");
            }
        }finally {
            endConnection();
        }
        return role;
    }


    /**
     * Find a mentoring mission created by a mentor to him/his mentees
     * @param status
     * @param type
     * @return HashMap<String, String>
     * @throws SQLException
     * @author christian.chacon
     */
    public HashMap<String, String> getMentoringMissionCreatedbyAMentortoMentees(String status, String type) throws SQLException {
        HashMap<String, String> result = new HashMap<>();
        String query="SELECT amg.globers_fk AS globeridmentee, am.created_by AS globeridmentor, am.id AS missionid, am.name AS missionname, glo.username AS mentorusername FROM acp_missions_history amh INNER JOIN acp_missions_globers amg ON amg.id=amh.acp_missions_fk INNER JOIN acp_missions am ON am.id=amg.acp_missions_fk INNER JOIN acp_mission_origins amo ON am.acp_origin_fk=amo.id INNER JOIN globers AS glo ON am.created_by=glo.id INNER JOIN mentor AS men ON men.glober_fk=amg.globers_fk INNER JOIN mentor_leader AS menlead ON menlead.mentor_fk=men.id JOIN acp_capabilities_missions AS acm ON acm.acp_missions_fk=am.id WHERE am.created_by=men.mentor_glober_fk and amh.status='"+status+"' AND amo.name='"+type+"' AND glo.username NOT like 'old.%' AND amh.end_date IS NULL AND am.created_by IS NOT NULL GROUP BY amg.globers_fk, am.id, glo.username ORDER BY RANDOM() LIMIT 1;";
        try{
            ResultSet resultSet=getResultSet(query);
            if (!resultSet.next()) {
                throw new SkipException("There is not mentoring mission in the database");
            } else {
                result.put("globeridmentee", resultSet.getString("globeridmentee"));
                result.put("globeridmentor", resultSet.getString("globeridmentor"));
                result.put("missionid", resultSet.getString("missionid"));
                result.put("missionname", resultSet.getString("missionname"));
                result.put("mentorusername", resultSet.getString("mentorusername"));
            }
        }finally {
            endConnection();
        }
        return result;
    }

    /**
     * Get mentor id and username for a given working ecosystem being it the primary one
     * @author nadia.silva
     * @param primaryWorkingEcosystem
     * @return String
     * @throws SQLException
     */
    public  HashMap<String, String> getMentorForAGivenPrimaryWorkingEcosystem(int primaryWorkingEcosystem) throws SQLException{
        HashMap<String, String> mentor= new HashMap<>();
        String query= "Select globers.id, globers.username from globers INNER JOIN mentor ON mentor.mentor_glober_fk = globers.id INNER JOIN acp_working_ecosystem_by_glober we_glober ON we_glober.glober_fk = mentor.mentor_glober_fk where globers.username NOT like 'old.%' AND we_glober.primary_we=true AND we_glober.acp_working_ecosystem_fk = "+primaryWorkingEcosystem+" order by Random() limit 1";
        try {
            ResultSet resultSet = getResultSet(query);
            if (!resultSet.next()) {
                throw new SkipException("There is no mentor available for that working ecosystem.");
            } else {
                mentor.put("id", resultSet.getString("id"));
                mentor.put("username", resultSet.getString("username"));
            }
        } finally {
            endConnection();
        }
        return mentor;

    }

    /**
     * Get glober who is not a mentor, given a working ecosystem
     * @return Hashmap<String, String>
     * @throws SQLException
     */
    public  HashMap<String, String> getAGloberWhoIsNotAMentor(int we) throws SQLException{
        HashMap<String, String> mentor= new HashMap<>();
        String query= "SELECT globers.id, globers.username FROM globers INNER JOIN acp_working_ecosystem_by_glober we_glober ON we_glober.glober_fk = globers.id WHERE globers.id NOT IN(SELECT mentor.mentor_glober_fk FROM mentor) AND globers.username NOT like 'old.%' AND we_glober.acp_working_ecosystem_fk = "+we+" AND we_glober.primary_we=true order by Random() Limit 1;";
        try {
            ResultSet resultSet = getResultSet(query);
            if (!resultSet.next()) {
                throw new SkipException("There is no globers available.");
            } else {
                mentor.put("id", resultSet.getString("id"));
                mentor.put("username", resultSet.getString("username"));
            }
        } finally {
            endConnection();
        }
        return mentor;

    }
}