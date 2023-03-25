package app.model;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Polinom {
    private Map<Integer, Double> polinom;

    public Polinom(){
        polinom = new TreeMap<>(Collections.reverseOrder());
    }

    public Map<Integer, Double> getPolinom() {
        return polinom;
    }

    public Integer getDegree(){
        return (Integer) polinom.keySet().toArray()[0];
    } //returns the leading term

    public void setPolinom(Map<Integer, Double> polinom) {
        this.polinom = polinom;
    }
}
