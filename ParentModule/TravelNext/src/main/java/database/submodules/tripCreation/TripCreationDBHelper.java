package database.submodules.tripCreation;

import database.TravelNextDBHelper;
import dto.submodules.tripCreation.tripDetail.TravelReason;
import org.testng.SkipException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author josealberto.gomez
 *
 */
public class TripCreationDBHelper extends TravelNextDBHelper {
    /**
     * This method will return random id for Travel Reason
     *
     * @return TravelReason
     * @throws SQLException
     */
    public TravelReason getRandomTravelReason() throws SQLException {
        TravelReason travelReason = new TravelReason();
        try {
            String query = "SELECT * FROM travel_reason ORDER BY random() LIMIT 1;";
            ResultSet rs = getResultSet(query);

            if(!rs.next()) {
                throw new SkipException("Cannot find travel reason.");
            }
            else {
                travelReason.setId(rs.getInt("id"));
                travelReason.setCode(rs.getString("code"));
                travelReason.setDescription(rs.getString("description"));
                travelReason.setActive(rs.getBoolean("active"));
                return travelReason;
            }

        } finally {
            endConnection();
        }
    }
}
