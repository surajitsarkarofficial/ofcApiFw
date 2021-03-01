package listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.ITestAnnotation;

import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

public class DisableDataProviderIterationsForStaffing extends  DisableDataProviderIterations{

	/**This method is used to exclude test method from sanity suite execution on preprod environment 
	 * @author deepakkumar.hadiya
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {  
		List<String> groups = Arrays.asList(annotation.getGroups());
		if (System.getProperty("Environment").equalsIgnoreCase("PreProd")) {
			if (groups.contains(ExeGroups.Sanity) && groups.contains(ExeGroups.NotAvailableInPreProd)) {
				annotation.setEnabled(false);
			}
		}
	}
}
