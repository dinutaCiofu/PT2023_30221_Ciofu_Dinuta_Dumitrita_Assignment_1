package app.service.implementation;

import app.model.Polinom;
import app.service.PolinomService;
import app.single_point_access.GUIFrameSinglePointAccess;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class PolinomServiceImpl implements PolinomService {

    @Override
    public Polinom addOp(Polinom p1, Polinom p2) {
        Polinom result=new Polinom();

        // init the result with the first polynomial
        for(Integer exponent:p1.getPolinom().keySet()){
            Double coeficient = p1.getPolinom().get(exponent);
           addMonom(result,exponent,coeficient);
        }

        // add the second polynomial to the result
        for(Integer exponent : p2.getPolinom().keySet()){
            Double coeficient = p2.getPolinom().get(exponent);
            if(result.getPolinom().containsKey(exponent)){
                coeficient+= result.getPolinom().get(exponent);
            }

            addMonom(result,exponent,coeficient);
        }
        return result;
    }

    @Override
    public Polinom substractOp(Polinom p1, Polinom p2) {
        Polinom result=new Polinom();
        Double coeficient1 = 0.0;
        Double coeficient2;

        // add to the result these monomials from p1 which are not in p2
        for(Integer exponent : p1.getPolinom().keySet()){
            if(!p2.getPolinom().containsKey(exponent)){
                addMonom(result,exponent, p1.getPolinom().get(exponent));
            }
        }

        for(Integer exponent : p2.getPolinom().keySet()){
             coeficient2 = p2.getPolinom().get(exponent);

            if(p1.getPolinom().containsKey(exponent)){
                coeficient1 = p1.getPolinom().get(exponent);
                coeficient1-=coeficient2;
                addMonom(result,exponent, coeficient1);
            }else{
                addMonom(result,exponent,coeficient2*(-1.0));
            }
        }
        return result;
    }


    @Override
    public Polinom multiplyOp(Polinom p1, Polinom p2) {
        Polinom result = new Polinom();

        for(Map.Entry<Integer, Double> monom1: p1.getPolinom().entrySet()){
            for(Map.Entry<Integer, Double> monom2 : p2.getPolinom().entrySet()){
                Integer exponent = monom1.getKey() + monom2.getKey();
                Double coeficient = monom1.getValue()*monom2.getValue();

                if(result.getPolinom().containsKey(exponent))
                    coeficient+=result.getPolinom().get(exponent);

                addMonom(result, exponent, coeficient);
            }
        }
        return result;
    }


    @Override
    public Polinom[] divisionOp(Polinom p1, Polinom p2) {
        System.out.println("A intrat in metoda");
        Polinom[] result = new Polinom[2];

        if(p2.getPolinom().get(p2.getDegree())==0 || p2.getPolinom().isEmpty()){
            throw new ArithmeticException("Division by zero!");
        }

        Polinom q = new Polinom();
        Polinom r = new Polinom();
        r.setPolinom(p1.getPolinom());

        while(!r.getPolinom().isEmpty() && r.getDegree() >= p2.getDegree()){
            Integer exponentT= r.getDegree()-p2.getDegree();
            Double coeficientT = r.getPolinom().get(r.getDegree())/p2.getPolinom().get(p2.getDegree());

            if(coeficientT == 0){
                r.getPolinom().remove(r.getDegree());
                continue;
            }
            System.out.println(exponentT +" "+coeficientT);
            Polinom t = new Polinom();
            addMonom(t,exponentT,coeficientT);
            q=addOp(q,t);
            r=substractOp(r,multiplyOp(t,p2));

        }
        result[0]=q;
        result[1]=r;
        return result;
    }



    @Override
    public Polinom derive(Polinom p) {
        Polinom result=new Polinom();
        for(Map.Entry<Integer, Double> monom: p.getPolinom().entrySet()){
            Integer exponent = monom.getKey();
            Double coeficient = monom.getValue();

            if(exponent > 0){
                Integer expDerivat = exponent -1;
                Double coefDerivat = coeficient * exponent;
                addMonom(result,expDerivat,coefDerivat);
            }
        }
        return result;
    }

    @Override
    public Polinom integrate(Polinom p) {
        Polinom result = new Polinom();
        for(Map.Entry<Integer, Double> monom : p.getPolinom().entrySet()){
            System.out.println("From PolinomServiceImpl.integrate: "+monom.getKey()+"  "+monom.getValue());
            Integer exponent = monom.getKey();
            Double coeficient = monom.getValue();

            Integer expIntegrat = exponent+1;
            Double coefIntegrat = coeficient / expIntegrat;
            addMonom(result, expIntegrat,coefIntegrat);
        }
        return result;
    }

}
