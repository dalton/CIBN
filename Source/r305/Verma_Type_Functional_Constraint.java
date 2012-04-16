package r305;
/*
 *  * @author Lexin Liu 
 */
import java.io.PrintStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Vector;
import uai04.Method;
import uai04.STCBayesNet;
import uai04.STCInference;
import BayesianNetworks.BayesNet;
import BayesianNetworks.DiscreteVariable;
import BayesianNetworks.ProbabilityFunction;
import BayesianNetworks.ProbabilityVariable;
import Identifiability.AlgorithmComputing;
import Identifiability.Probability;
import Identifiability.Q;
import Identifiability.QFraction;
import Identifiability.methods;
import QuasiBayesianNetworks.QuasiBayesNet; 

public class Verma_Type_Functional_Constraint {
	public ProbabilityVariable[] Paplus(QuasiBayesNet qbn, ProbabilityVariable[] S, PrintStream out){
		/*return a set of variables, which are Pa+(S). In paper r305 */ 
		Enumeration<ProbabilityVariable> e;
		Vector<ProbabilityVariable> R = new Vector<ProbabilityVariable>();
		for (int i=0; i<S.length; i++){
			Vector<ProbabilityVariable> temp = new Vector<ProbabilityVariable>();
			effective_parents(qbn, S[i],temp, out);
			ProbabilityVariable pv;
			for (e = temp.elements(); e.hasMoreElements(); ) {
	            pv = (ProbabilityVariable)(e.nextElement());
	            if(!is_in(pv,R))
	            	R.add(pv);
			}
		}
		Method M = new Method();
		//M.print_Vector(out, R);
		ProbabilityVariable[] re = new ProbabilityVariable[R.size()];
		methods m= new methods();
		re = m.from_Vector_to_ProbabilityVariable(R);
		//M.print_ProbabilityVariableArray(out, re);
		return re;
	
	}
	
	public boolean is_in (ProbabilityVariable S,Vector<ProbabilityVariable> R){
		if(S==null)
			return false;
		Enumeration<ProbabilityVariable> e;
		ProbabilityVariable pv = new ProbabilityVariable();
		for (e = R.elements(); e.hasMoreElements(); ) {
            pv = (ProbabilityVariable)(e.nextElement());
          
            if(S.get_name().equals(pv.get_name())){
            	return true;
            }
		}
		return false;
	}
	public boolean effective_parents(QuasiBayesNet qbn, ProbabilityVariable S,Vector<ProbabilityVariable> R, PrintStream out){
		/* If A is a effective_parents of S or S itself return true, otherwise return false*/
		if (S == null)
			return false;		
		if(S.is_observed()){
			R.add(S);
		}
		ProbabilityFunction pfS = qbn.get_function(S);
		int n= pfS.number_variables();
		ProbabilityVariable[] S_parents= new ProbabilityVariable[n-1];
		for (int i=1; i<n; i++){
			S_parents[i-1] = qbn.get_probability_variable(pfS.get_variables()[i].get_index());
			//out.println(S_parents[i-1].get_name());
			if(S_parents[i-1].is_observed()){
				R.add(S_parents[i-1]);
			}
			else{
				effective_parents(qbn, S_parents[i-1], R, out);
			}
		}
		
		return true;
		
	}
	public Vector<ProbabilityVariable> DUP(QuasiBayesNet qbn, ProbabilityVariable[] S, PrintStream out){
		Enumeration<ProbabilityVariable> e;
		Vector<ProbabilityVariable> R = new Vector<ProbabilityVariable>();
		for (int i=0; i<S.length; i++){
			Vector<ProbabilityVariable> temp = new Vector<ProbabilityVariable>();
			DUP(qbn, S[i],temp, out);
			ProbabilityVariable pv;
			for (e = temp.elements(); e.hasMoreElements(); ) {
	            pv = (ProbabilityVariable)(e.nextElement());
	            if(!is_in(pv,R))
	            	R.add(pv);
			}
		}
		/*
		Method M = new Method();
		M.print_Vector(out, R);
		ProbabilityVariable[] re = new ProbabilityVariable[R.size()];
		methods m= new methods();
		re = m.from_Vector_to_ProbabilityVariable(R);
		M.print_ProbabilityVariableArray(out, re);
		*/
		return R;
	}
	public boolean DUP(QuasiBayesNet qbn, ProbabilityVariable S,Vector<ProbabilityVariable> R, PrintStream out){
		/* return DUP(S) 1. hidden node. 2 a directed path and internal nodes are hidden nodes*/
		if (S == null)
			return false;		
		if(!S.is_observed()){
			R.add(S);
		}
		ProbabilityFunction pfS = qbn.get_function(S);
		int n= pfS.number_variables();
		ProbabilityVariable[] S_parents= new ProbabilityVariable[n-1];
		for (int i=1; i<n; i++){
			S_parents[i-1] = qbn.get_probability_variable(pfS.get_variables()[i].get_index());
			//out.println(S_parents[i-1].get_name());
			if(!S_parents[i-1].is_observed()){
				DUP(qbn, S_parents[i-1], R, out);
			}
		}
		
		return true;
		
	}
	public Q Lemma1(QuasiBayesNet qbn, QuasiBayesNet new_qbn,ProbabilityVariable[] W, ProbabilityVariable[] C, Q Qc, Vector Constraint,int[] Constrait_len, PrintStream out){
		Q Qw = new Q();
		if (Qw==null)
			out.println("lalalala");
		if (!Subset(W, C)){
			return Qw;
		}
	
		methods m= new methods();
		Method M = new Method();
		STCInference stcI = new STCInference(qbn, true);
		
		Vector<ProbabilityVariable> W_p = new Vector<ProbabilityVariable>();
		W_p = m.Subtraction(C, W);
		ProbabilityVariable[] W_P = new ProbabilityVariable[W_p.size()];
		W_P = m.from_Vector_to_ProbabilityVariable(W_p);
		int c_len = C.length;
		System.out.println("Lemma1");
		if (equle(W, ancestral(new_qbn, W, out)) || equle(W,descendent(new_qbn, W, out) )){
			Qw = stcI.Summation(Qc, W_P);
			Vector t1 = new Vector();
			
			ProbabilityVariable[] tem2 = new ProbabilityVariable[c_len];
			t1 = m.Subtraction(Paplus(qbn,C,out), W_P);
			ProbabilityVariable[] tem1 = new ProbabilityVariable[t1.size()];
			tem1 = m.from_Vector_to_ProbabilityVariable(t1);
			tem2 = Paplus(qbn,W,out);
			Constraint =m.Subtraction(tem1, tem2);
			
			
			Constrait_len[0] = Constraint.size();
		//System.out.println("Cons "+Constrait[0].get_name());
		}
		else{
			
			Constrait_len[0] = 0;
		}
		/*
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		for (int k=0; k<new_qbn.number_variables(); k++){
			if (new_qbn.get_probability_variable(k).is_observed())
			System.out.print(new_qbn.get_probability_variable(k).get_name()+": ");
		
		}
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		*/
		return Qw;
	}
	
