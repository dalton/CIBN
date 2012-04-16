package aaai_08;

import java.io.PrintStream;

import BayesianNetworks.ProbabilityVariable;
import Identifiability.AlgorithmComputing;
import Identifiability.STInference;
import Identifiability.methods;
import QuasiBayesianNetworks.QuasiBayesNet;

public class BuildSEMs {
	//QuasiBayesNet qbn;
	//SEMs sems;
	public SEMs build_sems(PrintStream out, QuasiBayesNet qbn, String queried_variable){
		//out.print(queried_variable);
		STInference STI = new STInference(qbn, true);
		int num_Vars = qbn.number_variables();
		methods m = new methods();
		ProbabilityVariable[] top_order = new ProbabilityVariable[num_Vars];
		top_order = STI.TopologicalOrder(qbn.get_probability_variables());
		int node_idx = m.getIndexFromPVArray(top_order, queried_variable);
		SEM[] sem = new SEM[node_idx];
		
		for (int k=0; k<node_idx; k++){
			System.out.println ("j="+node_idx);
			System.out.println ("k="+k);
			sem[k] = build_SEM(top_order, node_idx,k, qbn);
		}
		SEMs re = new SEMs(sem);
		re.print_Equation16(out);
		return re;
	}
	public SEM build_SEM(ProbabilityVariable[] top_order, int j, int k, QuasiBayesNet qbn){
		
		methods m = new methods();
		String[][] b_l = new String[j-k-1][2];
		String[][] a_l = new String[j-k-1][2];
		String[] b1 = new String[2];
		String[] c = new String[2];
		String[] a = new String[2];
			b1[0] = m.getpvnameFromArray(top_order, j);
			b1[1] = m.getpvnameFromArray(top_order, k);
			if (!qbn.isEdge(top_order[j], top_order[k])){
				c[0]= "0";
				c[1] = "0";
			}
			else{
				c[0]= b1[0];
				c[1] = b1[1];
			}
			if (!qbn.isBiEdge(top_order[j], top_order[k])){
				a[0]= "0";
				a[1] = "0";
			}
			else{
				a[0]= b1[0];
				a[1] = b1[1];
			}
			for (int l=k+1; l<j; l++){
				if (!qbn.isBiEdge(top_order[j], top_order[l])){
					a_l[l-k-1][0] = "0";
					a_l[l-k-1][1] = "0";
				}
				else{
				    a_l[l-k-1][0] = m.getpvnameFromArray(top_order, j);
					a_l[l-k-1][1] = m.getpvnameFromArray(top_order, l);
				}
				b_l[l-k-1][0] = m.getpvnameFromArray(top_order, l);
				b_l[l-k-1][1] = m.getpvnameFromArray(top_order, k);
			}
			SEM sem = new SEM(b1,getSjk(top_order,j,k),a,c,b_l,a_l);
			System.out.println("c="+sem.c[0]+sem.c[1]);
		return sem;
	}
	public String[] getSjk(ProbabilityVariable[] top_order, int j, int k){
	
		methods m = new methods();
		int len = top_order.length;
		if(len-2<1)
			return null;
		ProbabilityVariable[] tem1 = new ProbabilityVariable[len-1];
		tem1 = m.Subtraction(top_order, top_order[j]);
		ProbabilityVariable[] tem2 = new ProbabilityVariable[len-2];
		tem2 = m.Subtraction(tem1, top_order[k]);
		String[] re = new String[len-2];
		for (int i=0; i<len-2; i++){
			re[i] = tem2[i].get_name();
		}
		return re;
	}
}
