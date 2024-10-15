package com.bravos.news.dao;

import com.bravos.news.entity.Category;
import com.bravos.news.utils.DatabaseManager;
import com.bravos.news.utils.XJdbc;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO implements IDataObject<Category,Integer> {

    @Override
    public Category findById(Integer integer) {
        String sql = "SELECT * FROM Category WHERE id = ?";
        List<Category> categories = findBySql(sql,integer);
        return categories.isEmpty() ? null : categories.getFirst();
    }

    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM Category";
        return findBySql(sql);
    }

    @Override
    public Category insert(Category object) {
        String sql = "INSERT INTO Category(name) VALUES(?)";
        BigDecimal id = (BigDecimal) XJdbc.excuteUpdateAndGetKey(sql,object.getName());
        int idNumber = id.intValue();
        object.setId(idNumber);
        return object;
    }

    @Override
    public Category update(Category object) {
        String sql = "UPDATE Category " +
                "SET name = ? " +
                "WHERE id = ?";
        if(XJdbc.excuteUpdate(sql,object.getName(),object.getId()) > 0) return object;
        return null;
    }

    @Override
    public boolean delete(Category object) {
        String sql = "DELETE FROM Category WHERE id = ?";
        return XJdbc.excuteUpdate(sql,object.getId()) > 0;
    }

    @Override
    public List<Category> findBySql(String sql, Object... args) {
        List<Category> categories = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = XJdbc.getResultSet(sql,args);
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getNString("name"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseManager.gI().closeResultSet(rs);
        }
        return categories;
    }

}
