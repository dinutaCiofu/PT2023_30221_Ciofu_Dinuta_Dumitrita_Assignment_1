package app.service;
import app.model.Polinom;

import java.util.Map;

public interface PolinomService {
   default void addMonom(Polinom p, Integer exponent, Double coeficient){
               p.getPolinom().put(exponent,coeficient);

   }

    Polinom addOp(Polinom p1,Polinom p2);
    Polinom substractOp(Polinom p1, Polinom p2);
    Polinom multiplyOp(Polinom p1, Polinom p2);
    Polinom[] divisionOp(Polinom p1, Polinom p2);
    Polinom derive(Polinom p);
    Polinom integrate(Polinom p);
}
