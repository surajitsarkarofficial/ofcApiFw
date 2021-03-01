package database.submodules.catalog.features;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.SkipException;

import database.submodules.catalog.CatalogDBHelper;

/**
 * @author german.massello
 *
 */
public class GroupCatalogDBHelper extends CatalogDBHelper {

	/**
	 * Get active groups quantity
	 * @return int
	 * @throws SQLException
	 */
	public int getActiveGroupsQuantity() throws SQLException {
			try {
				String query = "SELECT COUNT(*) AS quantity\n" + 
						"FROM public.sap_items_groups\n" + 
						"WHERE active=true";
				ResultSet rs = getResultSet(query);
				if(!rs.next()) throw new SkipException("No groups availables");
				return Integer.valueOf(rs.getString("quantity"));
			}finally{
				endConnection();
			}	
		}
	
	/**
	 * Active group catalog
	 * @throws SQLException
	 */
	public void activeGroupCatalog() throws SQLException {
		try {
			String query = "UPDATE public.sap_items_groups\n" + 
					"SET active=true";
			executeUpdateQuery(query);
		}finally{
			endConnection();
		}		
	}

}
