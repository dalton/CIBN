package Identifiability;

import java.util.Enumeration;
import java.util.Vector;

import BayesianNetworks.DiscreteVariable;
import BayesianNetworks.ProbabilityVariable;
import InferenceGraphs.InferenceGraphNode;

public class methods {

	public methods() {
		// TODO Auto-generated constructor stub
	}
	public boolean VectorContain(Vector V, InferenceGraphNode[] ign){
		InferenceGraphNode one = ign[0];
		InferenceGraphNode two = ign[1];
		InferenceGraphNode[] IGN = new InferenceGraphNode[2];
		Enumeration e;
		for (e = V.elements(); e.hasMoreElements(); ) {
            IGN = (InferenceGraphNode[])(e.nextElement());
           if ((IGN[0].getPv().get_name().equals(one.getPv().get_name())
        		   && IGN[1].getPv().get_name().equals(two.getPv().get_name()))
        		   || (IGN[0].getPv().get_name().equals(two.getPv().get_name())
        		   && IGN[1].getPv().get_name().equals(one.getPv().get_name()))){
        	   return true;
           }
		}
		return false;
	}
	public ProbabilityVariable[] getObserved(ProbabilityVariable[] A){
		int k=get_num_Observed(A);
		int c=0;
		ProbabilityVariable[] R = new ProbabilityVariable[k];
		for (int i=0; i<A.length; i++){
			if (A[i].is_observed()){
				R[c] = A[i];
				c++;
			}
		}
		return R;
	}
	public int get_num_Observed(ProbabilityVariable[] A){
		int k=0;
		for (int i=0; i<A.length; i++){
			if (A[i].is_observed())
				k++;
		}
		return k;
	}
	/*
	 * change  Vector to ProbabilityVariable[].
	 */
	public ProbabilityVariable[] from_Vector_to_ProbabilityVariable(Vector<ProbabilityVariable> V){
		int size = V.size();
		ProbabilityVariable[] Re= new ProbabilityVariable[size];
		Enumeration<ProbabilityVariable> e;
		ProbabilityVariable pv;
		int n=0;
		for (e = V.elements(); e.hasMoreElements(); ) {
            pv = (ProbabilityVariable)(e.nextElement());
            Re[n] = pv;
           // System.out.print("___"+pv.get_name());
            n++;
		}
		return Re;
	}
	/*
	 * return A union B
	 */
	public ProbabilityVariable[] Add(ProbabilityVariable[] A, ProbabilityVariable[] B){
		if (A==null)
			return B;
		if (B==null){
			return A;
		}
		ProbabilityVariable[] C = new ProbabilityVariable[B.length+A.length];
	
		int b=0;
		for (int i=0; i<B.length+A.length; i++){
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
	/*
	 * return set A add node B
	 */
	public ProbabilityVariable[] Add(ProbabilityVariable[] A, ProbabilityVariable B){
		if (A==null){
			ProbabilityVariable[] C = new ProbabilityVariable[1];
			C[0] = B;
			return C;
		}
		if (B==null){
			return A;
		}
		ProbabilityVariable[] C = new ProbabilityVariable[1+A.length];
	
		int b=0;
		for (int i=0; i<A.length; i++){
				C[i] = A[i];
		}
		C[A.length] = B;
		return C;
	}
	public DiscreteVariable[] Add(DiscreteVariable[] A, DiscreteVariable[] B){
		if (A==null)
			return B;
		if (B==null){
			return A;
		}
		DiscreteVariable[] C = new DiscreteVariable[B.length+A.length];
	
		int b=0;
		for (int i=0; i<B.length+A.length; i++){
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
	public DiscreteVariable[] Copy(DiscreteVariable[] A){
		DiscreteVariable[] C = new DiscreteVariable[A.length];
		for (int i=0; i<A.length; i++){
			C[i] = A[i];
		}
		return C;
	}
	/*
	 * return set A \ B
	 */
	public ProbabilityVariable[] Subtraction (ProbabilityVariable[] A, ProbabilityVariable B){
		if (B==null){
			return A;
		}
		if(A==null){
			return null;
		}
		//System.out.println("SUB A.length:"+A.length);
		ProbabilityVariable[] C = new ProbabilityVariable[A.length-1];
		int n = 0;
		for(int i=0; i<A.length; i++){
			//System.out.println("SUB A:"+A[i].get_name());
			if (!A[i].get_name().equals(B.get_name())){
				C[n] = A[i];
				n++;
			}
			
		}
		
		return C;
	}
	/*
	 * return set A \ B
	 */
	public DiscreteVariable[] Subtraction (DiscreteVariable[] A, DiscreteVariable B){
		if (B==null){
			return A;
		}
		if(A==null){
			return null;
		}
		//System.out.println("SUB A.length:"+A.length);
		DiscreteVariable[] C = new DiscreteVariable[A.length-1];
		int n = 0;
		for(int i=0; i<A.length; i++){
			//System.out.println("SUB A:"+A[i].get_name());
			if (!A[i].get_name().equals(B.get_name())){
				C[n] = A[i];
				n++;
			}
			
		}
		
		return C;
	}
	public Vector<ProbabilityVariable> Subtraction (ProbabilityVariable[] A, ProbabilityVariable[] B){
		/* A\B */
		Vector<ProbabilityVariable> C = new Vector<ProbabilityVariable>();
		int count=0;
		for(int i=0; i<A.length; i++){
			int j=0;
			boolean is=false;
			while(!is && j<B.length){
				if(B[j]!=null && A[i].get_name().equals(B[j].get_name())){
					is = true;
//					System.out.println("("+A[i].get_name()+")");
					break;
				}
				j++;
			}
			if(!is){
//				System.out.println("*"+A[i].get_name()+"*");
				C.addElement(A[i]);
				count++;
			}
			
		}
		return C;
	}
	public Vector<ProbabilityVariable> Intersection(ProbabilityVariable[] A, ProbabilityVariable[] B){
		
		//int a_size = A.length;
		
		//int b_size = B.length;
		if(A == null || B == null){
			return null;
		}
		Vector<ProbabilityVariable> A_B = new Vector<ProbabilityVariable>();
		A_B = Subtraction(A, B);
		int a_b_size = A_B.size();
		ProbabilityVariable[] a_b = new ProbabilityVariable[a_b_size];
		a_b = from_Vector_to_ProbabilityVariable(A_B);
		Vector<ProbabilityVariable> D_temp = new Vector<ProbabilityVariable>();
		D_temp = Subtraction(A, a_b);
		
		return D_temp;
	}
	
  public int getIndexFromPVArray(ProbabilityVariable[] PV, String node){
	  
	  for (int i=0; i<PV.length; i++){
		  if(node.equals(PV[i].get_name())){
			  return i;
			  
		  }
	  }
	  return -1;
	  
  }
  public ProbabilityVariable getpvFromArray(ProbabilityVariable[] PV, int index){
	  ProbabilityVariable pv = new ProbabilityVariable();
	  for(int i=0; i<PV.length; i++){
		  if(i==index){
			  pv = PV[i];
			  break;
		  }
			  
	  }
	  return pv;
  }
  public String getpvnameFromArray(ProbabilityVariable[] PV, int index){
	  String re="";
	  for(int i=0; i<PV.length; i++){
		  if(i==index){
			 re = PV[i].get_name();
			  break;
		  }
			  
	  }
	  return re;
  }
}
