package payloads.submodules.benefit.features.post;

import java.sql.SQLException;
import java.util.Map;
import java.util.Random;

import database.GlowDBHelper;
import payloads.submodules.benefit.BenefitPayLoadHelper;

/**
 * @author german.massello
 *
 */
public class Benefit extends BenefitPayLoadHelper {
	private String benefitCode;
	private String benefitDescription;
	private String contableCode;
	private String currencyCode;
	private String maxAmount;
	private String periodCode;
	
	public Benefit(Map<String, String> map) throws SQLException {
		this.benefitCode="55000004";
		this.benefitDescription="Internet-"+map.get("username");
		this.contableCode="55000004";
		this.maxAmount="500";
		this.periodCode="20210101";
		this.currencyCode=map.get("currency");
	}

	/**
	 * This constructor will create a benefit payload and the currencyCode is going to be the glober currency.
	 * @param username
	 * @throws SQLException
	 */
	public Benefit(String username) throws SQLException {
		this();
		this.currencyCode=new GlowDBHelper().getGloberCurrency(username);
	}
	
	/**
	 * This constructor will create a benefit payload.
	 * @throws SQLException
	 */
	public Benefit() throws SQLException {
		Random randomSeed = new Random();
		String randomNumber = String.valueOf(randomSeed.nextInt(999999999)+1);
		this.benefitCode=randomNumber;
		this.benefitDescription=benefitNameOptions[new Random().nextInt(benefitNameOptions.length)]+randomNumber;
		this.contableCode="55000004";//new FinancialDBHelper().getContableCodeByName("Internet Links and hosting services");
		this.maxAmount=amountOptions[new Random().nextInt(amountOptions.length)];
		this.periodCode=todayInMs;
	}

	public String getBenefitCode() {
		return benefitCode;
	}
	
	public void setBenefitCode(String benefitCode) {
		this.benefitCode = benefitCode;
	}
	
	public String getContableCode() {
		return contableCode;
	}
	
	public void setContableCode(String contableCode) {
		this.contableCode = contableCode;
	}
	
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public String getMaxAmount() {
		return maxAmount;
	}
	
	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getBenefitDescription() {
		return benefitDescription;
	}

	public void setBenefitDescription(String benefitDescription) {
		this.benefitDescription = benefitDescription;
	}

	public String getPeriodCode() {
		return periodCode;
	}

	public void setPeriodCode(String periodCode) {
		this.periodCode = periodCode;
	}
	
}
