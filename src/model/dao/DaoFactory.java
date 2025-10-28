package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
    public static SallerDao createSallerDao(){
        return new SellerDaoJDBC();
    }
}
