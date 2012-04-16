package aaai_08;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;

public class SEM {
		protected String beta1[];
		protected String beta2[];
		protected String apha[];
		protected String c[];
		//protected String betaApha[][];
		protected String SEM_l[][];
		protected String apha_l[][];
		//protected int num_sum;
		
		public final static String B = "¦Â";
		public final static String A = "A";
		public final static String C = "C";
		public SEM(){
			
		}
		public SEM(String[] b1, String[] b2, String[] a, String[] c){
			beta1 = b1;
			beta2 = b2;
			apha = a;
			c = c;
		}
		public SEM(String[] b1){
			beta1 = b1;
			//beta2 = b2;
			apha = b1;
			c = b1;
		}
		public SEM(String[] b1, String[] b2, String[] a, String[] cc,String[][] SEMl, String[][] a_l){
			beta1 = b1;
			beta2 = b2;
			apha = a;
			c = cc;
			SEM_l = SEMl;
			apha_l= a_l;
		}
		public String[] getBeta1() {
			return beta1;
		}
		public void setBeta1(String[] beta1) {
			this.beta1 = beta1;
		}
		public String[] getBeta2() {
			return beta2;
		}
		public void setBeta2(String[] beta2) {
			this.beta2 = beta2;
		}
		public String[] getApha() {
			return apha;
		}
		public void setApha(String[] apha) {
			this.apha = apha;
		}
		public String[] getc() {
			return c;
		}
		public void setC(String[] c) {
			this.c = c;
		}
		
		public void print_Cjk(PrintStream out) {
			out.print(C);
		}
		public void print_Bjk(PrintStream out) {
			
		}
		public int num_summation(){
			//return betaApha.length;
			
			return SEM_l.length;
		}
		/*
		public String[][] getBetaApha() {
			return betaApha;
		}
		public void setBetaApha(String[][] betaApha) {
			this.betaApha = betaApha;
		}
		*/
		public String[][] getSEM_l() {
			return SEM_l;
		}
		public void setSEM_l(String[][] sem_l) {
			SEM_l = sem_l;
		}
		public String[][] getApha_l() {
			return apha_l;
		}
		public void setApha_l(String[][] apha_l) {
			this.apha_l = apha_l;
		}
		public void summation(String[][] a_l,SEM[] sem_l){
			int len = a_l.length;
			String[][] copy = new String[len][1];
			for(int i=0; i<len; i++){
				copy[i][0] = a_l[i][0];
			}
			Vector copy_apha = new Vector();
			Vector copy_bl = new Vector();
			for(int i=0; i<len; i++){
				if (!a_l[i][0].equals("0")){
					copy_apha.add(a_l[i][0]);
					copy_bl.add(sem_l[i]);
				}
			}
			int num = copy_apha.size();
			Enumeration e,f;
			String[] x = new String[2];
			int idx = 0;
			String[][] y = new String[num][2];
			for (e = copy_apha.elements(); e.hasMoreElements(); ) {
	              x = (String[]) e.nextElement();
	              y[idx] = x;
	              idx++;
			  }
			String[][] S = new String[num][2];
			String[] s = new String[2];
			idx=0;
			for (f = copy_bl.elements(); f.hasMoreElements(); ) {
	              s = (String[]) f.nextElement();
	              S[idx] = s;
	              idx++;
			  }
			this.SEM_l = S;
			this.apha_l = y;
		}
		
		public void print_Equation16(PrintStream out){
			out.print(B);
			out.print(beta1[0]+beta1[1]);
			out.print(" = ");
			if (!c[0].equals("0")){
				out.print(C + c[0]+c[1] );
				if (!apha[0].equals("0")){
					out.print(" + ");
				}
			}
			
			if (!apha[0].equals("0"))
				out.print(A + apha[0]+apha[1]);
			int num = num_summation();
			if (num > 0){
				out.print(" - ");
				if(num >1)
					out.print("(");
				for(int i=0; i<num; i++){
					if(!apha_l[i][0].equals("0")){
						out.print(B + SEM_l[i][0] + SEM_l[i][1] + " ");
						out.print(A + apha_l[i][0] + apha_l[i][1]);
					}
				}
				if(num >1)
					out.print(")");				
			}
			if (num==0 && c[0].equals("0") && apha[0].equals("0"))
				out.print(" 0");
			out.println();
		}
		public void print_Equation17(PrintStream out){
			out.print(C);
			out.print(c[0]+c[1]);
			out.print(" = ");
			
			out.print(B + beta1[0]+beta1[1]);
			if (!apha[0].equals("0"))
				out.print(" + " +A + apha[0]+apha[1]);
			int num = num_summation();
			if (num > 0){
				out.print(" + ");
				if(num >1)
					out.print("(");
				for(int i=0; i<num; i++){
					if(!apha_l[i][0].equals("0")){
						out.print(B + SEM_l[i][0] + SEM_l[i][1] + " ");
						out.print(A + apha_l[i][0] + apha_l[i][1]);
					}
				}
				if(num >1)
					out.print(")");				
			}
			if(apha[0].equals("0") && num == 0)
				out.print(" 0");
			out.println();
		}
}
