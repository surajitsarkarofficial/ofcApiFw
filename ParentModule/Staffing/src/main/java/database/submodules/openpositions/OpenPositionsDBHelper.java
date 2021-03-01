package database.submodules.openpositions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.testng.Reporter;

import database.StaffingDBHelper;

public class OpenPositionsDBHelper extends StaffingDBHelper {

	public OpenPositionsDBHelper() {
	}

	/**
	 * Get glober applied open position count
	 * 
	 * @param globalId
	 * @return {@link List}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public String getGloberAppliedOpenPositionCount(String globalId) throws SQLException {

		String query = "SELECT COUNT(s1.id) AS applied_count FROM applied_open_positions aop\r\n"
				+ "   JOIN staffing_plan s1 ON s1.id = aop.staffing_plan_fk\r\n" + " WHERE s1.user_fk = '" + globalId
				+ "' AND aop.state = 'Applied'";

		try {
			ResultSet resultSet = getResultSet(query);
			resultSet.next();
			return resultSet.getString("applied_count");
		} finally {
			endConnection();
		}
	}

	/**
	 * Get glober having applied to maximun opsen position/open position limit
	 * 
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public String[] getGloberIdWithMaxAppliedToOP() throws SQLException {
		String result[] = new String[2];
		String query = "SELECT staffing_plan.user_fk AS globerid,COUNT(staffing_plan.id) AS applied_count FROM staffing_plan,applied_open_positions \r\n"
				+ "WHERE staffing_plan.id = applied_open_positions.staffing_plan_fk \r\n"
				+ "AND applied_open_positions.state = 'Applied'\r\n"
				+ "AND staffing_plan.is_active = 'true'\r\n"
				+ "GROUP BY staffing_plan.user_fk HAVING COUNT(staffing_plan.id)=2\r\n"
				+ "ORDER BY RANDOM() LIMIT 1";

		try {
			ResultSet resultSet = getResultSet(query);
			resultSet.next();
			result[0] = resultSet.getString("globerid");
			result[1] = resultSet.getString("applied_count");
			return result;
		} finally {
			endConnection();
		}
	}
	
	/**
	 * Get glober having who doesn't reach max. applied limit.
	 * @return {@link String[]}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public String[] getGloberIdWithMinAppliedToOPCount() throws SQLException {
		String result[] = new String[2];
		String query = "SELECT staffing_plan.user_fk AS globerid,COUNT(staffing_plan.id) AS applied_count FROM staffing_plan,applied_open_positions\r\n" + 
				"    WHERE staffing_plan.id = applied_open_positions.staffing_plan_fk AND applied_open_positions.state = 'Applied'\r\n" + 
				"    GROUP BY staffing_plan.user_fk  HAVING COUNT(staffing_plan.id)<2 ORDER BY RANDOM() LIMIT 1";

		try {
			ResultSet resultSet = getResultSet(query);
			resultSet.next();
			result[0] = resultSet.getString("globerid");
			result[1] = resultSet.getString("applied_count");
			return result;
		} finally {
			endConnection();
		}
	}
	
	/**
	 * check no of plans available/made by glober on Sr via applying through open positions
	 * 
	 * @param globerId
	 * @param positionId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public String getStatusOfPlanAbsentForGlober(String globerId,String positionId) throws SQLException {

		String query = "SELECT COUNT(user_fk)<1 AS is_plan_absent FROM staffing_plan WHERE position_fk = "+positionId+" AND user_fk = "+globerId;

		try {
			ResultSet resultSet = getResultSet(query);
			resultSet.next();
			return resultSet.getString("is_plan_absent");
		} finally {
			endConnection();
		}
	}

	/**
	 * Get plan details w.r.t positionId
	 * 
	 * @param positionId
	 * @return {@link String[]}
	 * @throws SQLException
	 * @author shadab.waikar
	 */

	public String[] getPlanDetailsOfAppliedOpenPosition(String positionId) throws SQLException {
		String result[] = new String[4];
		String query = "SELECT plan_type,planstart_date,due_date,created_date FROM staffing_plan WHERE position_fk = "+positionId+" ORDER BY created_date DESC LIMIT 1";  

		try {
			ResultSet resultSet = getResultSet(query);
			resultSet.next();
			result[0] = resultSet.getString("plan_type");
			result[1] = resultSet.getString("planstart_date");
			result[2] = resultSet.getString("due_date");
			result[3] = resultSet.getString("created_date");
			return result;
		} finally {
			endConnection();
		}
	}
	
	/**
	 * unapply to applied open position 
	 * 
	 * @param positionId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikars
	 */

	public Integer unApplyToAppliedOpenPosition(String positionId) throws SQLException {
		Reporter.log("Position Id:"+positionId, true);
		String result = null;
		String query1 = "SELECT id FROM staffing_plan WHERE position_fk = "+positionId+" AND is_active = 't' ORDER BY created_date DESC LIMIT 1"; 
		String query2 = "UPDATE applied_open_positions SET state = 'Unapplied' WHERE staffing_plan_fk = "+result; 
		
		try {
			ResultSet resultSet = getResultSet(query1);
			resultSet.next();
			result = resultSet.getString("id");
			return executeUpdateQuery(query2);		
		} finally {
			endConnection();
		}
	}
}
