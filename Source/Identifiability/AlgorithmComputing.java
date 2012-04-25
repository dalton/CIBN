/**
 * AlgorithmComputing.java Author: Lexin Liu
 */
package Identifiability;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;

import r305.Verma_Type_Functional_Constraint;
import uai04.Algorithm1;
import uai04.Method;
import uai04.STCInference;

import BayesianNetworks.BayesNet;
import BayesianNetworks.DiscreteVariable;
import BayesianNetworks.ProbabilityFunction;
import BayesianNetworks.ProbabilityVariable;
import QuasiBayesianNetworks.QuasiBayesNet;
import BayesianInferences.*;
/*
 * For paper "Identifiability in Causal Bayesian Networks: A sound and Complete
 * Algorithm Yimin Huan9Sg and Marco Valta Compute Pt(S)
 */

public class AlgorithmComputing {

    QuasiBayesNet qbn;
    STBayesNet st;
    String pts;
    Vector D;
    ProbabilityVariable[] N;
    ProbabilityVariable[] T;
    ProbabilityVariable[] S;
    Vector<ProbabilityVariable> N_T;
    methods m;

    public AlgorithmComputing() {
	this(new QuasiBayesNet("InternalNetwork", 0, 0));
    }

    public AlgorithmComputing(QuasiBayesNet q) {
	this(new STBayesNet(q));
	qbn = q;
    }

    public AlgorithmComputing(STBayesNet q) {
	qbn = q;
	st = q;


	D = new Vector();
	

	int N_size = q.number_observed();
	int T_size = q.number_t();
	int S_size = q.number_s();
	ProbabilityVariable pv;
	N = new ProbabilityVariable[N_size];
	T = new ProbabilityVariable[T_size];
	S = new ProbabilityVariable[S_size];
	N = q.get_observed_variables();
	T = q.get_probability_variables_t();
	S = q.get_probability_variables_s();
	N_T = new Vector<ProbabilityVariable>();

	m = new methods();
	N_T = m.Subtraction(N, T);


	
    }

    /**
     * algorithm_computing() The S and T set should be set before use the
     * function. Both S and T are observable variables set. the function will
     * show whether a causal effect, that is, the joint response of any sset S
     * of variables to interventions on a set T of action variables, denoted
     * Pt(S) is identifiable or not.
     */
    public void algorithm_computing(PrintStream out) {
	pts = "==";
	STInference sti = new STInference(qbn, true);
	Probability[] P_T_S = null;
	Verma_Type_Functional_Constraint VTFC = new Verma_Type_Functional_Constraint();
	Method M = new Method();
	methods m = new methods();
	Enumeration e;
	int N_size = st.number_observed();
	int T_size = st.number_t();
	int S_size = st.number_s();
	ProbabilityVariable pv;
	ProbabilityVariable[] N = new ProbabilityVariable[N_size];
	ProbabilityVariable[] T = new ProbabilityVariable[T_size];
	ProbabilityVariable[] S = new ProbabilityVariable[S_size];
	N = st.get_observed_variables();
	T = st.get_probability_variables_t();
	S = st.get_probability_variables_s();
	Vector<ProbabilityVariable> N_T = new Vector<ProbabilityVariable>();

	N_T = m.Subtraction(N, T);

	Vector D = new Vector();

	ProbabilityVariable[] N_t = from_Vector_to_ProbabilityVariable(N_T);

	Vector DUP = new Vector();
	DUP = VTFC.DUP(qbn, N_t, out);
	ProbabilityVariable[] dup = new ProbabilityVariable[DUP.size()];
	dup = m.from_Vector_to_ProbabilityVariable(DUP);
	ProbabilityVariable[] union = new ProbabilityVariable[DUP.size() + N_t.length];
	union = m.Add(dup, N_t);
	BayesNet Gv_t = new BayesNet(VTFC.SubBayesNet(qbn, union, "Gv_t"));
	QuasiBayesNet q_Gv_t = new QuasiBayesNet(Gv_t);

	D = m.Intersection(VTFC.ancestors(q_Gv_t, S), N);

	//////////////////////////////////////////////////

	QFraction Q_D[] = new QFraction[D.size()];

	Q_D = Q(D, out);
	if (Q(D, out) == null) {
	    out.print("Pt(s) is UNIDENTIFIABLE!");
	    return;
	} else {

	    out.print("Pt(s) = ");
	    ProbabilityVariable[] d = new ProbabilityVariable[D.size()];
	    d = from_Vector_to_ProbabilityVariable(D);
	    Vector<ProbabilityVariable> Ds = new Vector<ProbabilityVariable>();
	    ProbabilityVariable[] D_S = new ProbabilityVariable[D.size() - S_size];
	    Ds = Subtraction(d, S);
	    D_S = from_Vector_to_ProbabilityVariable(Ds);
	    for (int i = 0; i < D_S.length; i++) {
		System.out.println("D_S: " + D_S[i].get_name());
	    }
	    if (D_S.length == 0) {
		for (int i = 0; i < Q_D.length; i++) {
		    out.print("{");
		    System.out.print("{");
		    out.print(QFprint(Q_D[i], out));
		    out.print("}");
		    System.out.print("}");
		}
	    } else {
		String R = "sum_(";
		for (int i = 0; i < D_S.length; i++) {
		    if (i == D_S.length - 1) {
			R = R + D_S[i].get_name() + ")";
		    } else {
			R = R + D_S[i].get_name() + " ";
		    }
		}
		System.out.print(R);
		out.print(R);
		for (int i = 0; i < Q_D.length; i++) {
		    if (Q_D[i] != null) {
			out.print("{");
			System.out.print("{");
			out.print(QFprint(Q_D[i], out));
			out.print("}");
			System.out.print("}");
		    }
		}
	    }

	}
	return;

    }

