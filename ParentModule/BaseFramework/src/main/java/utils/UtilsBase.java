package utils;

import org.testng.Reporter;

import com.aventstack.extentreports.Status;

/**
 * @author german.massello
 *
 */
public class UtilsBase {
	
	/**
	 * Log
	 * @param log
	 */
	public static void log (String log) {
		ExtentHelper.getExtentTest().log(Status.INFO, log);
		Reporter.log(log, true);
	}

}
