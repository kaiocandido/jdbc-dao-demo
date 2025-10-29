package model.dao;

import db.DB;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
    public static SallerDao createSallerDao(){
        return new SellerDaoJDBC(DB.getConnection());
    }
}
