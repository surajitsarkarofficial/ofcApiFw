package listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import utils.GlowEnums.ExecutionSuiteGroups.ExeGroups;

public class DisableNotAvailableInPreProdForRegression implements IAnnotationTransformer{

	/**This method is used to exclude test cases having NotAvailableInPreProd group from execution on preprod environment for regression test suite 
	 * @author deepakkumar.hadiya
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {  
		List<String> groups = Arrays.asList(annotation.getGroups());
		if (System.getProperty("Environment").equalsIgnoreCase("PreProd")) {
			if (groups.contains(ExeGroups.Regression) && groups.contains(ExeGroups.NotAvailableInPreProd)) {
				annotation.setEnabled(false);
			}
		}
	}
	
}
