package parameters.submodules;

import utils.Utilities;

/**
 * @author german.massello
 *
 */
public class PurchasesParameters {

	protected String status;
	protected String state;
	protected String isGlobalView;
	protected String userName;
	protected String pageSize;
	protected String pageNum;
	protected String isPurchaserView;
	protected String isApprovalView;
	protected String sapApprovalView;
	protected static String [] amountOptions = {"1.99", "7.99", "8.99", "9.99", "10.99", "11.99", "12.99"};
	protected String todayInMs = Utilities.getTodayInMilliseconds();

	/**
	 * Default constructor
	 */
	public PurchasesParameters() {
		this.pageNum="0";
		this.pageSize="15";
		this.isApprovalView="false";
		this.sapApprovalView="false";
		this.isGlobalView="false";
		this.isPurchaserView="false";
		this.status="";
		this.state="";
	}
	public String getStatus() {
		return status;
	}
	public PurchasesParameters setStatus(String status) {
		this.status = status;
		return this;
	}
	public String getIsGlobalView() {
		return isGlobalView;
	}
	public PurchasesParameters setIsGlobalView(String isGlobalView) {
		this.isGlobalView = isGlobalView;
		return this;
	}
	public String getUserName() {
		return userName;
	}
	public PurchasesParameters setUserName(String userName) {
		this.userName = userName;
		return this;
	}
	public String getPageSize() {
		return pageSize;
	}
	public PurchasesParameters setPageSize(String pageSize) {
		this.pageSize = pageSize;
		return this;
	}
	public String getPageNum() {
		return pageNum;
	}
	public PurchasesParameters setPageNum(String pageNum) {
		this.pageNum = pageNum;
		return this;
	}
	public String getState() {
		return state;
	}
	public PurchasesParameters setState(String state) {
		this.state = state;
		return this;
	}
	public String getIsPurchaserView() {
		return isPurchaserView;
	}
	public PurchasesParameters setIsPurchaserView(String isPurchaserView) {
		this.isPurchaserView = isPurchaserView;
		return this;
	}
	public String getIsApprovalView() {
		return isApprovalView;
	}
	public PurchasesParameters setIsApprovalView(String isApprovalView) {
		this.isApprovalView = isApprovalView;
		return this;
	}
	public String getSapApprovalView() {
		return sapApprovalView;
	}
	public PurchasesParameters setSapApprovalView(String sapApprovalView) {
		this.sapApprovalView = sapApprovalView;
		return this;
	}
	public String getTodayInMs() {
		return todayInMs;
	}
	
}
