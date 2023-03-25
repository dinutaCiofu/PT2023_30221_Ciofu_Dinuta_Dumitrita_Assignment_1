import app.controller.CalculatorController;
import app.view.Calculator;

public class Main {
    public static void main(String[] args) {

        try{
            CalculatorController calculatorController = new CalculatorController();
            calculatorController.startLogic();
        }catch(Exception ex){
            System.out.println("Error at starting the application...");
            ex.printStackTrace();
        }


    }
}