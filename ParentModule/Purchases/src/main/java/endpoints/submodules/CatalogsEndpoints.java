package endpoints.submodules;

import endpoints.PurchasesEndpoints;

/**
 * @author german.massello
 *
 */
public class CatalogsEndpoints extends PurchasesEndpoints {
	public static String countries = "/proxy/glow/sapcatalogsservice/catalogs/countries?sortAscending=%s&sortCriteria=%s";
	public static String groups = "/proxy/glow/sapcatalogsservice/catalogs/items_groups?sortAscending=%s&sortCriteria=%s&type=%s";
	public static String currencies = "/proxy/glow/sapcatalogsservice/catalogs/currencies?countryId=%s";
	public static String stores = "/proxy/glow/sapcatalogsservice/catalogs/stores?countryId=%s&sortAscending=%s&sortCriteria=%s";
	public static String rates = "/proxy/glow/sapcatalogsservice/rates?currencyFrom=%s&currencyTo=%s";
	public static String materials = "/proxy/glow/sapcatalogsservice/catalogs/materials?sortAscending=%s&sortCriteria=%s&groupId=%s&society=%s";
	public static String vendors = "/proxy/glow/sapcatalogsservice/catalogs/vendors?society=%s&name=%s";
}
