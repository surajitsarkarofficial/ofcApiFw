package utils.file;

import java.util.ArrayList;
import java.util.List;

import org.testng.SkipException;
import utils.UtilsBase;
import utils.ssh.SshConnectionManager;

/**
 * @author german.massello
 *
 */
public class CopyFileHelper extends UtilsBase {
	
	/**
	 * This method will copy a file from local to a specific server path.
	 * @param parameters
	 */
	public static void fileCopyToServer(FileParameters parameters) {
		SshConnectionManager.connect(parameters.getEnzoServer(), parameters.getCoorporativeUsername(), parameters.getCoorporativePassword());
		List<String> commands = new ArrayList<String>();
		if (parameters.getFileDestinationServer().equals("")) throw new SkipException("CopyFileHelper.fileCopyToServer has failed. "
				+ "Check the batchServer properite in the Financial<qa|dev|prepro>Properties.java file ");
		log("- Send: ssh "+parameters.getFileDestinationServer());
		commands.add("rm -r *");
		commands.add("ssh "+parameters.getFileDestinationServer());
		SshConnectionManager.executeCommands(commands,"Password:");
		log("- Send password");
		commands.clear();
		commands.add(parameters.getCoorporativePassword());
		SshConnectionManager.executeCommands(commands,"@glow-");
		commands.clear();
		log("- Copy file to enzo");
		if(SshConnectionManager.putFile(parameters.getCoorporativeUsername(), parameters.getFileName(), parameters.getFileOriginPath())!=null) throw new SkipException("CopyFileHelper.fileCopy() is not working.");
		commands.clear();
		log("- sudo su tomcat");
		commands.add("sudo su tomcat");
		SshConnectionManager.executeCommands(commands,"sudo");
		log("- Send password");
		commands.clear();
		commands.add(parameters.getCoorporativePassword());
		SshConnectionManager.executeCommands(commands,"tomcat@glow-");
		commands.clear();
		log("- Copy file to destination path");
		String commandToAdd = "cp /home/local/GLOBANT/"+parameters.getCoorporativeUsername()+"/"+parameters.getFileName()+parameters.getFileDestinationPath();
		commands.add(commandToAdd); 
		log(commandToAdd);
		SshConnectionManager.executeCommands(commands,"tomcat@glow-");
		SshConnectionManager.close();
	}
}
