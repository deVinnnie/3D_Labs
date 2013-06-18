package transfo;

import util.Vector;

public class RotateTransfo extends Transfo {

	public RotateTransfo(float angle, float x, float y, float z) {
		super(); 
		
		float angleInRad = (float) Math.toRadians(angle); 
		
		Vector r = new Vector(x,y,z); 
		r.normalize();
		
		float cosTheta = (float) Math.cos(angleInRad); 
		float sinTheta = (float) Math.sin(angleInRad);  
		
		this.mat.m[0][0]=cosTheta+((r.x*r.x)*(1-cosTheta)); 
		this.mat.m[0][1]=(r.x*r.y*(1-cosTheta))-(r.z*sinTheta);
		this.mat.m[0][2]=(r.x*r.z*(1-cosTheta))+(r.y*sinTheta);
		this.mat.m[0][3]=0; 
		
		this.mat.m[1][0]=(r.x*r.y*(1-cosTheta))+(r.z*sinTheta); 
		this.mat.m[1][1]=cosTheta+(r.y*r.y*(1-cosTheta));
		this.mat.m[1][2]=(r.y*r.z*(1-cosTheta))-(r.x*sinTheta);
		this.mat.m[1][3]=0; 
		
		this.mat.m[2][0]=(r.x*r.z*(1-cosTheta))-(r.y*sinTheta);
		this.mat.m[2][1]=(r.y*r.z*(1-cosTheta))-(r.x*sinTheta);
		this.mat.m[2][2]=cosTheta+(r.z*r.z*(1-cosTheta));
		this.mat.m[2][3]=0;
		
		//Inverse Matrix 
		sinTheta = -sinTheta;  
		
		this.invMat.m[0][0]=cosTheta+((r.x*r.x)*(1-cosTheta)); 
		this.invMat.m[0][1]=(r.x*r.y*(1-cosTheta))-(r.z*sinTheta);
		this.invMat.m[0][2]=(r.x*r.z*(1-cosTheta))+(r.y*sinTheta);
		this.invMat.m[0][3]=0; 
		
		this.invMat.m[1][0]=(r.x*r.y*(1-cosTheta))+(r.z*sinTheta); 
		this.invMat.m[1][1]=cosTheta+(r.y*r.y*(1-cosTheta));
		this.invMat.m[1][2]=(r.y*r.z*(1-cosTheta))-(r.x*sinTheta);
		this.invMat.m[1][3]=0; 
		
		this.invMat.m[2][0]=(r.x*r.z*(1-cosTheta))-(r.y*sinTheta);
		this.invMat.m[2][1]=(r.y*r.z*(1-cosTheta))-(r.x*sinTheta);
		this.invMat.m[2][2]=cosTheta+(r.z*r.z*(1-cosTheta));
		this.invMat.m[2][3]=0;
	}
	
	
	
}