    public QFraction[] Q(Vector d, PrintStream out) {
	// TODO Auto-generated method stub
	Enumeration e;
	Enumeration g;
	Method M = new Method();
	methods m = new methods();
	Verma_Type_Functional_Constraint VTFC = new Verma_Type_Functional_Constraint();
	STCInference stcI = new STCInference(qbn, true);
	Algorithm1 uai04 = new Algorithm1(qbn);
	ProbabilityVariable pv, pv1;
	STInference sti = new STInference(qbn, true);
	Probability[] Q_s = null;

	ProbabilityVariable[] D = from_Vector_to_ProbabilityVariable(d);
	int size = st.number_observed();
	int U_size = st.number_unobserved();
	int all_size = size + U_size;
	//System.out.println("U_size= "+U_size);

	ProbabilityVariable[] observed = new ProbabilityVariable[size];
	ProbabilityVariable[] U = new ProbabilityVariable[U_size];
	ProbabilityVariable[] All = new ProbabilityVariable[all_size];
	ProbabilityVariable[] S_set = new ProbabilityVariable[st.number_s()];
	S_set = st.get_probability_variables_s();
	observed = st.get_observed_variables();
	//System.out.println("st.get_unobserved_variables()= "+st.get_unobserved_variables().length);
	U = st.get_unobserved_variables();
	All = st.get_probability_variables();
	int[] observed_idx = new int[st.number_observed()];
	observed_idx = st.get_observed_idx(All);
	/*
	 * for(int i=0; i<observed_idx.length;i++){
	 * System.out.println("observed_idx: "+observed_idx[i]); }
	 */
	Vector[] N = new Vector[size];// decompose to c-component
	Vector[] V = new Vector[all_size];
	//Vector[] DD = new Vector[D.length];
	N = partition_c_component(observed);
	V = partition_c_component(All);
	//DD = partition_c_component(D);
	ProbabilityVariable[] N_top_order = new ProbabilityVariable[size];
	N_top_order = sti.TopologicalOrder(observed);
	//////////////////////////////
	//Print C-component of G.////
	/////////////////////////////
	Q[] Qcom = new Q[N.length];
	System.out.println("Print C-component of G.");
	out.println("Print C-component of G.");
	Qcom = uai04.print_C_Components(out, N, stcI);
	out.println();
	int idx_QF = 0;
	QFraction[] QF = new QFraction[Qcom.length];
	for (int i = 0; i < Qcom.length; i++) {
	    Q[] Q_UP = new Q[1];
	    Q_UP[0] = Qcom[i];
	    QFraction QFi = new QFraction(Q_UP);
	    QF[idx_QF] = QFi;
	    idx_QF++;
	}

	///////////////////////////////
	//Create QuasiBayesNet Gs./////
	///////////////////////////////		
	Vector DUP = new Vector();
	DUP = VTFC.DUP(qbn, D, out);
	ProbabilityVariable[] dup = new ProbabilityVariable[DUP.size()];
	dup = m.from_Vector_to_ProbabilityVariable(DUP);
	ProbabilityVariable[] union = new ProbabilityVariable[DUP.size() + D.length];
	union = m.Add(dup, D);
	BayesNet Gs = new BayesNet(VTFC.SubBayesNet(qbn, union, "Gs"));
	QuasiBayesNet q_Gs = new QuasiBayesNet(Gs);

	AlgorithmComputing AC = new AlgorithmComputing(q_Gs);
	Vector[] com_d = AC.partition_c_component(D);

	QFraction[] Qs = new QFraction[com_d.length];
	ProbabilityVariable pv_D = new ProbabilityVariable();
	ProbabilityVariable pv_G = new ProbabilityVariable();
	Enumeration<ProbabilityVariable> e1, e2;
	for (int i = 0; i < com_d.length; i++) {
	    ProbabilityVariable[] Di = new ProbabilityVariable[com_d[i].size()];
	    Di = m.from_Vector_to_ProbabilityVariable(com_d[i]);
	    boolean stop = false;
	    for (g = com_d[i].elements(); g.hasMoreElements();) {
		stop = false;
		pv_D = (ProbabilityVariable) (g.nextElement());

		for (int j = 0; j < N.length; j++) {
		    ProbabilityVariable[] Si = new ProbabilityVariable[N[j].size()];
		    Si = m.from_Vector_to_ProbabilityVariable(N[j]);
		    if (VTFC.In(pv_D, Si)) {
			ProbabilityVariable[] S_i = new ProbabilityVariable[Si.length];
			int idx_Si = 0;
			for (int k = 0; k < Si.length; k++) {
			    if (Si[k].is_observed()) {
				S_i[idx_Si] = Si[k];
				idx_Si++;
			    }
			}
			ProbabilityVariable[] SI = new ProbabilityVariable[idx_Si];
			for (int k = 0; k < idx_Si; k++) {
			    SI[k] = S_i[k];
			}
			Qs[i] = AlgorithmIdentify(Di, SI, QF[j], out);
			if (Qs[i] == null) {
			    return null;
			}
		    }
		}
	    }
	}

	return Qs;
    }

    public ProbabilityVariable[] getObserved(ProbabilityVariable[] A) {
	int k = get_num_Observed(A);
	int c = 0;
	ProbabilityVariable[] R = new ProbabilityVariable[k];
	for (int i = 0; i < A.length; i++) {
	    if (A[i].is_observed()) {
		R[c] = A[i];
		c++;
	    }
	}
	return R;
    }

    public int get_num_Observed(ProbabilityVariable[] A) {
	int k = 0;
	for (int i = 0; i < A.length; i++) {
	    if (A[i].is_observed()) {
		k++;
	    }
	}
	return k;
    }

    private Probability[] Product(Probability[] qs, Probability[] q_s) {
	if (qs == null) {
	    return q_s;
	}
	Probability[] Pr = new Probability[qs.length + q_s.length];
	for (int i = 0; i < qs.length + q_s.length; i++) {
	    if (i < qs.length) {
		Pr[i] = qs[i];
	    } else {
		Pr[i] = q_s[i - qs.length];
	    }
	}
	return Pr;
    }

