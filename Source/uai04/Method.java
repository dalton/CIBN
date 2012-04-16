package uai04;
/*
 *  * @author Lexin Liu
 *  
 */
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import BayesianNetworks.DiscreteVariable;
import BayesianNetworks.ProbabilityVariable;
import Identifiability.Q;
import Identifiability.QFraction;


public class Method {
	public String print_Vector(PrintStream out, Vector<ProbabilityVariable> S){
		Enumeration<ProbabilityVariable> e;
		ProbabilityVariable pv = new ProbabilityVariable();
		String v = "";
		for (e = S.elements(); e.hasMoreElements(); ) {
            pv = (ProbabilityVariable)(e.nextElement());
            v= v+pv.get_name()+",";		
		}
		out.print(v);
		System.out.print(v);
		return v;
	}
	public String print_ProbabilityVariableArray(PrintStream out, ProbabilityVariable[] S){
		ProbabilityVariable pv = new ProbabilityVariable();
		String v = "";
		for (int i=0; i<S.length; i++ ) {
            pv = S[i];
            v= v+pv.get_name()+",";	
           
		}
		out.print(v);
		System.out.print(v);
		return v;
	}
	public ProbabilityVariable[] LinkedListtoObject( LinkedList<ProbabilityVariable> S){
		ProbabilityVariable[] pa = new ProbabilityVariable[S.size()];
		int idx = 0;
		//pa = (DiscreteVariable[]) Pa.toArray();
		Iterator<ProbabilityVariable> i = S.iterator();
		while (i.hasNext()) {
	      		pa[idx] = i.next();
	            idx++;
	        }

		return pa;
	}
	public Vector[] Add(Vector[] A, Vector[] B){
		if (A==null)
			return B;
		if (B==null){
			return A;
		}
		int c = A.length+B.length;
		Vector[] C = new Vector[c];
		int b=0; 
		for(int i=0; i<c; i++){
			if(i<A.length){
				C[i] = A[i];
				//System.out.println("add="+C[i].get_name());
			}
			else{
				C[i] = B[i-A.length];
				b++;
				//System.out.println("add="+C[i].get_name());
			}
		}		
		return C;		
	}
	public Q[] Add(Q[] A, Q[] B){
		if (A==null)
			return B;
		if (B==null){
			return A;
		}
		int c = A.length+B.length;
		Q[] C = new Q[c];
		int b=0; 
		for(int i=0; i<c; i++){
			if(i<A.length){
				C[i] = A[i];
				//System.out.println("add="+C[i].get_name());
			}
			else{
				C[i] = B[i-A.length];
				b++;
				//System.out.println("add="+C[i].get_name());
			}
		}		
		return C;		
	}


}
