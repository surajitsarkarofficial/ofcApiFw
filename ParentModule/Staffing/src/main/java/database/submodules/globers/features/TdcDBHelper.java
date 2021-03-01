package database.submodules.globers.features;

import java.sql.ResultSet;

import database.submodules.globers.GlobersDBHelper;

public class TdcDBHelper extends GlobersDBHelper{

	public TdcDBHelper() {
	}
	
	/**
	 * This method will return TDC name from Glober_View
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public String getGloberTdc(String id) throws Exception {
		try {
			String res = "";
			String query = "select tdcname from glober_view where globalid = (select globalid from glober_view where globerid = '"
					+ id + "' limit 1)";
			ResultSet rs = getResultSet(query);
			if (rs.next()) {
				res = rs.getString("tdcname");
			}
			return res;
		} finally {
			endConnection();
		}
	}
}
