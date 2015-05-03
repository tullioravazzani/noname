package Main.java.it.objectway.stage.dcapozzi.employer;

import Main.java.it.objectway.stage.dcapozzi.employer.impl.EmployeeManagerMap;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Do you want to use the database?(y/n)");

        EmployeeManager manager = new EmployeeManagerMap();
        //EmployeeManager manager = new EmployeeManagerDB();

/*
        manager.add("mario", "rossi", "via test", "0000");
        manager.add("mario", "neri", "via testneri", "0000");
        manager.add("mario", "bianchi", "via testbianchi", "0000");
        manager.add("mario", "gialli", "via testgialli", "0000");
        manager.add("mario", "verdi", "via testverdi", "0000");
        manager.add("mario", "rossi", "via testrossi", "0000");
*/

        System.out.println(manager);
    }
}
