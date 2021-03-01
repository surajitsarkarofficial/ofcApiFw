package listeners;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.IDataProviderAnnotation;

/**
 * This class can be used to limit data provider iteration for sanity test suite
 * @author deepakkumar.hadiya
 *
 */

public abstract class DisableDataProviderIterations implements IAnnotationTransformer{

	@Override
	public void transform(IDataProviderAnnotation annotation, Method method) {
		List<Integer> indices=new ArrayList<>();
		indices.add(0); // For executing test case with only single data
		annotation.setIndices(indices);
	}
	}