    private void Print_Probability_array(Probability[] pr_N, PrintStream out) {
	if (pr_N == null) {
	    out.print("Fail \n");
	    out.print("Not Identifiable!!\n");
	    return;
	}
	String Pr_S = "";
	for (int i = 0; i < pr_N.length; i++) {

	    Pr_S = Pr_S + pr_N[i].print();
	    for (int j = 0; j < pr_N[i].getNum_value(); j++) {
		if (j == pr_N[i].getNum_value() - 1) {
		    out.print("\n");
		}
	    }
	}
	out.print("\n" + Pr_S + "\n");
    }

    public String Print_Probability_array(Probability[] pr_N) {
	if (pr_N == null) {
	    System.out.println("Not Identifiable!!\n");
	    return "NOT Identifiable";
	}
	String Pr_S = "";
	for (int i = 0; i < pr_N.length; i++) {
	    Pr_S = Pr_S + pr_N[i].print();
	}
	return Pr_S;
    }
    /*
     * S will be patitioned into S1 ..... Sk in st each of them belongs to a
     * c-componet Vector's size should be count - 1.
     */

    public Vector[] partition_c_component(ProbabilityVariable[] S) {
	// TODO Auto-generated method stub		
	int length = S.length;
	Enumeration e;
	Vector[] N = new Vector[length];

	ProbabilityVariable[] temp1 = new ProbabilityVariable[length];
	ProbabilityVariable[] temp2 = new ProbabilityVariable[length];
	temp1 = S;
	temp2 = S;
	int count = 0;
	while (temp1.length > 0) {
	    length = temp1.length;
	    ProbabilityVariable pv;
	    Vector<ProbabilityVariable> pN = new Vector<ProbabilityVariable>();
	    pv = temp1[0];
	    Vector<ProbabilityVariable> pn = new Vector<ProbabilityVariable>();
	    pn.addElement(pv);
	    pN.addElement(pv);
	    temp2 = Subtraction(temp1, pv);//
	    for (int i = 1; i < length; i++) {
		if (in_one_c_component(pv, temp1[i], pn) || is_c_component_relation(pv, temp1[i], pn)) {
		    pN.addElement(temp1[i]);

		    temp2 = Subtraction(temp2, temp1[i]);//
		}
	    }
	    N[count] = pN;
	    count++;
	    temp1 = temp2;
	}
	Vector[] rN = new Vector[count];
	for (int i = 0; i < count; i++) {
	    rN[i] = N[i];
	}
	return rN;

    }

    /*
     * check two Vertex wether is on c-component relation, A and B are in the
     * same graph return ture: 1. Both A and B are unobserable, and they have
     * c-component relation. 2. Only one of them is observed node, and the
     * unobserved node is the the other one's parent. return false: Otherwise
     */
    public boolean is_c_component_relation(ProbabilityVariable A, ProbabilityVariable B, Vector pn) {
	if (A.get_name().equals(B.get_name())) {
	    return true;
	}
	Enumeration e;
	boolean is = false;
	DSeparation dsep = new DSeparation((BayesNet) qbn);
	int size = st.number_observed();
	ProbabilityVariable[] observed = new ProbabilityVariable[size];
	observed = st.get_observed_variables();
	int un_size = st.number_unobserved();
	ProbabilityVariable[] unobserved = new ProbabilityVariable[un_size];
	unobserved = st.get_unobserved_variables();
	int all_size = st.number_variables();
	ProbabilityVariable[] all = new ProbabilityVariable[all_size];
	all = st.get_probability_variables();
	if (A.is_observed() || B.is_observed()) {
	    return in_one_c_component(A, B, pn);
	} else {
	    if (A.get_name().equals(B.get_name())) {
		return true;
	    } else if (dsep.adj(A.get_index(), B.get_index(), 0) || dsep.adj(B.get_index(), A.get_index(), 0)) {
		pn.addElement(B);
		return true;
	    } else {
		ProbabilityVariable pv;
		for (int i = 0; i < all_size; i++) {
		    int n = 0;
		    if (DUP_Path(A, all[i]) && DUP_Path(B, all[i])) //if(dsep.adj(A.get_index(), all[i].get_index(), 0) && dsep.adj(B.get_index(), all[i].get_index(), 0))
		    {	//System.out.println(A.get_name() + " and "+B.get_name()+ "----common child----"+observed[i].get_name());
			pn.addElement(B);
			return true;
		    }
		}
		for (e = pn.elements(); e.hasMoreElements();) {
		    pv = (ProbabilityVariable) (e.nextElement());
		    if (!B.get_name().equals(pv.get_name())) {
			Vector Bpa = new Vector();
			if (is_c_component_relation(B, pv, Bpa)) {
			    pn.addElement(B);
			    return true;
			}
		    }
		}
	    }

	}

	return false;
    }
    /*
     * pn is the vector hold nodes in c-com(A) already.
     */

    public boolean in_one_c_component(ProbabilityVariable A, ProbabilityVariable B, Vector pn) {
	if (A.get_name().equals(B.get_name())) {
	    return true;
	}
	boolean is = false;
	DSeparation dsep = new DSeparation((BayesNet) qbn);
	int un_size = st.number_unobserved();
	ProbabilityVariable[] unobserved = new ProbabilityVariable[un_size];
	unobserved = st.get_unobserved_variables();
	if (!A.is_observed() && !B.is_observed()) {
	    return is_c_component_relation(A, B, pn);
	} else if (A.is_observed() && !B.is_observed()) {
	    for (int i = 0; i < un_size; i++) {
		Vector Bpa = new Vector();
		if (dsep.adj(unobserved[i].get_index(), A.get_index(), 0) && is_c_component_relation(B, unobserved[i], Bpa)) {

		    return true;
		}
	    }
	} else if (!A.is_observed() && B.is_observed()) {
	    for (int i = 0; i < un_size; i++) {
		if (dsep.adj(unobserved[i].get_index(), B.get_index(), 0) && is_c_component_relation(A, unobserved[i], pn)) {
		    return true;
		}
	    }
	} else {

	    Vector<ProbabilityVariable> paA = new Vector<ProbabilityVariable>();
	    Vector<ProbabilityVariable> paB = new Vector<ProbabilityVariable>();
	    for (int i = 0; i < un_size; i++) {
		if (dsep.adj(unobserved[i].get_index(), A.get_index(), 0)) {
		    paA.addElement(unobserved[i]);
		}
		if (dsep.adj(unobserved[i].get_index(), B.get_index(), 0)) {
		    paB.addElement(unobserved[i]);
		}
	    }
	    Enumeration<ProbabilityVariable> e, f;
	    ProbabilityVariable pv1, pv2;
	    for (e = paA.elements(); e.hasMoreElements();) {
		pv1 = (ProbabilityVariable) (e.nextElement());
		for (f = paB.elements(); f.hasMoreElements();) {
		    pv2 = (ProbabilityVariable) (f.nextElement());
		    Vector<ProbabilityVariable> pa = new Vector<ProbabilityVariable>();
		    if (is_c_component_relation(pv1, pv2, pa)) {
			return true;
		    } else {
			for (int i = 0; i < un_size; i++) {
			    Vector<ProbabilityVariable> Bpa = new Vector<ProbabilityVariable>();
			    if (is_c_component_relation(A, unobserved[i], pn) && is_c_component_relation(B, unobserved[i], Bpa)) {
				return true;
			    }
			}
		    }
		}
	    }

	}
	return is;
    }
    /*
     * return intersection of A & B.
     */

