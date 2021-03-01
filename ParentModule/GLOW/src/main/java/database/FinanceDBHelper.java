package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * @author german.massello
 *
 */
public interface FinanceDBHelper {

	/**
	 * Get sap currency
	 * @param currencyCode
	 * @return HashMap<String, String>
	 * @throws SQLException
	 */
	public default HashMap<String, String> getSapCurrency(String currencyCode) throws SQLException {
		HashMap<String, String> currency = new HashMap<>();
		String query = "SELECT id\n" + 
				"FROM public.sap_currencies\n" + 
				"WHERE code = '"+currencyCode+"'";
		try {
			ResultSet rs = new BaseDBHelper().getResultSet(query);
			rs.next();
			currency.put("id", rs.getString("id"));
			return currency;
		} finally {
			new BaseDBHelper().endConnection();
		}
	}

}
