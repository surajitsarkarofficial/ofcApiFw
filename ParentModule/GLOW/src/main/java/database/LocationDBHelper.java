
package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface LocationDBHelper {
	
	/**
	 * Get locationId
	 * 
	 * @return locationId
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getLocationId() throws SQLException {
		String query = "SELECT id FROM location_office ORDER by random() limit 1";
		try {
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			return resultSet.getString("id");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * Get location name according to locationId
	 * 
	 * @param locationId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getLocationNameFromId(String locationId) throws SQLException {
	    String result = null;
		String query = "SELECT location FROM location_office WHERE id = '"+locationId+"'"; 
		ResultSet resultSet = new BaseDBHelper().getResultSet(query);	
		try {
			resultSet.next();
			result = resultSet.getString("location");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}
	
	/**
	 * Get Site name according to locationId
	 * 
	 * @param locationId
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getSiteNameByLocationId(String locationId) throws SQLException {
	    String result = null;
		String query = "SELECT DISTINCT sitename AS siteName FROM glober_view WHERE siteid='" + locationId + "'"; 
		ResultSet resultSet = new BaseDBHelper().getResultSet(query);	
		try {
			resultSet.next();
			result = resultSet.getString("siteName");
		} finally {
			new BaseDBHelper().endConnection();
		}
		return result;
	}
	
	/**
	 * Get location Id according to location name
	 * @param locationName
	 * @return {@link String}
	 * @throws SQLException
	 * @author shadab.waikar
	 */
	public default String getLocationIdFromLocationName(String locationName) throws SQLException {
		
		String query = " SELECT id AS locationId FROM location_office WHERE location='"+locationName+"'";
		ResultSet rs = new BaseDBHelper().getResultSet(query);
		try {
			rs.next();
			return rs.getString("locationId");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	/**
	 * Get list of valid location names
	 * @apiNote Enter the numeric value for specific records or String 'ALL' for all records
	 * @param totalLocationNames
	 * @return {@link List}
	 * @throws SQLException
	 * @author deepakkumar.hadiya
	 */

	public default List<String> getLocationNames(Object totalLocationNames) throws SQLException {
		try {
			String query = "SELECT * FROM sites WHERE id IN(SELECT id FROM sites WHERE site_details_fk!=0 OR site_details_fk is null) ORDER BY random() LIMIT " + totalLocationNames;
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			List<String> locations = new ArrayList<String>();
			while (resultSet.next()) {
				locations.add(resultSet.getString("name"));
			}
			return locations;
		} finally {
			new BaseDBHelper().endConnection();
		}
	}

	/**
	 * Get location name which has not site details
	 * @return {@link String}
	 * @throws SQLException
	 * @author deepakkumar.hadiya
	 */

	public default String getInvalidLocationName() throws SQLException {
		try {
			String query = "SELECT name FROM sites WHERE name NOT LIKE '%&%' AND site_details_fk=0 ORDER BY random() LIMIT 1";
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			return resultSet.getString("name");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * Get locationId except particular id
	 * @param locationId
	 * @return String 
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default String getLocationIdExceptParticularId(String locationId) throws SQLException{
		String query = "SELECT id FROM location_office where id != "+locationId+" ORDER by random() limit 1";
		try {
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			resultSet.next();
			return resultSet.getString("id");
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * This method will return list of locations except 2 location ids e.g except primary location and anywhere location
	 * @param locationId
	 * @param anywhereId
	 * @return string
	 * @throws SQLException
	 * @author akshata.dongare
	 */
	public default List<String> getSecondaryLocationIdExceptPrimaryLocation(String locationId,String anywhereId) throws SQLException {
		List<String> listOfSecondaryLocIds = new ArrayList<String>();
		ResultSet resultSet = null;
		String query = " SELECT loc.location AS sec_location,loc.id AS sec_loc_id FROM "
				+ "positions_location_office sec_loc " + "JOIN " + "location_office loc " + "ON "
				+ "loc.id=sec_loc.location_fk where loc.id not in ("+anywhereId+","+locationId+") ORDER by RANDOM() limit 3";
		try {
			resultSet = new BaseDBHelper().getResultSet(query);
			while (resultSet.next()) {
				listOfSecondaryLocIds.add(resultSet.getString("sec_loc_id"));
			}
			return listOfSecondaryLocIds;
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * Get list of sites : one site for each country
	 * @return {@link List}
	 * @throws SQLException
	 * @author deepakkumar.hadiya
	 */

	public default List<String> getLocationForEachCountry() throws SQLException {
		try {
			String query = "SELECT * FROM sites WHERE id IN (SELECT Max(id) FROM sites WHERE id IN(SELECT id FROM sites WHERE site_details_fk != 0 OR site_details_fk IS NULL) GROUP BY country_fk)";
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			List<String> locations = new ArrayList<String>();
			while (resultSet.next()) {
				locations.add(resultSet.getString("name"));
			}
			return locations;
		} finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * This method returns holiday list for country
	 * @param siteName
	 * @return {@link List}
	 * @throws Exception 
	 * @author deepakkumar.hadiya
	 */
	
	public default List<String> getHolidayListForLocation(String siteName) throws Exception {
		List<String> holidayList=new ArrayList<>();
		String query=String.format("SELECT h.holiday_date As holiday FROM sites s, holidays h WHERE s.country_fk = h.country_fk AND s.NAME = '%s';",siteName);
		try {
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			while(resultSet.next()) {
				holidayList.add(resultSet.getString("holiday"));
			}
			return holidayList;
		}finally {
			new BaseDBHelper().endConnection();
		}
	}
	
	/**
	 * This method returns upcoming holiday list for country
	 * @param siteName
	 * @return {@link List}
	 * @throws Exception 
	 * @author deepakkumar.hadiya
	 */
	
	public default List<String> getUpcomingHolidayListForLocation(String siteName) throws Exception {
		List<String> holidayList=new ArrayList<>();
		String query=String.format("SELECT h.holiday_date As holiday FROM sites s, holidays h WHERE s.country_fk = h.country_fk AND h.holiday_date>timezone('GMT+0', NOW()) AND s.NAME = '%s';",siteName);
		try {
			ResultSet resultSet = new BaseDBHelper().getResultSet(query);
			while(resultSet.next()) {
				holidayList.add(resultSet.getString("holiday"));
			}
			return holidayList;
		}finally {
			new BaseDBHelper().endConnection();
		}
	}
}
