package database.submodules.benefit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.SkipException;

import database.FinancialDBHelper;
import dto.submodules.expense.addExpenseToReport.FromBenefitToExpense;
import dto.submodules.expense.addExpenseToReport.ReportWithExpenseDTO;
import payloads.submodules.benefit.features.post.PostBenefitPayLoadHelper;

/**
 * @author german.massello
 *
 */
public class BenefitDBHelper extends FinancialDBHelper {

	/**
	 * This method will convert a benefit in a report with expense DTO.
	 * @param payload
	 * @return ReportWithExpenseDTO
	 * @throws SQLException
	 */
	public ReportWithExpenseDTO getReportWithExpenseDTOFromABenefit(PostBenefitPayLoadHelper payload) throws SQLException  {
		long timeOut = new Date().getTime() + 10000;
		ReportWithExpenseDTO report=new ReportWithExpenseDTO(new FromBenefitToExpense());
		String benefitOne;
		ResultSet rs;
		try {
			String benefitOneQuery="SELECT id\n" + 
					"FROM public.benefits b\n" + 
					"WHERE b.period_code='"+payload.getBenefitsLot().get(0).getBenefits().get(0).getPeriodCode()+"'\n" + 
					"AND b.description='"+payload.getBenefitsLot().get(0).getBenefits().get(0).getBenefitDescription()+"'";
			rs = getResultSet(benefitOneQuery);
			while (!rs.next() && new Date().getTime() < timeOut);
			benefitOne=rs.getString("id");
			String baseQuery="SELECT c.name AS clientName, t.id AS reportId, td.id AS expenseId, t.resume_fk AS authorId, t.origin_id AS originId,\n" + 
					"td.ticket_description AS title, td.amount_original_currency AS amount, provider AS provider\n" + 
					"FROM public.tickets t\n" + 
					"LEFT JOIN ticket_details td ON t.id=td.ticket_fk\n" + 
					"LEFT JOIN benefits b ON b.id=td.benefits_fk \n" + 
					"LEFT JOIN projects p ON p.id=t.origin_id\n" + 
					"LEFT JOIN clients c ON c.id=p.client_fk\n" + 
					"WHERE b.id=";
			
			String query = baseQuery+benefitOne;
			rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No expense report available or report was not created succesfully");
			else {
				report.getContent().setId(rs.getString("reportId"));
				report.getContent().setClientName(rs.getString("clientName"));
				report.setAuthorId(rs.getString("authorId"));
				report.getContent().setOriginId(rs.getString("originId"));
				report.getContent().getExpenses().get(0).setId(rs.getString("expenseId"));
				report.getContent().getExpenses().get(0).setTitle(rs.getString("title"));
				report.getContent().getExpenses().get(0).setAmount(rs.getString("amount"));
				report.getContent().getExpenses().get(0).setProvider(rs.getString("provider"));
			}
			return report;
		}catch (Exception e) {
			throw new SkipException("No benefit one available");
		}
		finally{
			endConnection();
		}	
	}


	/**
	 * This method will return all globers.
	 * @param limit
	 * @return List<Map<String, String>>
	 * @throws SQLException
	 * @author german.massello
	 */
	public List<Map<String, String>> getAllGlobers(String limit) throws SQLException {
		List<Map<String, String>> globersList = new ArrayList<Map<String, String>>();
		try {
			String query = "SELECT DISTINCT g.username, g.info_type_32 AS globalId, ci.currency\r\n" + 
					"FROM available_users_authorities aua\r\n" + 
					"INNER JOIN users ON aua.user_fk = users.id\r\n" + 
					"INNER JOIN globers g ON g.id = users.resume_fk\r\n" + 
					"INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk\r\n" + 
					"INNER JOIN contracts_data cd ON cd.contract_information_fk = ci.id\r\n" + 
					"WHERE (LOWER(SUBSTRING(g.username\r\n" + 
					"FROM 1\r\n" + 
					"FOR 3)) != 'old')\r\n" + 
					"AND (cd.end_date IS NULL)\r\n" + 
					"AND ((CURRENT_DATE - 0) > g.creation_date)\n" +
					"LIMIT "+limit;
			ResultSet rs = getResultSet(query);
			while (rs.next()) { 
				Map<String, String> m = new HashMap<String, String>();
				m.put("username", rs.getString("username"));
				m.put("globalId", rs.getString("globalId"));
				if (rs.getString("currency")==null || rs.getString("currency").equals("")) m.put("currency", "USD");
				else m.put("currency", rs.getString("currency"));
				globersList.add(m);
			}
		} finally {
			endConnection();
		}
		return globersList;
	}
	
