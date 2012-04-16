package aaai_08;

import java.io.PrintStream;

public class SEMs {
	protected SEM sem[];

	public SEMs (int n){
		sem = new SEM[n];
	}
	public SEMs (SEM[] sems){
		sem = sems;
	}
	public SEM[] getSem() {
		return sem;
	}

	public void setSem(SEM[] sem) {
		this.sem = sem;
	}
	public int number_Equations(){
		return sem.length;
	}
	public void print_Equation17(PrintStream out){
		int len = number_Equations();
		if(len > 0 ){
			for(int i=0; i<len; i++){
				sem[i].print_Equation17(out);
			}
		}
	}
	public void print_Equation16(PrintStream out){
		int len = number_Equations();
		if(len > 0 ){
			System.out.println(len);
			for(int i=0; i<len; i++){
				sem[i].print_Equation16(out);
			}
		}
	}
}
