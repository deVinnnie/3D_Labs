package geomobj.mesh;

import java.util.ArrayList;

public class Face {

	public ArrayList<Integer> vertIndices;	// the list of vertex indices
	public ArrayList<Integer> normIndices;  // the list of normal indices
	
	public Face(){
		vertIndices = new ArrayList<Integer>();
		normIndices = new ArrayList<Integer>();
	}
	
	public Face(int size){
		vertIndices = new ArrayList<Integer>(size);
		normIndices = new ArrayList<Integer>(size);
	}
	
	public Face(ArrayList<Integer> vertIndices, ArrayList<Integer> normIndices){
		if(vertIndices.size() != normIndices.size()){
			throw new IllegalArgumentException("The size of the vertex and normal index lists should be the same!");
		}
		this.vertIndices = vertIndices;
		this.normIndices = normIndices;
	}
	
	public void add(int vertIndex, int normIndex){
		vertIndices.add(vertIndex);
		normIndices.add(normIndex);
	}
		
}
