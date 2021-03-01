package payloads.submodules.benefit.features.post;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.testng.Reporter;

/**
 * @author german.massello
 *
 */
public class PostBenefitPayLoadHelper {
	private List<BenefitsLot> benefitsLot=new LinkedList<>();

	
	/**
	 * This constructor will create a benefit payload for one glober.
	 * @param username
	 * @throws SQLException
	 */
	public PostBenefitPayLoadHelper(String username) throws SQLException {
		this.benefitsLot.add(new BenefitsLot(username));
	}
	
	/**
	 * This constructor will create a benefit payload for all globers.
	 * @param globersList
	 * @throws SQLException
	 */
	public PostBenefitPayLoadHelper(List<Map<String, String>> globersList) throws SQLException {
		for (Map<String, String> map : globersList) {
			this.benefitsLot.add(new BenefitsLot(map));
			Reporter.log(map.get("username")+" was added to the body", true);
		}
	}

	public List<BenefitsLot> getBenefitsLot() {
		return benefitsLot;
	}

	public void setBenefitsLot(List<BenefitsLot> benefitsLot) {
		this.benefitsLot = benefitsLot;
	}
	
	
}
