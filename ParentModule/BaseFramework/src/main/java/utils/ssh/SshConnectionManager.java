package utils.ssh;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Properties;

import org.testng.SkipException;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import utils.UtilsBase;

/**
 * @author german.massello
 *
 */
public class SshConnectionManager extends UtilsBase {

	private static Session session;
	private static ChannelShell channel;
	private static String username = "";
	private static String password = "";
	private static String hostname = "";

	/**
	 * Get session
	 * @return {@link Session}
	 */
	public static Session getSession() {
		if (session == null || !session.isConnected()) {
			session = connect(hostname, username, password);
		}
		log("getSession");
		return session;
	}

	/**
	 * Get Channel
	 * @return {@link Channel}
	 */
	public static Channel getChannel() {
		if (channel == null || !channel.isConnected()) {
			try {
				channel = (ChannelShell) getSession().openChannel("shell");
				channel.connect();
			} catch (Exception e) {
				throw new SkipException("SshConnectionManager.getChannel() is not working.");
			}
		}
		log("getChannel");
		return channel;
	}
	
	/**
	 * Get SFTP
	 * @return {@link ChannelSftp}
	 */
	public static ChannelSftp getSFTP() {
		ChannelSftp channel = null;
		if (channel == null || !channel.isConnected()) {
			try {
				channel = (ChannelSftp) getSession().openChannel("sftp");
				channel.connect();
			} catch (Exception e) {
				throw new SkipException("SshConnectionManager.getChannel() is not working.");
			}
		}
		log("getSFTP");
		return channel;
	}

	/**
	 * Connect to
	 * @param hostname
	 * @param username
	 * @param password
	 * @return {@link Session}
	 */
	public static Session connect(String hostname, String username, String password) {
		JSch jSch = new JSch();
		try {
			session = jSch.getSession(username, hostname, 22);
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setPassword(password);
			session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
			log("Connecting SSH to " + hostname + " - Please wait for few seconds... ");
			session.connect();
			log("Connected! with username "+username);
		} catch (Exception e) {
			throw new SkipException("SshConnectionManager.connect() is not working. "
					+ "Check coorporative username and password in the FinancialProperties file");
		}
		log("connect");
		return session;
	}

	/**
	 * Execute Commands
	 * @param commands
	 */
	public static void executeCommands(List<String> commands, String expectedServerCursor) {
		try {
			Channel channel = getChannel();
			log("Sending commands...");
			sendCommands(channel, commands);
			log("expectedServerCursor: "+expectedServerCursor);
			readChannelOutput(channel, expectedServerCursor);
			log("Finished sending commands!");
		} catch (Exception e) {
			throw new SkipException("SshConnectionManager.executeCommands() is not working.");
		}
	}
	
	/**
	 * Get File
	 * @return {@link Exception}
	 */
	public static Exception getFile(String user, String fileName, String pathToCopy) {
		try {
			ChannelSftp c = getSFTP();
			try {
				log("Get File to local");
				c.get("/home/local/GLOBANT/" + user + "/" + fileName, pathToCopy);
			} catch (Exception e) {
				throw new SkipException("SshConnectionManager.getFile() to local is not working.");
			}
			c.disconnect();
			session.disconnect();
		} catch (Exception e) {
			throw new SkipException("SshConnectionManager.getFile() to local is not working.");
		}
		return null;
	}

	/**
	 * Put File
	 * @return {@link Exception}
	 */
	public static Exception putFile(String user, String fileName, String pathToCopy) {
		try {
			ChannelSftp c = getSFTP();
			try {
				log("Put File to enzo");
				c.put(pathToCopy  +  fileName, "/home/local/GLOBANT/"+ user);
				log ("copy:" + pathToCopy  +  fileName + " to " + "enzo.../home/local/GLOBANT/"+ user);
			} catch (Exception e) {
				throw new SkipException("SshConnectionManager.putFile() is not working.");
			}
			c.disconnect();
		} catch (Exception e) {
			throw new SkipException("SshConnectionManager.putFile() is not working.");
		}
		return null;
	}
	
	/**
	 * Send Commands
	 * @param channel
	 * @param commands
	 */
	public static void sendCommands(Channel channel, List<String> commands) {
		try {
			PrintStream out = new PrintStream(channel.getOutputStream());
			for (String command : commands) {
				out.println(command);
			}
			out.flush();
		} catch (Exception e) {
			throw new SkipException("SshConnectionManager.sendCommands() is not working.");
		}
	}

	/**
	 * Read channel
	 * @param channel
	 */
	public static void readChannelOutput(Channel channel, String expectedServerCursor) {
		Boolean executionInProgress = true;
		long startTime = System.currentTimeMillis();
		int timeOut = 10000;
		byte[] buffer = new byte[1024];
		try {
			InputStream in = channel.getInputStream();
			String line = "";
			while (executionInProgress&&(System.currentTimeMillis()-startTime)<timeOut) {
				if (line.contains(expectedServerCursor))
					break;
				if (line.contains("lost connection"))
					break;
				while (in.available() > 0) {
					int i = in.read(buffer, 0, 1024);
					if (i < 0)
						break;
					line = new String(buffer, 0, i);
				}
				if (line.contains("logout"))
					break;
				if (channel.isClosed())
					break;
			}
			if((System.currentTimeMillis()-startTime)>timeOut) {
				log ("expected server cursor: "+expectedServerCursor);
				log ("actual server cursor: "+line);				
				throw new SkipException("SshConnectionManager.readChannelOutput has failed by a timeout."
					+ "Check the expectedServerCursor variable");
			}
		} catch (Exception e) {
			throw new SkipException("SshConnectionManager.readChannelOutput() is not working.");
		}
	}
	
	/**
	 * Close channel & session
	 */
	public static void close() {
		channel.disconnect();
		session.disconnect();
		log("Disconnected channel and session");
	}

}