    public Vector<ProbabilityVariable> Intersection(Vector A, Vector<ProbabilityVariable> B) {
	Vector<ProbabilityVariable> D = new Vector<ProbabilityVariable>();
	int a_size = A.size();
	ProbabilityVariable[] pva = new ProbabilityVariable[a_size];
	int b_size = B.size();
	ProbabilityVariable[] pvb = new ProbabilityVariable[b_size];
	pva = from_Vector_to_ProbabilityVariable(A);
	pvb = from_Vector_to_ProbabilityVariable(B);
	Vector<ProbabilityVariable> A_B = new Vector<ProbabilityVariable>();
	A_B = Subtraction(pva, pvb);
	int a_b_size = A_B.size();
	ProbabilityVariable[] pva_b = new ProbabilityVariable[a_b_size];
	pva_b = from_Vector_to_ProbabilityVariable(A_B);
	D = Subtraction(pva, pva_b);

	return D;
    }

    public Vector<?> Intersection(ProbabilityVariable[] A, ProbabilityVariable[] B) {

	int a_size = A.length;

	int b_size = B.length;

	Vector<ProbabilityVariable> A_B = new Vector<ProbabilityVariable>();
	A_B = Subtraction(A, B);
	int a_b_size = A_B.size();
	ProbabilityVariable[] a_b = new ProbabilityVariable[a_b_size];
	a_b = from_Vector_to_ProbabilityVariable(A_B);
	Vector D_temp = new Vector();
	D_temp = Subtraction(A, a_b);

	return D_temp;
    }
    /*
     * change Vector to ProbabilityVariable[].
     */

    public ProbabilityVariable[] from_Vector_to_ProbabilityVariable(Vector<ProbabilityVariable> V) {
	int size = V.size();
	ProbabilityVariable[] Re = new ProbabilityVariable[size];
	Enumeration<ProbabilityVariable> e;
	ProbabilityVariable pv;
	int n = 0;
	for (e = V.elements(); e.hasMoreElements();) {
	    pv = (ProbabilityVariable) (e.nextElement());
	    Re[n] = pv;
	    n++;
	}
	return Re;
    }
    /*
     * change ProbabilityVariable[] to Vector.
     */

    public Vector<ProbabilityVariable> from_ProbabilityVariable_to_Vector(ProbabilityVariable[] pvs) {
	Vector<ProbabilityVariable> Re = new Vector<ProbabilityVariable>();

	for (int i = 0; i < pvs.length; i++) {
	    Re.addElement(pvs[i]);
	}
	return Re;
    }

    public ProbabilityVariable[] Subtraction(ProbabilityVariable[] A, ProbabilityVariable B) {
	if (B == null) {
	    return A;
	}
	if (A == null) {
	    return null;
	}
	ProbabilityVariable[] C = new ProbabilityVariable[A.length - 1];
	int n = 0;
	for (int i = 0; i < A.length; i++) {
	    if (!A[i].get_name().equals(B.get_name())) {
		C[n] = A[i];
		n++;
	    }

	}

	return C;
    }

    public Vector<ProbabilityVariable> Subtraction(ProbabilityVariable[] A, ProbabilityVariable[] B) {
	Vector<ProbabilityVariable> C = new Vector<ProbabilityVariable>();
	int count = 0;
	for (int i = 0; i < A.length; i++) {
	    int j = 0;
	    boolean is = false;
	    while (!is && j < B.length) {
		if (B[j] != null && A[i].get_name().equals(B[j].get_name())) {
		    is = true;
		}
		j++;
	    }
	    if (!is) {
		C.addElement(A[i]);
		count++;
	    }

	}

	return C;
    }

    public Vector<ProbabilityVariable> Gc(Vector<ProbabilityVariable> C) {
	Vector<ProbabilityVariable> Gc = new Vector<ProbabilityVariable>();
	Vector<ProbabilityVariable> DUP = new Vector<ProbabilityVariable>();
	Enumeration<ProbabilityVariable> e;
	ProbabilityVariable pv;
	st.clean_mark();
	DUP(C, DUP);
	st.clean_mark();

	for (e = C.elements(); e.hasMoreElements();) {
	    pv = (ProbabilityVariable) (e.nextElement());
	    Gc.addElement(pv);
	}
	for (e = DUP.elements(); e.hasMoreElements();) {
	    pv = (ProbabilityVariable) (e.nextElement());
	    Gc.addElement(pv);
	}
	return Gc;
    }
    /*
     * return C and DuP(C)
     */

    public Vector<ProbabilityVariable> Gc(ProbabilityVariable[] C) {
	Vector<ProbabilityVariable> Gc = new Vector<ProbabilityVariable>();
	Vector<ProbabilityVariable> DUP = new Vector<ProbabilityVariable>();
	Enumeration<ProbabilityVariable> e;
	ProbabilityVariable pv;
	st.clean_mark();
	DUP(C, DUP);
	st.clean_mark();
	for (int i = 0; i < C.length; i++) {
	    Gc.addElement(C[i]);
	}
	for (e = DUP.elements(); e.hasMoreElements();) {
	    pv = (ProbabilityVariable) (e.nextElement());
	    //System.out.println(pv.get_name()+"^^");
	    Gc.addElement(pv);
	}
	return Gc;
    }

