/**
 *@author Lexin Liu
 */
package uai04;

/**
 * @author Lexin
 *
 */
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import r305.Verma_Type_Functional_Constraint;

import BayesianNetworks.BayesNet;
import BayesianNetworks.DiscreteFunction;
import BayesianNetworks.DiscreteVariable;
import BayesianNetworks.ProbabilityFunction;
import BayesianNetworks.ProbabilityVariable;
import QuasiBayesianNetworks.QuasiBayesNet;
import BayesianInferences.*;
import Identifiability.AlgorithmComputing;
import Identifiability.Probability;
import Identifiability.Q;
import Identifiability.QFraction;
import Identifiability.STBayesNet;
import Identifiability.STInference;
import Identifiability.methods;
public class Algorithm1 {
	QuasiBayesNet qbn;
	STCBayesNet stc;
	String pts;
	private final String defaultBayesNetName = "InternalNetwork";
	/**
	 * 
	 */
	public Algorithm1() {
		qbn = new QuasiBayesNet(defaultBayesNetName, 0, 0);
		stc = new STCBayesNet(qbn);
		// TODO Auto-generated constructor stub
	}
	public Algorithm1(QuasiBayesNet q) {
		// TODO Auto-generated constructor stub
		qbn = q;
		stc = new STCBayesNet(qbn);
	}
	