	public ProbabilityVariable[] descendent(QuasiBayesNet qbn,ProbabilityVariable[] C, PrintStream out){
		// return a set of descendent set of A /
		methods m = new methods();
		Vector<ProbabilityVariable> R = new Vector<ProbabilityVariable>();
		STCBayesNet stc = new STCBayesNet(qbn);
		R= m.Intersection(stc.get_observed_variables(), m.from_Vector_to_ProbabilityVariable(descendents(qbn, C, out)));
		System.out.println();
		
		System.out.print("qbn:  ");
		for (int i=0; i<stc.number_observed(); i++){
			System.out.print(stc.get_observed_variables()[i].get_name()+"^");
		}
		System.out.println();
		System.out.println();
		ProbabilityVariable[] re = new ProbabilityVariable[R.size()];
		re = m.from_Vector_to_ProbabilityVariable(R);
		return re;
	}
	
	public ProbabilityVariable[] ancestral(QuasiBayesNet qbn,ProbabilityVariable[] C, PrintStream out){
	/* return a set of ancestral set of A */
		methods m = new methods();
		Vector<ProbabilityVariable> R = new Vector<ProbabilityVariable>();
		STCBayesNet stc = new STCBayesNet(qbn);
		R= m.Intersection(stc.get_observed_variables(), ancestors(qbn, C));
		ProbabilityVariable[] re = new ProbabilityVariable[R.size()];
		re = m.from_Vector_to_ProbabilityVariable(R);
		return re;
	}
	public BayesNet SubBayesNet(QuasiBayesNet qbn, ProbabilityVariable[] C, String name){
		BayesNet Gc = new BayesNet();
		int c_len = C.length;
		Gc.set_name(name);
		ProbabilityVariable[] C_copy = new ProbabilityVariable[c_len];
		for (int i=0;i<c_len; i++){
			C_copy[i] = C[i];
			//C_copy[i].set_index(i);
		}
		Gc.set_probability_variables(C_copy);
	
		ProbabilityFunction[] PF = new ProbabilityFunction[c_len];
		STCInference STCI = new STCInference(qbn, true);
		//QuasiBayesNet qbn = new QuasiBayesNet(bn);
		PF = STCI.transfer_pvs_to_pfs(C, qbn);
		
		PF  = Match_New_BayesNet(PF, C);
		Gc.set_probability_functions(PF);
		
		//Verma_Type_Functional_Constraint VTFC =new Verma_Type_Functional_Constraint();
/*		Method M=new Method();
		System.out.println();
		System.out.println("SubBayesNet:  ");
		for (int k=0; k<Gc.number_variables(); k++){
			if (Gc.get_probability_variable(k).is_observed())
			System.out.print(Gc.get_probability_variable(k).get_name()+": ");
		
		}
		System.out.println();
		System.out.println();
		*/
		return Gc;
	}
	private ProbabilityFunction[] Match_New_BayesNet(ProbabilityFunction[] pf,
			ProbabilityVariable[] C) {
		// TODO Auto-generated method stub
		int len = pf.length;
		ProbabilityFunction[] PF = new ProbabilityFunction[len];
		ProbabilityFunction p = new ProbabilityFunction();
		
		for (int i=0; i<len; i++){
			p=Match_New_BayesNet(pf[i], C);
			PF[i] = p;
		}
		return PF;
	}
	private ProbabilityFunction Match_New_BayesNet(
			ProbabilityFunction pf, ProbabilityVariable[] c) {
		// TODO Auto-generated method stub
		methods m = new methods();
		ProbabilityFunction re = new ProbabilityFunction();
		int num = pf.number_variables();
		DiscreteVariable[] DV = new DiscreteVariable[num];
		DiscreteVariable[] DV_copy = new DiscreteVariable[num];
		DV = pf.get_variables();
		DV_copy = pf.get_variables();
		
		
		for (int i = 0; i<num; i++){
			//System.out.println("i="+i);
			//System.out.println("DV[i]="+DV[i].get_name());
			if(!In(DV[i],c)){
				DV_copy = m.Subtraction(DV, DV[i]);
			}
		}
		re.set_variables(DV_copy);
		//System.out.println(re.get_variables()[0].get_index());
		return re;
	}
	public ProbabilityVariable[] ancestors(QuasiBayesNet qbn,ProbabilityVariable[] C){
		/*return C's Ancestors and itself to R */
		Enumeration<ProbabilityVariable> e;
		Vector<ProbabilityVariable> R = new Vector<ProbabilityVariable>();
		for(int i=0; i<C.length; i++){
			Vector<ProbabilityVariable> temp = new Vector<ProbabilityVariable>();
			ancestors(qbn, C[i], temp);
			ProbabilityVariable pv;
			for (e = temp.elements(); e.hasMoreElements(); ) {
	            pv = (ProbabilityVariable)(e.nextElement());
	            if(!is_in(pv,R))
	            	R.add(pv);
			}
		}
		ProbabilityVariable[] re = new ProbabilityVariable[R.size()];
		methods m= new methods();
		re = m.from_Vector_to_ProbabilityVariable(R);
		return re;
	}	
	
