package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SallerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SallerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;

        try{
            st = conn.prepareStatement("INSERT INTO seller "
                                            + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                                            + "VALUES"
                                            + "(?, ?, ?, ?, ?)",
                                            Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }else {
                throw new DbException("Erro inexperado nenhuma linha foi afetada");
            }
        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;

        try{
            st = conn.prepareStatement("UPDATE seller "
                            + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
                            + "WHERE id = ? ");

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4,obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            st.setInt(6, obj.getId());

            st.executeUpdate();

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName"
                    +"FROM seller INNER JOIN department "
                    +"ON seller.DepartmentID = department.Id"
                    +"WHERE seller.ID = ?"
            );

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()){
                Department dep = instantiateDepartment(rs);
                Seller sl = instantiateSeller(rs, dep);
                return sl;
            }

            return null;

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentID = department.Id "
                            + "WHERE DepartmentId = ? ORDER BY NAME "
            );

            st.setInt(1, department.getId());
            rs = st.executeQuery();
            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()){

                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("Departement"), dep);
                }

                Seller sl = instantiateSeller(rs, dep);
                list.add(sl);

            }

            return list;

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller sl =new Seller();
        sl.setId(rs.getInt("Id"));
        sl.setName(rs.getString("Name"));
        sl.setEmail(rs.getString("Email"));
        sl.setBaseSalary(rs.getDouble("BaseSalary"));
        sl.setBirthDate(rs.getDate("BirthDate"));
        sl.setDepartment(dep);

        return sl;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentID = department.Id "
                            + "ORDER BY NAME "
            );

            rs = st.executeQuery();
            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()){

                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null){
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("Departement"), dep);
                }

                Seller sl = instantiateSeller(rs, dep);
                list.add(sl);

            }

            return list;

        }catch (SQLException e){
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
