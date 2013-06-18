package geomobj.mesh;

import geomobj.Shape;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import renderer.HitInfo;
import renderer.Intersection;
import renderer.Ray;

import util.Point;
import util.Vector;

public class Mesh extends Shape{
	
	private ArrayList<Point> verts;	    // vertex list
	private ArrayList<Vector> norms;	// normal list
	private ArrayList<Face> faces;	    // face list
	
	public Mesh(String fileName){
		try{
			readFile(fileName);
		}
		catch(Exception e){
			e.printStackTrace(); 
		}
	}
	
	private void readFile(String fileName) throws FileNotFoundException{
		// to do
		File file = new File(fileName); 
		Scanner scanner = new Scanner(file); 
		scanner.useLocale(Locale.US); 
		
		int nVerts = scanner.nextInt(); 
		int nNorms = scanner.nextInt(); 
		int nFaces = scanner.nextInt(); 
		
		verts = new ArrayList<Point>(nVerts); 
		for(int i = 0; i<nVerts; i++){
			float x = scanner.nextFloat();
			float y = scanner.nextFloat(); 
			float z = scanner.nextFloat(); 
			Point p = new Point(x,y,z);
			verts.add(p);
		}
		
		norms = new ArrayList<Vector>(); 
		for(int i = 0; i<nNorms; i++){
			float x = scanner.nextFloat();
			float y = scanner.nextFloat(); 
			float z = scanner.nextFloat(); 
			Vector v = new Vector(x,y,z); 
			norms.add(v);
		}
		
		faces = new ArrayList<Face>(nFaces);
		for(int i = 0; i<nFaces; i++){
			int nVertsInFace = scanner.nextInt(); 
			//Face f = new Face(nVertsInFace);
			
			ArrayList<Integer> vertIndices = new ArrayList<Integer>(nVertsInFace); 
			ArrayList<Integer> normIndices = new ArrayList<Integer>(nVertsInFace);  
			
			for(int j = 0; j<nVertsInFace; j++){
				Integer n = new Integer(scanner.nextInt());
				vertIndices.add(n); 
			}
			
			for(int j = 0; j<nVertsInFace; j++){
				Integer n = new Integer(scanner.nextInt());
				normIndices.add(n); 
			}
			
			Face face = new Face(vertIndices, normIndices); 	
			int n1 = face.normIndices.get(0); 
			boolean oneNormal = true;
			
			for(int n2 : face.normIndices){
				if(n1!=n2){
					oneNormal = false; 
					break;
				}
			}
			face.oneNormal = oneNormal; 
			
			if(!oneNormal){
				Point p1 = this.verts.get(face.vertIndices.get(0));
				Point p2 = this.verts.get(face.vertIndices.get(1));
				Point p3 = this.verts.get(face.vertIndices.get(2));
				
				Vector v1 = new Vector(p1,p2); 
				Vector v2 = new Vector(p1,p3); 
				
				Vector vn = v1.getCross(v2); 
				face.facePlaneNormal = vn; 
				
				
			}
			else{
				face.facePlaneNormal = this.norms.get(n1);
			}
			faces.add(face); 
		}
	}
	
	public void draw(GLAutoDrawable drawable){
		GL2 gl = drawable.getGL().getGL2();
		for(Face face: faces){
			gl.glBegin(GL2.GL_POLYGON);  
			for(int i=0; i<face.vertIndices.size();i++){
				Vector normal = this.norms.get(face.normIndices.get(i));   
				Point vertex = this.verts.get(face.vertIndices.get(i)); 
				gl.glNormal3f(normal.x, normal.y,normal.z);
				gl.glVertex3f(vertex.x, vertex.y,vertex.z);
			}
            gl.glEnd();
		}
	}

