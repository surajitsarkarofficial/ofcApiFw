package utils;

import java.io.File;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentHelper {

	public static ExtentReports extent = new ExtentReports();
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentTest test, featureTest, subModuleTest, moduleTest;

	/**
	 * This method will configure the extent report
	 * 
	 * @param executionEnv
	 * @return
	 */
	public static ExtentReports configureExtentReport(String executionEnv) {
		String reportName = String.format("ExecutionReport_%s_%s.html", executionEnv,
				Utilities.getCurrentDateAndTime("ddMMMyy_hh_mm"));
		File dir = new File(System.getProperty("user.dir") + "/Reports");
		// Create folder if it doesnot exist
		if (!dir.exists()) {
			dir.mkdir();
		}
		String reportingPath = dir + "/" + reportName;

		htmlReporter = new ExtentHtmlReporter(reportingPath);
		extent.attachReporter(htmlReporter);
		htmlReporter.config().setDocumentTitle("API Automation");
		htmlReporter.config().setReportName("API Automation Report");
		htmlReporter.config().setTheme(Theme.DARK);
		return extent;
	}
	
	/**
	 * This method will fetch the extent test object which is not null
	 * hierarchy - test-->feature-->submodule-->module
	 * @return
	 */
	public static ExtentTest getExtentTest()
	{
		if (ExtentHelper.test==null)
		{
			if(ExtentHelper.featureTest==null)
			{
				if(ExtentHelper.subModuleTest==null)
				{
					return ExtentHelper.moduleTest ;
				}else {
					return ExtentHelper.subModuleTest;
				}
				
			}else {
				return ExtentHelper.featureTest;
			}
		}else {
			return ExtentHelper.test;
		}
	}

}
