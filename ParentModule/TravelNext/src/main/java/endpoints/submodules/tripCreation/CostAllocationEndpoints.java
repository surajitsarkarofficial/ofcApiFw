package endpoints.submodules.tripCreation;

import endpoints.TravelNextEndpoints;

/**
 * @author josealberto.gomez
 *
 */
public class CostAllocationEndpoints extends TravelNextEndpoints {
    public static String postCostAllocation = "/proxy/glow/travelorchestra/travel/orchestra/trips/%s/costAllocation";
    public static String putCostAllocation = "/proxy/glow/travelorchestra/travel/orchestra/trips/%s/costAllocations/%s";
    public static String getCostAllocation = "/proxy/glow/tripservice/trips/%s/costAllocations";
    public static String deleteCostAllocation = "/proxy/glow/tripservice/trip/costAllocation/%s";
}
