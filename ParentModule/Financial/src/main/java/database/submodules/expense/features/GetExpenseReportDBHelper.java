package database.submodules.expense.features;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.SkipException;

import database.submodules.expense.ExpenseDBHelper;
import parameters.submodules.expense.features.getReports.GetExpenseReportIsBenefitFilter;
import parameters.submodules.expense.features.getReports.GetExpenseReportUsdFilter;

/**
 * @author german.massello
 *
 */
public class GetExpenseReportDBHelper extends ExpenseDBHelper {

	/**
	 * This method will complete the parameters in order to validate the USD filter. 
	 * @return GetExpenseReportUsdFilter
	 * @throws SQLException
	 */
	public GetExpenseReportUsdFilter getRandomReportIdAndUsdAmountsLimits() throws SQLException  {
		GetExpenseReportUsdFilter filter=new GetExpenseReportUsdFilter();
		try {
			String query = "SELECT ROUND (t.amount_usd, 0)-1 AS fromUsdAmount, ROUND (t.amount_usd, 0)+1 AS toUsdAmount,t.id AS reportId\n" + 
					"FROM public.tickets t\n" + 
					"LEFT JOIN globers g ON g.id=t.submitter_fk\n" + 
					"LEFT JOIN ticket_states ts on t.state_fk=ts.id\n" + 
					"LEFT JOIN projects p ON p.id=t.origin_id\n" + 
					"LEFT JOIN clients c ON c.id=p.client_fk\n" + 
					"WHERE ts.state_type='approved_by_manager' \n" + 
					"AND t.is_benefit=false \n" + 
					"AND g.username NOT like 'old.%'\n" + 
					"ORDER BY reportId DESC\n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No report id available.");
			else {
				filter.setUsdFrom(rs.getString("fromUsdAmount"));
				filter.setUsdTo(rs.getString("toUsdAmount"));
				filter.setReportId(rs.getString("reportId"));
			}
			return  filter;
		}finally{
			endConnection();
		}	
	}
	
	/**
	 * This method will complete the parameters in order to validate the is benefit filter. 
	 * @return GetExpenseReportIsBenefitFilter
	 * @throws SQLException
	 */
	public GetExpenseReportIsBenefitFilter getRandomReportIsBenefit() throws SQLException  {
		GetExpenseReportIsBenefitFilter filter=new GetExpenseReportIsBenefitFilter();
		try {
			String query = "SELECT t.id AS reportId\n" + 
					"FROM public.tickets t\n" + 
					"LEFT JOIN globers g ON g.id=t.submitter_fk\n" + 
					"LEFT JOIN ticket_states ts on t.state_fk=ts.id\n" + 
					"LEFT JOIN projects p ON p.id=t.origin_id\n" + 
					"LEFT JOIN clients c ON c.id=p.client_fk\n" + 
					"WHERE ts.state_type='approved_by_manager' \n" + 
					"AND t.is_benefit=true \n" + 
					"AND g.username NOT like 'old.%'\n" + 
					"ORDER BY reportId DESC\n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No report id available.");
			else {
				filter.setReportId(rs.getString("reportId"));
			}
			return  filter;
		}finally{
			endConnection();
		}	
	}
}
