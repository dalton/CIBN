/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InferenceGraphs;

import BayesianNetworks.BayesNet;
import BayesianNetworks.ProbabilityFunction;
import BayesianNetworks.ProbabilityVariable;
import Identifiability.AlgorithmComputing;
import Identifiability.STBayesNet;
import InterchangeFormat.IFException;
import QuasiBayesianNetworks.QuasiBayesNet;
import java.awt.Point;
import java.io.*;
import java.util.Enumeration;
import java.util.Vector;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author dalton
 */
public class IdentifyCausalEffectTest {
    InferenceGraph instance;
    String fixture_file;
    
    public IdentifyCausalEffectTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() throws IOException, IFException {
        fixture_file = "/Users/dalton/code/CIBN/CIBN/Tests/fixtures/dog-problem.bif";
        
        instance = new InferenceGraph(fixture_file);
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void testAddLightOnToS() throws FileNotFoundException, IFException{
        System.out.println("ST Dog Problem");
        STBayesNet bn = new STBayesNet(new java.io.DataInputStream(new java.io.FileInputStream(fixture_file)));
        instance = new InferenceGraph(bn);
        ProbabilityVariable[] pvs = bn.get_probability_variables();
	int idx = bn.index_of_variable("light_on");
	System.out.println("s_index = "+idx);
        ProbabilityVariable pv = ((ProbabilityVariable)pvs[idx]);
	System.out.println("pv = "+pv.get_value(0));
	assertEquals(0, bn.number_s());
        pv.set_isS_value("true");
	assertEquals(1, bn.number_s());
	assertEquals(pv.get_name(), bn.get_probability_variables_s()[0].get_name());
        instance.print_identifiability_analysis(System.out);
        
	assertEquals(0, bn.number_t());
    }
    
    @Test
    public void testGetD() throws FileNotFoundException, IFException{
        System.out.println("ST Dog Problem");
        STBayesNet bn = new STBayesNet(new java.io.DataInputStream(new java.io.FileInputStream(fixture_file)));
        instance = new InferenceGraph(bn);
        ProbabilityVariable[] pvs = bn.get_probability_variables();
	int idx = bn.index_of_variable("light_on");
	System.out.println("s_index = "+idx);
        ProbabilityVariable pv = ((ProbabilityVariable)pvs[idx]);
	System.out.println("pv = "+pv.get_value(0));
        pv.set_isS_value("true");
	
	AlgorithmComputing ac = new AlgorithmComputing(bn);
	assertEquals(1, ac.number_d());
	assertEquals(pv.get_name(), ((ProbabilityVariable)ac.get_probability_variables_d().firstElement()).get_name());
    }
    
    
}