	/*
	public Intersection intersection(Ray ray){
		Ray invRay = ray.getInvTransfoRay(this.transfo.invMat);
		Intersection result = new Intersection();
		HitInfo minHit = null;

		for (Face face : faces) {
			Vector m = face.facePlaneNormal;

			if (Math.abs(m.dot(invRay.dir)) < 0.0000001) {
				continue;
			}

			Point a = verts.get(face.vertIndices.get(0));

			float thit = (m.dot(new Vector(invRay.start,a)))/(m.dot(invRay.dir));
			Point hitPoint = invRay.getPoint(thit);
			
			boolean inside = true;
			Point b;
			for (int i = 0; inside && i < face.vertIndices.size()-1; i++) {
				a = verts.get(face.vertIndices.get(i));
				b = verts.get(face.vertIndices.get(i+1));
				Vector v1 = new Vector(a,b);
				Vector v2 = new Vector(a, hitPoint); 
				Vector v3 = v1.getCross(v2);
				inside = v3.dot(m) > 0;
			}

			a = verts.get(face.vertIndices.get(face.vertIndices.size()-1));
			b = verts.get(face.vertIndices.get(0));
			Vector v1 = new Vector(a,b); 
			Vector v2 = new Vector(a,hitPoint);
			Vector v3 = v1.getCross(v2);
			inside &= v3.dot(m) > 0;


			if (inside) {
				if (!face.oneNormal) {
					m = interpolate(face, hitPoint);
				}

				m = this.transfo.invMat.getTranspose().mult(m);

				HitInfo tmp = new HitInfo(thit, this.mtrl, invRay.getPoint(thit), m, true);
				if (minHit == null || minHit.t > thit) {
					minHit = tmp;
				}
			}
		}
		if (minHit != null) {
			result.add(minHit);
		}
		return result;
	}
	
	*/
	@Override
	public Intersection intersection(Ray ray) {
		Ray rayInv = ray.getInvTransfoRay(this.transfo.invMat); 
		Intersection intersection = new Intersection(); 
		
		for(Face face : faces){
			Vector m = face.facePlaneNormal; //norms.get(face.normIndices.get(0)); 
			float mDotDir = m.dot(rayInv.dir); 
			
			if(Math.abs(mDotDir)<0.0000001){
				//Ignore Face
				continue; 
			}
			
			//Compute t-hit
			Point A = verts.get(face.vertIndices.get(0)); 
			float Thit = (m.dot(new Vector(rayInv.start, A)) / mDotDir); 
			
			if(Thit < 0){
				//Ignore Face
				continue; 
			}
			
			//Traverse edges
			Point t = rayInv.getPoint(Thit);  
			if(!pointInFace(t, face, m)){
				//Ignore Face
				continue; 
			}
			
			boolean isEntering= (mDotDir<0) ? true : false; 
			
			if(!face.oneNormal){
				m = this.interpolate(face, t);
			}
			
			t = ray.getPoint(Thit);
			Vector realHitNormalVector = this.transfo.invMat.getTranspose().mult(m); 
			realHitNormalVector.normalize(); 
			
			intersection.add(new HitInfo(Thit, this.mtrl, t,realHitNormalVector,isEntering)); 
		}
		
		if(intersection.getNumHits()>0){
			HitInfo bestHit = intersection.getHit(0); 
			for(int i=1; i<intersection.getNumHits(); i++){
				if(intersection.getHit(i).t<bestHit.t){
					bestHit = intersection.getHit(i); 
				}
			}
			intersection.hits.remove(bestHit); 
			intersection.hits.add(0, bestHit); 
		}
		return intersection;
	}
	
	private Vector interpolate(Face face, Point p){
		Vector result = null;

		Point p1 = verts.get(face.vertIndices.get(0));
		Point p2 = verts.get(face.vertIndices.get(1));
		Point p3 = verts.get(face.vertIndices.get(2));

		float x03 = p.x - p3.x;
		float y03 = p.y - p3.y;
		float z03 = p.z - p3.z;

		float x13 = p1.x - p3.x;
		float y13 = p1.y - p3.y;
		float z13 = p1.z - p3.z;

		float x23 = p2.x - p3.x;
		float y23 = p2.y - p3.y;
		float z23 = p2.z - p3.z;

		float a = (float) (Math.pow(x13, 2) + Math.pow(y13, 2) + Math.pow(z13, 2));
		float b = x13 * x23 + y13 * y23 + z13 * z23;
		float c = (float) (Math.pow(x23, 2) + Math.pow(y23, 2) + Math.pow(z23, 2));
		float d = (float) (a * c - Math.pow(b, 2));

		float l1 = ((c * x13 - b * x23) * x03 + (c * y13 - b * y23) * y03 + (c * z13 - b * z23) * z03)/ d;
		float l2 = ((-b * x13 + a * x23) * x03 + (-b * y13 + a * y23) * y03 + (-b * z13 + a * z23) * z03)/ d;
		float l3 = 1 - l1 - l2;

		Vector v1 = new Vector(norms.get(face.normIndices.get(0)));
		Vector v2 = new Vector(norms.get(face.normIndices.get(1)));
		Vector v3 = new Vector(norms.get(face.normIndices.get(2)));

		v1.mult(l1);
		v2.mult(l2);
		v3.mult(l3);

		v1.add(v2);
		v1.add(v3);

		result = v1;

		result.normalize();

		return result;
	}
	
	public boolean pointInFace(Point point, Face face, Vector m){
		boolean inFace=true; 
		Point A, B; 
		
		for(int i=0; inFace && i<face.vertIndices.size()-1;i++){
			A = verts.get(face.vertIndices.get(i));
			B = verts.get(face.vertIndices.get(i+1));
			Vector v1 = new Vector(A,B);
			Vector v2 = new Vector(A,point); 
			Vector v3 = v1.getCross(v2); 
			inFace = (v3.dot(m) > 0); 
		}
		
		A = verts.get(face.vertIndices.get(face.vertIndices.size()-1));
		B = verts.get(face.vertIndices.get(0));
		Vector v1 = new Vector(A,B); 
		Vector v2 = new Vector(A,point);
		Vector v3 = v1.getCross(v2);
		inFace &= v3.dot(m) > 0;
		return inFace;
	}

	@Override
	public boolean hit(Ray ray) {
		Ray rayInv = ray.getInvTransfoRay(this.transfo.invMat); 
		
		for(Face face : this.faces){
			Vector m = norms.get(face.normIndices.get(0)); 
			float mDotDir = m.dot(rayInv.dir); 
			if(mDotDir<0.0000001){
				//Ignore Face
				continue; 
			}
			
			//Compute t-hit
			Point A = verts.get(face.vertIndices.get(0)); 
			float Thit = m.dot(new Vector(rayInv.start, A)) / mDotDir; 
			
			if(Thit < 0){
				//Ignore Face
				continue; 
			}
			
			//Traverse edges
			Point t = rayInv.getPoint(Thit); 
			boolean inFace = pointInFace(t, face, m);
			if(inFace == false){
				//Ignore Face
				continue; 
			}
			else{
				return true; 
			}
		}
		return false;
	}
	
	/*Testing*/
	public List<Face> getFaces(){
		return this.faces; 
	}
}