package dto.submodules.interview;

public class TimezoneDTO {

	private String siteName;
	private String timeZone;
	private String dstStartDate;
	private String dstEndDate;
	public String getTimeZone() {
		return timeZone;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
	public String getDstStartDate() {
		return dstStartDate;
	}
	public void setDstStartDate(String dstStartDate) {
		this.dstStartDate = dstStartDate;
	}
	public String getDstEndDate() {
		return dstEndDate;
	}
	public void setDstEndDate(String dstEndDate) {
		this.dstEndDate = dstEndDate;
	}
	
}
