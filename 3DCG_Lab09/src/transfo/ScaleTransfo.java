package transfo;

public class ScaleTransfo extends Transfo {
	public ScaleTransfo(float sx,float sy,float sz){
		super(); 
		this.mat.m[0][0] = sx; 
		this.mat.m[1][1] = sy; 
		this.mat.m[2][2] = sz;
		
		this.invMat.m[0][0] = (float) (1.0/sx); 
		this.invMat.m[1][1] = (float) (1.0/sy);
		this.invMat.m[2][2] = (float) (1.0/sz); 
	}
}