	public Vector<ProbabilityVariable> descendents(QuasiBayesNet qbn,ProbabilityVariable[] C, PrintStream out){
		//return C's descendents and itself to R 
		int len = qbn.number_variables();
		methods m = new methods();
		ProbabilityVariable[] vars = new ProbabilityVariable[len];
		vars =	qbn.get_probability_variables();
		
		Vector<ProbabilityVariable> re = new Vector<ProbabilityVariable>();
		for (int i=0; i<C.length; i++){
			re.add(C[i]);
		}
		for (int i=0; i<len; i++){
			Vector<ProbabilityVariable> An = new Vector<ProbabilityVariable>();
			System.out.println();
			System.out.println(vars[i].get_name()+" ancestors:  ");
			ancestors(qbn,vars[i],An);
				
				//Method M = new Method();
				//M.print_Vector(out, An);
					
			ProbabilityVariable[] ances = new ProbabilityVariable[An.size()];
			ances = m.from_Vector_to_ProbabilityVariable(An);
			if (m.Intersection(ances, C).size()>0 || m.Intersection(C, ances).size()>0){
				System.out.println("De::: "+ ances[0].get_name());
				re.add(vars[i]);
			}
		}
		return re;
	}
	public void ancestors(QuasiBayesNet qbn,ProbabilityVariable S, Vector<ProbabilityVariable> R){
		/*return C's Ancestors and itself to R */
		methods m = new methods();
		if (S == null)
			return;
		ProbabilityFunction pfS = new ProbabilityFunction();
		pfS = qbn.get_function(S);
		if (pfS == null)
			return;
		int n= pfS.number_variables();
		if (n==0){
			return;
		}
		else{
			if (n == 1){		
			R.add(S);			
			//System.out.print(S.get_name()+"-");			
			return;
		    }
	
		   else{
			R.add(S);
			//System.out.print(S.get_name()+"-");
			DiscreteVariable[] S_Parents= new DiscreteVariable[n];
			S_Parents = pfS.get_variables();
			int len = qbn.number_variables();
			ProbabilityVariable[] V = new ProbabilityVariable[len];
			V = qbn.get_probability_variables();
			ProbabilityVariable[] V_s = new ProbabilityVariable[len-1];
			V_s = m.Subtraction(V, S);
			boolean flag = false;
			for (int i=0; i<n; i++){
				flag = false;
				//System.out.println(" S_Parents[i].get_name(): "+S_Parents[i].get_name());
				for(int j=0; j<len-1 && !flag; j++){
					if (V_s[j].get_name().equals(S_Parents[i].get_name())){
						//System.out.println("V[j].get_name(): " +V[j].get_name()+"  ::   S_Parents[i].get_name(): "+S_Parents[i].get_name());						 
						flag = true;
						R.add(V_s[j]);
						//System.out.print(V_s[j].get_name()+"-");
						ancestors(qbn,V_s[j],R);				         
					}
			    }
			}    
		   }
		}
	}
	/*
	public void ancestors(QuasiBayesNet qbn,ProbabilityVariable S, Vector<ProbabilityVariable> R){
		/*return C's Ancestors and itself to R */
		/*if (S==null){
			System.out.println("null");
		}
		ProbabilityFunction pfS = qbn.get_function(S);
		if(pfS!=null){
		int n= pfS.number_variables();
		//Vector R = new Vector();
		//if(n>1){
			R.add(S);
		//}
		methods m = new methods();
		DiscreteVariable[] S_Parents= new DiscreteVariable[n-1];
		S_Parents = m.Subtraction(pfS.get_variables(), S);
		int len = qbn.number_variables();
		ProbabilityVariable[] V = new ProbabilityVariable[len];
		V = qbn.get_probability_variables();
		ProbabilityVariable[] S_parents= new ProbabilityVariable[n-1];
		
		boolean flag2 = false;
		for (int i=0; i<n-1; i++){
			flag2 = false;
			for(int j=0; j<len && !flag2; j++){
				if (V[j].get_name().equals(S_Parents[i].get_name())){
			         S_parents[i] = V[j];
			         flag2 = true;
				}
			}
			
		}*/
		/*for (int i=1; i<n; i++){
			S_parents[i-1] = qbn.get_probability_variable(pfS.get_variables()[i].get_index());
			
			System.out.println(S_parents[i-1].get_name());
			//System.out.println("R.size="+R.size());
			if(!is_in(S_parents[i-1], R)&& S_parents[i-1]!=null){
				//R.add(S_parents[i-1]);
				ancestors(qbn, S_parents[i-1],R);
			}
			
		}*/
	/*	
		}
	return;
	}
	*/
	public boolean equle(ProbabilityVariable[] W, ProbabilityVariable[] C){
		/* if all variables of set W are in set C, return true*/
		int w = W.length;
		int c = C.length;
		if (w != c)
			return false;
		for (int i=0; i<w; i++){
			if (!In(W[i],C)){
				return false;
			}
		}
		return true;
	}
	public boolean Subset(ProbabilityVariable[] W, ProbabilityVariable[] C){
		/*  if W is subset of C return true */
		int w = W.length;
		int c = C.length;
		if (w>c)
			return false;
		for(int i=0; i<w; i++){
			if (!In(W[i],C)){
				return false;
			}
		}
		return true;
	}
	public boolean In(  ProbabilityVariable w, ProbabilityVariable[] C){
		if (w==null)
			return false;
		for (int i=0; i<C.length;i++){
			if (w.get_name().equals(C[i].get_name())){
				return true;
			}
		}
		return false;
	}
	public boolean In(  DiscreteVariable w, ProbabilityVariable[] C){
		if (w==null)
			return false;
		for (int i=0; i<C.length;i++){
			if (w.get_name().equals(C[i].get_name())){
				return true;
			}
		}
		return false;
	}
	public Q Corollary_1(QuasiBayesNet qbn, ProbabilityVariable[] S, PrintStream out){
		/* S is a c-component of qbn, return Q[S]*/
		STCBayesNet STC = new STCBayesNet(qbn);
		STCInference STCI = new STCInference(qbn, true);
		int len = STC.number_observed();
		ProbabilityVariable[] V = new ProbabilityVariable[len];
		V = STC.get_observed_variables();
		V = STCI.TopologicalOrder(V);
		int s_len = S.length;
		Q Qs = new Q();
		Probability[] Pr = new Probability[s_len];
		for (int j=0;j<s_len; j++){
			for(int i=0; i<len;i++ ){
				if (S[j].get_name().equals(V[i].get_name())){
					ProbabilityVariable[] Vi = new ProbabilityVariable[i+1];
					for (int k=0; k<i+1; k++){
						Vi[k] = V[k];
					}
					Pr[j]= Corollary_1(qbn, i, Vi, out);
				}
			}
		}
		Qs.setProbability(Pr);
		Qs.setUp(true);
		
		return Qs;
	}


