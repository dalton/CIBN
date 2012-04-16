/**
 * 
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

import BayesianInferences.DSeparation;
import BayesianNetworks.BayesNet;
import BayesianNetworks.DiscreteFunction;
import BayesianNetworks.DiscreteVariable;
import BayesianNetworks.ProbabilityFunction;
import BayesianNetworks.ProbabilityVariable;
import Identifiability.AlgorithmComputing;
import Identifiability.JointProbability;
import Identifiability.Probability;
import Identifiability.Q;
import Identifiability.QFraction;
import Identifiability.STBayesNet;
import Identifiability.STInference;
import Identifiability.methods;
import QuasiBayesianInferences.QBInference;
import QuasiBayesianNetworks.QuasiBayesNet;

public class STCInference extends QBInference{
	STCBayesNet stc;
	QuasiBayesNet qbn;
	STBayesNet st;
	STInference stInference;
	
	  /**
	   * Constructor for a QBInference.  
	   */
	  public STCInference(BayesNet b_n, boolean dpc) {
	    super(b_n, dpc);
	    qbn = new QuasiBayesNet(b_n);
	    stc = new STCBayesNet(qbn);
	    st = new STBayesNet(qbn);
	    stInference = new STInference(b_n, dpc);
	    original_bn = b_n;
	    transform_network();
	    
	  }
	  public STCInference(QuasiBayesNet b_n, boolean dpc) {
		    super(b_n, dpc);
		    qbn = b_n;
		    stc = new STCBayesNet(b_n);
		    st = new STBayesNet(b_n);
		    stInference = new STInference(b_n, dpc);
		    original_bn = b_n;
		    transform_network();
		  }
	  public STCInference(STCInference in) {
		// TODO Auto-generated constructor stub
		  super(in.get_bayes_net(), in.isDo_produce_clusters());
		
		  qbn = in.qbn;
		  stc = in.stc;
		  st = in.st;
		  stInference = in.stInference;
		  original_bn = qbn;
		  transform_network();
	}
	  public STCInference(STInference in) {
			// TODO Auto-generated constructor stub
			  super(in.get_bayes_net(), in.isDo_produce_clusters());
			 
			  stc = new STCBayesNet(in.getSt());
			  qbn = in.getQbn();
			
			  st = in.getSt();
			  stInference = in;
			  original_bn = qbn;
			  transform_network();
		}
		public JointProbability Q_N(ProbabilityVariable[] PVS){
			  STCInference STC = new STCInference(this);
			  //System.out.println("ST=&&&&&   "+ST.get_bayes_net().number_variables());
			  ProbabilityVariable[] all_var = new ProbabilityVariable[STC.bn.number_variables()];
			  ProbabilityVariable[] pvs = new ProbabilityVariable[PVS.length];
			  pvs = PVS;
			  STC.set_all_to_unobserved();
			 
			  JointProbability JP=new JointProbability();
			  String joint_probability;
			  int size = pvs.length;
			  int[] n_value = new int[size];
			  int total_value = 1;

			  ProbabilityFunction[] pfs = new ProbabilityFunction[size];
			  
			  Vector V = new Vector();
			  ProbabilityFunction pf;
			  for (int i=0; i<size; i++){
					//System.out.println(pvs[i].get_name());
				    pfs[i] = stc.get_function(pvs[i]);
				  	pvs[i].set_invalid_observed_index();
				  	n_value[i] = pvs[i].number_values();
				  	total_value = total_value * n_value[i];
				  	//System.out.println(total_value);
				}
			  
			  
			  DiscreteFunction Q_N = new DiscreteFunction(size,total_value ) ;
			// Vector V = new Vector();
			  for (int i=0; i<size; i++){
					//System.out.println(pvs[i].get_name());			  
					STC.inference(pvs[i].get_name());	
					double[] v = new double[n_value[i]];
					pf = STC.get_result(); //??????????????????????????/
					for(int j=0; j<n_value[i]; j++){
						v[j] = pf.get_value(j);
						
					}
					V.addElement(v);
				    print(false);
				}
			  Enumeration e;
			  //double[] joint_probability = new double[total_value];
			  double[] x;
			  for (e = V.elements(); e.hasMoreElements(); ) {
	              x = (double[])(e.nextElement());
	              for(int k=0; k<x.length; k++){
	            	  System.out.println("==="+x[k]);
	              }
			  }
			  joint_probability="";
			  String pro, temp;
			  int n_pvs;
			  for (int i=0; i<size; i++){;
					//System.out.println(pvs[i].get_name());			  	
					//pvs[i].set__observed_index();	
					Q_N.set_variable(i, pvs[i]);
					n_pvs = pfs[i].number_variables();
					DiscreteVariable[] dv= new DiscreteVariable[n_pvs];
					dv = pfs[i].get_variables();
					pro = "";
					if (dv.length == 1){
						pro = "P("+ dv[0].get_name()+")";
					}
					else if(dv.length>1){
						pro = "P(";
						//System.out.println("dv.length = "+dv.length);
						for (int j=0;j<dv.length;j++){
							if(j==0)
								temp = dv[j].get_name()+" | ";
							else if (j==n_pvs-1){
								temp = dv[j].get_name()+")";
								
							}
							else
								temp = dv[j].get_name() + " ";
							pro = pro+temp;
						}
					}
						if (i>0)
							joint_probability=joint_probability+"*"+pro;
						else
							joint_probability=pro;
					
				}
			  JP.setJoint_probability(joint_probability);
			  JP.setPfs(pfs);
			  JP.setPvs(pvs);
			  JP.setV(V);
			  JP.setNum_variables(pvs.length);
			  
			 //System.out.println(joint_probability);
			  return JP;
		  }
		  public Probability[] Joint_Probability(ProbabilityVariable[] PVS){
			  STCInference STC = new STCInference(this);
			  //System.out.println("ST=&&&&&   "+ST.get_bayes_net().number_variables());
			  ProbabilityVariable[] all_var = new ProbabilityVariable[STC.bn.number_variables()];
			  ProbabilityVariable[] pvs = new ProbabilityVariable[PVS.length];
			  pvs = PVS;
			  STC.set_all_to_unobserved();
			 
			  pvs = PVS;
			  JointProbability JP=new JointProbability();
			  Probability[] Pr = new Probability[pvs.length];
			  
			  String joint_probability;
			  int size = pvs.length;
			  int[] n_value = new int[size];
			  int total_value = 1;
			 
			  ProbabilityFunction[] pfs = new ProbabilityFunction[size];
			 
			  Vector V = new Vector();
			  ProbabilityFunction pf;
			  for (int i=0; i<size; i++){
					//System.out.println(pvs[i].get_name());
				    pfs[i] = stc.get_function(pvs[i]);
				  	pvs[i].set_invalid_observed_index();
				  	n_value[i] = pvs[i].number_values();
				  	total_value = total_value * n_value[i];
				  	//System.out.println(total_value);
				  	DiscreteVariable[] evs = new DiscreteVariable[pfs[i].number_variables()-1];
				  	evs = Subtraction(pfs[i].get_variables(), pvs[i]);
				  	Probability P = new Probability(pvs[i],evs);
				  	Pr[i]=P;
				  	Pr[i].setNum_evi(evs.length);
				}
			  
			  
			  DiscreteFunction Q_N = new DiscreteFunction(size,total_value ) ;
			// Vector V = new Vector();
			  for (int i=0; i<size; i++){
					//System.out.println(pvs[i].get_name());			  
					STC.inference(pvs[i].get_name());	
					double[] v = new double[n_value[i]];
					pf = STC.get_result();
					for(int j=0; j<n_value[i]; j++){
						v[j] = pf.get_value(j);
						
					}
					V.addElement(v);
					Pr[i].setValue(v);
					//////////////////
				    //print(false);///
					///////////////////
				}
			  Enumeration e;
			  //double[] joint_probability = new double[total_value];
			  double[] x;
			  for (e = V.elements(); e.hasMoreElements(); ) {
	              x = (double[])(e.nextElement());
	             /* for(int k=0; k<x.length; k++){
	            	  System.out.println("==="+x[k]);
	              }*/
			  }
			  joint_probability="";
			  String pro, temp;
			  int n_pvs;
			  for (int i=0; i<size; i++){;
					//System.out.println(pvs[i].get_name());			  	
					pvs[i].set__observed_index();	
					Q_N.set_variable(i, pvs[i]);
					n_pvs = pfs[i].number_variables();
					DiscreteVariable[] dv= new DiscreteVariable[n_pvs];
					dv = pfs[i].get_variables();
					pro = "";
					if (dv.length == 1){
						pro = "P("+ dv[0].get_name()+")";
					}
					else if(dv.length>1){
						pro = "P(";
						//System.out.println("dv.length = "+dv.length);
						for (int j=0;j<dv.length;j++){
							if(j==0)
								temp = dv[j].get_name()+" | ";
							else if (j==n_pvs-1){
								temp = dv[j].get_name()+")";
								
							}
							else
								temp = dv[j].get_name() + " ";
							pro = pro+temp;
						}
					}
						if (i>0)
							joint_probability=joint_probability+"*"+pro;
						else
							joint_probability=pro;
					
				}
			  JP.setJoint_probability(joint_probability);
			  JP.setPfs(pfs);
			  JP.setPvs(pvs);
			  JP.setV(V);
			  JP.setNum_variables(pvs.length);
			  
			 //System.out.println(joint_probability);
			  return Pr;
		  }
		  /*
		   * topology order only for H nodes.
		   */
		  
		  public ProbabilityVariable[] TopologicalOrder(ProbabilityVariable[] H){
			  DSeparation dsep =new DSeparation((BayesNet)qbn);
			  ProbabilityFunction[] order = new ProbabilityFunction[H.length];
			  ProbabilityVariable[] Order = new ProbabilityVariable[H.length];
			  ProbabilityFunction[] temp = new ProbabilityFunction[H.length];
			  int count = H.length;
			  for(int i=0; i<count; i++){
				  order[i]=stc.get_function(H[i]);
			  }
			  
			  temp = order;
			  set_all_mark_0(order);
			  set_all_mark_0(temp);
			  
			  int count_mark = 0;
			  while (count_mark<count){
				  boolean q = true;
				  for (int i=0; i<H.length && q; i++){
					  if(temp[i].get_variables()[0].getMark()==0){
						  temp[i].get_variables()[0].setMark(1);
						  order[i].get_variables()[0].setMark(1);
						  
						  count_mark++;
						  for (int j = i+1; j<H.length; j++){
						  	if(dsep.adj(temp[j].get_indexes()[0], temp[i].get_indexes()[0], 0)){
						  		//System.out.println("( j= "+j+"  )   ;  H.length = "+ H.length);
							  if (temp[j].number_variables()==1){
								  order = insert_j_to_first(order, j);
								  q=false;
								//  System.out.println("****("+temp[j].get_variables()[0].get_name()+")");
							  }
							  else{							 
								  order = insert_j_to_i(order, i, j);							 
								  q=false;
								 // System.out.println("***["+temp[j].get_variables()[0].get_name()+"]");
							  }
							//  System.out.println("?");
							  
						  	}
						  	
						  }
						//  System.out.println("-");
					  }
				  }
				 // if (order[0]==null)
					//  System.out.println("(--9999--)");
				  temp = order;
				 // System.out.println("&");
			  }
			  for(int i=0; i<count; i++){
				  //System.out.println("i="+i);
				  //System.out.println("("+order[i].get_variables()[0].get_name()+")");
				  //if (order[i]==null)
					//  System.out.println("(9999)");
				  ProbabilityVariable O = new ProbabilityVariable(stc.get_probability_variable(order[i].get_variables()[0].get_index()));
				  Order[i]=O;
				  
			  }
			  return Order;
		  }
		  /*
		   * i<j
		   */
		  public ProbabilityFunction[] insert_j_to_i(ProbabilityFunction[] O, int i, int j){
			  ProbabilityFunction[] R = new ProbabilityFunction[O.length];
			 
			  for (int k=0; k<O.length; k++){
				  if(k<i)
					  R[k] = O[k];
				  if(k==i)
					  R[k] = O[j];
				  if(k>i && k<=j)
					  R[k] = O[k-1];
				  if(k>j)
					  R[k] = O[k];
			  }
			  return R;
		  }
		  public ProbabilityFunction[] insert_j_to_first(ProbabilityFunction[] order,  int j){
			  ProbabilityFunction[] R = new ProbabilityFunction[order.length];
			  R[0] = order[j];
			  for (int k=1; k<order.length; k++){
				  if(k<=j)
					  R[k] = order[k-1];
				  
				  if(k>j)
					  R[k] = order[k];
			  }
			  return R;
		  }
		  /*
		   * set all of the variavle s in H 's mark to 0.
		   */
		  public void set_all_mark_0(ProbabilityFunction[] H){
			  for (int i=0; i<H.length; i++){
				  H[i].get_variables()[0].setMark(0);
			  }
		  }
		  public void set_all_mark_0(ProbabilityVariable[] H){
			  for (int i=0; i<H.length; i++){
				  H[i].setMark(0);
			  }
		  }
		  /*
		   * transfer ProbabilityVariable[] to ProbabilityFunction[]
		   */
		  public ProbabilityFunction[] transfer_pvs_to_pfs(ProbabilityVariable[] H, QuasiBayesNet qbn){
			 
			  ProbabilityFunction[] R = new ProbabilityFunction[H.length];
			  for (int i=0; i<H.length; i++){
				  R[i] = qbn.get_probability_function(H[i].get_index());
			  }
			  
			  return R;
		  }
		  /*
		   * Transfer JointProbability to Probability[].
		   * lemmar2 Q[H(j)] = SUM(h\h(j))Q[H]
		   */
		  public Probability[] Transfer_JointProbability_to_Probability(JointProbability Q_H){
			  //JointProbability Q_Hj = new JointProbability();
			  ProbabilityVariable[] Hv = new ProbabilityVariable[Q_H.getNum_variables()];
			  ProbabilityFunction[] Hf = new ProbabilityFunction[Q_H.getNum_variables()];
			  Probability[] Pr = new Probability[Q_H.getNum_variables()];
			  Hv = Q_H.getPvs();
			  Hf = Q_H.getPfs();
			  Enumeration e;
			  Vector v = new Vector();
			  v = Q_H.getV();
			  int c=0;
			  double[] val ;
			  //System.out.println("Q_H.getNum_variables()="+Q_H.getNum_variables());
	/*		  System.out.println("Pr="+Pr.length);
			  for (e = v.elements(); e.hasMoreElements(); ) {
		            val = (double[])(e.nextElement());
		            System.out.println("c="+c);
		            System.out.println("val.length="+val.length);
		            if (val!=null)
		            	System.out.println("cccccc=");
		            //Pr[c].value=val;
		            //Pr[c].setValue(val);
		            Pr[c].setNum_value(val.length);
		            c++;
			  }
			  */
			  for (int i=0; i<Q_H.getNum_variables(); i++){
				  Pr[i].setName(Hf[i].get_variables()[0]);
				  DiscreteVariable[] dv = new DiscreteVariable[Hf[i].number_variables()-1] ;
				  for (int k=1; k<Hf[i].number_variables(); k++){
					  dv[k-1] = Hf[i].get_variables()[k];
				  }
				  Pr[i].setEvidences(dv);
				  Pr[i].setNum_evi(dv.length);
				 			  
			  }
			 
//			  System.out.println("c="+c+";  Q_H.getNum_variables()="+Q_H.getNum_variables());
			  
	/*		  ProbabilityVariable[] Hvj = new ProbabilityVariable[Q_H.getNum_variables()];
			  ProbabilityFunction[] Hfj = new ProbabilityFunction[Q_H.getNum_variables()];
			  for(int i = 0; i<=j; i++){
				  Hfj[i] = Hf[i];
				  Hvj[i] = Hv[i];
				  
			  }
	*/		  
			  return Pr;
		  }
		  /*
		   * Summation : SUM(h\h[j])Q(H); 
		   * Q_H in in topological order.
		   */
		  public Probability[] Summation(Probability[] Q_H, int j){
			  //Vector Q = new Vector();
			  Probability[] temp = new Probability[Q_H.length];
			  temp=Q_H;
			  //int sum = 0;
			  int exp=0;
			  int dis=1;
			  for(int k=j; k<Q_H.length; k++){
				  for (int i=0; i<Q_H.length; i++){
					 if (Q_H[i].getName().get_name().equals(Q_H[k].getName().get_name())){
						 temp=Subtraction(temp, Q_H[i]);
					 }
					 else{
						 for(int n=0; n<Q_H[i].getNum_evi(); n++){
							 if(Q_H[i].getEvidences()[n].get_name().equals(Q_H[k].getName().get_name())){
								 DiscreteVariable[] dv = new DiscreteVariable[Q_H[i].getNum_evi()];
								 DiscreteVariable[] new_dv = new DiscreteVariable[Q_H[i].getNum_evi()-1];
								 dv = Q_H[i].getEvidences();
								 new_dv = Subtraction(dv, dv[n]);
								 temp[i].setEvidences(new_dv);
								 exp = Q_H[i].getName().number_values();
								 int total = Q_H[i].getNum_value();
								 if(n==Q_H[i].getNum_evi()-1){
									 dis=1;
								 }
								 else{
									 for(int q=n; q<Q_H[i].getNum_evi(); q++){
										 dis = Q_H[i].getEvidences()[q+1].number_values()*dis;
									 }
								 }
								 int count=0;
								 double[] new_v = new double[total/exp];
								 double s;
								 int a,b;
								 while(count<total/exp){
									 s=0;
									 b=exp*count;
									 for (int c=0; c<exp; c++){
										 a=b+c*dis;
										 s= s+Q_H[i].getValue()[a];
										 
									 }
									 new_v[count]=s;
									 count ++ ;
									 
								 }
								 temp[i].setValue(new_v);
								 temp[i].setNum_value(new_v.length);
								 
							 }
						 }
					 }
			
				  }
			  }
			  return temp;
		  }
		  public Q Summation(Q Q, ProbabilityVariable[] U){
			  AlgorithmComputing AC = new AlgorithmComputing(this.qbn);
			  Q Q_R = new Q();
			  //Vector Q = new Vector();
			  if(Q==null)
				  return null;
			  if (Q.getProbability() == null )
				  return null;
			  if (U==null)
				  return Q;
			  
			  Probability[] upper = new Probability[Q.number_Probability()]; 
			  upper = Q.getProbability();
			  upper = Summation(upper, U);
				boolean del=true;
				for(int i=0; i<U.length; i++){
					del = true;
					for (int j=0; j<upper.length; j++){
						
							if (upper[j].getName().get_name().equals(U[i].get_name())){
								del = false;
								break;
							}
						
					}
					if (del){
						//System.out.println("del="+U[i].get_name());
						U = Subtraction(U, U[i]);
						U= Add(U,Q.getSummation());

					}
				}
			  Q_R = new Q(U, Summation(upper, U), Q.isUp());
				 
				 // AC.Print_Probability_array(Summation(upper, U));
			  

			  return Q_R;
		  }
		  
		  public Probability[] Summation(Probability[] Q_H, ProbabilityVariable[] U){
			  //Vector Q = new Vector();
			  if(Q_H==null)
				  return null;
			  if (U==null)
				  return Q_H;
			  Probability[] temp = new Probability[Q_H.length];
			  ProbabilityVariable[] U_temp = new ProbabilityVariable[U.length];
			  temp=Q_H;
			  U_temp = U;
			
			  int count_sub = -1;
			 // System.out.println("U.length="+U.length);
			
			  for(int k=0; k<U.length; k++){
				  boolean p=false;
				  boolean c=false;
				  count_sub = -1;
				  for (int i=0; i<Q_H.length; i++){
					
					 if (Q_H[i].getName().get_name().equals(U[k].get_name())){
						 count_sub = i;
						 p = true;					
						 //System.out.println("sub[i]="+Q_H[i].getName().get_name()+"  i="+i); 
					 }
					
					 for(int n=0; n<Q_H[i].getNum_evi(); n++){
						 if(Q_H[i].getEvidences()[n].get_name().equals(U[k].get_name())){
							c=true;
							break;
						 }
					 }				
				  }
				  if(p && !c){
						 temp = Subtraction(temp, Q_H[count_sub]);
						 U_temp = Subtraction(U_temp, U[k]);
					 }
			  }
			 /* U= new ProbabilityVariable[U_temp.length];
			  for (int i=0; i<U.length; i++){
				
					  U[i] = U_temp[i];
				
			  }*/
			  return temp;
		  }
		  /*
		   * Subtraction
		   */
		  public Probability[] Subtraction (Probability[] A, Probability B){
			  if (B==null)
				  return A;
			 if (B.getName()==null)
				 return A;
			 if (B.getName().get_name()==null || B.getName().get_name()=="")
				 return A;
			  Probability[] C = new Probability[A.length-1];
				int n = 0;
				for(int i=0; i<A.length; i++){
					if (!A[i].getName().get_name().equals(B.getName().get_name())){
						
						C[n] = A[i];
						n++;
					}
					
				}
				
				return C;
			}
		  
		  public DiscreteVariable[] Subtraction (DiscreteVariable[] A, DiscreteVariable B){
			  DiscreteVariable[] C = new DiscreteVariable[A.length-1];
				int n = 0;
				for(int i=0; i<A.length; i++){
					if (!A[i].get_name().equals(B.get_name())){
						C[n] = A[i];
						n++;
					}
					
				}
				
				return C;
			}
		  public ProbabilityVariable[] Subtraction (ProbabilityVariable[] A, ProbabilityVariable B){
			  ProbabilityVariable[] C = new ProbabilityVariable[A.length-1];
				int n = 0;
				System.out.println(A.length);
				for(int i=0; i<A.length; i++){
					if (A[i]!=null){
					if (!A[i].get_name().equals(B.get_name())){
						C[n] = A[i];
						n++;
					}
					}
					
				}
				
				return C;
			}
		  public void set_all_to_unobserved(){
			 
				  stc.set_all_to_unobserved();
				  
			
		  }
			public Probability[] QofN(ProbabilityVariable[] pvs, QuasiBayesNet qbn) {
				// TODO Auto-generated method stub
				ProbabilityFunction[] pfs = new ProbabilityFunction[pvs.length];
				pfs = transfer_pvs_to_pfs(pvs, qbn);
				Probability[] R = new Probability[pvs.length];
				
				for (int i=0; i<pvs.length; i++){
					Probability pr = new Probability(pfs[i]);
					R[i]=pr;
				}
				return R;
			}
			public Probability[] Q_of_N(ProbabilityVariable[] pvs, ProbabilityVariable[] top_order, QuasiBayesNet qb) {
				// TODO Auto-generated method stub
				Probability[] R = new Probability[pvs.length];
				int cont_e = 0;
				for(int i=0; i<pvs.length; i++){
					cont_e = 0;
					for(int j=0; j<top_order.length; j++){
						if(top_order[j].get_name().equals(pvs[i].get_name())){
							cont_e = j;
							break;
						}			
					}
					ProbabilityVariable[] evi = new ProbabilityVariable[cont_e];
					evi = array_0_to_j(top_order, cont_e-1);
					R[i] = new Probability(pvs[i], evi);
				}
				return R;
			}
			private ProbabilityVariable[] array_0_to_j(ProbabilityVariable[] A, int j) {
				// TODO Auto-generated method stub
				if (A==null)
					return null;
				ProbabilityVariable[] R = new ProbabilityVariable[j+1];
				for(int i=0; i<=j; i++){
					R[i] = A[i];
				}
				return R;
			}
			public ProbabilityVariable form_Probability_to_ProbabilityVariable(Probability Pr){
				for(int i=0; i<stc.number_variables(); i++){
					ProbabilityVariable p = new ProbabilityVariable();
					p = stc.get_probability_variables()[i];
					if (Pr.getName().get_name().equals(p.get_name())){
						return p;
					}
				}
				return null;
			}
			public Probability[] PrAll() {
				// TODO Auto-generated method stub
				int[] observed_idx = new int[stc.number_observed()];
				
				int size = stc.number_variables();
				ProbabilityVariable[] All= new ProbabilityVariable[size];
				All = stc.get_probability_variables();
				observed_idx = stc.get_observed_idx(All);
				ProbabilityVariable[] All_top_order= new ProbabilityVariable[size];
				All_top_order = TopologicalOrder(All);
				Probability[] Pr_All = new Probability[size];
				Pr_All = Joint_Probability(All_top_order);
				stc.set_observed_by_idx(observed_idx);
				return Pr_All;
			}
			public Vector  Ancestors(ProbabilityVariable[] C){
				Vector An = new Vector();
				if (C==null)
					return null;
				//ProbabilityVariable[] An = null;
				AlgorithmComputing AC = new AlgorithmComputing(qbn);
				int size = stc.number_variables();
				Enumeration e, tem;
				ProbabilityVariable pv, pv1, pv2, pv3;
				ProbabilityVariable[] pvG = new ProbabilityVariable[size];
				pvG=stc.get_probability_variables();
			/*	for(int i=0; i<size; i++){
					System.out.println(qbn.get_name()+": "+pvG[i].get_name());
				}
				for(int i=0; i<C.length; i++){
					System.out.println("C[i]: "+C[i].get_name());
				}
				*/
				Vector temp =new Vector();
				Vector VC =new Vector();
				VC = AC.from_ProbabilityVariable_to_Vector(C);
//				unObserved = st.get_unobserved_variables();
				DSeparation dsep =new DSeparation((BayesNet)qbn);
				int c_length = C.length;
//				int count =0;
				//System.out.println("G size: "+size);
				int stop =0;
				int cont=0;
				boolean root = false;
				for (int i=0; i<size; i++ ) {
		            pv = pvG[i];
		           // System.out.println("pvG[i]: "+pvG[i].get_name());
					boolean is =false;
					int j=0;
					
					//System.out.println("???????===");
					while (!is && j<c_length){	
						boolean add = true;
//						if (i<size-1 || j<c_length-1){
							if (dsep.adj_st(pv.get_index(), C[j].get_index())){
								//System.out.println("pv: "+pv.get_name()+" adj C: "+ C[j].get_name());
								stop ++ ;
								is = true;
								temp.addElement(pv);						
								An.addElement(pv);
								cont++;
								if(Root(pv))	
									root=true;
								//break;											
							}
							
							else if (pv.get_name().equals(C[j].get_name())){
								//System.out.println("pv: "+pv.get_name()+" == C: "+ C[j].get_name());
								
								is = true;
								for (tem = temp.elements(); tem.hasMoreElements(); ) {
						            pv1 = (ProbabilityVariable)(tem.nextElement());
						           if (pv1.get_name().equals(pv.get_name())){
						        	   add =false;
						           }
								}
								if (add){
									temp.addElement(pv);						
									An.addElement(pv);
									cont++;
								}
								if(Root(pv))	
									root=true;
								//break;											
							}
							j++;
						}
					
					}

					int temp_size = temp.size();
					int num = 0;
//					System.out.println("temp_size = "+temp_size);
					ProbabilityVariable[] Temp = new ProbabilityVariable[temp_size];
					for (e = temp.elements(); e.hasMoreElements(); ) {
			            pv = (ProbabilityVariable)(e.nextElement());
			           // System.out.println(pv.get_name()+"^^");
						Temp[num]=pv;
						num++;
					}
					
		/*			for(int i=0; i<Temp.length; i++){
						System.out.println("*temp["+i+"]**");
						System.out.println(Temp[i].get_name()+"**");
					}
		*/			
					//System.out.println("stop = "+stop);
					
					if(stop>0 && stop<size && !root){
						//int exit1 = An.size();
						An = Ancestors(Temp);
						//int exit2 = An.size();
					}
					else{
						//st.clean_mark();
						//System.out.println("DONE!!!!");
						return An;
					}
				return An;
			}
			/*
			 * check pv is a root or not
			 */
			public boolean Root(ProbabilityVariable pv) {
				// TODO Auto-generated method stub
				DSeparation dsep =new DSeparation((BayesNet)qbn);
				for(int i=0; i< stc.number_variables(); i++){
					if(dsep.adj(i, pv.get_index(), 0)){
						return false;
					}
				}
				return true;
			}
			public QFraction Summation(QFraction qq, ProbabilityVariable[] t_c) {
				// TODO Auto-generated method stub
				if(t_c==null)
					return qq;
				else if(t_c.length==0){
					return qq;
				}
				else if (qq==null){
					return null;
				}
				else{
					System.out.println("sum.length="+t_c.length);
					System.out.println("qqsum.length="+qq.num_sum());
					QFraction Q_R;// = new QFraction();
					Q[] up = new Q[qq.num_up()];
					up	= qq.getUp();
					Q[] down = new Q[qq.num_down()];
					down	=qq.getDown();
					Q[] new_up = new Q[qq.num_up()];
					Q[] new_down = new Q[qq.num_down()];
					ProbabilityVariable[] QFsum = new ProbabilityVariable[qq.num_QFsum()];
					QFsum =	qq.getQF_Sum();
					
					if (QFsum==null){
						ProbabilityVariable[] new_sum = t_c;
						if(down == null){
							if(up.length==1){
								new_up[0] = Summation(up[0],t_c);
								//+++++++++++++++++++++++++++++++++++++++++++
								ProbabilityVariable[] sum = new ProbabilityVariable[new_up[0].number_Summation()];
								sum = new_up[0].getSummation();
								new_up[0].setSummation(null);
								new_up[0] =Summation(new_up[0],sum);
								//+++++++++++++++++++++++++++++++++++++++++++
								Q_R = new QFraction(new_up, down);
								return Q_R;
							}
							else{
								new_up = Summation(up, t_c);
							
							boolean del=true;
							for(int i=0; i<t_c.length; i++){
								del = true;
								for (int j=0; j<new_up.length; j++){
									for (int k=0; k<new_up[j].number_Probability(); k++){
										if (new_up[j].getProbability()[k].getName().get_name().equals(t_c[i].get_name())){
											del = false;
											break;
										}
									}
								}
								if (del){
									new_sum = Subtraction(new_sum, t_c[i]);
									System.out.println("del="+t_c[i].get_name());
								}
							}
							new_sum = Add(new_sum, qq.getSum());
							Q_R = new QFraction(new_up, down, new_sum);
							return Q_R;
							}
						}
						else{
							QFraction temQF = new QFraction();
							temQF = qq.getU();
							Q_R =new QFraction(temQF, qq.getD(),t_c);
							return Q_R;
						}
					}
				
					else{
						ProbabilityVariable[] new_sum = t_c;
						if(down == null){
							if(up.length==1){
								new_up[0] = Summation(up[0],t_c);
								Q_R = new QFraction(new_up, down);
								return Q_R;
							}
							else{
								new_up = Summation(up, t_c);
							boolean del=true;
							for(int i=0; i<t_c.length; i++){
								del = true;
								for (int j=0; j<new_up.length; j++){
									for (int k=0; k<new_up[j].number_Probability(); k++){
										if (new_up[j].getProbability()[k].getName().get_name().equals(t_c[i].get_name())){
											del = false;
											break;
										}
									}
								}
								if (del){
									new_sum = Subtraction(new_sum, t_c[i]);
									System.out.println("del="+t_c[i].get_name());
								}
							}
							new_sum = Add(new_sum, qq.getSum());
							for (int i=0;i<new_sum.length; i++){
								System.out.println("new_sum="+new_sum[i].get_name());
							}
							Q_R = new QFraction(new_up, down, new_sum);
							Q_R = new QFraction(Q_R);
							Q_R.setSum(new_sum);
							System.out.println(",,,"+Q_R.getSum()[0].get_name());
							return Q_R;
							//Q_R = new QFraction(Q_R, null, new_sum);
							//System.out.println("Q_R:");
							//AlgorithmComputing AC = new AlgorithmComputing(qbn);
							//AC.QFprint(Q_R);
							}
						}					
						else{
							//Q_R =new QFraction(up,down, new_sum);
						
							new_sum = new ProbabilityVariable[t_c.length+QFsum.length];
							new_sum = Add(t_c, qq.getSum());
							
							//Q_R = new QFraction(up, down,null);
							Q_R = new QFraction(qq.getU(), qq.getD(), new_sum);
							return Q_R;
						}
					}
					//return Q_R;
				}
				
			}
			private Q[] Summation(Q[] Q, ProbabilityVariable[] sum, LinkedList returnSum) {
				// TODO Auto-generated method stub
				AlgorithmComputing AC = new AlgorithmComputing(this.qbn);
				  //Vector Q = new Vector();
				  if(Q==null)
					  return null;
				  else if(sum==null){
					  return Q;
				  }
				  else{
					  for(int i=0; i<sum.length; i++){
						  System.out.println("sum="+sum[i].get_name());
					  }
					  Q[] Q_R = new Q[Q.length];
					  Q_R = Q;
					  int cont_i = 0;
					  int cont_j = 0;
					  int[] save_i= new int[Q.length];
					  int[] save_j= new int[Q.length];
					  for (int i=0; i<Q.length; i++){
						  save_i[i] = -1;
						  save_j[i] = -1;
					  }
					  for (int k =0; k<sum.length; k++){
						  ProbabilityVariable pv = new ProbabilityVariable(sum[k]);
						  boolean p=false;
						  boolean c=false;
						  for (int i=0; i<Q.length; i++){
							  save_i[i] = -1;
							  save_j[i] = -1;
						  }
						  cont_i = 0;
						  cont_j = 0;
						  for (int i=0; i<Q.length; i++){
							  Q Q_i = new Q(Q[i]);
							  Probability[] ps = new Probability[Q[i].getProbability().length];
							  ps = Q[i].getProbability();
							  for (int j=0; j<ps.length; j++){
								  if (ps[j].getName().get_name().equals(pv.get_name())){
									  p = true;
									  save_i[cont_i] = i;
									  save_j[cont_j] = j;
									  cont_i++;
									  cont_j++;
								  }
								  for(int n=0; n<ps[j].getNum_evi(); n++){ 
									  if(ps[j].getEvidences()[n].get_name().equals(pv.get_name())){
										  c=true;
										  break;
									  }
								  }
							  }
						  }
						  if(p && !c){
							  for (int m =0; m<cont_i; m++){
								  Q temQ = new Q();
								  temQ =Q_R[save_i[m]];
								  //Q_R[save_i[m]].Probability = Subtraction(Q_R[save_i[m]].getProbability(),Q_R[save_i[m]].getProbability()[save_j[m]]);
								  temQ.setProbability(Subtraction(Q_R[save_i[m]].getProbability(),Q_R[save_i[m]].getProbability()[save_j[m]]));
								  Q_R[save_i[m]] = temQ;
								  sum = Subtraction(sum, pv);
							  }
						  }
					  }
				  return Q_R;
				  }
			}
			public ProbabilityVariable[] Add(ProbabilityVariable[] A, ProbabilityVariable[] B){
				if (A==null)
					return B;
				if (B==null){
					return A;
				}
				ProbabilityVariable[] C = new ProbabilityVariable[B.length+A.length];
				//System.out.println("A,length="+A[0].get_name());
				//System.out.println("B,length="+B[0].get_name());
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
			public QFraction Simply(QFraction Q){
				Probability p1, p2;
				QFraction R = new QFraction();
				R=Q;
				if (Q.getD().getQF_Sum()==null || Q.getD().num_QFsum()==0){
					if (Q.getD().getD()==null && Q.num_up()==1){
						if (Q.getD().getUp()[0].getSummation()==null || Q.getD().getUp()[0].number_Summation()==0){
							for (int i=0; i<Q.getD().getUp()[0].number_Probability(); i++){
								p1 = Q.getD().getUp()[0].getProbability()[i];
								if (p1.getNum_evi()==0){
									for (int j=0; j<Q.num_up(); j++){
										for (int m=0;m<Q.getUp()[j].number_Probability(); m++){
											p2=Q.getUp()[j].getProbability()[m];
											if (p2.getNum_evi()==0 && p2.getName().get_name().equals(p1.getName().get_name())){
												Probability[] up = new Probability[Q.getUp()[j].number_Probability()-1];
												Probability[] d = new Probability[Q.getD().getUp()[0].number_Probability()-1];
												up =Q.getUp()[j].getProbability();
												up = Subtraction(up,p2);
												d = Q.getD().getUp()[0].getProbability();
												d = Subtraction(d,p1);
												R.getUp()[j].setProbability(up);
												R.getD().getUp()[0].setProbability(d);
											}
										}
									}
								}
							}
						}
					}
				}
				return R;
			}
			
			public ProbabilityVariable[] Parents(Vector<ProbabilityVariable> V, PrintStream out, int[] len){
			/* len is the returnned array size*/
				methods m = new methods();				
				ProbabilityVariable[] PV = new ProbabilityVariable[V.size()];
				PV = m.from_Vector_to_ProbabilityVariable(V);
				ProbabilityFunction[] PF = new ProbabilityFunction[V.size()];
				PF = transfer_pvs_to_pfs(PV, qbn);
				
				LinkedList<DiscreteVariable> Pa = new LinkedList<DiscreteVariable>();
				for (int i=0; i< V.size(); i++){
					int size = PF[i].number_variables();
					DiscreteVariable[] temp = new DiscreteVariable[size];
					temp = PF[i].get_variables();
					DiscreteVariable p = new DiscreteVariable();
					for(int j=0;j<size;j++){
						Iterator<DiscreteVariable> t = Pa.iterator();
						boolean b=false;
						while (t.hasNext()) {
							p = t.next();
							if (temp[j].get_name().equals(p.get_name())){
								b=true;
								break;
							}
						}
						if(!b){
							Pa.add(temp[j]);
						}
					
					}
				}
				DiscreteVariable[] pa = new DiscreteVariable[Pa.size()];
				int idx = 0;
				//pa = (DiscreteVariable[]) Pa.toArray();
				Iterator<DiscreteVariable> i = Pa.iterator();
			      	while (i.hasNext()) {
			      		pa[idx] = i.next();
			            System.out.println(pa[idx].get_name());
			            idx++;
			        }

				ProbabilityVariable[] R = new ProbabilityVariable[pa.length];
				//convert DV[] to PV[]
				
				R = convertDVtoPV(pa);
				//Method M = new Method();
				
				//M.print_ProbabilityVariableArray(out, m.getObserved(R));
				len[0] = 0;
				len[0] = len[0]+m.getObserved(R).length;
				
				return m.getObserved(R);
				
			}
			
			public ProbabilityVariable[] convertDVtoPV(DiscreteVariable[] DV){
				int DVsize = DV.length;
				ProbabilityVariable[] R = new ProbabilityVariable[DVsize];
				int length = stc.number_variables();
				ProbabilityVariable[] V = new ProbabilityVariable[length];
				V = stc.get_probability_variables();
				ProbabilityVariable pv = new ProbabilityVariable();
				DiscreteVariable dv = new DiscreteVariable();
				System.out.println("___________________");
				System.out.println(DVsize);
				System.out.println(length);
				System.out.println("___________________");
				for (int i=0;i<DVsize; i++){
					dv = DV[i];
					for(int j=0; j<length; j++){						
						pv = V[j];
						if (pv.get_name().equals(dv.get_name())){
							R[i] =pv;
							break;
						}							
					}
				}
				return R;
			}
			
			public Q[] Summation(Q[] Q, ProbabilityVariable[] sum) {
				// TODO Auto-generated method stub
				AlgorithmComputing AC = new AlgorithmComputing(this.qbn);
				  //Vector Q = new Vector();
				  if(Q==null)
					  return null;
				  else if(sum==null){
					  return Q;
				  }
				  else{
					  for(int i=0; i<sum.length; i++){
						  System.out.println("sum="+sum[i].get_name());
					  }
					  Q[] Q_R = new Q[Q.length];
					  Q_R = Q;
					  int cont_i = 0;
					  int cont_j = 0;
					  int[] save_i= new int[Q.length];
					  int[] save_j= new int[Q.length];
					  for (int i=0; i<Q.length; i++){
						  save_i[i] = -1;
						  save_j[i] = -1;
					  }
					  for (int k =0; k<sum.length; k++){
						  ProbabilityVariable pv = new ProbabilityVariable(sum[k]);
						  boolean p=false;
						  boolean c=false;
						  for (int i=0; i<Q.length; i++){
							  save_i[i] = -1;
							  save_j[i] = -1;
						  }
						  cont_i = 0;
						  cont_j = 0;
						  for (int i=0; i<Q.length; i++){
							  Q Q_i = new Q(Q[i]);
							  Probability[] ps = new Probability[Q[i].getProbability().length];
							  ps = Q[i].getProbability();
							  for (int j=0; j<ps.length; j++){
								  if (ps[j].getName().get_name().equals(pv.get_name())){
									  p = true;
									  save_i[cont_i] = i;
									  save_j[cont_j] = j;
									  cont_i++;
									  cont_j++;
								  }
								  for(int n=0; n<ps[j].getNum_evi(); n++){ 
									  if(ps[j].getEvidences()[n].get_name().equals(pv.get_name())){
										  c=true;
										  break;
									  }
								  }
							  }
						  }
						  if(p && !c){
							  for (int m =0; m<cont_i; m++){
								  Q_R[save_i[m]].setProbability(Subtraction(Q_R[save_i[m]].getProbability(),Q_R[save_i[m]].getProbability()[save_j[m]]));
								  sum = Subtraction(sum, pv);
							  }
						  }
					  }
				  return Q_R;
				  }
			}
}
