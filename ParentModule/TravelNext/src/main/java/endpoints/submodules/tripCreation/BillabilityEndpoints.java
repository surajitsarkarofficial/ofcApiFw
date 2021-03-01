package endpoints.submodules.tripCreation;

import endpoints.TravelNextEndpoints;

/**
 * @author josealberto.gomez
 *
 */
public class BillabilityEndpoints extends TravelNextEndpoints {
    public static String getBillabilities = "/proxy/glow/tripservice/billabilities";
    public static String putBillability = "/proxy/glow/tripservice/trips/%s/billability/%s";
}
