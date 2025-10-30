package application;

import model.dao.DaoFactory;
import model.dao.SallerDao;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;
import java.time.format.DateTimeFormatter;
import java.util.Date;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SellerDaoJDBC sl = (SellerDaoJDBC) DaoFactory.createSallerDao();

        System.out.println("===TEST 1: SELLER FINDBYID===");
        Seller seller = sl.findById(3);
        System.out.println(seller);
    }
}