    public Vector<ProbabilityVariable> DUP(ProbabilityVariable[] C, Vector<ProbabilityVariable> DUP) {

	int size = st.number_unobserved();
	Enumeration<ProbabilityVariable> e;
	ProbabilityVariable pv;
	ProbabilityVariable[] unObserved = new ProbabilityVariable[size];
	Vector<ProbabilityVariable> temp = new Vector<ProbabilityVariable>();
	unObserved = st.get_unobserved_variables();
	int c_length = C.length;
	int stop = 0;
	for (int i = 0; i < size; i++) {
	    boolean is = false;
	    int j = 0;
	    while (!is && j < c_length) {
		if (DUPPath(unObserved[i], C[j])) {
		    stop++;
		    is = true;
		    if (unObserved[i].getMark() == 0) {
			unObserved[i].setMark(1);
			temp.addElement(unObserved[i]);
			DUP.addElement(unObserved[i]);

		    }
		}
		j++;

	    }
	}
	int temp_size = temp.size();
	int num = 0;
	ProbabilityVariable[] Temp = new ProbabilityVariable[temp_size];
	for (e = temp.elements(); e.hasMoreElements();) {
	    pv = (ProbabilityVariable) (e.nextElement());
	    Temp[num] = pv;
	    num++;
	}
	if (stop > 0) {
	    DUP(Temp, DUP);
	} else {
	    return DUP;
	}

	return DUP;
    }

    public Vector<ProbabilityVariable> DUP(Vector<ProbabilityVariable> B, Vector<ProbabilityVariable> DUP) {
	int c_length = B.size();
	ProbabilityVariable[] C = new ProbabilityVariable[c_length];
	Enumeration<ProbabilityVariable> e;
	ProbabilityVariable pv;
	int n = 0;
	for (e = B.elements(); e.hasMoreElements();) {
	    pv = (ProbabilityVariable) (e.nextElement());
	    C[n] = pv;
	    n++;
	}
	int size = st.number_unobserved();

	ProbabilityVariable[] unObserved = new ProbabilityVariable[size];
	Vector<ProbabilityVariable> temp = new Vector<ProbabilityVariable>();
	unObserved = st.get_unobserved_variables();

	int stop = 0;
	for (int i = 0; i < size; i++) {
	    boolean is = false;
	    int j = 0;
	    while (!is && j < c_length) {
		if (DUPPath(unObserved[i], C[j])) {
		    stop++;
		    is = true;
		    if (unObserved[i].getMark() == 0) {
			unObserved[i].setMark(1);
			temp.addElement(unObserved[i]);
			DUP.addElement(unObserved[i]);

		    }
		}
		j++;

	    }
	}
	int temp_size = temp.size();
	int num = 0;
	ProbabilityVariable[] Temp = new ProbabilityVariable[temp_size];
	for (e = temp.elements(); e.hasMoreElements();) {
	    pv = (ProbabilityVariable) (e.nextElement());
	    Temp[num] = pv;
	    num++;
	}
	if (stop > 0) {
	    DUP(Temp, DUP);
	} else {
	    return DUP;
	}

	return DUP;
    }

    private boolean DUPPath(ProbabilityVariable unObserved, ProbabilityVariable C) {
	boolean DUP = false;
	DSeparation dsep = new DSeparation((BayesNet) qbn);
	int i_u, i_c;
	i_u = unObserved.get_index();
	i_c = C.get_index();
	if (dsep.adj(i_u, i_c, 0)) {
	    DUP = true;
	}

	return DUP;
    }

    private boolean DUP_Path(ProbabilityVariable unObserved, ProbabilityVariable C) {
	boolean DUP = false;
	ProbabilityVariable[] un = new ProbabilityVariable[st.number_unobserved()];
	un = st.get_unobserved_variables();
	DSeparation dsep = new DSeparation((BayesNet) qbn);
	int i_u, i_c;
	i_u = unObserved.get_index();
	i_c = C.get_index();
	ProbabilityVariable[] U = new ProbabilityVariable[st.number_unobserved()];;
	int j = 0;
	for (int i = 0; i < un.length; i++) {
	    if (dsep.adj(un[i].get_index(), i_c, 0)) {
		if (un[i].get_name().equals(unObserved.get_name())) {
		    DUP = true;
		    return true;
		}
		U[j] = un[i];
		j++;
	    }
	}
	for (int i = 0; i < j; i++) {
	    DUP = DUP_Path(unObserved, U[i]);
	}
	return DUP;
    }

    public void Qprint(Q Q, PrintStream out) {

	if (Q.getSummation() == null) {
	    Print_Probability_array(Q.getProbability(), out);
	} else {
	    String SUM = "sum_(";
	    for (int i = 0; i < Q.number_Summation(); i++) {
		if (i == Q.number_Summation() - 1) {
		    SUM = SUM + Q.getSummation()[i].get_name() + ")";
		} else {
		    SUM = SUM + Q.getSummation()[i].get_name() + " ";
		}

	    }
	    out.print(SUM);

	}

    }

    public String Qprint(Q Q) {
	String R = "";
	if (Q.getProbability() == null) {
	    return R;
	}
	if (Q.number_Summation() == 0) {
	    R = Print_Probability_array(Q.getProbability());
	}

	if (Q.number_Summation() > 0) {
	    String SUM = "sum_(";
	    for (int i = 0; i < Q.number_Summation(); i++) {
		if (i == Q.number_Summation() - 1) {
		    SUM = SUM + Q.getSummation()[i].get_name() + ")";
		} else {
		    SUM = SUM + Q.getSummation()[i].get_name() + " ";
		}

	    }
	    SUM = SUM + Print_Probability_array(Q.getProbability());
	    R = SUM;

	}
	return R;
    }

