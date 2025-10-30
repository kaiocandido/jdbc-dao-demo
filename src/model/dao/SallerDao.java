package model.dao;

import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public interface SallerDao{
    void insert(Seller obj);
    void update(Seller obg);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findByDepartment(Department department);
    List<Seller> findAll();
}
