package renderer.raytracer;

import java.util.Properties;

import scene.Scene;

public class RayTracerFactory {
	public static RayTracer createRayTracer(Scene scene, Properties prop) {
		String raytraceMode = prop.getProperty("raytrace.mode");
		String raytraceSuperSampling = prop.getProperty("raytrace.supersampling");
		RayTracer rayTracer = null; 
		
		if (raytraceMode.equals("shadow")){
			rayTracer = new RayTracerWithShadow(scene);
		}
		else if(raytraceMode.equals("reflectivity") || raytraceMode.equals("reflection")){
			rayTracer = new RayTracerWithReflection(scene, prop); 
		}
		else{
			rayTracer = new BasicRayTracer(scene);
		}
		
		if(raytraceSuperSampling != null){
			rayTracer.supersampling = Integer.parseInt(raytraceSuperSampling);
		}
		return rayTracer; 
	}
}