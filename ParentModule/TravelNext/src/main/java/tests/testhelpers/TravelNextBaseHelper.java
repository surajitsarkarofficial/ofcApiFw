package tests.testhelpers;

import database.TravelNextDBHelper;
import dto.submodules.tripCreation.tripDetail.Trip;
import tests.GlowBaseTestHelper;
import utils.RestUtils;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author josealberto.gomez
 *
 */
public class TravelNextBaseHelper extends GlowBaseTestHelper {
    protected RestUtils restUtils = new RestUtils();

    /**
     * This method format now time with format of param
     *
     * @param format
     * @return String
     */
    public static String nowDateTimeWithFormat(String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date nowTime = new Date(System.currentTimeMillis());

        return formatter.format(nowTime);
    }

    /**
     * This method get last Trip created by Automation Tests
     *
     * @return Trip
     * @throws SQLException
     */
    public static Trip getLastTripByAutomation() throws SQLException {
        Trip tmpTrip = new Trip();
        tmpTrip.setTripId(new TravelNextDBHelper().getLastTripIdAutomation());
        return tmpTrip;
    }

}
