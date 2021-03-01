package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TravelMobileDBHelper extends GlowDBHelper{

	public TravelMobileDBHelper() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * This method will fetch the customer specified property info from the database
	 * @param customerEmailId
	 * @param propertyName
	 * @return
	 * @throws SQLException
	 */
	public Object getCustomerInfo(String customerEmailId, String propertyName) throws SQLException
	{
		try {
		String query = String.format("SELECT * FROM customers WHERE email='%s'",customerEmailId);
		ResultSet rs = getResultSet(query);
		rs.next();
		Object value =rs.getObject(propertyName);
		return value;
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		finally{
			endConnection();
		}	
	}
	

}
