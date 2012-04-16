/**
 * 
 */
package Identifiability;

import java.io.PrintStream;
import java.util.Vector;

import BayesianNetworks.DiscreteVariable;
import BayesianNetworks.ProbabilityFunction;
import BayesianNetworks.ProbabilityVariable;

/**
 * @author Le
 *
 */
public class JointProbability {
	 protected Vector V ;
	 protected String joint_probability;
	 ProbabilityVariable[] pvs;
	 ProbabilityFunction[] pfs;
	 int num_variables;
	 
	 /**
	   * Default constructor for a JointProbability.
	   */
	 public JointProbability() {
		 V=new Vector();
		 joint_probability="";
		 pvs = null;
		 pfs = null;
		 num_variables = 0;
	 }
	 /**
	   * Simple constructor for JointProbability.   
	   * @param n_vb Number of variables in the function.
	   * @param n_vl Number of values in the function.
	   */
	 public JointProbability(Vector v, String joint, ProbabilityVariable[] pv, ProbabilityFunction[] pf) {
		V=v;
		joint_probability = joint;
		pvs = pv;
		pfs =pf;
		num_variables = pv.length;
	 }
	/**
	 * @return the joint_probability
	 */
	public String getJoint_probability() {
		return joint_probability;
	}
	/**
	 * @param joint_probability the joint_probability to set
	 */
	public void setJoint_probability(String joint_probability) {
		this.joint_probability = joint_probability;
	}
	/**
	 * @return the pfs
	 */
	public ProbabilityFunction[] getPfs() {
		return pfs;
	}
	/**
	 * @param pfs the pfs to set
	 */
	public void setPfs(ProbabilityFunction[] pfs) {
		this.pfs = pfs;
	}
	/**
	 * @return the pvs
	 */
	public ProbabilityVariable[] getPvs() {
		return pvs;
	}
	/**
	 * @param pvs the pvs to set
	 */
	public void setPvs(ProbabilityVariable[] pvs) {
		this.pvs = pvs;
	}
	/**
	 * @return the v
	 */
	public Vector getV() {
		return V;
	}
	/**
	 * @param v the v to set
	 */
	public void setV(Vector v) {
		V = v;
	}
	/**
	 * @return the num_variables
	 */
	public int getNum_variables() {
		return num_variables;
	}
	/**
	 * @param num_variables the num_variables to set
	 */
	public void setNum_variables(int num_variables) {
		this.num_variables = num_variables;
	}  

}