	public void algorithm_computing(PrintStream out){
		
		System.out.println("uai04");
		
		//qbn.print(out);
		STCInference stcI = new STCInference(qbn, true);
		AlgorithmComputing Huang = new AlgorithmComputing(stc);
		Method thisM = new Method();
		methods Method = new methods();
		Verma_Type_Functional_Constraint VTFC = new Verma_Type_Functional_Constraint();
		//stcI.qbn.print(out);
		//out.println("***BREAK***");
		int size = stc.number_observed();
		int U_size = stc.number_unobserved();
		int all_size = size+U_size;
		ProbabilityVariable[] All= new ProbabilityVariable[all_size];
		Vector[] G_C_Com = new Vector[all_size];
		
		All = stc.get_probability_variables();
		/***********/
		/* Phase 1 */
		/***********/
		
		/* Find c-components of G */
		G_C_Com = Huang.partition_c_component(All);
		Q[] Qs = new Q[G_C_Com.length];
		//Qs = print_C_Components(out, G_C_Com, stcI);
		
		ProbabilityVariable[] S = new ProbabilityVariable[stc.number_s()];
		ProbabilityVariable[] C = new ProbabilityVariable[stc.number_c()];
		ProbabilityVariable[] S_U_C = new ProbabilityVariable[stc.number_c()+stc.number_s()];
		
		S = stc.get_probability_variables_s();
		C = stc.get_probability_variables_c();
		
		/*S Union C*/
		S_U_C = Method.Add(S, C);
		Vector G_V_T = new Vector();
		ProbabilityVariable[] V = new ProbabilityVariable[stc.number_observed()];
		ProbabilityVariable[] T = new ProbabilityVariable[stc.number_t()];
		Vector<ProbabilityVariable> V_T = new Vector<ProbabilityVariable>();
		V = stc.get_observed_variables();
		T = stc.get_probability_variables_t();
		/* V_T =  V\T  */
		V_T = Method.Subtraction(V, T);
		/* G_v_t = Gv\t  That is a BayesNetwork, where include variables in v\t*/
		G_V_T = Huang.Gc(V_T);
		QuasiBayesNet G_v_t = new QuasiBayesNet();
		G_v_t = Huang.from_Vector_to_QuasiBayesNet(G_V_T, "Gv_t");
		/* D = An(S U C) in BayesNetwork G_v_t */
	
		
		System.out.println("Gv_t:");
		for (int k=0; k<G_v_t.number_variables(); k++){
			if (G_v_t.get_probability_variable(k).is_observed())
			System.out.print(G_v_t.get_probability_variable(k).get_name()+": ");
		
		}
		
		
		int An_len = VTFC.ancestors(G_v_t, S_U_C).length;
		ProbabilityVariable[] An_SC = new ProbabilityVariable[An_len];
		An_SC = VTFC.ancestors(G_v_t, S_U_C);
		int An_i = 0;
		ProbabilityVariable[] An = new ProbabilityVariable[An_len];
		for (int k=0;k<An_len; k++){
			if (An_SC[k].is_observed()){
				An[An_i] = An_SC[k];
				An_i ++;
			}
		}
		ProbabilityVariable[] D = new ProbabilityVariable[An_i];
		for(int k = 0; k< An_i; k++){
			D[k] = An[k];
		}
		//thisM.print_ProbabilityVariableArray(out, D);
		
	   ///////////////////////////////////////////////////////////////
		/*  subgraphh Gd */
		Vector DUP = new Vector();
		DUP = VTFC.DUP(qbn, D, out);
		ProbabilityVariable[] dup = new ProbabilityVariable[DUP.size()];
		dup = Method.from_Vector_to_ProbabilityVariable(DUP);
		ProbabilityVariable[] union1 = new ProbabilityVariable[DUP.size()+D.length];
		union1 = Method.Add(dup, D);
		BayesNet Gd = new BayesNet(VTFC.SubBayesNet(qbn, union1, "Gd"));
		QuasiBayesNet q_Gd = new QuasiBayesNet(Gd);
		/*
		System.out.println("Gd:");
		for (int k=0; k<q_Gd.number_variables(); k++){
			if (q_Gd.get_probability_variable(k).is_observed())
			System.out.print(q_Gd.get_probability_variable(k).get_name()+": ");
		
		}
		System.out.println();
		*/
		//find c-com of Gd
		Vector[] G_D_Com = new Vector[Gd.number_variables()];
		AlgorithmComputing AC = new AlgorithmComputing(q_Gd);
		G_D_Com = AC.partition_c_component(D);
		int num_c = G_D_Com.length;
		//System.out.println("&&&&&"+G_D_Com.length);
		////////////////////////////////////////////////////////////////
		out.println("Identifying Conditional Causal Effect Pt(s|c).");
		out.print("T = {");
		String t = thisM.print_ProbabilityVariableArray(out, T);
		out.println("}");	
		out.print("S = {");
		String s = thisM.print_ProbabilityVariableArray(out, S);
		out.println("}");
		out.print("C = {");
		String c = thisM.print_ProbabilityVariableArray(out, C);
		out.println("}");
		out.println();
		out.println("Print C-Components in graph G.");
		Qs = print_C_Components(out, G_C_Com, stcI);
		out.println();
		out.print('\n'+"Print D = (An(S U C) in BayesNetwork G_v\t): {");
		System.out.println('\n'+"Print D = (An(S U C) in BayesNetwork G_v_t): ");
		thisM.print_ProbabilityVariableArray(out, D);
		out.println("}");
		//////////////////////////////////////////////////////////////
		/* finding the c-components of the subGraph Gd. */
/*
		out.println("Print the c-components of the subGraph Gd:");
		System.out.println("Print the c-components of the subGraph Gd:");
		print_C_Components(out, G_D_Com, stcI);
*/		
		/* F = D\(S U C) */
		Vector<ProbabilityVariable> VF = new Vector<ProbabilityVariable>();
		ProbabilityVariable[] F = new ProbabilityVariable[D.length - S_U_C.length];
		VF = Method.Subtraction(D, S_U_C);
		F = Huang.from_Vector_to_ProbabilityVariable(VF);
		System.out.print("F = D|(SUC) : { ");
		out.print("F = D|(SUC) : { ");
		thisM.print_ProbabilityVariableArray(out, F);
		System.out.println("}");
		out.println("}");
		/*******************/
		/*    Phase 2      */
		/*******************/
		
		Vector N = new Vector();
		Vector I = new Vector();
		Vector I0 = new Vector();
		Vector I1 = new Vector();
		ProbabilityVariable[] F_0;
		ProbabilityVariable[] F_1;
		QFraction[] QF = new QFraction[G_D_Com.length];
		QFraction[] QF_out = new QFraction[G_D_Com.length];
		QF_out = Phase2_1(out, N, I, QF, G_D_Com, G_C_Com, Qs);
		Enumeration g, e;
		System.out.print("N:  { ");
		out.print("N:  { ");
		for (g = N.elements(); g.hasMoreElements(); ) {
			ProbabilityVariable[] PVs_N = (ProbabilityVariable[]) g.nextElement();
			System.out.print("{");
			out.print("{");
			thisM.print_ProbabilityVariableArray(out, PVs_N);
			System.out.print("} ");
			out.print("} ");
		}
		System.out.println(" } ");
		out.println(" } ");
		
		System.out.print("I:  { ");
		out.print("I:  { ");
		for (g = I.elements(); g.hasMoreElements(); ) {
			ProbabilityVariable[] PVs_I = (ProbabilityVariable[]) g.nextElement();
			System.out.print("{");
			out.print("{");
			thisM.print_ProbabilityVariableArray(out, PVs_I);
			System.out.print("} ");
			out.print("} ");
		}
		System.out.println(" } ");
		out.println(" } ");
		//Phase2_2
		if (N.size()==0){
			/* If N is Empty, then stop and output Pt(S|C). */
			//out.println("N is Empty, then stop and output Pt(S|C).");
			String R = output(out, QF_out,S, F, I, G_D_Com);
			System.out.println(R);
			out.println(R);
			return;
		}	
		else {
			/***********/
			/* Phase 3 */
			/***********/
			
			/*Initialize F0 = F n (To all of Di in N Pa(Di)) */
			ProbabilityVariable[] Union_pa = null;
			for (g = N.elements(); g.hasMoreElements(); ) {
				ProbabilityVariable[] PVs_N = (ProbabilityVariable[]) g.nextElement();
				ProbabilityVariable[] PVs_N_pa = VTFC.ancestors(qbn, PVs_N);
				Union_pa = Method.Add(Union_pa, PVs_N_pa);				
			}
			ProbabilityVariable[] Union_parents = null;
			if (Union_pa != null){
			  for(int i=0; i<Union_pa.length; i++){
				if (Union_pa[i].is_observed())
					Union_parents = Method.Add(Union_parents, Union_pa[i]);
			  }
			}
			//System.out.print("Union Parents:  ");
			//thisM.print_ProbabilityVariableArray(out, Union_parents);
			Vector<ProbabilityVariable> F0 = new Vector<ProbabilityVariable>();
			Vector<ProbabilityVariable> F1 = new Vector<ProbabilityVariable>();
			F0 = Method.Intersection(F, Union_parents);
			if (F0.size()==0){
				out.println("F0 is Empty.");
				System.out.println("F0 is Empty.");
			}
			else{
				out.print("F0 = ");
				System.out.print("F0 = {");
				thisM.print_Vector(out, F0);
				System.out.print("} ");
				out.println("} ");
			}
			F_0 = new ProbabilityVariable[F0.size()];
			F_0 = Method.from_Vector_to_ProbabilityVariable(F0);
			F_1 = new ProbabilityVariable[F.length-F0.size()];
			/* F1 = F\F0 */
			F1 = Method.Subtraction(F, F_0);
			F_1 = Method.from_Vector_to_ProbabilityVariable(F1);
			out.print("F1 = {");
			System.out.print("F1 = {");
			thisM.print_ProbabilityVariableArray(out, F_1);
			System.out.println("} ");
			out.println("} ");
			/* I0 = empty; I1 = I*/
			I0 = new Vector();
			I1 = I;
			// Print I1 
			System.out.print("I1:  { ");
			out.print("I1:  { ");
			for (g = I1.elements(); g.hasMoreElements(); ) {
				ProbabilityVariable[] PVs_I1 = (ProbabilityVariable[]) g.nextElement();
				System.out.print("{");
				out.print("{");
				thisM.print_ProbabilityVariableArray(out, PVs_I1);
				System.out.print("} ");
				out.print("} ");
			}
			System.out.println(" } ");
			out.println(" } ");
			
			
			//  Phase3_2. For each Di in I1
			boolean stop_Phase3_step2 = true;
			while (stop_Phase3_step2){
				
			Vector I1_copy = I1;
			for (g = I1.elements(); g.hasMoreElements(); ) {
				ProbabilityVariable[] PVs_I1 = (ProbabilityVariable[]) g.nextElement();
				ProbabilityVariable[] Di_pa = VTFC.ancestors(qbn, PVs_I1);
				Vector Intersection = Method.Intersection(Di_pa, F_0);
				if (Intersection.size()>0){
					I1_copy.remove(PVs_I1);
					I0.add(PVs_I1);
				}
			}
			I1 = I1_copy;
			/*
			// Print I1
			System.out.print("I1:  { ");
			out.print("I1:  { ");
			for (g = I1.elements(); g.hasMoreElements(); ) {
				ProbabilityVariable[] PVs_I1 = (ProbabilityVariable[]) g.nextElement();
				System.out.print("{");
				out.print("{");
				thisM.print_ProbabilityVariableArray(out, PVs_I1);
				System.out.print("} ");
				out.print("} ");
			}
			System.out.println(" } ");
			out.println(" } ");
			*/
		    //  Phase3_3. B = Intersection(F1, Union(Pa(Di))
			ProbabilityVariable[] Union_pa_B = null;
			for (g = I0.elements(); g.hasMoreElements(); ) {
				ProbabilityVariable[] PVs_I0 = (ProbabilityVariable[]) g.nextElement();
				ProbabilityVariable[] PVs_I0_pa = VTFC.ancestors(qbn, PVs_I0);
				Union_pa_B = Method.Add(Union_pa_B, PVs_I0_pa);				
			}
			int Union_size;
			if (Union_pa_B == null){
			Union_size = 0 ;
			}
			else{
				Union_size = Union_pa_B.length;
			}
			ProbabilityVariable[] Union_parents_B_tem = new ProbabilityVariable[Union_size];
			int idx_b =0;
			if(Union_pa_B != null){
			  for(int i=0; i<Union_pa_B.length; i++){
				if (Union_pa_B[i].is_observed()){
					//Union_parents_B = Method.Add(Union_parents_B, Union_pa[i]);
					Union_parents_B_tem[idx_b] = Union_pa_B[i];
				    idx_b++;
				}
			  }
			}
			ProbabilityVariable[] Union_parents_B = new ProbabilityVariable[idx_b];
			for(int i=0; i<idx_b; i++){
				Union_parents_B[i] = Union_parents_B_tem[i];
			  }
			
			Vector<ProbabilityVariable> B = new Vector<ProbabilityVariable>();
		    B = Method.Intersection(F_1, Union_parents_B);
		    // Print B
		    if (B==null){
		    	System.out.print("B is Empty.");
		    	out.println("B is Empty.");
		    	stop_Phase3_step2 = false;
		    }
		    else if (B.size()==0){
		    	System.out.print("B is Empty.");
		    	out.println("B is Empty.");
		    	stop_Phase3_step2 = false;
		    }
		    else{
		      /*
			  System.out.print("B:  { ");
			  out.print("B:  { ");
			  for (g = B.elements(); g.hasMoreElements(); ) {
				ProbabilityVariable PVs_B = (ProbabilityVariable) g.nextElement();
				System.out.print("{");
				out.print("{");
				System.out.print(PVs_B.get_name()+", ");
				out.print(PVs_B.get_name()+", ");
				System.out.print("} ");
				out.print("} ");
			  }			
			  out.println(" } ");
			  */
		    	Vector F1_copy = F1;
		    	Vector F0_copy = F0;
		    	for (g = B.elements(); g.hasMoreElements(); ) {
					ProbabilityVariable pv_B = (ProbabilityVariable) g.nextElement();
					F1_copy.remove(pv_B);
					F0_copy.add(pv_B);
				}			
			    F1 = F1_copy;
			    F0 = F0_copy;
			    F_1 = Method.from_Vector_to_ProbabilityVariable(F1);
		    }// end else if B not null
			}//end while (stop_Phase3_step2) 
		}//end the else of Phase 3
		/***********/
		/* Phase 4 */
		/***********/
		ProbabilityVariable[] Union_pa = null;
		for (g = N.elements(); g.hasMoreElements(); ) {
			ProbabilityVariable[] PVs_N = (ProbabilityVariable[]) g.nextElement();
			ProbabilityVariable[] PVs_N_pa = VTFC.ancestors(qbn, PVs_N);
			Union_pa = Method.Add(Union_pa, PVs_N_pa);				
		}
		for (g = I0.elements(); g.hasMoreElements(); ) {
			ProbabilityVariable[] PVs_I0 = (ProbabilityVariable[]) g.nextElement();
			ProbabilityVariable[] PVs_I0_pa = VTFC.ancestors(qbn, PVs_I0);
			Union_pa = Method.Add(Union_pa, PVs_I0_pa);				
		}
		
		ProbabilityVariable[] Union_parents = null;
		if (Union_pa != null){
		  for(int i=0; i<Union_pa.length; i++){
			if (Union_pa[i].is_observed())
				Union_parents = Method.Add(Union_parents, Union_pa[i]);
		  }
		}
		Vector<ProbabilityVariable> phase4 = new Vector<ProbabilityVariable>();
		phase4 = Method.Intersection(S, Union_parents);
		/*out.print("======================================== ");
		out.print("F1 = ");
		System.out.print("F1 = {");
		thisM.print_ProbabilityVariableArray(out, F_1);
		System.out.println("} ");
		out.println("} ");
		*/
		if (phase4 == null){
			System.out.println("Intersection of S and Union parents of Di is empty.");
			out.println("Intersection of S and Union parents of Di is empty.");
			System.out.println();
			out.println();
			System.out.print("Pt(s|c) = ");
			out.print("Pt(s|c) = ");
			String R = output(out, QF_out,S, F_1, I1, G_D_Com);
			System.out.println(R);
			out.println(R);
		}
		else if (phase4.size()==0){
			System.out.println("Intersection of S and Union parents of Di is empty.");
			out.println("Intersection of S and Union parents of Di is empty.");
			System.out.println();
			System.out.print("Pt(s|c) = ");
			out.print("Pt(s|c) = ");
			String R = output(out, QF_out,S, F_1, I1, G_D_Com);
			System.out.println(R);
			out.println(R);
		}
		else{
			System.out.print("Pt(s|c) is unidentifiable ");
			out.print("Pt(s|c) is unidentifiable ");
		}
		/*********************************************************	
		Vector[] N = new Vector[G_D_Com.length];
		Vector[] Icopy = new Vector[G_D_Com.length];
		int idx_n = 0;
		int idx_i = 0;
		///////////////////////////////////////
		QFraction[] Qi = new QFraction[G_D_Com.length];
		ProbabilityVariable pv, pv1;
		//System.out.println("Qi length = "+G_D_Com.length);
		int cont=0;
		Enumeration<ProbabilityVariable> g, e;
		for (int i=0; i<G_D_Com.length; i++){
			//System.out.println("=D["+i+"]="+"size = "+DD[i].size());
			ProbabilityVariable[] pvs_d = new ProbabilityVariable[G_D_Com[i].size()];
			for (g = G_D_Com[i].elements(); g.hasMoreElements(); ) {
                pv1 = (ProbabilityVariable)(g.nextElement());
               // System.out.println(pv1.get_name()+"\t");
                boolean call = false;
                Probability[] Pr_G_C_Com_i = null;
                ProbabilityVariable[] G_C_Com_of_i = null;
                for (int j=0; j<G_C_Com.length; j++){
    			//System.out.println("===V["+j+"]==="+"size = "+N[i].size());
    			int k = 0,kn=0;
    			boolean hiden = false;
    			ProbabilityVariable[] pvs = new ProbabilityVariable[G_C_Com[j].size()];
    			
     			for (e = G_C_Com[j].elements(); e.hasMoreElements(); ) {
                    pv = (ProbabilityVariable)(e.nextElement());
                    if(!pv.is_observed())
                    	hiden = true;
                   
                    pvs[k]=pv;
                    k++;
                   // System.out.println(pv.get_name()+"\t");
                    if (pv.get_name().equals(pv1.get_name())){
                    	call = true;
                    	//System.out.println(pv.get_name()+"-----------\t");
                   }
    			}
    			if (call){
    				pvs = Huang.from_Vector_to_ProbabilityVariable(G_C_Com[j]);
    				ProbabilityVariable[] G_C_Com_i = new ProbabilityVariable[Huang.get_num_Observed(pvs)];
    				G_C_Com_i = Huang.getObserved(pvs);
    				G_C_Com_of_i = G_C_Com_i;
    				ProbabilityVariable[] N_top_order= new ProbabilityVariable[stc.number_observed()];
					N_top_order = stcI.TopologicalOrder(stc.get_observed_variables());
    				if (hiden){
    					Pr_G_C_Com_i = stcI.Q_of_N(G_C_Com_i,N_top_order, qbn);	
    					
    				}
    				if(!hiden){
    					Pr_G_C_Com_i = stcI.QofN(G_C_Com_i,qbn);
    				}
    				
    				break;
    			}
    		}
       */         
         
               // System.out.println("\\\\\\\\\\\\\\");
      /*          pvs_d = Huang.from_Vector_to_ProbabilityVariable(G_D_Com[i]);
                pvs_d = Huang.getObserved(pvs_d);
                Q Q_G_C_Com_i = new Q(null, Pr_G_C_Com_i);
                Q[] QUP = new Q[1];
                QUP[0] = Q_G_C_Com_i;
               // System.out.println("?");
                QFraction QQ =new QFraction(QUP, null);
               // System.out.println("?");
                QFraction Qsi = new QFraction();
                System.out.println("Call Q[S]");
                
                //out.println("G_C_Com_of_i.size = " + G_C_Com_of_i.length);
                //out.println("pvs_d.size = " + pvs_d.length);
      /*          Qsi = Huang.AlgorithmIdentify(pvs_d, G_C_Com_of_i, QQ);
                
                
                if (Qsi == null){
                	N[idx_n] = G_D_Com[i];
                	idx_n++;
                
                }
                else{
                	Icopy[idx_i] = G_D_Com[i];
                	idx_i++;
                	
                	
                	if (cont <Qi.length ){
                		Qi[cont] = Qsi;
                    	cont++;
                	}
                	
                }
                break;
			}
			
		}
		Vector[] I = new Vector[idx_i];
		for (int i=0; i<idx_i; i++){
			I[i] = Icopy[i];
		}
		if (idx_n==0){
			/* If N is Empty, then stop and output Pt(S|C). */
	/*		Output(out, Qi,S, F , I);
			
			return;
		}
		
		/***********/
		/* Phase 3 */
		/***********/
		
		/*Initialize F0 = F n (To all of Di in N Pa(Di)) */
	/*	else{
			out.println("*****************************");
	
			LinkedList<ProbabilityVariable> all_pa_n = new LinkedList<ProbabilityVariable>();
			all_pa_n = UnionParents(out, N, idx_n, stcI);

			ProbabilityVariable[] all_N = new ProbabilityVariable[all_pa_n.size()];
			all_N =  thisM.LinkedListtoObject(all_pa_n);
			thisM.print_ProbabilityVariableArray(out, all_N);
			/*F0 = F intersection of all_N*/
	/*		Vector<ProbabilityVariable> F0 = new Vector<ProbabilityVariable>();
			Vector<ProbabilityVariable> F1 = new Vector<ProbabilityVariable>();
			F0 = Method.Intersection(F, all_N);
			ProbabilityVariable[] F_0 = new ProbabilityVariable[F0.size()];
			F_0 = Method.from_Vector_to_ProbabilityVariable(F0);
			ProbabilityVariable[] F_1 = new ProbabilityVariable[F.length-F0.size()];
			/* F1 = F\F0 */
		/*	F1 = Method.Subtraction(F, F_0);
			F_1 = Method.from_Vector_to_ProbabilityVariable(F1);
			/* I0 = empty; I1 = I*/
			
		/*	LinkedList<Vector<ProbabilityVariable>> I0 = new LinkedList<Vector<ProbabilityVariable>>();
			LinkedList<Vector<ProbabilityVariable>> I1 = new LinkedList<Vector<ProbabilityVariable>>();
			for (int i=0; i<idx_i; i++){
				I1.add(I[i]);
			}
			Vector[] I_0 = new Vector[I0.size()];
			boolean b_empty = false;
			while(!b_empty){
			/*  Phase 3  2. For each Di in I1*/
		/*		for (int i=0; i<idx_i; i++){
					int[] len = new int[1];
					len[0]=0;
					if(Method.Intersection(stcI.Parents(I[i], out, len), F_0).size()>0){
						I0.add(I[i]);
						I1.remove(I[i]);
					}
				}
				*/
				/* change LinkedList to Vector[] */
			/*	Vector<ProbabilityVariable> tem = new Vector<ProbabilityVariable>();
				I_0 = new Vector[I0.size()];
				Iterator<Vector<ProbabilityVariable>> list = I0.iterator();
				int idx = 0;
				while (list.hasNext()) {
		      		tem = list.next();
		      		I_0[idx] = tem;
		      		idx++;
		        }
				*/
				/*  Phase 3  3. Generate B*/
			/*	LinkedList<ProbabilityVariable> all_pa_I0 = new LinkedList<ProbabilityVariable>();
				all_pa_I0 = UnionParents(out, I_0, idx, stcI);
				ProbabilityVariable[] all_I0 = new ProbabilityVariable[all_pa_I0.size()];
				all_I0 =  thisM.LinkedListtoObject(all_pa_I0);
				Vector<ProbabilityVariable> B = new Vector<ProbabilityVariable>();
				B = Method.Intersection(F_1, all_I0);
				LinkedList<ProbabilityVariable> LinkedList_F1 = new LinkedList<ProbabilityVariable>();
				LinkedList<ProbabilityVariable> LinkedList_F0 = new LinkedList<ProbabilityVariable>();
			*/
				/* form Vector F1 to LinkedList_F1 */
				/*for (e = F1.elements(); e.hasMoreElements(); ) {
					pv = (ProbabilityVariable)(e.nextElement());
					LinkedList_F1.add(pv);
				}
				*/
				/* form Vector F0 to LinkedList_F0 */
		/*		for (e = F0.elements(); e.hasMoreElements(); ) {
					pv = (ProbabilityVariable)(e.nextElement());
					LinkedList_F0.add(pv);
				}
				if(B.size()==0){
					b_empty =true;
				}
				else{
					for (e = B.elements(); e.hasMoreElements(); ) {
						pv = (ProbabilityVariable)(e.nextElement());
 	                	LinkedList_F0.add(pv);
 	                	LinkedList_F1.remove(pv);
					}
					F_0 = thisM.LinkedListtoObject(LinkedList_F0);
					F_1 = thisM.LinkedListtoObject(LinkedList_F1);
				}
				
			}//end while
		*/	
		
			/***********/
			/* Phase 4 */
			/***********/
		/*	Vector[] copyN = new Vector[idx_n];
			for(int k=0; k<idx_n; k++){
				copyN[k] = N[k];
			}
			Vector[] NunionI0 = new Vector[idx_n+I_0.length];
			NunionI0 = thisM.Add(copyN, I_0);
			LinkedList Union = new LinkedList();
			Union = UnionParents(out, NunionI0, NunionI0.length, stcI);
			ProbabilityVariable[] union = new ProbabilityVariable[Union.size()];
			union = thisM.LinkedListtoObject(Union);
			if(Method.Intersection(S, union).size()==0){
				
			}
			else{
				out.println("FAIL!");
			}
		
		}
		
		
		*/
	}
	public void Output(PrintStream out, QFraction[] Qi, ProbabilityVariable[] S , ProbabilityVariable[] F , Vector[] I){
		String Q = "";
		LinkedList Qup = new LinkedList();
		LinkedList<Probability> LinkedList_p_up = new LinkedList<Probability>();
		LinkedList<Q> LinkedList_Q_up = new LinkedList<Q>();
		LinkedList Qdown = new LinkedList();
		STCInference stcI = new STCInference(qbn, true);
		for(int i=0; i<Qi.length; i++){
			int n_up = Qi[i].num_up() ;
			Q[] U = new Q[n_up];
			U = Qi[i].getUp();			
			for(int j=0; j<n_up; j++){
				/**
				int n_p = U[j].number_Probability();
				Probability[] p = new Probability[n_p];
				p= U[j].getProbability();
				for (int k=0; k<n_p;k++){
					LinkedList_p_up.add(p[k]);
				}
				*/
				LinkedList_Q_up.add(U[j]);					
			}
			
			Qup.add(U);
			Qdown.add(Qi[i].getDown());			 
		}
		Q[] Q_array_up = new Q[LinkedList_Q_up.size()];
		Q[] Q_array_u;
		Q[] Q_array_d;
		Iterator<Q> Q_list = LinkedList_Q_up.iterator();
		int idx = 0;
		while (Q_list.hasNext()) {     	
			Q_array_up[idx] = Q_list.next();
      		idx++;
        }
		methods m = new methods();
		ProbabilityVariable[] sum_up = F;
		Q_array_u = stcI.Summation(Q_array_up, sum_up);
		ProbabilityVariable[] sum_down = m.Add(F, S);
		Q_array_d = stcI.Summation(Q_array_up, sum_down);
		AlgorithmComputing Huang = new AlgorithmComputing(stc);
		String output_up = "";
		output_up = Huang.print(out, Q_array_u, sum_up);
		
		String output_down = "";
		output_down = Huang.print(out, Q_array_d, sum_down);
		if (output_down.equals("")){
			out.println(output_up);
		}
		else{
			String output = "";
			output = "{"+output_up+"}/{"+output_down+"}";
			out.println(output);
		}
	}
	public String output(PrintStream out, QFraction[] Qi, ProbabilityVariable[] S , ProbabilityVariable[] F1, Vector I1, Vector[] Di){
		String R = "";
		String strS = "";
		String strF = "";
		if(I1 == null){
			R="0";
		}
		else if(I1.size() == 0){
			R="0";
		}
		else{//1
		  if (S == null || S.length == 0){
			R = "1";
		  }
		  else{//2
			  String S_names = "";
			  for (int i=0; i<S.length; i++){
					S_names = S_names+" " +S[i].get_name();
			  }
			  strS = "¡Æ(" + S_names + ")";
			  String F1_names = "";
			  if (F1 != null && F1.length > 0){		  
				  for (int i=0; i<F1.length; i++){
					F1_names = F1_names+" " +F1[i].get_name();									
				  }
					 strF = "¡Æ(" + F1_names + ")";
		      }
		      String product = "";
			  product = Product(out, Qi, I1, Di) ;			 
			  R = "{"+strF + product + "} / {" +strS + strF + product +"}";
			  
		  }//2
		}//1
		return R;
	}
	public String Product(PrintStream out,QFraction[] Qi, Vector I1, Vector[] Di){
		AlgorithmComputing AC = new AlgorithmComputing(qbn);
		String R = "";
		Enumeration<ProbabilityVariable[]> e;
		Enumeration<ProbabilityVariable> g;
		//int idx = 0;
		int i_q = 0;
		int [] idx_q = new int[I1.size()];
		for (e = I1.elements(); e.hasMoreElements(); ) {
			ProbabilityVariable[] pvs_I= (ProbabilityVariable[])(e.nextElement());
			//idx = 0;
			
			for (int i = 0; i<Di.length; i++ ) {
				for (g = Di[i].elements(); g.hasMoreElements(); ) {
				  ProbabilityVariable pv_D = new ProbabilityVariable();
				  pv_D = (ProbabilityVariable)(g.nextElement());
				  if (pv_D.get_name().equals(pvs_I[0].get_name())){
					idx_q[i_q] = i;
					i_q ++;
					
					break;
				  }	
				}
				//idx ++;
			}
		}	
		for (int i = 0; i<idx_q.length; i++){
			R =R+"[";
			//out.print("[");
			//here for detail probability.
			//R =R+AC.QFprint(Qi[idx_q[i]], out);
			R = R+"Q[{";
			for (g = Di[idx_q[i]].elements(); g.hasMoreElements(); ) {
				ProbabilityVariable pv_D = new ProbabilityVariable();
				pv_D = (ProbabilityVariable)(g.nextElement());
				R=R+" " +pv_D.get_name();
			}
			R = R+"}] ";
			//out.print("]");
			R=R+"]";
		}
		return R;
	}
	/*
	public void output(PrintStream out, QFraction[] Qi, ProbabilityVariable[] S , ProbabilityVariable[] F1, Vector I1){
		Enumeration<ProbabilityVariable[]> e;
		Vector[] I = new Vector[I1.size()];
		int idx = 0;
		for (e = I1.elements(); e.hasMoreElements(); ) {
			ProbabilityVariable[] pvs_I= (ProbabilityVariable[])(e.nextElement());
			for (int i=0; i<I1.size(); i++){
				I[idx].add(pvs_I);
			}
			idx++;
		}
		Output(out, Qi, S , F1 , I);
		
	}
	*/
	public void Output(PrintStream out, QFraction[] Qi, ProbabilityVariable[] S , ProbabilityVariable[] F){
		String Q = "";
		LinkedList Qup = new LinkedList();
		LinkedList<Probability> LinkedList_p_up = new LinkedList<Probability>();
		LinkedList<Q> LinkedList_Q_up = new LinkedList<Q>();
		LinkedList Qdown = new LinkedList();
		STCInference stcI = new STCInference(qbn, true);
		for(int i=0; i<Qi.length; i++){
			int n_up = Qi[i].num_up() ;
			Q[] U = new Q[n_up];
			U = Qi[i].getUp();			
			for(int j=0; j<n_up; j++){
				/**
				int n_p = U[j].number_Probability();
				Probability[] p = new Probability[n_p];
				p= U[j].getProbability();
				for (int k=0; k<n_p;k++){
					LinkedList_p_up.add(p[k]);
				}
				*/
				LinkedList_Q_up.add(U[j]);					
			}
			
			Qup.add(U);
			Qdown.add(Qi[i].getDown());			 
		}
		Q[] Q_array_up = new Q[LinkedList_Q_up.size()];
		Q[] Q_array_u;
		Q[] Q_array_d;
		Iterator<Q> Q_list = LinkedList_Q_up.iterator();
		int idx = 0;
		while (Q_list.hasNext()) {     	
			Q_array_up[idx] = Q_list.next();
      		idx++;
        }
		methods m = new methods();
		ProbabilityVariable[] sum_up = F;
		Q_array_u = stcI.Summation(Q_array_up, sum_up);
		ProbabilityVariable[] sum_down = m.Add(F, S);
		Q_array_d = stcI.Summation(Q_array_up, sum_down);
		AlgorithmComputing Huang = new AlgorithmComputing(stc);
		String output_up = "";
		output_up = Huang.print(out, Q_array_u, sum_up);
		
		String output_down = "";
		output_down = Huang.print(out, Q_array_d, sum_down);
		if (output_down.equals("")){
			out.println(output_up);
		}
		else{
			String output = "";
			output = "{"+output_up+"}/{"+output_down+"}";
			out.println(output);
		}
	}
    public LinkedList<ProbabilityVariable> UnionParents(PrintStream out, Vector[] I, int idx_i, STCInference stcI){
		/* idx_i is number of not null I. Notice: Not each I[i] has value*/
    	Vector<ProbabilityVariable> Ii = new Vector();
		LinkedList<ProbabilityVariable> all_pa_i = new LinkedList<ProbabilityVariable>();
		for (int i=0; i<idx_i; i++){
			Ii = I[i];
			int[] len = new int[1];
			len[0]=0;
			//thisM.print_ProbabilityVariableArray(out,stcI.Parents(Ii, out, len));
			stcI.Parents(Ii, out, len);
			ProbabilityVariable b = new ProbabilityVariable();
			ProbabilityVariable a = new ProbabilityVariable();
			ProbabilityVariable[] curr = new ProbabilityVariable[len[0]];
			curr = stcI.Parents(Ii, out, len);
			System.out.println(len[0]);
			for (int j=0; j<len[0]; j++){
				System.out.println("j = "+j);
				a = curr[j];
				Iterator<ProbabilityVariable> k = all_pa_i.iterator();
				boolean flag=false;
				while (k.hasNext()) {
					b =  (ProbabilityVariable) k.next();
					if(b.get_name().equals(a.get_name())){
						flag = true;
						break;
					}
				}
				if(!flag){
					all_pa_i.add(a);
				}
			}			
		}

		return all_pa_i;
	}
	public Q[] print_C_Components(PrintStream out, Vector[] S, STCInference stcI){
		Enumeration<ProbabilityVariable> e;
		Q[] Q_R = new Q[S.length];
		int idx_Q = 0;
		ProbabilityVariable pv = new ProbabilityVariable();
		String v = "";
		AlgorithmComputing AC = new AlgorithmComputing(qbn);
		for(int i=0; i<S.length; i++){
			v="";
			int len = S[i].size();
			ProbabilityVariable[] Si_tem = new ProbabilityVariable[len];
			int idx = 0;
			if (S[i]!=null){
				for (e = S[i].elements(); e.hasMoreElements(); ) {
		            pv = (ProbabilityVariable)(e.nextElement());
		            if (pv.is_observed()){
		               v= v+pv.get_name()+",";	
		               Si_tem[idx] = pv;
		               idx++;
		            }
				}
				out.println("S["+i+"]:   "+v);
				
			}
			out.print("Q[{"+v+"}] = ");
			ProbabilityVariable[] Si = new ProbabilityVariable[idx];
			
			System.out.print("S" + i+":  ");
			
			for (int j=0; j<idx; j++){
				Si[j] = Si_tem[j];
				System.out.print(Si[j].get_name()+",");
			}
			System.out.println();
			String Q_S="";
			Verma_Type_Functional_Constraint VTFC = new Verma_Type_Functional_Constraint();
			Q Qsi = new Q();
			Qsi = VTFC.Corollary_1(qbn, Si, out);
			Q_S = AC.Qprint(Qsi);
			Q_R[idx_Q] = Qsi;
			idx_Q ++;
			out.print(Q_S);
			//Q_S = print_Q_of_S(out,  S[i], stcI);
			//out.println(Q_S);
			out.println();
			out.println();
			//out.println("==============================="+"/n");
		}//end for
		return Q_R;
	}
 
