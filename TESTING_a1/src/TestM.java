import org.junit.*;
import static org.junit.Assert.*;

import java.io.PrintStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;

public class TestM {

    OutputStream bos;
    M m;
    String a;
    String b;
    String zero;
    PrintStream ps;
    PrintStream originalps;


    public TestM() {
        bos = new ByteArrayOutputStream();
        ps = new PrintStream(bos);
        a = "a\n";
        b = "b\n";
        zero = "zero\n";
    }


    @Before
    public void setUp(){
        m = new M();
        originalps = System.out;
        System.setOut(ps);

    }

    @After
    public void tearDown(){
        System.setOut(originalps);
        m = null;
    }

    public void test(String s, int i, String expectedOutput){
        m.m(s,i);
        Assert.assertEquals(expectedOutput,bos.toString());

    }

    /**
     * The next three test case satisfies node coverage
     * but its missing edge [7,8]
     * so it doesn't satisfies edge coverage
     */
    /**
     * nodes: {1,2,3,4,8,10,11}
     * edges: {[1,2],[2,3],[3,4],[4,8],[8,10],[10,11]}
     * edge-pair: {[1,2,3],[2,3,4],[3,4,8],[4,8,10],[8,10,11]}
     * prime path: {[1,2,3,4,8,10,11]}
     */
    @Test
    public void NC_len0(){
        String s = "";
        test(s,0,zero);
    }

    /**
     * node: {1,3,5,8,9,11}
     * edges: {[1,3],[3,5],[5,8],[8,9],[9,11]}
     * edge-pair: {[1,3,5],[3,5,8],[5,8,9],[8,9,11]}
     * prime-path: {[1,3,5,8,9,11]}
     */
    @Test
    public void NC_len1(){
        String s = "t";
        test(s,1,a);
    }

    /**
     * nodes: {1,3,6,7,8,9,11}
     * edges: {[1,3],[3,6],[6,7],[7,8],[8,9],[9,11]}
     * edge-pairs: {[1,3,6],[3,6,7],[6,7,8],[7,8,9],[8,9,11]}
     * prime-path: {[1,3,6,7,8,9,11]}
     */
    @Test
    public void NC_len2(){
        String s = "aa";
        test(s,2,b);
    }

    /**
     * edges: {[1,3],[3,7],[7,8],[8,9],[9,11]}
     * this test case has the missing edge [7,8] from the previous 3 test cases
     * this test plus the previous 3 test case satisfies edge coverage but not edge pair coverage
     * edge pair coverage is infeasible
     * it will miss edge pair like [case 0, arg.length()>0, o.m()]
     * edges-pair: {[1,3,7],[3,7,8],[7,8,9],[8,9,11]}
     * prime-path: {[1,3,7,8,9,11]}
     */
    @Test
    public void EC_len3(){
        String s = "aaa";
        test(s,3,b);
    }

    /* add your test code here */

    /**
     * edge-pairs: {[1,2,3],[2,3,7],[3,7,8],[7,8,10],[8,9,11]}
     * prime-pate: {[1,2,3,7,8,9,11]}
     */
    @Test
    public void EPC_1(){
        String s = "aabb";
        test(s,0,b);
    }

    /**
     * edge-pairs: {[1,2,3],[2,3,6],[3,6,7],[6,7,8],[7,8,10],[8,9,11]}
     * prime-path: {[1,2,3,6,7,8,9,11]}
     */
    @Test
    public void EPC_2(){
        String s = "cc";
        test(s,0,b);
    }


    /**
     * edge-pair: {[1,2,3],[2,3,5],[3,5,8],[5,8,9],[8,9,11]}
     * prime-path: {[1,2,3,5,8,9,11]}
     */
    @Test
    public void EPC_3(){
        String s = "f";
        test(s,0,a);
    }

    /**
     * edge-pair: {[1,3,4],[3,4,8],[4,8,10],[8,10,11]}
     * prime-path: {{1,3,4,8,10,11}}
     */
    @Test
    public void EPC_4(){
        String s = "";
        test(s,1,zero);
    }
    /**
     * The previous test cases covers all feasible edge pairs
     * however at the same time, it also covers all the prime paths
     * So the above test cases covers prime paths
     * But it doesn't achieve edge-pair coverage but not prime path coverage;
     */

}

class M {
    public static void main(String [] argv){
        M obj = new M();
        if (argv.length > 0)
            obj.m(argv[0], argv.length);
    }

    public void m(String arg, int i) {
        int q = 1;
        A o = null;
        Impossible nothing = new Impossible();
        if (i == 0)
            q = 4;
        q++;
        switch (arg.length()) {
            case 0: q /= 2; break;
            case 1: o = new A(); new B(); q = 25; break;
            case 2: o = new A(); q = q * 100;
            default: o = new B(); break;
        }
        if (arg.length() > 0) {
            o.m();
        } else {
            System.out.println("zero");
        }
        nothing.happened();
    }
}

class A {
    public void m() {
        System.out.println("a");
    }
}

class B extends A {
    public void m() {
        System.out.println("b");
    }
}

class Impossible{
    public void happened() {
        // "2b||!2b?", whatever the answer nothing happens here
    }
}