	/**
	 * This method will return one glober.
	 * @param username
	 * @return List<Map<String, String>>
	 * @throws SQLException
	 * @author german.massello
	 */
	public List<Map<String, String>> getOneGlober(String username) throws SQLException {
		List<Map<String, String>> globersList = new ArrayList<Map<String, String>>();
		try {
			String query = "SELECT DISTINCT g.username, g.info_type_32 AS globalId, ci.currency\r\n" + 
					"FROM available_users_authorities aua\r\n" + 
					"INNER JOIN users ON aua.user_fk = users.id\r\n" + 
					"INNER JOIN globers g ON g.id = users.resume_fk\r\n" + 
					"INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk\r\n" + 
					"INNER JOIN contracts_data cd ON cd.contract_information_fk = ci.id\r\n" + 
					"WHERE (LOWER(SUBSTRING(g.username\r\n" + 
					"FROM 1\r\n" + 
					"FOR 3)) != 'old')\r\n" + 
					"AND (cd.end_date IS NULL)\r\n" + 
					"AND ((CURRENT_DATE - 0) > g.creation_date)" +
					"AND g.username='"+username+"'";
			ResultSet rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No info available for "+username);
			Map<String, String> m = new HashMap<String, String>();
			m.put("username", rs.getString("username"));
			m.put("globalId", rs.getString("globalId"));
			if (rs.getString("currency")==null || rs.getString("currency").equals("")) m.put("currency", "USD");
			else m.put("currency", rs.getString("currency"));
			globersList.add(m);
		} finally {
			endConnection();
		}
		return globersList;
	}
	
	/**
	 * Get talent pool glober.
	 * @return List<Map<String, String>>
	 * @throws SQLException
	 * @author german.massello
	 */
	public List<Map<String, String>> getTalentPoolGlober() throws SQLException {
		List<Map<String, String>> globersList = new ArrayList<Map<String, String>>();
		try {
			String query = "SELECT g.username,\n" + 
					"       g.id,\n" + 
					"       ass.percentage,\n" + 
					"	   g.info_type_32 AS globalId,\n" + 
					"	   ci.currency\n" + 
					"FROM globers g\n" + 
					"INNER JOIN assignments ass ON ass.resume_fk = g.id\n" + 
					"INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk AND ci.last_date IS NULL\n" + 
					"INNER JOIN contracts_data cd ON cd.contract_information_fk = g.contract_information_fk AND cd.end_date IS NULL\n" + 
					"AND CURRENT_DATE BETWEEN ass.starting_date AND coalesce(ass.end_date, '2021-12-31')\n" + 
					"INNER JOIN projects p ON p.id = ass.project_fk\n" + 
					"AND p.id = 1000 AND percentage=100\n" + 
					"AND ci.currency != ''\n" + 
					"ORDER BY RANDOM() LIMIT 1";
			ResultSet rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No glober available in talent pool ");
			Map<String, String> m = new HashMap<String, String>();
			m.put("username", rs.getString("username"));
			m.put("globalId", rs.getString("globalId"));
			m.put("currency", rs.getString("currency"));
			globersList.add(m);
		} finally {
			endConnection();
		}
		return globersList;
	}
	
	/**
	 * Get random glober
	 * @return List<Map<String, String>>
	 * @throws SQLException
	 * @author german.massello
	 */
	public List<Map<String, String>> getRandomGlober() throws SQLException {
		List<Map<String, String>> globersList = new ArrayList<Map<String, String>>();
		try {
			String query = "SELECT g.username, g.info_type_32 AS globalId, ci.currency\n" + 
					"FROM available_users_authorities aua\n" + 
					"INNER JOIN users ON aua.user_fk = users.id\n" + 
					"INNER JOIN globers g ON g.id = users.resume_fk\n" + 
					"INNER JOIN contracts_information ci ON ci.id = g.contract_information_fk\n" + 
					"INNER JOIN contracts_data cd ON cd.contract_information_fk = ci.id\n" + 
					"WHERE (LOWER(SUBSTRING(g.username\n" + 
					"FROM 1\n" + 
					"FOR 3)) != 'old')\n" + 
					"AND (cd.end_date IS NULL)\n" + 
					"AND ((CURRENT_DATE - 0) > g.creation_date)\n" + 
					"AND ci.currency != ''\n" + 
					"ORDER BY RANDOM()\n" + 
					"LIMIT 1";
			ResultSet rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No glober available in talent pool ");
			Map<String, String> m = new HashMap<String, String>();
			m.put("username", rs.getString("username"));
			m.put("globalId", rs.getString("globalId"));
			m.put("currency", rs.getString("currency"));
			globersList.add(m);
		} finally {
			endConnection();
		}
		return globersList;
	}

}
