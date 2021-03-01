package dto.submodules.staffRequest.features;

import java.util.List;

public class PositionDTOList {
	
	private String status;
	
	private String message;
	
	private List<DataDetails> data;
	
	public PositionDTOList(String status, String message, List<DataDetails> data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<DataDetails> getData() {
		return data;
	}

	public void setData(List<DataDetails> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "PositionDTOList [status=" + status + ", message=" + message + ", data=" + data + "]";
	}	
}

