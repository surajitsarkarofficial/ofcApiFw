package database;

import org.testng.SkipException;
import properties.TravelNextConstants;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author josealberto.gomez
 *
 */
public class TravelNextDBHelper extends GlowDBHelper{
    /**
     * This method will return random Passenger mail
     *
     * @return String
     * @throws SQLException
     */
    public String getRandomPassengerMail() throws SQLException {
        try {
            String query = "SELECT * FROM passenger ORDER BY random() LIMIT 1;";
            ResultSet rs = getResultSet(query);

            if(!rs.next()) {
                throw new SkipException("Cannot find passenger email.");
            }
            else {
                return rs.getString("mail");
            }

        } finally {
            endConnection();
        }
    }

    /**
     * This method will return travelId created for Automation Test
     *
     * @return Integer
     * @throws SQLException
     */
    public Integer getLastTripIdAutomation() throws SQLException {
        try {
            String query = "SELECT * FROM trip WHERE description LIKE '%" + TravelNextConstants.automationTestLabel + "%' ORDER BY id DESC LIMIT 1;";
            ResultSet rs = getResultSet(query);

            if(!rs.next()) {
                throw new SkipException("Cannot find Automation Test tripId.");
            }
            else {
                return rs.getInt("id");
            }
        } finally {
            endConnection();
        }
    }

    /**
     * This method will return id for Passenger mail
     *
     * @param mail
     * @return Integer
     * @throws SQLException
     */
    public Integer getPassengerIdFromMail(String mail) throws SQLException {
        try {
            String query = "SELECT * FROM passenger WHERE mail LIKE '%" + mail + "%';";
            ResultSet rs = getResultSet(query);

            if(!rs.next()) {
                throw new SkipException("Cannot find passenger id.");
            }
            else {
                return rs.getInt("passenger_id");
            }

        } finally {
            endConnection();
        }
    }

    /**
     * This method will return random Passenger mail for KnownTravelerNumber
     *
     * @return String
     * @throws SQLException
     */
    public String getRandomPassengerMailKnownTravelerNumber() throws SQLException {
        try {
            String query = "SELECT passenger.mail FROM passenger LEFT JOIN tsa_number ON passenger.passenger_id = tsa_number.id_passenger_fk WHERE tsa_number.id_passenger_fk isnull ORDER BY random() LIMIT 1;";
            ResultSet rs = getResultSet(query);

            if(!rs.next()) {
                throw new SkipException("Cannot find passenger email.");
            }
            else {
                return rs.getString("mail");
            }

        } finally {
            endConnection();
        }
    }
}
