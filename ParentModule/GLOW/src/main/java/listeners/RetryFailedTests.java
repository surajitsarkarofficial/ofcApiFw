package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * This class can be used to re-execute failed test cases for mentioned maxRetrycount
 * @author deepakkumar.hadiya
 *
 */

public class RetryFailedTests implements IRetryAnalyzer{

	private int retryCount = 0;
    private int maxRetryCount = 1;
	
    @Override
	public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            return true;
        }
        return false;
    }

    
}
