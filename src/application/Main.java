package application;

import model.entities.Department;
import model.entities.Seller;
import java.time.format.DateTimeFormatter;
import java.util.Date;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Department dp = new Department(1, "DEV");

        //DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Seller sl = new Seller(1, "kaio", "kaio.candf@gmail.com", new Date(), 7500.00, dp);

        System.out.println(sl);

    }
}