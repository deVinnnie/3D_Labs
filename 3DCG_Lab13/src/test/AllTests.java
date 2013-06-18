package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BoxExtentTest.class, CameraTest.class,
		DifferenceBooleanTest.class, IntersectionTest.class, LightTest.class,
		MatrixTest.class, MeshTest.class, RayTest.class,
		RotateTransfoTest.class, TransfoStackTest.class,
		TranslateTransfoTest.class })
public class AllTests {

}
