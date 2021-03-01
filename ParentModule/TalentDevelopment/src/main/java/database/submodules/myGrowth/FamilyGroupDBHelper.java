package database.submodules.myGrowth;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import database.TalentDevelopmentDBHelper;

/**
 * @author christian.chacon
 */
public class FamilyGroupDBHelper extends TalentDevelopmentDBHelper {

    /**
     * get a Random globerId and username with Family Group
     * @return Map<String, String>
     * @throws SQLException
     * @author christian.chacon
     */
    public Map<String, String> getRandomGloberIdAndUsernameWithFamilyGroup() throws SQLException {
        String query="SELECT glo.id AS globerID, glo.username AS username, cd.family_group as familyGroup FROM contracts_data cd JOIN contracts_information ci ON ci.id = cd.contract_information_fk JOIN globers glo ON glo.contract_information_fk = ci.id WHERE glo.work_email NOT like 'old.%' AND glo.username NOT like 'old.%' AND cd.end_date IS NULL AND cd.family_group IS NOT NULL GROUP BY glo.username, glo.id, cd.family_group ORDER BY RANDOM() LIMIT 1;";
        Map<String, String> m;
        try {
            m= new HashMap<String, String>();
            ResultSet resultSet = getResultSet(query);
            resultSet.next();
            m.put("globerId", resultSet.getString("globerId"));
            m.put("username", resultSet.getString("username"));
            m.put("familyGroup", resultSet.getString("familyGroup"));
            return m;
        }finally {
        	endConnection();
        }
    }

    /**
     * get a Random globerId and username without Family Group
     * @return Map<String, String>
     * @throws SQLException
     * @author christian.chacon
     */
    public Map<String, String> getRandomGloberIdAndUsernameWithoutFamilyGroup() throws SQLException {
        String query="SELECT glo.id AS globerID, glo.username AS username, cd.family_group as familyGroup FROM contracts_data cd JOIN contracts_information ci ON ci.id = cd.contract_information_fk JOIN globers glo ON glo.contract_information_fk = ci.id WHERE glo.work_email NOT like 'old.%' AND glo.username NOT like 'old.%' AND cd.end_date IS NULL AND cd.family_group IS NULL GROUP BY glo.username, glo.id, cd.family_group ORDER BY RANDOM() LIMIT 1;";
        Map<String, String> m;
        try {
            m= new HashMap<String, String>();
            ResultSet resultSet = getResultSet(query);
            resultSet.next();
            m.put("globerId", resultSet.getString("globerId"));
            m.put("username", resultSet.getString("username"));
            m.put("familyGroup", resultSet.getString("familyGroup"));
            return m;
        }finally {
        	endConnection();
        }
    }

    /**
     * Get a random glober with pod, project and Family group
     * @return Map<String, String>
     * @throws SQLException
     * @author christian.chacon
     */
    public Map<String, String> getRandomUserWithPodidProjectIdandFamilyGroupNOTNULL() throws SQLException {
        String query="SELECT ass.project_fk AS projectId, g.username, pods.id AS podId, cd.family_group AS familyGroup\n" +
                "FROM assignments ass JOIN positions pos ON ass.position_fk = pos.id\n" +
                "JOIN pod_positions pp ON pos.id=pp.position\n" +
                "JOIN pods ON pods.id=pp.pod \n" +
                "JOIN position_roles posrol ON pos.position_role_fk = posrol.id \n" +
                "JOIN globers g ON ass.resume_fk = g.id \n" +
                "INNER JOIN projects p ON ass.project_fk = p.id\n" +
                "INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk \n" +
                "INNER JOIN contracts_data cd ON cd.contract_information_fk = ci.id \n" +
                "WHERE current_date BETWEEN ass.starting_date AND ass.end_date AND cd.end_date IS NULL AND cd.family_group IS NOT NULL ORDER BY RANDOM() LIMIT 1;";
        Map<String, String> m;
        try{
            m= new HashMap<String, String>();
            ResultSet resultSet=getResultSet(query);
            resultSet.next();
            m.put("projectId", resultSet.getString("projectId"));
            m.put("podId", resultSet.getString("podId"));
            m.put("username", resultSet.getString("username"));
            m.put("familyGroup", resultSet.getString("familyGroup"));
            return m;
        }finally {
        	endConnection();
        }
    }

    /**
     * Get a random glober with pod, project and without Family group assigned
     * @return Map<String, String>
     * @throws SQLException
     * @author christian.chacon
     */
    public Map<String, String> getRandomUserWithPodidProjectIdandFamilyGroupISNULL() throws SQLException {
        String query="SELECT ass.project_fk AS projectId, g.username, pods.id AS podId, cd.family_group AS familyGroup\n" +
                "FROM assignments ass JOIN positions pos ON ass.position_fk = pos.id\n" +
                "JOIN pod_positions pp ON pos.id=pp.position\n" +
                "JOIN pods ON pods.id=pp.pod \n" +
                "JOIN position_roles posrol ON pos.position_role_fk = posrol.id \n" +
                "JOIN globers g ON ass.resume_fk = g.id \n" +
                "INNER JOIN projects p ON ass.project_fk = p.id\n" +
                "INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk \n" +
                "INNER JOIN contracts_data cd ON cd.contract_information_fk = ci.id \n" +
                "WHERE current_date BETWEEN ass.starting_date AND ass.end_date AND cd.end_date IS NULL AND cd.family_group IS NULL ORDER BY RANDOM() LIMIT 1;";
        Map<String, String> m;
        try{
            m= new HashMap<String, String>();
            ResultSet resultSet=getResultSet(query);
            resultSet.next();
            m.put("projectId", resultSet.getString("projectId"));
            m.put("podId", resultSet.getString("podId"));
            m.put("username", resultSet.getString("username"));
            m.put("familyGroup", resultSet.getString("familyGroup"));
            return m;
        }finally {
        	endConnection();
        }
    }

}
