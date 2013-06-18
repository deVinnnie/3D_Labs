package transfo;

public class TranslateTransfo extends Transfo {
	public TranslateTransfo(float tx, float ty, float tz){
		super();  
		this.mat.m[0][3]=tx; 
		this.mat.m[1][3]=ty; 
		this.mat.m[2][3]=tz; 
		this.mat.m[3][3]=1; 
		
		this.invMat.m[0][3]=-tx; 
		this.invMat.m[1][3]=-ty; 
		this.invMat.m[2][3]=-tz; 
		this.invMat.m[3][3]=1; 
	}
}
