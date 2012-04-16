package Identifiability;

import java.io.PrintStream;

import BayesianNetworks.ProbabilityVariable;
/*
 * QF1/QF2
 */
public class QFraction {
	Q[] Up;
	Q[] Down;
	ProbabilityVariable[] Sum;
	QFraction D ;
	QFraction U ;
	ProbabilityVariable[] QF_Sum;
	
	public QFraction(){

	}
	public QFraction(QFraction QF){
		Up = QF.Up;
		Down = QF.Down;
		Sum =  QF.Sum;
		D=null;
		QF_Sum = null;
		U = new QFraction();
		U = QF;
	}
	public QFraction(Q[] UP){
		Up=UP;
		Down = null;
		Sum = null;
		D=null;
		QF_Sum = null;
		U = new QFraction();
		U.Up=Up;
	}
	public QFraction(Q[] UP, Q[] D){
		Up=UP;
		Down = D;
		Sum = null;
		D=null;
		QF_Sum = null;
		U =new QFraction();
		U.Up = Up;
		U.Down = Down;
	}
	public QFraction(Q[] UP, Q[] D, ProbabilityVariable[] S){
		Up=UP;
		Down = D;
		Sum = S;
		D=null;
		QF_Sum = null;
		U =new QFraction();
		U.Up = UP;
		U.Down = D;
		U.Sum = S;
	}
	public QFraction(QFraction QF, QFraction QD){
		Up = QF.Up;
		Down = QF.Down;
		Sum =  QF.Sum;
		D=QD;
		QF_Sum = null;
		U=QF;
	}
	public QFraction(QFraction QF, QFraction QD, ProbabilityVariable[] QFsum){
		Up = QF.Up;
		Down = QF.Down;
		Sum =  QF.Sum;
		D=QD;
		QF_Sum = QFsum;
		U=QF;
	}
	/**
	 * @return the down
	 */
	public Q[] getDown() {
		return Down;
	}

	/**
	 * @param down the down to set
	 */
	public void setDown(Q[] down) {
		Down = down;
	}

	/**
	 * @return the up
	 */
	public Q[] getUp() {
		return Up;
	}

	/**
	 * @param up the up to set
	 */
	public void setUp(Q[] up) {
		Up = up;
	}
	/**
	 * @return the sum
	 */
	public ProbabilityVariable[] getSum() {
		return Sum;
	}
	/**
	 * @param sum the sum to set
	 */
	public void setSum(ProbabilityVariable[] sum) {
		Sum = sum;
	}
	public int num_up(){
		if(Up==null)
			return 0;
		else
			return Up.length;
	}
	public int num_down(){
		if(Down==null)
			return 0;
		else
			return Down.length;
	}
	public int num_sum(){
		if(Sum==null)
			return 0;
		else
			return Sum.length;
	}
	public int num_QFsum(){
		if(QF_Sum==null)
			return 0;
		else
			return QF_Sum.length;
	}
	/**
	 * @return the d
	 */
	public QFraction getD() {
		return D;
	}
	/**
	 * @param d the d to set
	 */
	public void setD(QFraction d) {
		D = d;
	}
	/**
	 * @return the U
	 */
	public QFraction getU() {
		return U;
	}
	/**
	 * @param u the U to set
	 */
	public void setU(QFraction u) {
		U = u;
	}
/*	public boolean isUp(Q[] Q){
		if (Up==null && Q==null)
			return true;
		else if (Up==null){
			if (Q.length==0)
				return true;
			else
				return false;
		}
		else if(Q== null){
			if (Up.length==0)
				return true;
			else
				return false;
		}
		else{
			for (int i=0; i<Up.length; i++){
				
			}
		}
	}
	*/
	/**
	 * @return the qF_Sum
	 */
	public ProbabilityVariable[] getQF_Sum() {
		return QF_Sum;
	}
	/**
	 * @param sum the qF_Sum to set
	 */
	public void setQF_Sum(ProbabilityVariable[] sum) {
		QF_Sum = sum;
	}
	
		
}
