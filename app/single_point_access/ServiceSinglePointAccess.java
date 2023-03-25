package app.single_point_access;

import app.service.PolinomService;
import app.service.implementation.PolinomServiceImpl;

public class ServiceSinglePointAccess {
    private static PolinomService polinomService;

    static{
        polinomService=new PolinomServiceImpl();
    }
    public static PolinomService getPolinomService(){
        return polinomService;
    }
}
