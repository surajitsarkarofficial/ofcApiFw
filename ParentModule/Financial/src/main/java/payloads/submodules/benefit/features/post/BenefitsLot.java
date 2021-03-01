package payloads.submodules.benefit.features.post;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import database.FinancialDBHelper;

/**
 * @author german.massello
 *
 */
public class BenefitsLot {
	
	private List<Benefit> benefits=new LinkedList<>();
	private String globalId;
	
	/**
	 * This constructor will create a benefit lot.
	 * @param map
	 * @throws SQLException
	 */
	public BenefitsLot(Map<String, String> map) throws SQLException {
		this.benefits.add(new Benefit(map));
		this.globalId=map.get("globalId");
	}

	/**
	 * This constructor will create a benefit lot.
	 * @param username
	 * @throws SQLException
	 */
	public BenefitsLot(String username) throws SQLException {
		this.benefits.add(new Benefit(username));
		this.globalId=new FinancialDBHelper().getGlobalIdByUsername(username);
	}
	
	public String getGlobalId() {
		return globalId;
	}
	
	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}

	public List<Benefit> getBenefits() {
		return benefits;
	}

	public void setBenefits(List<Benefit> benefits) {
		this.benefits = benefits;
	}
	
}
