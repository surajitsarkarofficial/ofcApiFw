package database.submodules.invoice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.testng.SkipException;

import database.FinancialDBHelper;

/**
 * @author german.massello
 *
 */
public class InvoiceDBHelper extends FinancialDBHelper {

	/**
	 * Get invoice.
	 * @param vimId
	 * @return Map<String,String>
	 * @throws SQLException
	 */
	public Map<String,String> getInvoice(String vimId) throws SQLException  {
		ResultSet rs;
		Map<String,String> invoice = new HashMap<>();
		try {
			String query = "SELECT g.username AS submmiter, i.status AS status, description, globant_society AS society, invoice_number AS invoiceNumber\n" + 
					", vendor, currency, amount, local_amount AS localAmount, image_url AS urlImage, rate_convertion AS rateConvertion\n" + 
					", processing_status AS processingStatus\n" + 
					"FROM invoices_vim i\n" + 
					"LEFT JOIN globers g ON g.id=i.glober_fk\n" + 
					"WHERE vim_id="+vimId;
			rs = getResultSet(query);
			if(!rs.next()) throw new SkipException("No invoice available.");
			else {
				invoice.put("submmiter", rs.getString("submmiter"));
				invoice.put("status", rs.getString("status"));
				invoice.put("description", rs.getString("description"));
				invoice.put("society", rs.getString("society"));
				invoice.put("invoiceNumber", rs.getString("invoiceNumber"));
				invoice.put("vendor", rs.getString("vendor"));
				invoice.put("amount", rs.getString("amount"));
				invoice.put("localAmount", rs.getString("localAmount"));
				invoice.put("urlImage", rs.getString("urlImage"));
				invoice.put("rateConvertion", rs.getString("rateConvertion"));
				invoice.put("processingStatus", rs.getString("processingStatus"));
			}
			return invoice;
		}finally{
			endConnection();
		}	
	}

}
