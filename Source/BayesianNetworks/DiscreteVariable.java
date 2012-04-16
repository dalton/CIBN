/**
 * DiscreteVariable.java
 * @author Fabio G. Cozman 
 * Copyright 1996 - 1999, Fabio G. Cozman,
 *          Carnergie Mellon University, Universidade de Sao Paulo
 * fgcozman@usp.br, http://www.cs.cmu.edu/~fgcozman/home.html
 *
 * The JavaBayes distribution is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation (either
 * version 2 of the License or, at your option, any later version), 
 * provided that this notice and the name of the author appear in all 
 * copies. Upon request to the author, some of the packages in the 
 * JavaBayes distribution can be licensed under the GNU Lesser General
 * Public License as published by the Free Software Foundation (either
 * version 2 of the License, or (at your option) any later version).
 * If you're using the software, please notify fgcozman@usp.br so
 * that you can receive updates and patches. JavaBayes is distributed
 * "as is", in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with the JavaBayes distribution. If not, write to the Free
 * Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

package BayesianNetworks;

import java.io.PrintStream;
import java.util.Vector;
import java.util.Enumeration;

/*******************************************************************/

public class DiscreteVariable {
  protected String name; // Name of the variable
  protected int index;   // Index of the variable in a collection of variables
  protected String values[]; // Values of the variable
  protected int mark;
 // protected boolean is_S;  //True means the variable is in S set.
 // protected boolean is_T;  //True means the variable is in T set.

  /**
   * Default constructor for a DiscreteVariable.
   */
  public DiscreteVariable() {
	mark = 0;  
    name = null;
    index = BayesNet.INVALID_INDEX;
    values = null;
 //   is_S = false;
 //   is_T = false;
  }

  /**
   * Simple constructor for DiscreteVariable.  
   * @param n_vb Name of the variable.
   */
  public DiscreteVariable(String n_vb) {
	mark = 0;
	name = n_vb;
    index = BayesNet.INVALID_INDEX;
    values = null;
//    is_S = false;
//    is_T = false;
  }

  /**
   * Simple constructor for DiscreteVariable. 
   * @param vb Name of the variable.
   * @param vi Index of the variable.
   * @param vl Values of the variable.
   */
  public DiscreteVariable(String vb, int vi, String vl[]) {
    name = vb;
    index = vi;
    values = vl;
    mark = 0;
//    is_S = false;
//    is_T = false;
  }

  public DiscreteVariable(String vb, int vi, String vl[],int m) {
	name = vb;
	index = vi;
	values = vl;
	mark = m;
//	is_S = s;
//	is_T = t;
  }
 
  
  /**
   * Simple constructor for DiscreteVariable.
   * @param dv DiscreteVariable that is copied into current DiscreteVariable.
   */
  public DiscreteVariable(DiscreteVariable dv) {
    name = dv.name;
    index = dv.index;
    values = dv.values;
    mark = dv.mark;
//    is_S = dv.is_S;
//    is_T = dv.is_T;
  }

  /**
   * Determine the index of a value given
   * its name; returns INVALID_INDEX if there is no index.
   */
  public int index_of_value(String value) {
	 
    for (int i=0; i<values.length; i++) {
      if (values[i].equals(value)) return(i);
    }
    return(BayesNet.INVALID_INDEX);
  }

  /**
   * Produce an array of numeric values for the  
   * values of a variable. The values are direct translation of 
   * the string values into doubles; if the translation fails 
   * for a particular value, that value is replaced by its index.
   */
  public DiscreteFunction get_numeric_values() {
    Double daux;
    DiscreteVariable dvs[] = new ProbabilityVariable[1];
    dvs[0] = this;
    double numeric_values[] = new double[values.length];
    for (int i=0; i<values.length; i++) {
        try {
            daux = Double.valueOf(values[i]);
            numeric_values[i] = daux.doubleValue();
        } catch (NumberFormatException e) {
            numeric_values[i] = (double)i;
        }
    }
    return(new DiscreteFunction(dvs, numeric_values));
  }

  /**
   * Print method for DiscreteVariable.
   */
  public void print() {
    print(System.out);
  }

  /**
   * Print method for DiscreteVariable.   
   */
  public void print(PrintStream out) {
    if (this == null) return;
    out.print("variable ");
    if (name != null)
      out.print(" \"" + name + "\" ");
//    if(is_S)
//    	out.print("\tis in S.");
//    if(is_T)
//        	out.print("\tis in T.");
    
    out.print("{");
    
    if (values != null) {
      out.println("//" + values.length + " values");
      out.print("\ttype discrete[" + values.length + "] { ");
      for (int i=0; i<values.length; i++)
	out.print(" \"" + values[i] + "\" ");
      out.println("};");
    }
    out.println("}");
  }

  /* *************************************************************** */
  /*  Methods that allow basic manipulation of non-public variables  */
  /* *************************************************************** */

  /** 
   * Get the name of the current DiscreteVariable.
   */
  public String get_name(){
    return(name);
  }
  
  /**
   * Set the name of the current DiscreteVariable.
   */
  public void set_name(String n) {
    name = n;
  }

  /**
   * Get the index of the current DiscreteVariable.
   */
  public int get_index() {
    return(index);
  }

  /**
   * Return the number of values in the current DiscreteVariable.
   */
  public int number_values() {
    return(values.length);
  }

  /**
   * Get the values of the current DiscreteVariable.
   */
  public String[] get_values() {
    return(values);
  }

  /**
   * Set the values of the current DiscreteVariable.
   */
  public void set_values(String vals[]) {
    values = vals;
  }

  /**
   * Get a value of the current DiscreteVariable.
   * @param i Position of the value in the array of values.
   */
  public String get_value(int i) {
    return(values[i]);
  }

/**
 * @return the mark
 */
public int getMark() {
	return mark;
}

/**
 * @param mark the mark to set
 */
public void setMark(int mark) {
	this.mark = mark;
}
public void setIndex(int idx){
	this.index = idx;
}
}
