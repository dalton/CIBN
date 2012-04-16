package Identifiability;

import java.io.PrintStream;

import BayesianNetworks.DiscreteVariable;
import BayesianNetworks.ProbabilityFunction;
import BayesianNetworks.ProbabilityVariable;

public class Probability {
	DiscreteVariable name;
	DiscreteVariable[] evidences;
	double [] value;
	int num_evi;
	int num_value;
	public  int fail = -1;
	
	public Probability(){
		name = null;
		evidences = null;
		value = null;
		num_evi = 0;
		num_value = 0;
		 fail = -1;
	}
	public Probability(ProbabilityFunction pf){
		name = pf.variables[0];
		 DiscreteVariable[] e = new  DiscreteVariable[pf.number_variables()-1];
		 for(int i=0; i<pf.number_variables()-1;i++){
			 e[i]=pf.variables[i+1];
		 }
		evidences =e;
		value = pf.values;
		num_evi = e.length;
		num_value = value.length;
		 fail = -1;
	}
	public Probability(DiscreteVariable n, DiscreteVariable[] e, double[] v){
		name = n;
		evidences = e;
		value = v;
		num_evi = e.length;
		if (v==null){
			num_value = 0;
		}
		else
			num_value = v.length;
		 fail = -1;
	}
	public Probability(DiscreteVariable n, DiscreteVariable[] e){
		name = n;
		evidences = e;
		value = null;
		num_evi = e.length;		
		num_value = 0;		
		 fail = -1;
	}
	public Probability(ProbabilityVariable n, ProbabilityVariable[] e){
		name = n;
		evidences = e;
		value = null;
		num_evi = e.length;		
		num_value = 0;		
		 fail = -1;
	}
	/**
	 * @return the evidences
	 */
	public DiscreteVariable[] getEvidences() {
		return evidences;
	}
	/**
	 * @param evidences the evidences to set
	 */
	public void setEvidences(DiscreteVariable[] evidences) {
		this.evidences = evidences;
		this.num_evi=evidences.length;
	}
	/**
	 * @return the name
	 */
	public DiscreteVariable getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(DiscreteVariable name) {
		this.name = name;
	}
	/**
	 * @return the num_evi
	 */
	public int getNum_evi() {
		return num_evi;
	}
	/**
	 * @param num_evi the num_evi to set
	 */
	public void setNum_evi(int num_evi) {
		this.num_evi = num_evi;
	}
	/**
	 * @return the num_value
	 */
	public int getNum_value() {
		return num_value;
	}
	/**
	 * @param num_value the num_value to set
	 */
	public void setNum_value(int num_value) {
		this.num_value = num_value;
	}
	/**
	 * @return the value
	 */
	public double[] getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(double[] value) {
		this.value = value;
		this.num_value = value.length;
	}
	/*
	 * print Probability
	 */
	
	public String print(){
		return print(System.out);
	}
	public String print(PrintStream out){
		String R = "P("+name.get_name();
		int n=evidences.length;
		if(n==0)
			R=R+")";
		if(n>0)
			R=R+"|";
		for(int i=0; i<n; i++){
			R=R+evidences[i].get_name();
			if(i==n-1)
				R=R+")";
			else
				R=R+", ";
		}
		
		//out.print(R);
		return R;
	}
	/**
	 * @return the fail
	 */
	public int getFail() {
		return fail;
	}
	/**
	 * @param fail the fail to set
	 */
	public void setFail(int fail) {
		this.fail = fail;
	}
	
}