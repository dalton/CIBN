package Identifiability;

import BayesianNetworks.ProbabilityVariable;

public class Q {
	boolean up;
	ProbabilityVariable[] Summation;
	Probability[] Probability;
	
	public Q(){
		up = true;
		Summation = null;
		Probability =null;
	}
	public Q(int s, int p){
		up= true;
		Summation = new ProbabilityVariable[s];
		Probability = new Probability[p];
	}
	public Q(ProbabilityVariable[] S, Probability[] P,boolean u ){
		Summation = S;
		Probability =P;
		up = u;
	}
	public Q(ProbabilityVariable[] S, Probability[] P ){
		Summation = S;
		Probability =P;
		up = true;	
	}
	public Q(Q Q1 ){
		Summation = Q1.Summation;
		Probability =Q1.Probability;
		up = Q1.up;	
	}
	public Q(Probability[] P ){
		Summation = null;
		Probability =P;
		up = true;	
	}


	public int number_Summation(){
		if (Summation==null)
			return 0;
		return Summation.length;
	}	

	public int number_Probability(){
		if (Probability==null)
			return 0;
		return Probability.length;
	}
	/**
	 * @return the probability
	 */
	public Probability[] getProbability() {
		return Probability;
	}
	/**
	 * @param probability the probability to set
	 */
	public void setProbability(Probability[] probability) {
		Probability = probability;
	}
	/**
	 * @return the summation
	 */
	public ProbabilityVariable[] getSummation() {
		return Summation;
	}
	/**
	 * @param summation the summation to set
	 */
	public void setSummation(ProbabilityVariable[] summation) {
		Summation = summation;
	}
	/**
	 * @return the up
	 */
	public boolean isUp() {
		return up;
	}
	/**
	 * @param up the up to set
	 */
	public void setUp(boolean up) {
		this.up = up;
	}
	

}