	public ProbabilityVariable[] get_Vi(ProbabilityVariable[] V, int i){
		ProbabilityVariable[] Vi = new ProbabilityVariable[i+1];
		for (int k=0; k<i+1; k++){
			Vi[k] = V[k];
		}
		return Vi;
	}
	
	private Probability Corollary_1(QuasiBayesNet qbn, int i, ProbabilityVariable[] Vi, PrintStream out) {
		// return : P(Vi|Pa+(Ti)\{Vi}), V.length = i+1;
		Vector DUP = new Vector();
		DUP = DUP(qbn, Vi, out);
		ProbabilityVariable[] dup = new ProbabilityVariable[DUP.size()];
		methods m = new methods();
		dup = m.from_Vector_to_ProbabilityVariable(DUP);
		ProbabilityVariable[] union = new ProbabilityVariable[DUP.size()+Vi.length];
		union = m.Add(dup, Vi);
		BayesNet Gvi = new BayesNet(SubBayesNet(qbn, union, "Gvi"));
		Probability P = new Probability();
		P.setName(Vi[i]);
		AlgorithmComputing AC = new AlgorithmComputing(qbn);
		Vector[] c_com = new Vector[i+1];
		c_com = AC.partition_c_component(Vi);
		Enumeration e;
		int c_idx=-1;
		for(int k=0; k<c_com.length; k++){			
				ProbabilityVariable pv;
				for (e = c_com[k].elements(); e.hasMoreElements(); ) {
		            pv = (ProbabilityVariable)(e.nextElement());
		            if(pv.get_name().equals(Vi[i].get_name())){
		            	c_idx= k;
		            	break;
		            }
				}		
		}
		//System.out.println("c_idx = "+c_idx);
		if(c_idx>-1){
			ProbabilityVariable[] T = new ProbabilityVariable[c_com[c_idx].size()];
			T = m.from_Vector_to_ProbabilityVariable(c_com[c_idx]);
			P.setEvidences(m.Subtraction(Paplus(qbn, T, out), Vi[i]));
		}
		return P;
	}
	public void Identifying_Constraints(QuasiBayesNet qbn, PrintStream out){
		methods m = new methods();
		Method M = new Method();
		STCBayesNet STC = new STCBayesNet(qbn);
		STCInference STCI = new STCInference(qbn, true);
		int len = STC.number_observed();
		ProbabilityVariable[] V = new ProbabilityVariable[len];
		V = STC.get_observed_variables();
		V = STCI.TopologicalOrder(V);
		ProbabilityVariable[] Si = new ProbabilityVariable[len];
		Q Qs = new Q();
		String Qprint = "";
		String Q_variables = "";
		for(int i=0; i<len; i++){
			if (i==1){
				out.println("Q[" +V[0].get_name() +"] = P(" + V[0].get_name() +")");
				out.println("Q[" +V[0].get_name() +"] does not give any constraint.");
				out.println();
			}
			else{
			/* (A1) */
			/*  subgraphh G(Vi) */
			out.println(V[i].get_name()+":");
			//i=2;
			ProbabilityVariable[] Vi = new ProbabilityVariable[i+1];
			Vi =get_Vi(V, i);
			Vector DUP = new Vector();
			DUP = DUP(qbn, Vi, out);
			ProbabilityVariable[] dup = new ProbabilityVariable[DUP.size()];
			dup = m.from_Vector_to_ProbabilityVariable(DUP);
			ProbabilityVariable[] union = new ProbabilityVariable[DUP.size()+Vi.length];
			union = m.Add(dup, Vi);
			BayesNet Gvi = new BayesNet(SubBayesNet(qbn, union, "Gvi"));
			QuasiBayesNet q_Gvi = new QuasiBayesNet(Gvi);
			//LinkedList com = new LinkedList();
			Vector[] com = new Vector[i+1];
			AlgorithmComputing AC = new AlgorithmComputing(q_Gvi);
			com = AC.partition_c_component(Vi);
			int num_c = com.length;
			System.out.println("&&&&&"+com.length);
			int Si_idx = -1;
			if (num_c == 1){
				Q_variables = "";
				for (int k=0; k<Vi.length; k++){
					Q_variables += Vi[k].get_name() +",";
				}
				out.print("Q[" +Q_variables + "] ");
				out.println("Only one c-component. Cannot continue.");
				out.println();
			}
			if(num_c >1){
				/* A1 */
				Si_idx = find_Si(com, V[i]);
				Q_variables = "";
				Si = m.from_Vector_to_ProbabilityVariable(com[Si_idx]);
				Qs = Corollary_1(qbn, Si,out);
				for (int k=0; k<Qs.number_Probability(); k++){
						Q_variables += Qs.getProbability()[k].getName().get_name() +",";
				}
				Qprint = AC.Qprint(Qs);
				out.print("Q[" +Q_variables + "] = " + Qprint);
				out.print('\n');
				if (Si.length == 1){
					out.println("Q[" + Q_variables + "] does not give any constraints.");
					out.print('\n');
				}
				else{
				boolean flag1 = true;
				while(flag1){	
				/* (A2) */
				/* subgraph G(Si)*/
				DUP = DUP(qbn, Si, out);
				dup = m.from_Vector_to_ProbabilityVariable(DUP);
				union = m.Add(dup, Si);
				Gvi = new BayesNet(SubBayesNet(qbn, union, "Gsi"));
				q_Gvi = new QuasiBayesNet(Gvi);
				System.out.println("Gsi:");
				for (int k=0; k<q_Gvi.number_variables(); k++){
					if (q_Gvi.get_probability_variable(k).is_observed())
					System.out.print(q_Gvi.get_probability_variable(k).get_name()+": ");
				
				}
				System.out.println();
				ProbabilityVariable[] Si_Vi = new ProbabilityVariable[Si.length-1];
				/* each descendent set D in G(Si), that not contain Vi */
				Si_Vi = m.Subtraction(Si, V[i]);
				Vector subset = new Vector();
				Combine(Si_Vi, subset);
				//subset = getSubset(Si_Vi);
				Enumeration e;
			    int number_D = subset.size();
			    int D_Counter =0;
				for (e = subset.elements(); e.hasMoreElements(); ) {
					
					ProbabilityVariable[] pv = new ProbabilityVariable[i];
		            pv = (ProbabilityVariable[])(e.nextElement());
					//System.out.print("^^^^^^^^^^^^^^^^^^^^");
		            out.print("D = {"); //pv is D of r305 algorithm.
		            System.out.print("D = {"); //pv is D of r305 algorithm.
					for (int j=0; j<pv.length; j++){
						out.print(pv[j].get_name()+",");
						System.out.print(pv[j].get_name()+",");
					}
					out.print("}  ");
					System.out.println("}  ");
					int de_size = descendent(q_Gvi, pv,out).length;
					ProbabilityVariable[] de = new ProbabilityVariable[de_size];
					de  = descendent(q_Gvi, pv,out);
					//if (!equle(pv, descendent(q_Gvi, pv,out))){
					if(!equle (pv, de)){
						D_Counter ++;
						out.println("is not a descendent set. ");
						/*out.print("de = {" );
						for (int k= 0; k<de_size; k++){
							out.print(de[k].get_name()+",");
						}
						out.println("}" );*/
					}
					//if (equle(pv, descendent(q_Gvi, pv,out))){
					else{
					     System.out.println("is descendentral. ");  
		            ProbabilityVariable[] constraint = new ProbabilityVariable[len];
		            Vector Constraint = new Vector();
		            int[] constraint_len = new int[1];
		            Q Qsi_d = new Q();
		            Vector<ProbabilityVariable> W_p = new Vector<ProbabilityVariable>();
					W_p = m.Subtraction(Si, pv);
					
					ProbabilityVariable[] D_P = new ProbabilityVariable[W_p.size()];
					D_P = m.from_Vector_to_ProbabilityVariable(W_p);
		            Qsi_d = Lemma1 (qbn, q_Gvi,D_P, Si, Qs, Constraint, constraint_len, out);
		            Vector t1 = new Vector();
					if(Qsi_d !=null){
						//ProbabilityVariable[] tem2 = new ProbabilityVariable[c_len];
						
						t1 = m.Subtraction(Paplus(qbn,Si,out), pv);
						ProbabilityVariable[] tem1 = new ProbabilityVariable[t1.size()];
						tem1 = m.from_Vector_to_ProbabilityVariable(t1);
						//tem2 = Paplus(qbn,W_P,out);
						Constraint =m.Subtraction(tem1,  Paplus(qbn,D_P,out));
						Enumeration ff;
						ProbabilityVariable xxx = new ProbabilityVariable();
						
						for (ff = Constraint.elements(); ff.hasMoreElements(); ) {
							xxx = (ProbabilityVariable)(ff.nextElement());
							System.out.println(xxx.get_name()+ "'");
						}
						
		           /////////////////////////////////////////////////
						    //	if(D_P.length==1){
						    {							flag1 = false;
						Q_variables = "";
						for(int q=0; q<D_P.length; q++){
							 Q_variables += D_P[q].get_name() +",";
						}
						out.print("Q[" +Q_variables + "] = " + AC.Qprint(Qsi_d));
						out.print('\n');
						if (Constraint.size()==0){
							System.out.println("______Constraint.size()___________"+Constraint.size());
							//out.println(printLeft(pv, Si, out));
							
							//Qprint = AC.Qprint(Qsi_d);
							
							out.print("Q[" + Q_variables + "] does not give any constraints.");
							out.print('\n');
							out.print('\n');
						}
						else{
												
							out.println("Q[" + Q_variables + "] It implies a constraint on P(v) that the right hand side is independent of " + M.print_Vector(out, Constraint) + ".");
							out.print('\n');
						}
						} // if(D_P.length==1)
						/////////////////////////////////////
						if (D_P.length>1){
						/* subgraph G(D_pi)*/
						DUP = DUP(qbn, D_P, out);
						dup = m.from_Vector_to_ProbabilityVariable(DUP);
						union = m.Add(dup, D_P);
						Gvi = new BayesNet(SubBayesNet(qbn, union, "GDpi"));
						QuasiBayesNet q_Gvi_2 = new QuasiBayesNet(Gvi);
						
						//STCI = new STCInference(q_Gvi, true);
						ProbabilityVariable[] Ei = new ProbabilityVariable[D_P.length];
						AlgorithmComputing AC2 = new AlgorithmComputing(q_Gvi_2);
						com = AC2.partition_c_component(D_P);
						num_c = com.length;
						Si_idx = -1;
						if (num_c>1){
							
							Si_idx = find_Si(com, V[i]);
							Ei = m.from_Vector_to_ProbabilityVariable(com[Si_idx]);
							D_P = STCI.TopologicalOrder(D_P);
							Q[] QDP = new Q[1];
							QDP[0] = Qsi_d;
							QFraction QDpi = new QFraction(QDP);
							QFraction QEi = new QFraction();
							QEi = AC2.lemma2(QDpi, Ei, D_P);// AC or AC2??
							
							Constraint = m.Subtraction(Paplus(qbn, D_P, out), Paplus(qbn, Ei, out));
							Q_variables = "";
							for(int q=0; q<Ei.length; q++){
								 Q_variables += Ei[q].get_name() +",";
							}
							out.print("Q[" +Q_variables + "] = " + AC2.QFprint(QEi, out));
							//AC2.QFprint(QEi, out);
							out.print('\n');
							if (Constraint.size()>0){
								out.println("Q[" + Q_variables + "] It implies a constraint on P(v) that the right hand side is independent of " + M.print_Vector(out, Constraint) + ".");
								out.print('\n');
								
								//M.print_Vector(out, Constraint);
							}
							else{
								out.print("Q[" + Q_variables + "] does not give any constraints.");
								out.print('\n');
								out.print('\n');
							}
							if (Ei.length < 2){
								
								flag1 = false;
							}
						}// if (num_c>1)
						else if (num_c==1){
							flag1 = false;
							Q_variables = "";
							for(int q=0; q<D_P.length; q++){
								 Q_variables += D_P[q].get_name() +",";
							}
							out.print("Q[" +Q_variables + "] ");
							out.println("Only one c-component. Cannot continue.");
						} //else if (num_c==1)   //else --- (if (num_c>1))
						
						} //end if (D_P.length>0)
					}//if(Qsi_d !=null)
					}//end if ancestral or descendent
				}//end for	
				if (D_Counter == number_D){
					flag1 = false;
				}
				}//while
				
			}//if (Si.length == 1) else{}/*A2*/
			}//end if c_num>1
		}//else /*A1*/
		}
	}
	private String printLeft(ProbabilityVariable[] pv, ProbabilityVariable[] si,
			PrintStream out) {
		// TODO Auto-generated method stub
		Method M =new Method();
		String R= "sum_";
		R=R+M.print_ProbabilityVariableArray(out, pv);
		R=R + "Q["+M.print_ProbabilityVariableArray(out, si)+"]";
		return R;
	}
	public void Combine(ProbabilityVariable[] inS, Vector R){//R is return.
		
		int length = inS.length;
		Vector outS = new Vector();
		doCombine(inS, outS,length, 0, 0, R);
		
		
	}
	public void doCombine(ProbabilityVariable[] inS, Vector outS, int length,
			int level, int start, Vector R){
		//Vector<ProbabilityVariable[]> R = new Vector<ProbabilityVariable[]>();
		for(int i= start; i<length; i++){
			outS.add(inS[i]);
			Enumeration e;
			int len = outS.size();
			ProbabilityVariable[] pv = new ProbabilityVariable[len];
			int idx = 0;
			ProbabilityVariable xx = new ProbabilityVariable();
			System.out.println("********");
			for (e = outS.elements(); e.hasMoreElements();){
				xx = (ProbabilityVariable)(e.nextElement());
				pv[idx] = xx;
				System.out.println(pv[idx].get_name()+",");
				idx ++;
			}
			R.add(pv);
			System.out.println("__R___"+R.size());
			if(i < length - 1){
				 doCombine(inS, outS, length, level+1, i+1, R);
			}
			outS.clear();
		}
		
	}
	public Vector getSubset(ProbabilityVariable[] S){
		/*modify needed, use HashSrt to get all of subsets. */
		/*<=1i<=3*/
		Vector R = new Vector();
		int s_len = S.length;
		//System.out.println(" S.length"+ S.length);
		ProbabilityVariable[] set= new ProbabilityVariable[s_len];
		
		for (int i=0; i<s_len; i++){
			if (i==0 && i<s_len){
				for (int j=0; j<s_len;  j++){
				set= new ProbabilityVariable[i+1];
				set[0]= S[j];
				R.add(set);
				}
			
			}
			if (i==1  && i<s_len){			
				set= new ProbabilityVariable[i+1];
				set[0]= S[0];
				set[1]= S[1];
				R.add(set);
				set= new ProbabilityVariable[i+1];
				set[0]= S[0];
				set[1]= S[2];
				R.add(set);
				set= new ProbabilityVariable[i+1];
				set[0]= S[1];
				set[1]= S[2];
				R.add(set);
			
			}
			if (i==2  && i<s_len){
				set= new ProbabilityVariable[i+1];
				set[0]= S[0];
				set[1]= S[1];
				set[1]= S[2];
				R.add(set);
			
			}
		}
		//System.out.println(" R.length"+ R.size());
		return R;
		}
	public int find_Si(Vector[] com, ProbabilityVariable Vi){
		int idx = -1;
		ProbabilityVariable pv;
		for (int i=0; i<com.length; i++){
			Enumeration<?> e;
			
			for (e =com[i].elements(); e.hasMoreElements(); ) {
	            pv = (ProbabilityVariable)(e.nextElement());
	            if(pv.get_name().equals(Vi.get_name())){
	            	idx = i;
	            	break;
	            }
			}
		}
		return idx;
	}
	public LinkedList partition_c_component(QuasiBayesNet qbn, ProbabilityVariable[] S) {
		// TODO Auto-generated method stub		
		int length = S.length;
		LinkedList R = new LinkedList();
		//Enumeration e;
		Vector[] N = new Vector[length];
		methods m = new methods();
		AlgorithmComputing AC = new AlgorithmComputing(qbn);
		ProbabilityVariable[] temp1 = new ProbabilityVariable[length];
		ProbabilityVariable[] temp2 = new ProbabilityVariable[length];
		temp1 = S;
		temp2 = S;
		int count = 0;
		while(temp1.length>0){
			length = temp1.length;			
			ProbabilityVariable pv;
			Vector<ProbabilityVariable> pN = new Vector<ProbabilityVariable>();
			pv = temp1[0];
			Vector<ProbabilityVariable> pn = new Vector<ProbabilityVariable>();
			pn.addElement(pv);
			pN.addElement(pv);
			temp2 = m.Subtraction(temp1, pv);//
			//if(temp2.length>0)
				//System.out.println(temp2[0].get_name()+"********************888");
			for(int i=1; i<length; i++){
				if(AC.in_one_c_component(pv, temp1[i],pn) || AC.is_c_component_relation(pv, temp1[i],pn) ){
					//System.out.println(temp1[0].get_name()+"  and  "+temp1[i].get_name() + "  is in oneC-com.");
					pN.addElement(temp1[i]);
					
					temp2 = m.Subtraction(temp2, temp1[i]);//
					//length = length--;
				}
				//else
				//	System.out.println(temp1[0].get_name()+"  and  "+temp1[i].get_name() + "  is NOT in oneC-com.");
			
			}
			//System.out.println("count = "+count);
			N[count] = pN;
			R.add(pN);
			count++;
			temp1 = temp2;
		}
		Vector[] rN = new Vector[count];
		for (int i=0; i<count; i++){
			rN[i] = N[i];
		}
		return R;
		
	}
	public void driver(QuasiBayesNet qbn, PrintStream out){
		STCBayesNet STC = new STCBayesNet(qbn);
		//ProbabilityVariable[] S = new ProbabilityVariable[STC.number_s()];
		ProbabilityVariable[] S = new ProbabilityVariable[10];
		//S = STC.get_probability_variables_s();
		Vector R = new Vector();
		//R =DUP(qbn, S, out);
		//Paplus(qbn,S, out);
		Method M = new Method();
		//M.print_ProbabilityVariableArray(out, ancestors(qbn,S));
		//M.print_Vector(out, R);
		//BayesNet subBN = new BayesNet();
		//subBN = SubBayesNet(qbn, S, "Gc");
		//subBN.print(out);
		
		Vector<ProbabilityVariable> Dup = new Vector<ProbabilityVariable>();
		Dup = DUP(qbn, S, out);
		ProbabilityVariable[] DUP = new ProbabilityVariable[Dup.size()];
		methods m= new methods();
		DUP = m.from_Vector_to_ProbabilityVariable(Dup);
		/*
		M.print_ProbabilityVariableArray(out, DUP);
		ProbabilityVariable[] Union = new ProbabilityVariable[Dup.size()+S.length];
		Union = m.Add(DUP, S);
		BayesNet Gc = new BayesNet(SubBayesNet(qbn,Union,"Gc"));
		QuasiBayesNet new_qbn = new QuasiBayesNet(Gc);
		//new_qbn.print(out);
		/*for (int i=0; i<new_qbn.number_variables();i++){
			if (new_qbn.get_probability_functions()[i]!=null)
			out.println(new_qbn.get_probability_functions()[i].get_variables()[0].get_name());
		}
		*/
		//M.print_ProbabilityVariableArray(out, ancestral(new_qbn,S,out));
		//M.print_ProbabilityVariableArray(out,descendent(qbn, S, out));
		//M.print_Vector(out, descendents(qbn,S,out));
		
		AlgorithmComputing AC = new AlgorithmComputing(qbn);
		Vector[] VC = new Vector[5];
		S= STC.get_observed_variables();
		//VC = AC.partition_c_component(STC.get_probability_variables());
		//M.print_Vector(out, VC[0]);
		//S =m.from_Vector_to_ProbabilityVariable(VC[0]);
		//out.println(S.length);
		//M.print_Vector(out, VC[0]);
		Identifying_Constraints(qbn, out);
		
		
	}
	public void print(PrintStream out, QuasiBayesNet qbn){
		//out.println(qbn.number_s());
		Identifying_Constraints(qbn, out);
		//driver(qbn, out); // common out at 05/26/2008
							// driver method is a test method.
	}
}
