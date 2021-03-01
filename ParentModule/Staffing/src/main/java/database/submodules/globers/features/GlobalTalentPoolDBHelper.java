package database.submodules.globers.features;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.submodules.globers.GlobersDBHelper;

public class GlobalTalentPoolDBHelper extends GlobersDBHelper {

	public GlobalTalentPoolDBHelper() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method will return total globers count on talent pool
	 * 
	 * @return list Of Global TalentPool Count
	 * @throws SQLException
	 */
	public List<String> getGlobalTalentPoolCount() throws SQLException {
		List<String> listOfGlobalTalentPool = new ArrayList<String>();
		ResultSet resultSet = null;
		String query = "SELECT DISTINCT globerid AS count FROM glober_view  WHERE "
				+ "((((assignmentstartdate BETWEEN CURRENT_DATE and (CURRENT_DATE+ INTEGER '30')) OR (assignmentstartdate < (CURRENT_DATE+ INTEGER '30') "
				+ "AND assignmentenddate IS NULL) OR (assignmentstartdate<(CURRENT_DATE+ INTEGER '30')"
				+ "AND assignmentenddate>(CURRENT_DATE+ INTEGER '30'))) AND type='Glober' AND availability BETWEEN 0 AND 100 "
				+ "AND internalassignmenttype='BENCH' AND lastdate IS NULL AND enddate IS NULL) )" + "UNION "
				+ "SELECT distinct candidateid AS count from glober_view where (type<>'Glober' AND assignedforsr='false') ";
		try {
			resultSet = getResultSet(query);
			while (resultSet.next()) {
				listOfGlobalTalentPool.add(resultSet.getString("count"));
			}
			return listOfGlobalTalentPool;
		} finally {
			endConnection();
		}
	}
}
