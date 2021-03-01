package dto.submodules.expense.report;

/**
 * @author german.massello
 *
 */
public class ReportStatus {
	private String status; 
	private String author; 
	private String comments;
	
	public ReportStatus() {
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	} 
	
	
}
