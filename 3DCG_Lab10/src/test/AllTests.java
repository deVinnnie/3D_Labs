package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ IntersectionTest.class, MatrixTest.class, RayTest.class,
		TranslateTransfoTest.class, RotateTransfoTest.class})
public class AllTests {

}