    public String QFprint(QFraction QF, PrintStream out) {
	String R = "";

	if (QF == null) {
	    out.println("Unidentifiable!");
	    R = "Unidentifiable!";
	    return R;
	} else if (QF.num_QFsum() == 0 && QF.D == null) {
	    System.out.print("else if 0 0 ");
	    if (QF.num_sum() == 0) {
		if (QF.num_down() == 0) {
		    R = print(QF.Up);
		} else {

		    if (QF.num_down() > 0) {
			R = "[";
			R = R + print(QF.Up) + "] / ";
			R = R + "[" + print(QF.Down) + "]";
		    } else {
			R = R + print(QF.Up);
		    }
		}
	    } else {
		R = "sum_(";
		for (int i = 0; i < QF.num_sum(); i++) {
		    if (i == QF.num_sum() - 1) {

			R = R + QF.Sum[i].get_name() + ")";
		    } else {
			R = R + QF.Sum[i].get_name() + " ";
		    }
		}
		String sum = R;
		if (QF.num_down() == 0) {
		    if (sum.equals("sum_(")) {
			R = print(QF.Up);
		    } else {
			R = R + print(QF.Up);
		    }
		} else {
		    if (QF.num_down() > 0) {
			R = "[";
			R = R + print(QF.Up) + "] / ";
			R = R + "[" + print(QF.Down) + "]";
		    } else {
			R = R + print(QF.Up);
		    }
		    if (!sum.equals("sum_(")) {
			R = sum + R;
		    }
		}
	    }
	    return R;
	} else if (QF.num_QFsum() == 0 && QF.D != null) {
	    System.out.print("else if 0 1 ");
	    R = "[";
	    R = R + QFprint(QF.U, out);
	    R = R + "] / " + "[";
	    R = R + QFprint(QF.D, out);
	    R = R + "] ";
	} else if (QF.num_QFsum() != 0 && QF.D == null) {
	    System.out.print("else if 1 0 ");
	    R = "sum_(";
	    for (int i = 0; i < QF.num_QFsum(); i++) {
		if (i == QF.num_QFsum() - 1) {
		    R = R + QF.getQF_Sum()[i].get_name() + ")";
		} else {
		    R = R + QF.getQF_Sum()[i].get_name() + " ";
		}
	    }
	    R = R + "[";
	    R = R + QFprint(QF.U, out);
	    R = R + "] ";
	} else {
	    System.out.print("else if 1 1 ");
	    R = "sum_(";
	    for (int i = 0; i < QF.num_QFsum(); i++) {
		if (i == QF.num_QFsum() - 1) {
		    R = R + QF.getQF_Sum()[i].get_name() + ")";
		} else {
		    R = R + QF.getQF_Sum()[i].get_name() + " ";
		}
	    }
	    R = R + "[";
	    R = R + QFprint(QF.U, out);
	    R = R + "] ";
	    if (QF.getD() != null || QF.getDown() != null || QF.Down.length > 0) {
		String check = "";
		for (int i = 0; i < QF.Down.length; i++) {
		    System.out.print("?");
		    check = check + Qprint(QF.Down[i]);
		}
		if (check.length() > 0) {
		    R = R + " / " + "[";
		    R = R + QFprint(QF.D, out);
		    R = R + "] ";
		}
	    }
	}
	return R;
    }

    public void QFprint(QFraction QF) {
	String R = "";

	if (QF == null) {
	    return;
	} else if (QF.num_QFsum() == 0 && QF.D == null) {
	    System.out.print("--else if 0 0 ");
	    if (QF.num_sum() == 0) {
		if (QF.num_down() == 0) {
		    R = print(QF.Up);
		} else {

		    if (QF.num_down() > 0) {
			R = "[";
			R = R + print(QF.Up) + "] / ";
			R = R + "[" + print(QF.Down) + "]";
		    } else {
			R = R + print(QF.Up);
		    }
		}
		System.out.print(R);
	    } else {
		R = "sum_(";
		for (int i = 0; i < QF.num_sum(); i++) {
		    if (i == QF.num_sum() - 1) {

			R = R + QF.Sum[i].get_name() + ")";
		    } else {
			R = R + QF.Sum[i].get_name() + " ";
		    }
		}
		System.out.print(R);
		if (QF.num_down() == 0) {
		    R = print(QF.Up);
		} else {
		    if (QF.num_down() > 0) {
			R = "[";
			R = R + print(QF.Up) + "] / ";
			R = R + "[" + print(QF.Down) + "]";
		    } else {
			R = R + print(QF.Up);
		    }
		}
		System.out.print(R);
	    }
	    return;
	} else if (QF.num_QFsum() == 0 && QF.D != null) {
	    System.out.print("--else if 0 1 ");
	    System.out.print("[");
	    QFprint(QF.U);
	    System.out.print("] / ");
	    System.out.print("[");
	    QFprint(QF.D);
	    System.out.print("] ");
	} else {
	    System.out.print("--else if 1 1 ");
	    R = "sum_(";
	    for (int i = 0; i < QF.num_QFsum(); i++) {
		if (i == QF.num_QFsum() - 1) {
		    R = R + QF.getQF_Sum()[i].get_name() + ")";
		} else {
		    R = R + QF.getQF_Sum()[i].get_name() + " ";
		}
	    }
	    System.out.print(R);
	    System.out.print("[");
	    QFprint(QF.U);
	    System.out.print("] / ");
	    System.out.print("[");
	    QFprint(QF.D);
	    System.out.print("] ");
	}

    }

    private String print(Q[] Q) {
	String R = "";
	if (Q == null) {
	    return "";
	}
	for (int i = 0; i < Q.length; i++) {
	    R = R + " " + Qprint(Q[i]);
	}
	return R;
    }

    public String print(PrintStream out, Q[] Q, ProbabilityVariable[] sum) {
	String R = "";
	if (Q == null) {
	    return "";
	}
	R = print(Q);
	if (sum.length != 0) {
	    String s = "";
	    int len = sum.length;
	    for (int i = 0; i < len; i++) {
		if (i == len - 1) {

		    s = s + sum[i].get_name() + ")";
		} else {
		    s = s + sum[i].get_name() + " ";
		}
	    }
	    R = s + R;
	}

	return R;
    }

