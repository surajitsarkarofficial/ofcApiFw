package dto.submodules.staffRequest.features;

public class RequestExtensionDTO {
	String extensionDate;
	String extensionReason;
	String comment;
	
	public RequestExtensionDTO(String extensionDate, String extensionReason, String comment) {
		this.extensionDate = extensionDate;
		this.extensionReason = extensionReason;
		this.comment = comment;
	}

	public RequestExtensionDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getExtensionDate() {
		return extensionDate;
	}

	public void setExtensionDate(String extensionDate) {
		this.extensionDate = extensionDate;
	}

	public String getExtensionReason() {
		return extensionReason;
	}

	public void setExtensionReason(String extensionReason) {
		this.extensionReason = extensionReason;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
