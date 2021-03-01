package database.submodules.myTrips;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import database.TravelMobileDBHelper;

public class MyTripsDBHelper extends TravelMobileDBHelper{

	public MyTripsDBHelper() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * This method will fetch the trip Passenger Id for the specified trip and passenger id
	 * @param tripId
	 * @param passengerId
	 * @return
	 * @throws SQLException
	 */
	public String getTripPassengerId(String tripId, String passengerId) throws SQLException
	{
		try {
		String query = String.format("Select trip_passenger_id from trip_passenger where trip_id= %s and passenger_id=%s;",tripId,passengerId);
		ResultSet rs = getResultSet(query);
		//rs.next();
		try {
			rs.next();
			System.out.println(rs.getString(1));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		java.sql.ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (rs.next()) {
		    for (int i = 1; i <= columnsNumber; i++) {
		        if (i > 1) System.out.print(",  ");
		        String columnValue = rs.getString(i);
		    }
		}
		
		
		
		String value =String.valueOf(rs.getObject("trip_passenger_id"));
		return value;
		}finally{
			endConnection();
		}
		
	}
}
