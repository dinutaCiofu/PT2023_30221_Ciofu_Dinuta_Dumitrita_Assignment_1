package app.controller;


import app.model.Polinom;
import app.service.PolinomService;
import app.single_point_access.GUIFrameSinglePointAccess;
import app.single_point_access.ServiceSinglePointAccess;
import app.view.Calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Objects;
import java.util.SimpleTimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorController {
    private Calculator calculatorPanel = new Calculator();
    private PolinomService polinomService = ServiceSinglePointAccess.getPolinomService();

    private Polinom toPolynomial(String s){
        if(s.isEmpty()){
            throw new ArrayIndexOutOfBoundsException("Invalid argument");
        }
        Polinom polinom = new Polinom();

        Integer exponent=0;
        Double coeficient=0.0;

        Pattern pattern = Pattern.compile("([+-]?[^-+]+)");
        Matcher matcher = pattern.matcher(s);
        while(matcher.find()){
            System.out.println("From toPolynomial: "+matcher.group(1));
            if((matcher.group(1).contains("x") && matcher.group(1).length()==1) || (matcher.group(1).startsWith("+") && matcher.group(1).contains("x") && matcher.group(1).length()==2)){ // the monomyal looks like: x
                exponent = 1;
                coeficient = 1.0;
            }else if(matcher.group(1).startsWith("-") && matcher.group(1).contains("x") && matcher.group(1).length()==2){ // -x
                exponent = 1;
                coeficient=-1.0;
            }
            else if (matcher.group(1).contains("x")){ // the monomyal looks like: (+-)x^exp
                String[] monom = matcher.group(1).split("x");
                exponent =0;
                coeficient=0.0;

                for(String i : monom){
                    if(i.startsWith("^")){
                        exponent = Integer.valueOf(String.valueOf(i.substring(1)));
                    }
                    else if(i.isEmpty()){
                        coeficient=1.0;}
                    else{
                        coeficient = Double.valueOf(Integer.valueOf(i));
                    }
                }
            }else{ // does not contains x
                coeficient = Double.valueOf(matcher.group(1));
                exponent = 0;
            }
            polinomService.addMonom(polinom,exponent,coeficient);
        }
        return polinom;
    }


    private String polynomialToString(Polinom p){
        String polinom = "";
        if(p.getPolinom().isEmpty()){
            polinom="0";
        }else{
            for(Map.Entry<Integer, Double> monom: p.getPolinom().entrySet()){
                if(monom.getValue()==0){
                    polinom+="";
                }else if(monom.getKey()==0){
                    polinom=polinom+(monom.getValue()>0?"+"+monom.getValue(): monom.getValue());
                }else{
                    polinom = polinom+(monom.getValue()>0?"+"+monom.getValue(): monom.getValue())+"x^"+monom.getKey();
                }
            }
        }
        System.out.println("from polynomialToString:"+polinom);
        return polinom;
    }
    private Polinom[] readPolynomials(){
        Polinom[] polynomials = new Polinom[2];
        String firstPolynimial = calculatorPanel.getFirstPolynomialField().getText();
        String secondPolynomial = calculatorPanel.getSecondPolynomialField().getText();

        polynomials[0] = toPolynomial(firstPolynimial);
        polynomials[1] = toPolynomial(secondPolynomial);

        return polynomials;
    }

    public void startLogic(){
        GUIFrameSinglePointAccess.changePanel(calculatorPanel.getPolynomialCalculatorPanel(), "Polynimial Calculator");

        // action listeners

        // add op
        calculatorPanel.getAddBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Polinom[] polynomials;
                    polynomials=readPolynomials();
                    Polinom result;
                    result = polinomService.addOp(polynomials[0],polynomials[1]);
                    System.out.println("The result is: ");
                    for(Map.Entry<Integer,Double> monom : result.getPolinom().entrySet()){
                        System.out.println("exp: "+monom.getKey()+"  coef: "+monom.getValue());
                    }
                    String resultToString = polynomialToString(result);
                    calculatorPanel.getShowResultsPane().setText(resultToString);
                }catch (ArrayIndexOutOfBoundsException ex){
                    GUIFrameSinglePointAccess.showDialogMessage(ex.getMessage());
                }

            }
        });

        calculatorPanel.getSubstractBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Polinom[] polynomials;
                    polynomials=readPolynomials();
                    Polinom result;
                    result = polinomService.substractOp(polynomials[0],polynomials[1]);
                    for(Map.Entry<Integer,Double> monom:result.getPolinom().entrySet()){
                        System.out.println("exp: "+monom.getKey()+"  coef: "+monom.getValue());
                    }
                    String resultToString = polynomialToString(result);
                    calculatorPanel.getShowResultsPane().setText(resultToString);
                }catch (ArrayIndexOutOfBoundsException ex){
                    GUIFrameSinglePointAccess.showDialogMessage(ex.getMessage());
                }

            }
        });

        calculatorPanel.getMultiplyBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Polinom[] polynomials;
                    polynomials=readPolynomials();
                    Polinom result;
                    result = polinomService.multiplyOp(polynomials[0],polynomials[1]);
                    for(Map.Entry<Integer,Double> monom:result.getPolinom().entrySet()){
                        System.out.println("exp: "+monom.getKey()+"  coef: "+monom.getValue());
                    }
                    String resultToString = polynomialToString(result);
                    calculatorPanel.getShowResultsPane().setText(resultToString);
                }catch (ArrayIndexOutOfBoundsException ex){
                    GUIFrameSinglePointAccess.showDialogMessage(ex.getMessage());
                }

            }
        });

        calculatorPanel.getDivideBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    Polinom[] polynomials;
                    polynomials=readPolynomials();
                    Polinom[] result = new Polinom[2];
                    result = polinomService.divisionOp(polynomials[0],polynomials[1]);
                    String result1ToString = polynomialToString(result[0]);
                    String result2ToString = polynomialToString(result[1]);
                    String resultToString=result1ToString+" \r"+result2ToString;
                    calculatorPanel.getShowResultsPane().setText(resultToString);
                }catch (ArithmeticException ex){
                    GUIFrameSinglePointAccess.showDialogMessage(ex.getMessage());
                }catch (ArrayIndexOutOfBoundsException ex){
                    GUIFrameSinglePointAccess.showDialogMessage(ex.getMessage());
                }
            }
        });


        calculatorPanel.getDeriveBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Polinom[] polynomials;
                    polynomials=readPolynomials();
                    Polinom result1,result2;
                    result1 = polinomService.derive(polynomials[0]);
                    result2 = polinomService.derive(polynomials[1]);
                    String result1ToString = polynomialToString(result1);
                    String result2ToString = polynomialToString(result2);
                    String result = result1ToString+" \r"+result2ToString;
                    calculatorPanel.getShowResultsPane().setText(result);
                }catch (ArrayIndexOutOfBoundsException ex){
                    GUIFrameSinglePointAccess.showDialogMessage(ex.getMessage());
                }

            }
        });

        calculatorPanel.getIntegrateBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Polinom[] polynomials;
                    polynomials=readPolynomials();
                    Polinom result1,result2;
                    result1 = polinomService.integrate(polynomials[0]);
                    result2 = polinomService.integrate(polynomials[1]);
                    String result1ToString = polynomialToString(result1);
                    String result2ToString = polynomialToString(result2);
                    String result = result1ToString+" \r"+result2ToString;
                    calculatorPanel.getShowResultsPane().setText(result);
                }catch (ArrayIndexOutOfBoundsException ex){
                    GUIFrameSinglePointAccess.showDialogMessage(ex.getMessage());
                }
            }
        });
        calculatorPanel.getClearBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               calculatorPanel.getFirstPolynomialField().setText("");
               calculatorPanel.getSecondPolynomialField().setText("");
               calculatorPanel.getShowResultsPane().setText("");
            }
        });
    }
}
