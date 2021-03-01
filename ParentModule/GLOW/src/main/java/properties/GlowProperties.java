package properties;

public abstract class GlowProperties{

	public static String executionEnvironment;
	public static String database;
	public static String dbUser;
	public static String dbPassword;
	public static String baseURL;
	public static String alberthaBaseURL;
	public static String staffingorchestraBaseURL;
	public int requestTimeOutInSeconds=15;
	public static String alberthaOpenPositionDB;
	public static String glowVIQAMs;
	public static String batchServer;
	public static String microserviceBaseURL;
	public static String enzoServer = "enzo.corp.globant.com";
	public static String coorporativeUsername;
	public static String coorporativePassword;
	
	/**
	 * This method will configure the properties based on environment set
	 * @throws Exception 
	 * 
	 */
	public abstract void configureProperties() throws Exception;	
	
	/**
	 * This method will set the execution environment.
	 * If the 'executionEnvironment' is null or empty then
	 * Value coming form pom.xml will be set.
	 */
	public static String setEnvironment()
	{
		if(executionEnvironment==null || executionEnvironment.toString().equals(""))
		{
			executionEnvironment = System.getProperty("Environment").toUpperCase();
		}
		return executionEnvironment;
	}

}
