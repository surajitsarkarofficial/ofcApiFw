package parameters;

/**
 * @author german.massello
 *
 */
public abstract class FinancialParameters {
	protected String pageSize;
	protected String pageNum;
	protected String limit;
	protected String sortAscending;
	protected int statusCode;

	/**
	 * Default constructor.
	 */
	public FinancialParameters() {
		this.pageSize="0";
		this.pageNum="0";
		this.sortAscending="true";
		this.statusCode=200;
	}
	
	public String getPageSize() {
		return pageSize;
	}
	
	public FinancialParameters setPageSize(String pageSize) {
		this.pageSize = pageSize;
		return this;
	}
	
	public String getPageNum() {
		return pageNum;
	}
	public FinancialParameters setPageNum(String pageNum) {
		this.pageNum = pageNum;
		return this;
	}
	public String getSortAscending() {
		return sortAscending;
	}
	public FinancialParameters setSortAscending(String sortAscending) {
		this.sortAscending = sortAscending;
		return this;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public FinancialParameters setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
