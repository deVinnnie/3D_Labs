package transfo;

import util.Matrix;

public class Transfo {
	public Matrix mat;
	public Matrix invMat;

	public Transfo() {
		mat = new Matrix();
		invMat = new Matrix();
	}
	
	public Transfo(Transfo transfo){
		mat = new Matrix(transfo.mat);
		invMat = new Matrix(transfo.invMat);
	}
	
	public void transform(Transfo transfo){
		this.mat.postMult(transfo.mat); 
		this.invMat.preMult(transfo.invMat); 
	}
	
	@Override
	public boolean equals(Object o){
		boolean isEqual =false; 
		if(o instanceof Transfo){
			Transfo t2 = (Transfo) o; 
			if(this.mat.equals(t2.mat) &&
					this.invMat.equals(t2.invMat)){
				isEqual = true;
			}
		}
		return isEqual; 
	}
	
}