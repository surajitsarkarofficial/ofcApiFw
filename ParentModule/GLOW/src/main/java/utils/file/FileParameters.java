package utils.file;

/**
 * @author german.massello
 *
 */
public class FileParameters {
	private String enzoServer;
	private String coorporativeUsername;
	private String coorporativePassword;
	private String fileOriginPath;
	private String fileOriginName;
	private String fileDestinationServer;
	private String fileDestinationPath;
	private String fileName;

	/**
	 * Default constructor
	 * @param enzoServer
	 * @param coorporativeUsername
	 * @param coorporativePassword
	 * @param fileOriginPath
	 * @param fileOriginName
	 * @param fileDestinationServer
	 * @param fileDestinationPath
	 * @param fileName
	 */
	public FileParameters(String enzoServer, String coorporativeUsername, String coorporativePassword,
			String fileOriginPath, String fileOriginName, String fileDestinationServer, String fileDestinationPath,
			String fileName) {
		this.enzoServer = enzoServer;
		this.coorporativeUsername = coorporativeUsername;
		this.coorporativePassword = coorporativePassword;
		this.fileOriginPath = fileOriginPath;
		this.fileOriginName = fileOriginName;
		this.fileDestinationServer = fileDestinationServer;
		this.fileDestinationPath = fileDestinationPath;
		this.fileName = fileName;
	}

	public String getEnzoServer() {
		return enzoServer;
	}

	public void setEnzoServer(String enzoServer) {
		this.enzoServer = enzoServer;
	}

	public String getCoorporativeUsername() {
		return coorporativeUsername;
	}

	public void setCoorporativeUsername(String coorporativeUsername) {
		this.coorporativeUsername = coorporativeUsername;
	}

	public String getCoorporativePassword() {
		return coorporativePassword;
	}

	public void setCoorporativePassword(String coorporativePassword) {
		this.coorporativePassword = coorporativePassword;
	}

	public String getFileOriginPath() {
		return fileOriginPath;
	}

	public void setFileOriginPath(String fileOriginPath) {
		this.fileOriginPath = fileOriginPath;
	}

	public String getFileDestinationServer() {
		return fileDestinationServer;
	}

	public void setFileDestinationServer(String fileDestinationServer) {
		this.fileDestinationServer = fileDestinationServer;
	}

	public String getFileDestinationPath() {
		return fileDestinationPath;
	}

	public void setFileDestinationPath(String fileDestinationPath) {
		this.fileDestinationPath = fileDestinationPath;
	}

	public String getFileOriginName() {
		return fileOriginName;
	}

	public void setFileOriginName(String fileOriginName) {
		this.fileOriginName = fileOriginName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