    public void print(PrintStream out) {

	algorithm_computing(out);

    }

    /**
     * Algorithm Identify(C, T, Q). Input: C belong to T and T bellong to N. Q =
     * Q[T] Gt and Gc are both composed of one single c-component.
     */
    /*
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */
    public QFraction AlgorithmIdentify(ProbabilityVariable[] C, ProbabilityVariable[] T, QFraction qq, PrintStream out) {
	// TODO Auto-generated method stub
	System.out.print("qq = ");
	QFprint(qq);
	System.out.println();

	Verma_Type_Functional_Constraint VTFC = new Verma_Type_Functional_Constraint();
	methods m = new methods();
	Vector Q_R = new Vector();
	//Q[] Qup = qq.getUp();

	//Q Qc = new Q();
	STInference sti = new STInference(qbn, true);
	Enumeration e;

	ProbabilityVariable p = new ProbabilityVariable();
	// Create QuasiBayesNet G_t.
	Vector DUP = new Vector();
	DUP = VTFC.DUP(qbn, T, out);
	ProbabilityVariable[] dup = new ProbabilityVariable[DUP.size()];
	dup = m.from_Vector_to_ProbabilityVariable(DUP);
	ProbabilityVariable[] union = new ProbabilityVariable[DUP.size() + T.length];
	union = m.Add(dup, T);
	BayesNet Gt = new BayesNet(VTFC.SubBayesNet(qbn, union, "Gt"));
	QuasiBayesNet q_Gt = new QuasiBayesNet(Gt);
	ProbabilityVariable[] pA_tem = VTFC.ancestors(q_Gt, C);
	int len_A = pA_tem.length;
	ProbabilityVariable[] patem = new ProbabilityVariable[len_A];
	int pa = 0;
	for (int i = 0; i < len_A; i++) {
	    if (pA_tem[i].is_observed()) {
		patem[pa] = pA_tem[i];
		pa++;
	    }
	}

	ProbabilityVariable[] pA = new ProbabilityVariable[pa];
	for (int i = 0; i < pa; i++) {
	    pA[i] = patem[i];
	}


	System.out.print("A:  ");
	for (int i = 0; i < pA.length; i++) {
	    System.out.print(pA[i].get_name() + ",");
	}

	int t_c = T.length - C.length;
	Vector<ProbabilityVariable> T_c = new Vector<ProbabilityVariable>();
	ProbabilityVariable[] T_C = new ProbabilityVariable[t_c];
	T_c = Subtraction(T, C);
	T_C = from_Vector_to_ProbabilityVariable(T_c);

	// Create QuasiBayesNet Ga.
	Vector DUP_a = new Vector();
	DUP_a = VTFC.DUP(qbn, pA, out);
	ProbabilityVariable[] dup_a = new ProbabilityVariable[DUP_a.size()];
	dup_a = m.from_Vector_to_ProbabilityVariable(DUP_a);
	ProbabilityVariable[] union_a = new ProbabilityVariable[DUP_a.size() + pA.length];
	union_a = m.Add(dup_a, pA);
	BayesNet Ga = new BayesNet(VTFC.SubBayesNet(qbn, union_a, "Ga"));
	QuasiBayesNet q_Ga = new QuasiBayesNet(Ga);
	Vector[] com_A = new Vector[q_Ga.number_variables()];
	AlgorithmComputing AC = new AlgorithmComputing(q_Ga);
	com_A = AC.partition_c_component(pA);



	if (pA.length == C.length) {
	    System.out.println("**==**");
	    QFraction Q_C = new QFraction();
	    Q_C = sti.Summation(qq, T_C);

	    //QFprint(Q_C);
	    return Q_C;
	} else if (pA.length == T.length) {
	    Q_R = null;
	    return null;
	} else {

	    //change Ga to BayesNet or STBayesNet
	    AlgorithmComputing AC1 = new AlgorithmComputing(q_Ga);
	    STInference sti_Ga = new STInference(q_Ga, true);
	    STBayesNet Ga_st = new STBayesNet(q_Ga);
	    Vector[] c_com;

	    ProbabilityVariable[] G_A_N = new ProbabilityVariable[Ga_st.number_observed()];
	    G_A_N = Ga_st.get_observed_variables();
	    System.out.println("G_A_N=" + G_A_N.length);
	    c_com = AC1.partition_c_component(G_A_N);
	    int t_a = T.length - pA.length;
	    Vector<ProbabilityVariable> T_a = new Vector<ProbabilityVariable>();
	    ProbabilityVariable[] T_A = new ProbabilityVariable[t_a];
	    T_a = Subtraction(T, pA);
	    T_A = from_Vector_to_ProbabilityVariable(T_a);

	    QFraction Q_A = new QFraction();
	    Q_A = sti_Ga.Summation(qq, T_A);
	    boolean y = false; // check whether is in T1
	    System.out.println("qq:");
	    QFprint(qq);
	    System.out.println();
	    System.out.println("Q_A:");
	    QFprint(Q_A);
	    System.out.println();
	    int find_c = -1;
	    for (int i = 0; i < c_com.length && !y; i++) {
		for (e = c_com[i].elements(); e.hasMoreElements();) {
		    p = (ProbabilityVariable) (e.nextElement());
		    if (C[0].get_name().equals(p.get_name())) {
			find_c = i;
			break;
		    }

		}
	    }
	    System.out.println("c-com.length=" + c_com.length);
	    System.out.println("find_c=" + find_c);
	    ProbabilityVariable[] T_1 = new ProbabilityVariable[c_com[find_c].size()];
	    T_1 = from_Vector_to_ProbabilityVariable(c_com[find_c]);

	    QFraction Q_T_1;// =new QFraction();
	    ProbabilityVariable[] Ttop = new ProbabilityVariable[pA.length];
	    Ttop = sti.TopologicalOrder(G_A_N);



	    Q_T_1 = lemma2(Q_A, T_1, Ttop);
	    return AlgorithmIdentify(C, T_1, Q_T_1, out);

	}

    }

