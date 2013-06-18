package renderer.raytracer;

import java.util.Properties;

import scene.Scene;

public class RayTracerFactory {
	public static RayTracer createRayTracer(Scene scene, Properties prop) {
		String raytraceMode = prop.getProperty("raytrace.mode");
		if (raytraceMode.equals("shadow")){
			return new RayTracerWithShadow(scene);
		}
		else if(raytraceMode.equals("reflectivity")){
			return new RayTracerWithReflection(scene, prop); 
		}
		return new BasicRayTracer(scene);
		
	}
}
