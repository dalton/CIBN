package uai04;

import BayesianInferences.DSeparation;
import BayesianNetworks.*;
import Identifiability.STBayesNet;
import InterchangeFormat.*;
import CredalSets.*;
import QuasiBayesianNetworks.*;

import java.io.*;
import java.net.URL;
import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;



/**
 * This class is a bayesnet for identifiability analysis.
 * @author Lexin
 *
 */

public class STCBayesNet  extends QuasiBayesNet{
	
	ProbabilityVariable probability_variables_s[];
	ProbabilityFunction probability_functions_s[];
	
	ProbabilityVariable probability_variables_t[];
	ProbabilityFunction probability_functions_t[];
	
	ProbabilityVariable probability_variables_c[];
	ProbabilityFunction probability_functions_c[];

	STBayesNet st;
	int n_s;
	int n_t;
	int n_c;
	
	public STCBayesNet(){		
		super();
		n_s = 0;
		n_t = 0;
		n_c = 0;
	}
	
	public STCBayesNet(String n_n, int n_v, int n_f) {
		super(n_n, n_v, n_f);
		n_s = 0;
		n_t = 0;
		n_c = 0;
	}
	
	public STCBayesNet(String n_n, Vector p){
		super(n_n, p);
		n_s = 0;
		n_t = 0;
		n_c = 0;
	}
	public STCBayesNet(STBayesNet bn){
		super(bn.bn);
		probability_variables_s = bn.get_probability_variables_s();
		probability_variables_t = bn.get_probability_variables_t();
		
		st = bn;
		n_s = bn.number_s();
		n_t = bn.number_t();
	}
	
	public STCBayesNet(BayesNet bn){
		super(bn);

	}
	
	public STCBayesNet(QuasiBayesNet bn){
		super(bn);
		

	}
	public STCBayesNet(String s) throws IFException {
		super(s);
		n_s = 0;
		n_t = 0;
		n_c = 0;
	}
	
	public STCBayesNet(InputStream istream) throws IFException {
		super(istream);
	
	}
	
	public STCBayesNet(URL context, String spec) throws IFException, IOException{
	    super(context, spec);
	    n_s = 0;
		n_t = 0;
		n_c = 0;
	  }
	
	public STCBayesNet(URL url) throws IFException, IOException{
	    super(url);
	 
	  }
	public ProbabilityVariable[] get_probability_variables_s(){
		int n_pv = number_variables();
		int j=0;
		for (int i=0; i<n_pv; i++){
			   if (probability_variables[i].is_S()){
				   j++;
			   }
		 }
	  // System.out.println("s.lenght = "+j);
	   ProbabilityVariable pv[] = new ProbabilityVariable[j];
	   j=0;
	   for (int i=0; i<n_pv; i++){
		   if (probability_variables[i].is_S()){
			   ProbabilityVariable p= new ProbabilityVariable(probability_variables[i]);
			   pv[j] = p;
			   j++;
		   }
		}
	   
	  
	   return pv;
	}
	
