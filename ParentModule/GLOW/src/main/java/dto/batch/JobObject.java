package dto.batch;

/**
 * @author german.massello
 *
 */
public class JobObject {

	private String resource;
	private String id;
	private String name;
	private String status;
	private String startTime;
	private String duration;
	private String exitCode;
	private String exitDescription;

	public JobObject(String resource, String id, String name, String status, String startTime, String duration,
			String exitCode, String exitDescription) {
		this.resource = resource;
		this.id = id;
		this.name = name;
		this.status = status;
		this.startTime = startTime;
		this.duration = duration;
		this.exitCode = exitCode;
		this.exitDescription = exitDescription;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getExitCode() {
		return exitCode;
	}

	public void setExitCode(String exitCode) {
		this.exitCode = exitCode;
	}

	public String getExitDescription() {
		return exitDescription;
	}

	public void setExitDescription(String exitDescription) {
		this.exitDescription = exitDescription;
	}
	


}