	public String print_Q_of_S(PrintStream out, Vector<ProbabilityVariable> S, STCInference stci){
		/*Print Q[S]
		 * If there is no hide variables in S, Then Q[S] = P(v|pa(v))
		 * Otherwise, Q[S]=P(vi|v(i-1))
		 * That is, Print Lemma1
		 */
		Enumeration<ProbabilityVariable> e;
		ProbabilityVariable pv = new ProbabilityVariable();
		
		//stci.qbn.print(out);
		
		Boolean hide = false;
		String Q_S = "";
			if (S!=null){
				methods M = new methods();
				ProbabilityVariable[] pvs = new ProbabilityVariable[S.size()];
				ProbabilityFunction[] pfs = new ProbabilityFunction[S.size()];
				pvs = M.from_Vector_to_ProbabilityVariable(S);
				pfs =stci.transfer_pvs_to_pfs(pvs, qbn);
				//Probability[] R = new Probability[pvs.length];
				//out.println("S["+i+"]:"+"/n");
				for (e = S.elements(); e.hasMoreElements(); ) {
		            pv = (ProbabilityVariable)(e.nextElement());
		            if (!pv.is_observed()){
		            	hide = true;
		            	break;
		            }
		            out.println("^ "+pv.get_name()+" ^");		
				}
				if(!hide){
					for(int i =0; i<pvs.length; i++){
						if (pvs[i]!=null){
							Q_S = "P("+pvs[i].get_name();
							Probability P = new Probability(pfs[i]);
							if (P.getNum_evi()>0){
								Q_S =Q_S+"|";
								for (int j=0;j<P.getNum_evi(); j++){
									if (j!=P.getNum_evi()-1){
										Q_S =Q_S+P.getEvidences()[j].get_name()+", ";
									}
									else{
										Q_S =Q_S+P.getEvidences()[j].get_name()+")";
									}
								}
							}
							else{
								Q_S =Q_S+")";
							}
						}
					}
					
				}
				else{
					ProbabilityVariable[] N_top_order= new ProbabilityVariable[stc.number_observed()];
					ProbabilityVariable[] Ni = new ProbabilityVariable[M.get_num_Observed(pvs)];
					N_top_order = stci.TopologicalOrder(stc.get_observed_variables());
    				Ni = M.getObserved(pvs);
    				Probability[] Pr_N_i = new Probability[Ni.length];
    				Pr_N_i = stci.Q_of_N(Ni,N_top_order, qbn);
    				for (int k=0; k< Pr_N_i.length; k++){
    					Q_S = Q_S+Pr_N_i[k].print(out);
    				}
    				
				}
			}
			return Q_S;
	}
	
