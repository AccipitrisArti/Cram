package test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class VsiTesti {
	
	public static Test suite() {
		TestSuite suite = new TestSuite(VsiTesti.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(TestLogikaIgre.class);
		//$JUnit-END$
		return suite;
	}
}