	public ProbabilityVariable[] get_probability_variables_t(){
		int n_pv = number_variables();
		int j=0;
		for (int i=0; i<n_pv; i++){
			   if (probability_variables[i].is_T()){
				   j++;
			   }
		 }
	   System.out.println("t.lenght = "+j);
	   ProbabilityVariable pv[] = new ProbabilityVariable[j];
	   j=0;
	   for (int i=0; i<n_pv; i++){
		   if (probability_variables[i].is_T()){
			   ProbabilityVariable p= new ProbabilityVariable(probability_variables[i]);
			   pv[j] = p;
			   j++;
		   }
		}
	   
	  
	   return pv;
	}
	public ProbabilityVariable[] get_probability_variables_c(){
		int n_pv = number_variables();
		int j=0;
		for (int i=0; i<n_pv; i++){
			   if (probability_variables[i].is_C()){
				   j++;
			   }
		 }
	   System.out.println("c.lenght = "+j);
	   ProbabilityVariable pv[] = new ProbabilityVariable[j];
	   j=0;
	   for (int i=0; i<n_pv; i++){
		   if (probability_variables[i].is_C()){
			   ProbabilityVariable p= new ProbabilityVariable(probability_variables[i]);
			   pv[j] = p;
			   j++;
		   }
		}
	   
	  
	   return pv;
	}
	/*
	 * store all of unobserved vertex
	 */
	public ProbabilityVariable[] get_unobserved_variables() {
		// TODO Auto-generated method stub
		int size = number_variables();
		ProbabilityVariable[] unObserved = new ProbabilityVariable[size];
		int count =0;
		for(int n=0; n<size; n++){
			if(!this.probability_variables[n].is_observed()){
				unObserved[count]=probability_variables[n];
//				System.out.println(_VertexStore[n].v().get_Name());
				count++;
			}
		}
		ProbabilityVariable[] un = new ProbabilityVariable[count];
		for(int i=0; i<count;i++){
			un[i] = unObserved[i];
		}
//		System.out.println("count="+count);
		return un;
		
	}
	/*
	 * store all of observed vertex
	 */
	public ProbabilityVariable[] get_observed_variables() {
		// TODO Auto-generated method stub
		int size = number_observed();
		ProbabilityVariable[] Observed = new ProbabilityVariable[size];
		int count =0;
		for(int n=0; n<number_variables(); n++){
/*			System.out.println(probability_variables[n].get_name());
			if(probability_variables[n].is_S())	System.out.println("is S.");
			if(probability_variables[n].is_T())	System.out.println("is T.");
			*/
			if(this.probability_variables[n].is_observed()){
				Observed[count]=probability_variables[n];
				//System.out.println("hahahaha observed!!!!!!.");
				count++;
			}
		}
		
//		System.out.println("count="+count);
		return Observed;
		
	}
	/*
	 * return the number of unobservabel vertex
	 */
	
	public int number_unobserved(){
		int num = this.number_variables();
		for(int n=0; n<this.number_variables();n++){
			if(this.probability_variables[n].is_observed())
				num--;
		}
		return num;
	}
	/*
	 * return the number of observabel vertex
	 */
	
	public int number_observed(){
		int num = this.number_variables();
		num = num - number_unobserved();
		return num;
	}
	public void clean_mark() {
		// TODO Auto-generated method stub
		for(int i=0; i<number_variables(); i++){
			ProbabilityVariable pv;
			pv = get_probability_variable(i);
			set_variable_mark(pv);
		}
		
	}

	private void set_variable_mark(ProbabilityVariable pv) {
		// TODO Auto-generated method stub
		int n=0;
		pv.setMark(n);
	}

	public void set_all_observed(PrintStream pstream) {
		// TODO Auto-generated method stub
		for(int i=0; i<number_variables(); i++){
			ProbabilityVariable pv;
			pv = get_probability_variable(i);
			set_variable_mark(pv);
		}
	}
	public void set_all_to_unobserved(){
		int size = number_variables();
		for(int i=0; i<size; i++){
			//set_pv_unobserved(i);
			this.probability_variables[i].set_invalid_observed_index();
		}
	}

	public int[] get_observed_idx(ProbabilityVariable[] pvs) {
		// TODO Auto-generated method stub
		int[] R = new int[pvs.length];
		
		int r=0;
		for(int i=0; i<pvs.length; i++){
			if(pvs[i].is_observed()){
				R[r]  = pvs[i].get_index();
				r++;
			}
		}
		int[] Re = new int[r];
		for(int i=0;i<r;i++){
			Re[i] = R[i];
		}
		return Re;
	}

	public void set_observed_by_idx(int[] observed_idx) {
		// TODO Auto-generated method stub
		int r=0;
		for(int i=0; i<number_variables() && r<observed_idx.length; i++){
			if(i==observed_idx[r]){
				this.probability_variables[i].set__observed_index();
				r++;
			}
		}
	}


}