	public void print(PrintStream out){
		 algorithm_computing(out);
		
	
		//Print_Probability_array(Q_D, out);
		/*
		String name = st.get_name();
		int s_lenght = st.get_probability_variables_s().length;
		ProbabilityVariable pv[] = new ProbabilityVariable[s_lenght];
		pv = st.get_probability_variables_s();
		
		for (int i=0; i< s_lenght; i++){
			
				out.print("s["+i+"].name = "+pv[i].get_name()+"\n\n");
		
		}
		out.print("s_lenght = "+s_lenght+"\n\n");
		out.print(pts+name +pts+ ":  algorithm computing. \n");
		*/
	}
	public QFraction[] Phase2_1(PrintStream out, Vector N, Vector I, QFraction[] QF, Vector[] com_D, Vector[] com_G, Q[] Qs){
		QFraction[] R = new QFraction[com_D.length];
		QF = new QFraction[Qs.length];
		int idx_QF = 0;
		int idx_N = 0;
		int idx_I = 0;
		for (int i=0; i<Qs.length; i++){
			Q[] Q_UP = new Q[1];
			Q_UP[0] = Qs[i];
			QFraction QFi = new QFraction(Q_UP);
			QF[idx_QF] = QFi;
			idx_QF ++;		
		}
		Method M = new Method();
		methods m = new methods();
		Verma_Type_Functional_Constraint VTFC = new Verma_Type_Functional_Constraint();
		idx_QF ++;
		Enumeration<ProbabilityVariable> g, e;
		AlgorithmComputing AC = new AlgorithmComputing(qbn);
		ProbabilityVariable pv_D = new ProbabilityVariable();
		ProbabilityVariable pv_G = new ProbabilityVariable();
		for (int i=0; i<com_D.length; i++){
			ProbabilityVariable[] Di = new ProbabilityVariable[com_D[i].size()];
			Di = m.from_Vector_to_ProbabilityVariable(com_D[i]);
			boolean stop = false;
			for (g = com_D[i].elements(); g.hasMoreElements(); ) {
				stop = false;
                pv_D = (ProbabilityVariable)(g.nextElement());
                
                for (int j=0; j<com_G.length; j++ ) {
                	
                	ProbabilityVariable[] Si = new ProbabilityVariable[com_G[j].size()];
                	Si = m.from_Vector_to_ProbabilityVariable(com_G[j]);
                	if (VTFC.In(pv_D, Si)){
                		String di = "";
                		out.print("Di:  {");
                		di = M.print_ProbabilityVariableArray(out, Di);
                		out.println("}");
                		ProbabilityVariable[] S_i = new ProbabilityVariable[Si.length];
                		int idx_Si = 0;
                		for (int k = 0; k<Si.length; k++){
                			if (Si[k].is_observed()){
                				S_i[idx_Si] = Si[k];
                				idx_Si ++;
                			}
                		}
                		ProbabilityVariable[] SI = new ProbabilityVariable[idx_Si];
                		for (int k = 0; k<idx_Si; k++){
                			SI[k] =S_i[k];
                		}
                		//System.out.println("Si:  ");
                		//M.print_ProbabilityVariableArray(out, SI);
                		//System.out.println();
                		R[i] = AC.AlgorithmIdentify(Di, SI, QF[j], out);
                		System.out.println();
                		out.println();
                		System.out.print("Q[{"+di+"}] = " );
                		out.print("Q[{"+di+"}] = " );
                		//out.print(AC.QFprint(R[i]));
                		out.print(AC.QFprint(R[i], out));
                		System.out.println();
                		//System.out.println("-----------------" );
                		out.println();
                		out.println();
                		//out.println("-----------------" );
                		if (R[i] == null){
                        	N.add(Di);
                        	idx_N++;
                        	//return null;
                        }
                		else{
                			I.add(Di);
                			idx_I ++;
                		}
                		stop = true;
                		break;
                		
                	}
                }
                if (stop){
                	break;
                }
			}
		}
		return R;
	}

}