    public QFraction lemma2(QFraction QH, ProbabilityVariable[] H, ProbabilityVariable[] top) {
	if (QH == null) {
	    return null;
	}
	if (H == null || top == null) {
	    return null;
	}

	for (int i = 0; i < H.length; i++) {
	    System.out.println("H==" + H[i].get_name());
	}
	System.out.println("=========================");
	for (int i = 0; i < top.length; i++) {
	    System.out.println("top==" + top[i].get_name());
	}
	System.out.println("---------------------------------------------");

	QFraction Q_H_i = new QFraction();
	int cont = 0;
	STInference sti = new STInference(qbn, true);
	int find_j = -1;
	int last = -2;
	for (int i = 0; i < H.length; i++) {
	    find_j = -1;
	    for (int j = 0; j < top.length; j++) {
		if (H[i].get_name().equals(top[j].get_name())) {
		    find_j = j;
		    break;
		}
	    }
	    System.out.println("find_j= " + find_j);
	    ProbabilityVariable[] Hj = new ProbabilityVariable[top.length - find_j - 1];
	    ProbabilityVariable[] Hj_1 = new ProbabilityVariable[top.length - find_j];
	    int contj = 0, contj_1 = 0;
	    for (int k = find_j + 1; k < top.length; k++) {
		Hj[contj] = top[k];
		System.out.println("Hj = " + Hj[contj].get_name());
		contj++;
	    }
	    for (int k = find_j; k < top.length; k++) {
		Hj_1[contj_1] = top[k];
		System.out.println("Hj_1 = " + Hj_1[contj_1].get_name());
		contj_1++;
	    }
	    System.out.println("Q_H :");

	    QFraction Q_Hj = sti.Summation(QH, Hj);
	    QFraction Q_Hj_1 = sti.Summation(QH, Hj_1);
	    System.out.println("Q_Hj :");
	    QFprint(Q_Hj);
	    System.out.println("\nQ_Hj_1 :");
	    QFprint(Q_Hj_1);
	    System.out.println();
	    if (find_j - last == 1) {
		ProbabilityVariable[] Hl_1 = new ProbabilityVariable[top.length - last - 1];
		for (int k = 0; k < top.length - last - 1; k++) {
		    Hl_1[k] = top[k + find_j - 1];
		}
		QFraction Q_Hl_1 = sti.Summation(QH, Hl_1);
		Q_H_i = new QFraction(Q_Hj, Q_Hl_1);
	    } else {
		Q_H_i = new QFraction(Q_Hj, Q_Hj_1);
	    }
	    Q_H_i = sti.Simply(Q_H_i);
	}
	return Q_H_i;
    }

    private QuasiBayesNet from_ProbabilityArray_to_QuasiBayesNet(Probability[] Ga_Pr, String name) {
	STInference sti = new STInference(qbn, true);
	BayesNet bn = new BayesNet(name, Ga_Pr.length, Ga_Pr.length);

	ProbabilityVariable[] G_V = new ProbabilityVariable[Ga_Pr.length];
	ProbabilityFunction[] G_F = new ProbabilityFunction[Ga_Pr.length];
	ProbabilityFunction[] G_F_temp = new ProbabilityFunction[Ga_Pr.length];
	for (int i = 0; i < Ga_Pr.length; i++) {
	    G_V[i] = sti.form_Probability_to_ProbabilityVariable(Ga_Pr[i]);
	    G_F_temp[i] = st.get_function(G_V[i]);
	}
	bn.set_probability_variables(G_V);
	for (int i = 0; i < Ga_Pr.length; i++) {
	    DiscreteVariable[] dvs = new DiscreteVariable[Ga_Pr[i].num_evi + 1];
	    dvs = Add(Ga_Pr[i].getName(), Ga_Pr[i].getEvidences());
	    ProbabilityFunction pf = new ProbabilityFunction(bn, dvs, Ga_Pr[i].getValue(), G_F_temp[i].get_properties());
	    G_F[i] = pf;
	}
	bn.set_probability_functions(G_F);
	QuasiBayesNet qbn = new QuasiBayesNet(bn);

	return qbn;
    }

    public DiscreteVariable[] Add(DiscreteVariable A, DiscreteVariable[] B) {
	DiscreteVariable[] C = new DiscreteVariable[B.length + 1];
	C[0] = A;
	for (int i = 1; i < B.length + 1; i++) {
	    C[i] = B[i - 1];
	}
	return C;
    }

    public QuasiBayesNet from_Vector_to_QuasiBayesNet(Vector Ga, String name) {
	STInference sti = new STInference(qbn, true);
	ProbabilityVariable[] Ga_V = new ProbabilityVariable[Ga.size()];
	Ga_V = from_Vector_to_ProbabilityVariable(Ga);
	Probability[] Ga_Pr = null;
	Ga_Pr = sti.QofN(Ga_V, qbn);


	QuasiBayesNet G_A = new QuasiBayesNet();
	G_A = from_ProbabilityArray_to_QuasiBayesNet(Ga_Pr, name);
	return G_A;
    }

    public int number_d() {


	return get_probability_variables_d().size();

    }

    public Vector get_probability_variables_d() {
	ProbabilityVariable[] N_t = from_Vector_to_ProbabilityVariable(N_T);
	
	Vector DUP = new Vector();
	Verma_Type_Functional_Constraint VTFC = new Verma_Type_Functional_Constraint();

	DUP = VTFC.DUP(st, N_t, System.out);
	ProbabilityVariable[] dup = new ProbabilityVariable[DUP.size()];
	dup = m.from_Vector_to_ProbabilityVariable(DUP);
	ProbabilityVariable[] union = m.Add(dup, N_t);
	BayesNet Gv_t = new BayesNet(VTFC.SubBayesNet(st, union, "Gv_t"));
	QuasiBayesNet q_Gv_t = new QuasiBayesNet(Gv_t);
	D = m.Intersection(VTFC.ancestors(q_Gv_t, S), N);

	return D;

    }
}
