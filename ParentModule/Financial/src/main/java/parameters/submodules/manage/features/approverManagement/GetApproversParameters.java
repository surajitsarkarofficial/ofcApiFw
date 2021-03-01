package parameters.submodules.manage.features.approverManagement;

/**
 * @author german.massello
 *
 */
public class GetApproversParameters {

	private String module;
	private String projectId;
	private String amount;
	
	/**
	 * Default constructor.
	 */
	public GetApproversParameters() {
		this.module="invoices";
		this.projectId="38";
		this.amount="1000000";
	}

	public String getModule() {
		return module;
	}
	
	public GetApproversParameters setModule(String module) {
		this.module = module;
		return this;
	}
	
	public String getProjectId() {
		return projectId;
	}
	
	public GetApproversParameters setProjectId(String projectId) {
		this.projectId = projectId;
		return this;
	}
	
	public String getAmount() {
		return amount;
	}
	
	public GetApproversParameters setAmount(String amount) {
		this.amount = amount;
		return this;
	}
}
