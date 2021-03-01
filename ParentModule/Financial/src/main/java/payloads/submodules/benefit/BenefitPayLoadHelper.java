package payloads.submodules.benefit;

import payloads.FinancialPayLoadHelper;

/**
 * @author german.massello
 *
 */
public class BenefitPayLoadHelper extends FinancialPayLoadHelper {
	protected static String [] benefitNameOptions = {"Internet "};
	protected String username;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
