package util;

public class Matrix {
	
	public float[][] m = new float[4][4];
	
	public Matrix(){
		for(int i=0; i<4;i++){
			m[i][i]=1;
			for(int j=i+1;j<4;j++){
				m[i][j] = 0;
				m[j][i] = 0;
			}		
		}
	}
	
	public Matrix(Matrix a){
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				m[i][j] = a.m[i][j];
			}
		}
	}
	
	public Point mult(Point p){
		float[] ph = new float[4]; 
		ph[0]=p.x; 
		ph[1]=p.y; 
		ph[2]=p.z; 
		ph[3]=1; 
		
		float[] np = new float[3]; 
		
		for(int i=0; i<3; i++){
			float res = 0; 
			for(int j=0; j<4; j++){
				res+= m[i][j]*ph[j]; 
			}
			np[i]=res; 
		}
		return new Point(np[0],np[1],np[2]);  
	}
	
	public Vector mult(Vector v){
		float[] vh = new float[4]; 
		vh[0]=v.x; 
		vh[1]=v.y; 
		vh[2]=v.z; 
		vh[3]=0; 
		
		float[] nv = new float[3]; 
		
		for(int i=0; i<3; i++){
			float res = 0; 
			for(int j=0; j<4; j++){
				res+= m[i][j]*vh[j]; 
			}
			nv[i]=res; 
		}
		return new Vector(nv[0],nv[1],nv[2]);
	}
	
	public Matrix getTranspose(){
		Matrix res = new Matrix();
		for(int i=0;i<4;i++){
			res.m[i][i]=m[i][i];
			for(int j=i;j<4;j++){
				res.m[i][j]=m[j][i];
				res.m[j][i]=m[i][j];
			}
		}
		return res;
	}
}
