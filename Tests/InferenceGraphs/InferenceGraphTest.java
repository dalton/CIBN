/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InferenceGraphs;

import BayesianNetworks.BayesNet;
import BayesianNetworks.ProbabilityFunction;
import BayesianNetworks.ProbabilityVariable;
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
public class InferenceGraphTest {
    InferenceGraph instance;
    String fixture_file;
    
    public InferenceGraphTest() {
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

    /**
     * Test of get_name method, of class InferenceGraph.
     */
    @Test
    public void testGet_name() {
        System.out.println("get_name");
        String expResult = "Dog_Problem";
        String result = instance.get_name();
        assertEquals(expResult, result);
    }

    /**
     * Test of set_name method, of class InferenceGraph.
     */
    @Test
    public void testSet_name() {
        System.out.println("set_name");
        String n = "Cat Problem";
        InferenceGraph instance = new InferenceGraph();
        String expResult = n;
        instance.set_name(n);
        String result = instance.get_name();
        assertEquals(expResult, result);
    }

    /**
     * Test of get_network_properties method, of class InferenceGraph.
     */
    @Test
    public void testGet_network_properties() {
        System.out.println("get_network_properties");
        Vector expResult = new Vector();
        Vector result = instance.get_network_properties();
        assertEquals(expResult, result);
    }

    /**
     * Test of validate_value method, of class InferenceGraph.
     */
    @Test
    public void testValidate_value() {
        System.out.println("validate_value");
        String value = "This Is A Name";
        String expResult = "This_Is_A_Name";
        String result = instance.validate_value(value);
        assertEquals(expResult, result);
    }


    /**
     * Test of get_nodes method, of class InferenceGraph.
     */
    @Test
    public void testGet_nodes() {
        System.out.println("get_nodes");
        Vector result = instance.get_nodes();
        assertEquals(5, result.size());
    }

    /**
     * Test of number_nodes method, of class InferenceGraph.
     */
    @Test
    public void testNumber_nodes() {
        System.out.println("number_nodes");
        int expResult = 5;
        int result = instance.number_nodes();
        assertEquals(expResult, result);
    }

    /**
     * Test of create_node method, of class InferenceGraph.
     */
    @Test
    public void testCreate_node() {
        System.out.println("create_node");
        int x = 0;
        int y = 0;
        instance.create_node(x, y);
        assertEquals(6, instance.number_nodes());
    }

    @Test
    public void testSTBayesNet(){
        System.out.println("ST Bayes Net");
        BayesNet bn = new STBayesNet();
        bn.add_property("adam is cool");
        bn.set_name("Test ST Bayes Net");
        ProbabilityFunction[] pfs = new ProbabilityFunction[0];
        ProbabilityVariable[] pvs = new ProbabilityVariable[0];
        bn.set_probability_functions(pfs);
        bn.set_probability_variables(pvs);
        instance = new InferenceGraph(bn);
    }
    
//    @Test
//    public void testSTDogProblem() throws FileNotFoundException, IFException{
//        System.out.println("ST Dog Problem");
//        STBayesNet bn = new STBayesNet(new java.io.DataInputStream(new java.io.FileInputStream(fixture_file)));
//        instance = new InferenceGraph(bn);
//        ProbabilityVariable[] pvs = bn.get_probability_variables();
//        ProbabilityVariable pv = ((ProbabilityVariable)pvs[0]);
//        pv.set_isT_value(pv.get_value(0));
//        System.out.println(pv.get_name());
//        pv.set_isT_value(pv.get_value(0));
//        pv = ((ProbabilityVariable)pvs[1]);
//        System.out.println(pv.get_name());
//        pv.set_isS_value(pv.get_value(0));
//        
//        System.out.println("====================================");
//        
//        instance.print_identifiability_analysis(System.out);
//        
//    }
    
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
    
    
}
