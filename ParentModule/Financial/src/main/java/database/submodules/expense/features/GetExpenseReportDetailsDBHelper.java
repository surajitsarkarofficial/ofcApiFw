package database.submodules.expense.features;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.testng.SkipException;

import database.submodules.expense.ExpenseDBHelper;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import dto.submodules.expense.report.ReportStatus;

/**
 * @author german.massello
 *
 */
public class GetExpenseReportDetailsDBHelper extends ExpenseDBHelper {

	/**
	 * This method will return an user name, an expenses report id and a client name.
	 * @param reportStatus
	 * @param isBenefit
	 * @return Map<String,String>
	 * @throws SQLException
	 * @author german.massello
	 */
	public Map<String,String> getExpensesReport(String reportStatus, String isBenefit) throws SQLException  {
		ResultSet rs;
		Map<String,String> report = new HashMap<>();
		try {
			String query = "SELECT g.username AS username, t.id AS reportId, "
					+ "c.name AS clientName, t.group_description AS title, \n"
					+ "t.amount_usd-0.01 AS usdFrom, t.amount_usd+0.01 AS usdTo " + 
					"FROM public.tickets t\n" + 
					"LEFT JOIN globers g ON g.id=t.submitter_fk\n" + 
					"LEFT JOIN ticket_states ts on t.state_fk=ts.id\n" + 
					"LEFT JOIN projects p ON p.id=t.origin_id\n" + 
					"LEFT JOIN clients c ON c.id=p.client_fk\n" + 
					"WHERE ts.state_type='"+reportStatus+"'\n" + 
					"AND g.username NOT like 'old.%'\n" + 
					"AND t.is_benefit="+isBenefit+"\n" + 
					"ORDER BY t.id DESC\n" + 
					"LIMIT 1";
			rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No report id available.");
			else {
				report.put("username", rs.getString("username"));
				report.put("reportId", rs.getString("reportId"));
				if (rs.getString("clientName")==null) report.put("clientName", "No Client name");
				else report.put("clientName", rs.getString("clientName"));
				report.put("title", rs.getString("title"));
				report.put("usdFrom", rs.getString("usdFrom"));
				report.put("usdTo", rs.getString("usdTo"));
			}
		}finally{
			endConnection();
		}	
		return report;
	}

	/**
	 * This method will return the report status.
	 * @param report
	 * @return ReportStatus
	 * @throws SQLException
	 * @author german.massello
	 */
	public ReportStatus getReportStatus(ReportWithExpenseDTO report) throws SQLException  {
		ReportStatus reportStatus=new ReportStatus();
		try {
			String query = "SELECT ts.state_type AS status, ts.author, ts.comments\n" + 
					"FROM public.tickets t\n" + 
					"LEFT JOIN ticket_states ts on t.state_fk=ts.id\n" + 
					"WHERE t.id="+report.getContent().getId();
			ResultSet rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No report id available.");
			else {
				reportStatus.setAuthor(rs.getString("author"));
				reportStatus.setComments(rs.getString("comments"));
				reportStatus.setStatus(rs.getString("status"));
			}
			return reportStatus;
		}finally{
			endConnection();
		}	
	}
}
