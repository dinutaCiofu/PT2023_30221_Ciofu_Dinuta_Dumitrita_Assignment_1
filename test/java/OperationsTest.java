import app.model.Polinom;
import app.service.PolinomService;
import app.service.implementation.PolinomServiceImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Map;


public class OperationsTest {
    private PolinomService polinomService;

    @Before
    public void setUp(){
        polinomService=new PolinomServiceImpl();
    }

    @Test
    public void addOpTest(){
        Polinom p1 = new Polinom();
        Polinom p2 = new Polinom();

        Map<Integer, Double> polinom1 = p1.getPolinom();
        polinom1.put(2,3.0);
        polinom1.put(1,-1.0);
        polinom1.put(0,2.0);
        p1.setPolinom(polinom1);

        Map<Integer,Double> polinom2 = p2.getPolinom();
        polinom2.put(2,1.0);
        polinom2.put(1,2.0);
        polinom2.put(0,-1.0);
        p2.setPolinom(polinom2);

        Polinom result = polinomService.addOp(p1,p2);
        assertEquals(4.0,result.getPolinom().get(2), 0.0001);
        assertEquals(1.0, result.getPolinom().get(1), 0.0001);
        assertEquals(1.0, result.getPolinom().get(0), 0.0001);

    }

    @Test
    public void testSubstractOp(){
        Polinom p1 = new Polinom();
        Polinom p2 = new Polinom();

        Map<Integer, Double> polinom1 = p1.getPolinom();
        polinom1.put(2,3.0);
        polinom1.put(1,-1.0);
        polinom1.put(0,2.0);
        p1.setPolinom(polinom1);

        Map<Integer,Double> polinom2 = p2.getPolinom();
        polinom2.put(2,1.0);
        polinom2.put(1,2.0);
        polinom2.put(0,-1.0);
        p2.setPolinom(polinom2);

        Polinom result = polinomService.substractOp(p1,p2);
        assertEquals(2.0,result.getPolinom().get(2), 0.0001);
        assertEquals(-3.0, result.getPolinom().get(1), 0.0001);
        assertEquals(3.0, result.getPolinom().get(0), 0.0001);
    }

    @Test
    public void testMultiplyOp(){
        Polinom p1 = new Polinom();
        Polinom p2 = new Polinom();

        Map<Integer, Double> polinom1 = p1.getPolinom();
        polinom1.put(2,3.0);
        polinom1.put(1,-1.0);
        polinom1.put(0,2.0);
        p1.setPolinom(polinom1);

        Map<Integer,Double> polinom2 = p2.getPolinom();
        polinom2.put(2,1.0);
        polinom2.put(1,2.0);
        polinom2.put(0,-1.0);
        p2.setPolinom(polinom2);

        Polinom result = polinomService.multiplyOp(p1,p2);
        assertEquals(3.0,result.getPolinom().get(4), 0.0001);
        assertEquals(5.0, result.getPolinom().get(3), 0.0001);
        assertEquals(-3.0, result.getPolinom().get(2), 0.0001);
        assertEquals(5.0, result.getPolinom().get(1), 0.0001);
        assertEquals(-2.0, result.getPolinom().get(0), 0.0001);
    }

    @Test
    public void testDivisionOp(){
        Polinom p1 = new Polinom();
        Polinom p2 = new Polinom();

        Map<Integer, Double> polinom1 = p1.getPolinom();
        polinom1.put(3,1.0);
        polinom1.put(2,-2.0);
        polinom1.put(1,0.0);
        polinom1.put(0,-4.0);
        p1.setPolinom(polinom1);

        Map<Integer,Double> polinom2 = p2.getPolinom();
        polinom2.put(1,1.0);
        polinom2.put(0,-3.0);
        p2.setPolinom(polinom2);

        Polinom[] result = polinomService.divisionOp(p1,p2);
        assertEquals(1.0,result[0].getPolinom().get(2),0.0001);
        assertEquals(1.0,result[0].getPolinom().get(1),0.0001);
        assertEquals(3.0,result[0].getPolinom().get(0),0.0001);

        assertEquals(5.0,result[1].getPolinom().get(0),0.0001);

    }

    @Test
    public void testDerive(){
        Polinom p1 = new Polinom();

        Map<Integer, Double> polinom1 = p1.getPolinom();
        polinom1.put(3,1.0);
        polinom1.put(2,-2.0);
        polinom1.put(1,0.0);
        polinom1.put(0,-4.0);
        p1.setPolinom(polinom1);
        Polinom result = polinomService.derive(p1);

        assertEquals(3.0,result.getPolinom().get(2),0.0001);
        assertEquals(-4.0,result.getPolinom().get(1),0.0001);
    }

    @Test
    public void testIntegrate(){
        Polinom p1 = new Polinom();

        Map<Integer, Double> polinom1 = p1.getPolinom();
        polinom1.put(2,3.0);
        polinom1.put(1,2.0);
        polinom1.put(0,1.0);
        p1.setPolinom(polinom1);

        Polinom result = polinomService.integrate(p1);

        assertEquals(1.0,result.getPolinom().get(3), 0.0001);
        assertEquals(1.0,result.getPolinom().get(2), 0.0001);
        assertEquals(1.0,result.getPolinom().get(1), 0.0001);
    }
}
